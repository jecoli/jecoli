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
 * The Class PermutationOnePtCrossover.
 */
public class PermutationOnePtCrossover extends AbstractPermutationCrossoverOperator {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9085539242217373415L;

	public void crossoverGenomes (PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{		
		int isize = parentGenome1.getNumberOfElements();
		
		int pos = (int) (randomNumberGenerator.nextDouble()*(isize-2)+1);

		for(int i=0;i<pos;i++) {
			childGenome1.setElement(i, parentGenome1.getElement(i) );
			childGenome2.setElement(i, parentGenome2.getElement(i) );
		}
		
		int pos1=pos;
		int pos2=pos;

		for(int j=0; j<isize; j++) 
		{
		  if( (pos1 < isize) && ( !PermutationUtils.is_in (childGenome1.getGenomeAsArray(), parentGenome2.getElement(j), pos1))) 
		  {
		      childGenome1.setElement(pos1, parentGenome2.getElement(j) );
		      pos1++;
		  }
		  if((pos2 < isize) && ( !PermutationUtils.is_in(childGenome2.getGenomeAsArray(), parentGenome1.getElement(j), pos2))) 
		  {
		      childGenome2.setElement(pos2, parentGenome1.getElement(j) );
		      pos2++;
		  }
		  
		}
		
	}

	@Override
	public PermutationOnePtCrossover deepCopy() throws Exception {
		return new PermutationOnePtCrossover();
	}
	
}
