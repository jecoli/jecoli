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

// TODO: Auto-generated Javadoc
/**
 * The Class PermutationPMXCrossover.
 */
public class PermutationPMXCrossover extends AbstractPermutationCrossoverOperator {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7475843874892869824L;

	public void crossoverGenomes (PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{		

		int isize = parentGenome1.getNumberOfElements();

		int pos1 = (int) (randomNumberGenerator.nextDouble()*(isize-2)+1);
		int pos2 = (int) (randomNumberGenerator.nextDouble()*(isize-2)+1);
		if(pos1 > pos2) {
			int aux = pos1;
			pos1 = pos2;
			pos2 = aux;
		}
   
		for(int i = 0; i < isize; i++){
			childGenome1.setElement (i, parentGenome1.getElement(i) );
			childGenome2.setElement (i, parentGenome2.getElement(i) );
		}
  
		for(int i = pos1;i <= pos2;i++) {
     
			int aux = PermutationUtils.pos_in_arr(childGenome1.getGenomeAsArray(), parentGenome1.getElement(i));
			int aux2 = PermutationUtils.pos_in_arr(childGenome1.getGenomeAsArray(), parentGenome2.getElement(i));
			childGenome1.setElement (aux2, parentGenome1.getElement(i));
			childGenome1.setElement (aux, parentGenome2.getElement(i));
     
			aux = PermutationUtils.pos_in_arr(childGenome2.getGenomeAsArray(), parentGenome2.getElement(i));
			aux2 = PermutationUtils.pos_in_arr(childGenome2.getGenomeAsArray(), parentGenome1.getElement(i));
			childGenome2.setElement (aux2, parentGenome2.getElement(i));
			childGenome2.setElement (aux, parentGenome1.getElement(i));
		}
	}

	@Override
	public PermutationPMXCrossover deepCopy() throws Exception {
		return new PermutationPMXCrossover();
	}
}
