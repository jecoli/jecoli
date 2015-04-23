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
 * The Class PermutationMPXCrossover.
 */
public class PermutationMPXCrossover extends AbstractPermutationCrossoverOperator {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 4450436671013056045L;

	/* (non-Javadoc)
	 * @see operators.reproduction.permutations.AbstractPermutationCrossoverOperator#crossoverGenomes(core.representation.permutations.PermutationRepresentation, core.representation.permutations.PermutationRepresentation, core.representation.permutations.PermutationRepresentation, core.representation.permutations.PermutationRepresentation)
	 */
	public void crossoverGenomes (PermutationRepresentation donor, PermutationRepresentation receiver,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int up = donor.getNumberOfElements()-1;
		int low = 3;
		
		int i,j;
		int inipos = (int) (randomNumberGenerator.nextDouble()*(donor.getNumberOfElements()-1));
		int leng = (int) (randomNumberGenerator.nextDouble()*(up-low+1)+low);
		int cur, next;

		for(i=inipos, j=0; j<leng; i=(i+1)%donor.getNumberOfElements(),j++)
			childGenome1.setElement(j, donor.getElement(i) );

		j = leng;

		while( j < donor.getNumberOfElements()){
			cur = childGenome1.getElement(j-1);
	    	next = PermutationUtils.next_elem(receiver.getGenomeAsArray(), cur);
	    	if(PermutationUtils.is_in(childGenome1.getGenomeAsArray(), next, j))
			{
				next = PermutationUtils.prev_elem(receiver.getGenomeAsArray(), cur);
				if(PermutationUtils.is_in(childGenome1.getGenomeAsArray(), next, j))
				{
					next = PermutationUtils.next_elem(donor.getGenomeAsArray(), cur);
					if(PermutationUtils.is_in(childGenome1.getGenomeAsArray(), next, j))
					{
						next = PermutationUtils.prev_elem(donor.getGenomeAsArray(), cur);
		  				if(PermutationUtils.is_in(childGenome1.getGenomeAsArray(), next, j))
						{
		    				i = PermutationUtils.pos_in_arr(receiver.getGenomeAsArray(), cur);
							while(PermutationUtils.is_in(childGenome1.getGenomeAsArray(), next, j)) 
							{
		      					i=(i+1)%receiver.getNumberOfElements();
		      					next = receiver.getElement((i+1)%receiver.getNumberOfElements());
		    				}
		  				}
					}
	      		}
	    	}
	    	childGenome1.setElement(j, next); 
	    	j++;
	  	}
	  
		for(i=inipos, j=0; j<leng;i=(i+1)%donor.getNumberOfElements(),j++)
			childGenome2.setElement(j, receiver.getElement(i));
		
		j = leng;

		while( j < donor.getNumberOfElements()){
			cur = childGenome2.getElement(j-1);
	    	next = PermutationUtils.next_elem(donor.getGenomeAsArray(), cur);
	    	if(PermutationUtils.is_in(childGenome2.getGenomeAsArray(), next, j))
			{
				next = PermutationUtils.prev_elem(donor.getGenomeAsArray(), cur);
				if(PermutationUtils.is_in(childGenome2.getGenomeAsArray(), next, j))
				{
					next = PermutationUtils.next_elem(receiver.getGenomeAsArray(), cur);
					if(PermutationUtils.is_in(childGenome2.getGenomeAsArray(), next, j))
					{
						next = PermutationUtils.prev_elem(receiver.getGenomeAsArray(), cur);
		  				if(PermutationUtils.is_in(childGenome2.getGenomeAsArray(), next, j))
						{
		    				i = PermutationUtils.pos_in_arr(donor.getGenomeAsArray(), cur);
							while(PermutationUtils.is_in(childGenome2.getGenomeAsArray(), next, j)) 
							{
		      					i=(i+1)%donor.getNumberOfElements();
		      					next = donor.getElement((i+1)%donor.getNumberOfElements());
		    				}
		  				}
					}
	      		}
	    	}
	    	childGenome2.setElement(j, next); 
	    	j++;
	  	}


	}

	@Override
	public PermutationPMXCrossover deepCopy() throws Exception {
		return new PermutationPMXCrossover();
	}

}
