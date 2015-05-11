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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;


/**
 * The Class SetUniformCrossover.
 */
public class SetUniformCrossover<E> extends AbstractSetCrossoverOperator<E> {

	private static final long serialVersionUID = 64885740331915788L;

	@Override
	public void crossoverGenomes(ISetRepresentation<E> parentGenome1,
			ISetRepresentation<E> parentGenome2,
			ISetRepresentation<E> childGenome1,
			ISetRepresentation<E> childGenome2,
			ISetRepresentationFactory<E> solutionFactory,
			IRandomNumberGenerator randomNumberGenerator) {
	
		int maxSetSize = solutionFactory.getMaxSetSize();
		int minSetSize = solutionFactory.getMinSetSize();
		
		
		// Compute the intersection of the elements of par1 and par2
		TreeSet<E> intersection = new TreeSet<E>();
		
		for(E parentGene2:parentGenome2.getGenome())
			if(parentGenome1.getGenome().contains(parentGene2))
				intersection.add(parentGene2);
	
		// Add those elements to both offspring
		childGenome1.getGenome().addAll(intersection);
		childGenome2.getGenome().addAll(intersection);

		// Take all the elements that are not in common
		List<E> otherElements = new ArrayList<E>();

		otherElements.addAll(parentGenome1.getGenome());
		otherElements.addAll(parentGenome2.getGenome());
		otherElements.removeAll(intersection);

		//Iterator<E> it = otherElements.iterator();

		while (otherElements.size() > 0) {
			double prob = 0.5;
            int currentPosition = randomNumberGenerator.nextInt(otherElements.size());

			//Bias
			if(childGenome1.getNumberOfElements() >= maxSetSize || childGenome2.getNumberOfElements() < minSetSize ) 
				prob = 0;
            //Bias
			else if(childGenome2.getNumberOfElements() >= maxSetSize || childGenome1.getNumberOfElements() < minSetSize) 
				prob = 1;
			
			E element = otherElements.get(currentPosition);
			if (randomNumberGenerator.nextDouble() <= prob)
			{
			
				if (!childGenome1.containsElement(element))
					childGenome1.addElement(element);
			}
			else
			{
				
				if (!childGenome2.containsElement(element)) 
					childGenome2.addElement(element);
			}
            otherElements.remove(currentPosition);
		}

	}

	@Override
	public SetUniformCrossover<E> deepCopy() throws Exception {
		return new SetUniformCrossover<E>();
	}	
}
