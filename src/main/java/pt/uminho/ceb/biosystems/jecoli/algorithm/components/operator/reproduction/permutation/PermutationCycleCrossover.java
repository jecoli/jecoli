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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.permutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;

/**
 * The Class PermutationCycleCrossover.
 */
public class PermutationCycleCrossover extends AbstractPermutationCrossoverOperator {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4220587011096189438L;


	public void crossoverGenomes (PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator)
	{		

		int isize = parentGenome1.getNumberOfElements();
		int [] colector = new int[isize];
	  
		int aux = parentGenome1.getElement(0);
		boolean reached_cycle = false;
		int already_in = 0, indice = 0;

		while((!reached_cycle) && (already_in < isize)) {
			childGenome1.setElement(indice, parentGenome1.getElement(indice) );
			childGenome2.setElement(indice, parentGenome2.getElement(indice) );
	    	colector[already_in] = indice;
	    	already_in++;
	    	if(parentGenome2.getElement(indice) == aux) reached_cycle = true;
	    	else
				indice = PermutationUtils.pos_in_arr(parentGenome1.getGenomeAsArray(), parentGenome2.getElement(indice));
		
	  	}
		
		if(already_in != isize){
	    	for(int i = 0; i< isize; i++)
	       		if(!PermutationUtils.is_in(colector, i, already_in))
				{
	       			childGenome1.setElement(i, parentGenome2.getElement(i) );
	       			childGenome2.setElement(i, parentGenome1.getElement(i) );
	       		}
	  	}

	}


	@Override
	public PermutationCycleCrossover deepCopy() throws Exception {
		return new PermutationCycleCrossover();
	}
}
