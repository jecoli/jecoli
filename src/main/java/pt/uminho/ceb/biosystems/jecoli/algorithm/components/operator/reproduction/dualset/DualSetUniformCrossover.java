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

import java.util.Iterator;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentationFactory;


/**
 * The Class DualSetUniformCrossover.
 */
public class DualSetUniformCrossover extends AbstractDualSetCrossoverOperator {

	private static final long serialVersionUID = 64885740331915788L;

	public void crossoverGenomes (DualSetRepresentation parentGenome1, DualSetRepresentation parentGenome2,
			DualSetRepresentation childGenome1, DualSetRepresentation childGenome2,DualSetRepresentationFactory solutionFactory ,IRandomNumberGenerator randomNumberGenerator)
	{
		int maxSetSize;
		TreeSet<Integer> pGenome1, pGenome2,cGenome1, cGenome2;
		boolean isKnockout = false;
		// Select the list to make crossover
		if (randomNumberGenerator.nextDouble() <= 0.5 && solutionFactory.getMaxSetSizeKnockouts()>0){
//		if(true){
			maxSetSize = solutionFactory.getMaxSetSizeKnockouts();
			pGenome1 = parentGenome1.getGenomeKnockout();
			pGenome2 = parentGenome2.getGenomeKnockout();
			cGenome1 = childGenome1.getGenomeKnockout();
			cGenome2 = childGenome2.getGenomeKnockout();
			childGenome1.getGenomeAddReactions().addAll(parentGenome1.getGenomeAddReactions());
			childGenome2.getGenomeAddReactions().addAll(parentGenome2.getGenomeAddReactions());
			isKnockout = true;
		}
		else{
			maxSetSize = solutionFactory.getMaxSetSizeAddReactions();
			pGenome1 = parentGenome1.getGenomeAddReactions();
			pGenome2 = parentGenome2.getGenomeAddReactions();
			cGenome1 = childGenome1.getGenomeAddReactions();
			cGenome2 = childGenome2.getGenomeAddReactions();
			childGenome1.getGenomeKnockout().addAll(parentGenome1.getGenomeKnockout());
			childGenome2.getGenomeKnockout().addAll(parentGenome2.getGenomeKnockout());
		}
		
		int minSetSize = solutionFactory.getMinSetSize();
				
		// Compute the intersection of the elements of par1 and par2
		TreeSet<Integer> intersection = new TreeSet<Integer>();
		
		for(Integer parentGene2:pGenome2)
			if(pGenome1.contains(parentGene2))
				intersection.add(parentGene2);
	
		// Add those elements to both offspring
		cGenome1.addAll(intersection);
		cGenome2.addAll(intersection);

		// Take all the elements that are not in common
		TreeSet<Integer> otherElements = new TreeSet<Integer>();

		otherElements.addAll(pGenome1);
		otherElements.addAll(pGenome2);
		otherElements.removeAll(intersection);

		Iterator<Integer> it = otherElements.iterator();

		while (it.hasNext()) {
			double prob = 0.5;
			if(childGenome1.getNumberOfElements(isKnockout) >= maxSetSize || childGenome2.getNumberOfElements(isKnockout) < minSetSize ) 
				prob = 0;
			else if(childGenome2.getNumberOfElements(isKnockout) >= maxSetSize || childGenome1.getNumberOfElements(isKnockout) < minSetSize) 
				prob = 1;
			
			Integer element = it.next();
			if (randomNumberGenerator.nextDouble() <= prob)
			{			
				if (!cGenome1.contains(element)) 
					cGenome1.add(element);
			}
			else
			{				
				if (!cGenome2.contains(element)) 
					cGenome2.add(element);
			}
		}

	}


	@Override
	public DualSetUniformCrossover deepCopy() throws Exception {
		return new DualSetUniformCrossover();
	}
}
