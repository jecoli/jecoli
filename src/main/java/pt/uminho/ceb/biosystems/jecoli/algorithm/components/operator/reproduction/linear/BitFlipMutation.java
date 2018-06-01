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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;

// TODO: Auto-generated Javadoc
/**
 * The Class BitFlipMutation.
 */
public class BitFlipMutation extends AbstractMutationOperator<ILinearRepresentation<Boolean>,BinaryRepresentationFactory> {
	
	private static final long serialVersionUID = 5086841564553271701L;

	/** The max number genes. */
	int maxNumberGenes = 1; // maximum number of genes to mutate; actual number generated between 1 and this parameter

	/** The position probability. */
	double positionProbability = 0.0; // probability to apply mutation at each position; use if larger then 0 (if zero do not use)
	
	
	
	/**
	 * Instantiates a new bit flip mutation.
	 * 
	 * @param maxNumberGenes the max number genes
	 */
	public BitFlipMutation(int maxNumberGenes){
		this.maxNumberGenes = maxNumberGenes;
	}

	/**
	 * Instantiates a new bit flip mutation.
	 * 
	 * @param positionProbability the position probability
	 */
	public BitFlipMutation(double positionProbability) {
		this.positionProbability = positionProbability;
	}

	public BitFlipMutation(BitFlipMutation bitFlipMutation) throws Exception {
		positionProbability = bitFlipMutation.positionProbability;
		this.maxNumberGenes = bitFlipMutation.maxNumberGenes;
	}

	@Override
	protected void mutateGenome(ILinearRepresentation<Boolean> childGenome,BinaryRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int numberOfGenes = childGenome.getNumberOfElements();
		
		// check if positional probability will be sued
		if (positionProbability > 0.0)
		{
			for(int i=0; i < childGenome.getNumberOfElements(); i++)
			{
				double v = randomNumberGenerator.nextDouble();
				if (v < positionProbability) mutatePosition(childGenome, i);
			}			
		}
		else
		{
			int numberGenesToMutate = 1;
			if (maxNumberGenes > 1) 
				numberGenesToMutate = ((int) (randomNumberGenerator.nextDouble()*(maxNumberGenes)))+1;
		
			for(int i=0; i < numberGenesToMutate; i++)
			{
				int selectedGeneIndex = (int) (randomNumberGenerator.nextDouble()*numberOfGenes);
				mutatePosition(childGenome, selectedGeneIndex);
			}
		}
	}

	/**
	 * Mutate position.
	 * 
	 * @param childGenome the child genome
	 * @param position the position
	 */
	private void mutatePosition(ILinearRepresentation<Boolean> childGenome, int position)
	{
		boolean geneValue = childGenome.getElementAt(position);
		boolean newGeneValue = !geneValue;
		childGenome.setElement(position,newGeneValue);		
	}

	@Override
	public BitFlipMutation deepCopy() throws Exception {
		return new BitFlipMutation(this);
	}
}
	
