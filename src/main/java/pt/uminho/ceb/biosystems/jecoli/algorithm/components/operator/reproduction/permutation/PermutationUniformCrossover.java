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
 * The Class PermutationUniformCrossover.
 */
public class PermutationUniformCrossover extends AbstractPermutationCrossoverOperator{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5270305623255158944L;

	public void crossoverGenomes (PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{		

		int isize = parentGenome1.getNumberOfElements();
		
		boolean [] mask = createBooleanMask(isize); 

		for(int i = 0;i < isize; i++)
		{
			if(mask[i])
			{
				childGenome1.setElement (i, parentGenome1.getElement(i) );
				childGenome2.setElement (i, parentGenome2.getElement(i) );
			}
			else
	      	{
				childGenome1.setElement (i, -1);
				childGenome2.setElement (i, -1);
			}
		}
	  
		int pos1 = 0, pos2 = 0;
		for(int j = 0; j < isize; j++)
		{
			if(!PermutationUtils.is_in( childGenome1.getGenomeAsArray(), parentGenome2.getElement(j) ))
			{
				while( childGenome1.getElement(pos1) != -1) pos1++;
				childGenome1.setElement(pos1, parentGenome2.getElement(j)); 
			}

			if(!PermutationUtils.is_in( childGenome2.getGenomeAsArray(), parentGenome1.getElement(j) ))
			{
				while( childGenome2.getElement(pos2) != -1) pos2++;
				childGenome2.setElement(pos2, parentGenome1.getElement(j)); 
			}
	    }
	}


	 /**
 	 * Creates the boolean mask.
 	 * 
 	 * @param size the size
 	 * 
 	 * @return the boolean[]
 	 */
 	private boolean [] createBooleanMask(int size)
	 {
		boolean [] res = new boolean[size];

		for(int i=0;i<size;i++)
			if(Math.random() < 0.5) res[i] = false;
			else res[i]= true; 
		
		return(res);
	 }

	@Override
	public PermutationUniformCrossover deepCopy() throws Exception {
		return new PermutationUniformCrossover();
	}
	
}
