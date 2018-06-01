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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class LinearGenomeRandomMutation.
 */
public class LinearGenomeRandomMutation<G> extends AbstractMutationOperator<ILinearRepresentation<G>,ILinearRepresentationFactory<G>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7430973291434064663L;
	/** The max number genes. */
	double percentageOfGenesToMutate; // maximum number of genes to mutate; actual number generated between 1 and this parameter

	/**
	 * Instantiates a new linear genome random mutation.
	 * 
	 * @param percentageOfGenesToMutate the max number genes
	 */
	public LinearGenomeRandomMutation(double percentageOfGenesToMutate) {
		this.percentageOfGenesToMutate = percentageOfGenesToMutate;
	}

	public LinearGenomeRandomMutation() {
		this.percentageOfGenesToMutate = 0.1;
	}
	
	/* (non-Javadoc)
	 * @see operators.reproduction.linear.AbstractMutationOperator#mutateGenome(core.representation.ILinearRepresentation)
	 */
	@Override
	protected void mutateGenome(ILinearRepresentation<G> childGenome,ILinearRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int numberOfGenes = childGenome.getNumberOfElements();
		
		int numberGenesToMutate = (int) Math.ceil(numberOfGenes*percentageOfGenesToMutate);
	
		for(int i=0; i < numberGenesToMutate; i++)
		{
			int selectedGeneIndex = randomNumberGenerator.nextInt(numberOfGenes);
			mutatePosition(childGenome, selectedGeneIndex,solutionFactory,randomNumberGenerator);
		}
	}
	
	/**
	 * Mutate position.
	 * 
	 * @param childGenome the child genome
	 * @param position the position
	 */
	protected void mutatePosition(ILinearRepresentation<G> childGenome, int position,ILinearRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		G newGeneValue = solutionFactory.generateGeneValue(position,randomNumberGenerator);
		childGenome.setElement(position,newGeneValue);
	}

	@Override
	public LinearGenomeRandomMutation<G> deepCopy() {
		return new LinearGenomeRandomMutation<G>(percentageOfGenesToMutate);
	}

}
