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


// TODO: Auto-generated Javadoc
/**
 * The Class SetRandomMutation.
 */
public class SetRandomMutation<E> extends AbstractSetMutationOperator<E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9052759557582323863L;

	/** The number genes to change. */
	protected int numberGenesToChange = 1;
	
	/** The MAXTRIES. */
	private static int MAXTRIES = 20;
	
	
	public SetRandomMutation(){
		this.numberGenesToChange = 1;
	}
	
	public SetRandomMutation(int numberGenesToChange){
		this.numberGenesToChange = numberGenesToChange;
	}
	

	/* (non-Javadoc)
	 * @see operators.reproduction.sets.AbstractSetMutationOperator#mutateGenome(core.representation.sets.SetRepresentation)
	 */
	@Override
	protected void mutateGenome (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
	 	int N = randomNumberGenerator.nextInt(numberGenesToChange)+1;
	 	for(int k=0; k < N; k++){
	 		changeElement(childGenome,solutionFactory,randomNumberGenerator);
	 	}
	}
	
	
	protected void changeElement (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int tries = 0;
		if(childGenome.getNumberOfElements() >0)
		{
			E element = childGenome.getRandomElement(randomNumberGenerator);
			childGenome.removeElement(element);
			E newElement;
//			int maxElement = solutionFactory.getMaxElement();
			do {
				tries++;
				newElement = solutionFactory.generateGeneValue(randomNumberGenerator);
			} while(childGenome.containsElement(newElement) && tries < MAXTRIES);
			if (tries < MAXTRIES) childGenome.addElement(newElement);
			else childGenome.addElement(element);
		}
	}

	@Override
	public SetRandomMutation<E> deepCopy() throws Exception {
		return new SetRandomMutation<E>(numberGenesToChange);
	}
}
