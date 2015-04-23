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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.dualset;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentationFactory;

/**
 * The Class SetShrinkMutation.
 */
public class DualSetShrinkMutation extends AbstractDualSetMutationOperator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 870642702424643829L;
	/** The number genes to remove. */
	int numberGenesToRemove = 1;

	
	
	public DualSetShrinkMutation() {
		this.numberGenesToRemove = 1;
	}
	/**
	 * Instantiates a new sets the shrink mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param numberGenesToRemove the number genes to remove
	 */
	public DualSetShrinkMutation(int numberGenesToRemove) {
		this.numberGenesToRemove = numberGenesToRemove;
	}

	@Override
	protected void mutateGenome (DualSetRepresentation childGenome,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		// Select the list to make mutation
		boolean isKnockout = false;
		if (randomNumberGenerator.nextDouble() <= 0.5){
			isKnockout = true;
		}
		
		int numberOfGenes = childGenome.getNumberOfElements(isKnockout);
		int genesToRemove = 1;
	 	if(this.numberGenesToRemove > 1) genesToRemove = this.numberGenesToRemove;
	 	
	 	int N = (int)(randomNumberGenerator.nextDouble()*genesToRemove+1);
	 	int minSize = solutionFactory.getMinSetSize();
 
	 	genesToRemove = Math.min(N, numberOfGenes-1);
	 	
 		for(int k=0; k<genesToRemove; k++)
 		{
 			if (childGenome.getNumberOfElements(isKnockout) > minSize){
 				Integer element = childGenome.getRandomElement(randomNumberGenerator, isKnockout);
 				childGenome.removeElement(element, isKnockout);
 			} else return;
 		}
	}

	@Override
	public DualSetShrinkMutation deepCopy() throws Exception {
		return new DualSetShrinkMutation(numberGenesToRemove);
	}	
}
