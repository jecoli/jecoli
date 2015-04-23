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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;

/**
 * The Class SetGrowthMutation.
 */
public class SetGrowthMutation<E> extends AbstractSetMutationOperator<E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2501862357841781220L;

	/** The MAXTRIES. */
	private static int MAXTRIES = 20;
	
	/** The number genes to add. */
	protected int numberGenesToAdd;

	
	public SetGrowthMutation() {
		this.numberGenesToAdd = 1;
	}
	
	/**
	 * Instantiates a new sets the growth mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param numberGenesToAdd the number genes to add
	 */
	public SetGrowthMutation(int numberGenesToAdd) {
		this.numberGenesToAdd = numberGenesToAdd;
	}

	@Override
	protected void mutateGenome(ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
	 	int N = randomNumberGenerator.nextInt(numberGenesToAdd)+1;

	 	int maxSetSize = solutionFactory.getMaxSetSize();
	 	for(int k=0; k < N; k++){
	 		if(childGenome.getNumberOfElements() < maxSetSize)
	 			addNewElement(childGenome,solutionFactory,randomNumberGenerator);
	 		else return;
	 	}
	}

	/**
	 * Adds the new element.
	 * 
	 * @param childGenome the child genome
	 * @param randomNumberGenerator 
	 */
	protected void addNewElement(ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator){
		E newElement;
		int tries = 0;
		do {
			tries++;
			newElement = solutionFactory.generateGeneValue(randomNumberGenerator);
		}while(childGenome.containsElement(newElement) && tries < MAXTRIES);
		
		if (tries < MAXTRIES) 
			childGenome.addElement(newElement);
		
	}

	@Override
	public SetGrowthMutation<E> deepCopy() throws Exception {
		return new SetGrowthMutation<E>(numberGenesToAdd);
	}
}
