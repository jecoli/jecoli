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

import java.util.Arrays;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class PermutationSchleuterCrossover.
 */
public class PermutationSchleuterCrossover extends AbstractPermutationCrossoverOperator{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3708072080191079152L;

	public void crossoverGenomes (PermutationRepresentation donor, PermutationRepresentation receiver,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int seg_leng, start;
		int [] temp1 = new int[donor.getNumberOfElements()];
		int [] temp2 = new int[donor.getNumberOfElements()];
		int cityA, cityB, posA, posB;
		int aux, i, j, sel;

		seg_leng = (int) (randomNumberGenerator.nextDouble()*((int)(donor.getNumberOfElements()/3)-1)+3);
		start = (int) (randomNumberGenerator.nextDouble()*(donor.getNumberOfElements()-seg_leng+1));
	  
		cityA = donor.getElement(start);
		cityB = donor.getElement(start+seg_leng-1);
	  
		posA = PermutationUtils.pos_in_arr(receiver.getGenomeAsArray(), cityA);
		posB = PermutationUtils.pos_in_arr(receiver.getGenomeAsArray(), cityB);

		if(posA>posB){
	    	aux = posA;
	    	posA = posB;
	    	posB = aux;
	  	}
		
		temp1 = Arrays.copyOf(receiver.getGenomeAsArray(),receiver.getGenomeAsArray().length);
		temp2 = Arrays.copyOf(receiver.getGenomeAsArray(),receiver.getGenomeAsArray().length);
		
		for(i=posA+1, j=posB;i<=posB; i++, j--){
	    	temp1[i] = receiver.getElement(j);
	    	temp2[i-1] = receiver.getElement(j-1);
	  	}
	 
		for(i=0; i<donor.getNumberOfElements(); i++)
	  	{
			childGenome1.setElement(i, -1);
			childGenome2.setElement(i, -1);
	  	}     
	 
		for(i=0;i<seg_leng;i++){
			childGenome1.setElement((posA+i)%donor.getNumberOfElements(), donor.getElement(start+i) );
	    if((posB+i) >= (seg_leng-1)) aux = posB-seg_leng+1+i;
	    else aux = posB-seg_leng+1+i+donor.getNumberOfElements();
	    childGenome2.setElement(aux, donor.getElement(start+i) );
		}

		sel = (posA+seg_leng)%donor.getNumberOfElements();
		for(i=(posA+2)%donor.getNumberOfElements(); i!=posA; i=(i+1)%donor.getNumberOfElements()){
	    	if(!PermutationUtils.is_in(childGenome1.getGenomeAsArray(), temp1[i] ))
			{
	    		childGenome1.setElement(sel, temp1[i]);
				sel = (sel+1)%donor.getNumberOfElements();
	    	}
		}
	  
		sel = (posB+1)%donor.getNumberOfElements();
		for(i=(posB+1)%donor.getNumberOfElements(); i!=posB-1; i=(i+1)%donor.getNumberOfElements()){
	    	if(!PermutationUtils.is_in(childGenome2.getGenomeAsArray(), temp2[i] ))
			{
	    		childGenome2.setElement(sel, temp2[i]);
				sel = (sel+1)%donor.getNumberOfElements();
	    	}
	  	}

	}

	@Override
	public PermutationSchleuterCrossover deepCopy() throws Exception {
		return new PermutationSchleuterCrossover();
	}
	
}
