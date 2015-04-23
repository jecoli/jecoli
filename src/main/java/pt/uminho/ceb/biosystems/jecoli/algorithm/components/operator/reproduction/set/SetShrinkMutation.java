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
 * The Class SetShrinkMutation.
 */
public class SetShrinkMutation<E> extends AbstractSetMutationOperator<E> {


	private static final long serialVersionUID = 870642702424643829L;
	/** The number genes to remove. */
	int numberGenesToRemove = 1;

	
	
	public SetShrinkMutation() {
		this.numberGenesToRemove = 1;
	}
	/**
	 * Instantiates a new sets the shrink mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param numberGenesToRemove the number genes to remove
	 */
	public SetShrinkMutation(int numberGenesToRemove) {
		this.numberGenesToRemove = numberGenesToRemove;
	}

	@Override
	protected void mutateGenome (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
	 	int minSize = solutionFactory.getMinSetSize();
	 	int N = randomNumberGenerator.nextInt(numberGenesToRemove)+1;//Math.min(numberGenesToRemove,numberOfGenes);
	 	
 		for(int k=0; k < N; k++)
 		{
 			if (childGenome.getNumberOfElements() > minSize){
 				E element = childGenome.getRandomElement(randomNumberGenerator);
 				childGenome.removeElement(element);
 			} else return;
 		}
	}

	@Override
	public SetShrinkMutation<E> deepCopy() throws Exception {
		return new SetShrinkMutation<E>(numberGenesToRemove);
	}	
}
