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
 * The Class SetGrowthMutation.
 */
public class DualSetGrowthMutation extends AbstractDualSetMutationOperator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2501862357841781220L;

	/** The MAXTRIES. */
	private static int MAXTRIES = 20;
	
	/** The number genes to add. */
	protected int numberGenesToAdd;

	
	public DualSetGrowthMutation() {
		this.numberGenesToAdd = 1;
	}
	
	/**
	 * Instantiates a new sets the growth mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param numberGenesToAdd the number genes to add
	 */
	public DualSetGrowthMutation(int numberGenesToAdd) {
		this.numberGenesToAdd = numberGenesToAdd;
	}

	@Override
	protected void mutateGenome(DualSetRepresentation childGenome,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int genesToAdd = 1;
	 	if(this.numberGenesToAdd > 1) genesToAdd = this.numberGenesToAdd;
	 	
	 	int N = (int)(randomNumberGenerator.nextDouble()*genesToAdd+1);
	 // Select the list to make mutation
		boolean isKnockout = false;
		int maxSetSize = solutionFactory.getMaxSetSizeAddReactions();
		
		if (randomNumberGenerator.nextDouble() <= 0.5){
			isKnockout = true;
			maxSetSize = solutionFactory.getMaxSetSizeKnockouts();
		}	
	 	for(int k=0; k<N; k++){
	 		if(childGenome.getNumberOfElements(isKnockout) < maxSetSize)
	 			addNewElement(childGenome,solutionFactory,randomNumberGenerator, isKnockout);
	 		else return;
	 	}
	 	
	}

	/**
	 * Adds the new element.
	 * 
	 * @param childGenome the child genome
	 * @param randomNumberGenerator 
	 */
	protected void addNewElement(DualSetRepresentation childGenome,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator, boolean isKnockout){
		Integer newElement;
		int tries = 0;
		int maxElement = solutionFactory.getMaxElementAddReactions();
		if (isKnockout){
			maxElement = solutionFactory.getMaxElementKnockouts();
		}	
		do {
			tries++;
			newElement = (int)(randomNumberGenerator.nextDouble()*(maxElement));
		}while(childGenome.containsElement(newElement,isKnockout) && tries < MAXTRIES);
		
		if (tries < MAXTRIES) 
			childGenome.addElement(newElement, isKnockout);
		
	}

	@Override
	public DualSetGrowthMutation deepCopy() throws Exception {
		return new DualSetGrowthMutation(numberGenesToAdd);
	}
}
