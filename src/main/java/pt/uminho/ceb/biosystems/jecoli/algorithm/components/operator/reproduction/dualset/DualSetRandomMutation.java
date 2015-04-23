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


// TODO: Auto-generated Javadoc
/**
 * The Class SetRandomMutation.
 */
public class DualSetRandomMutation extends AbstractDualSetMutationOperator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9052759557582323863L;

	/** The number genes to change. */
	protected int numberGenesToChange = 1;
	
	/** The MAXTRIES. */
	private static int MAXTRIES = 20;
	
	
	public DualSetRandomMutation(){
		this.numberGenesToChange = 1;
	}
	
	public DualSetRandomMutation(int numberGenesToChange){
		this.numberGenesToChange = numberGenesToChange;
	}
	

	/* (non-Javadoc)
	 * @see operators.reproduction.sets.AbstractSetMutationOperator#mutateGenome(core.representation.sets.SetRepresentation)
	 */
	@Override
	protected void mutateGenome (DualSetRepresentation childGenome,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int genesToChange = 1;
	 	if(this.numberGenesToChange > 1) genesToChange = this.numberGenesToChange;
	 	
	 	int N = (int)(randomNumberGenerator.nextDouble()*genesToChange+1);
	 
	 	for(int k=0; k<N; k++)
	 	{
	 		changeElement(childGenome,solutionFactory,randomNumberGenerator);
	 	}
	 	
	}
	
	
	protected void changeElement (DualSetRepresentation childGenome,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int tries = 0;
		// Select the list to make mutation
		boolean isKnockout = false;
		int maxElement = solutionFactory.getMaxElementAddReactions();
		if (randomNumberGenerator.nextDouble() <= 0.5 && solutionFactory.getMaxSetSizeKnockouts()>0){
			isKnockout = true;
			maxElement = solutionFactory.getMaxElementKnockouts();
		}		
		if(childGenome.getNumberOfElements(isKnockout)>0)
		{	
			Integer element = childGenome.getRandomElement(randomNumberGenerator, isKnockout);
			childGenome.removeElement(element, isKnockout);
			Integer newElement;
			do {
				tries++;
				newElement = (int) (randomNumberGenerator.nextDouble()*(maxElement));
			} while(childGenome.containsElement(newElement, isKnockout) && tries < MAXTRIES);
			if (tries < MAXTRIES) childGenome.addElement(newElement, isKnockout);
			else childGenome.addElement(element, isKnockout);
		}
		
		
		
		
	}

	@Override
	public DualSetRandomMutation deepCopy() throws Exception {
		return new DualSetRandomMutation(numberGenesToChange);
	}
}
