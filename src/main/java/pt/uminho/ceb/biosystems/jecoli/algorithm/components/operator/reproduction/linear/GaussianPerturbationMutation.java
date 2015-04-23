/**
* Copyright 2009,
* CCTC - Computer Science and Technology Center
* IBB-CEB - Institute for Biotechnology and  Bioengineering - Centre of Biological Engineering
* University of Minho
*
* This is free software: you can redistribute it and/or modify
* it under the terms of the GNU Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Public License for more details.
*
* You should have received a copy of the GNU Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
* 
* Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
* University of Minho
*/
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class GaussianPerturbationMutation.
 */
public class GaussianPerturbationMutation extends AbstractMutationOperator<ILinearRepresentation<Double>,RealValueRepresentationFactory> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7510708751080624451L;

	/** The max number genes. */
	int maxNumberGenes = 1; // maximum number of genes to mutate; actual number generated between 1 and this parameter

	/** The position probability. */
	double positionProbability = 0.0; // probability to apply mutation at each position; use if larger then 0 (if zero do not use)

	/** The D. */
	double D = 4.0; // change this !!!
	
	/**
	 * Instantiates a new gaussian perturbation mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param maxNumberGenes the max number genes
	 */
	public GaussianPerturbationMutation(int maxNumberGenes){
		this.maxNumberGenes = maxNumberGenes;
	}
	
	/**
	 * Instantiates a new gaussian perturbation mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param positionProbability the position probability
	 */
	public GaussianPerturbationMutation(double positionProbability) {
		this.positionProbability = positionProbability;
	}

		
	public GaussianPerturbationMutation(double positionProbability,int maxNumberGenes){
		this.positionProbability = positionProbability;
		this.maxNumberGenes = maxNumberGenes;
	}

	@Override
	protected void mutateGenome(ILinearRepresentation<Double> childGenome,RealValueRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int numberOfGenes = childGenome.getNumberOfElements()-1;
	
		// check if positional probability will be used
		if (this.positionProbability > 0.0)
		{
			for(int i=0; i < childGenome.getNumberOfElements(); i++)
			{
				double v = randomNumberGenerator.nextDouble();
				if (v < this.positionProbability) mutatePosition(childGenome, i,solutionFactory,randomNumberGenerator);
			}			
		}
		else
		{
			int numberGenesToMutate = 1;
			if (this.maxNumberGenes > 1) 
				numberGenesToMutate = (int) (randomNumberGenerator.nextDouble()*((maxNumberGenes)+1));
		
			for(int i=0; i < numberGenesToMutate; i++)
			{
				int selectedGeneIndex = (int) (randomNumberGenerator.nextDouble()*numberOfGenes);
				mutatePosition(childGenome, selectedGeneIndex,solutionFactory,randomNumberGenerator);
			}
		}
		
	}
	
	/**
	 * Mutate position.
	 * 
	 * @param childGenome the child genome
	 * @param position the position
	 */
	protected void mutatePosition(ILinearRepresentation<Double> childGenome, int position,RealValueRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		double pert, ul, ll, sd;
		
		do { //do while value is not valid
			ul = solutionFactory.getGeneUpperBound(position);
			ll = solutionFactory.getGeneLowerBound(position);
			sd = (ul-ll)/D;
			pert = childGenome.getElementAt(position) + normalDistribution(0.0, sd,randomNumberGenerator);
		} while (pert < ll || pert > ul);

		childGenome.setElement(position, pert);
	}
	
	  protected double normalDistribution(double mean, double stdDev,IRandomNumberGenerator randomNumberGenerator){
		  return randomNumberGenerator.nextGaussian()*stdDev+mean;
	  }
	
	@Override
	public GaussianPerturbationMutation deepCopy() {
		return new GaussianPerturbationMutation(positionProbability,maxNumberGenes);
	}
}
