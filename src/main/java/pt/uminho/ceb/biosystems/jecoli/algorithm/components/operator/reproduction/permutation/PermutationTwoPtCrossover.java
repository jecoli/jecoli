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
 * The Class PermutationTwoPtCrossover.
 */
public class PermutationTwoPtCrossover extends AbstractPermutationCrossoverOperator{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3733701493292312619L;


	public void crossoverGenomes (PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{		

		int isize = parentGenome1.getNumberOfElements();
		
		int pos = (int) (randomNumberGenerator.nextDouble()*(isize-2)+1);
		int pos1 = (int) (randomNumberGenerator.nextDouble()*(isize-2)+1);

		if(pos > pos1){   
			int aux = pos;
			pos = pos1;//Swap
			pos1 = aux;      
		}

		for(int i=pos;i<=pos1;i++) {
			childGenome1.setElement(i, parentGenome1.getElement(i) );
			childGenome2.setElement(i, parentGenome2.getElement(i) );
		}

		int poss1=0, poss2=0;

		for(int j=0;j < isize; j++)
		{
		  if(	(poss1 < isize) && 
				(!PermutationUtils.is_in(childGenome1.getGenomeAsArray(), parentGenome2.getElement(j), pos, pos1+1))) 
		  {
		  	childGenome1.setElement( poss1, parentGenome2.getElement(j));
		    if(poss1==pos-1) poss1=pos1+1;
		    else poss1++;
		  }
		  if(	(poss2 < isize) && 
				(!PermutationUtils.is_in(childGenome2.getGenomeAsArray(), parentGenome1.getElement(j), pos, pos1+1))) 
		  {
		  	childGenome2.setElement( poss2, parentGenome1.getElement(j));
		    if(poss2==pos-1) poss2=pos1+1;
		    else poss2++;
		  }
		}				
	}


	@Override
	public PermutationTwoPtCrossover deepCopy() throws Exception {
		return new PermutationTwoPtCrossover();
	}

}
