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
 * The Class PermutationEdgeCrossover.
 */
public class PermutationEdgeCrossover extends AbstractPermutationCrossoverOperator {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see operators.reproduction.permutations.AbstractPermutationCrossoverOperator#crossoverGenomes(core.representation.permutations.PermutationRepresentation, core.representation.permutations.PermutationRepresentation, core.representation.permutations.PermutationRepresentation, core.representation.permutations.PermutationRepresentation)
	 */
	public void crossoverGenomes (PermutationRepresentation f, PermutationRepresentation m,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator)
	{		

		int st1, st2;

		st1 = (int) (randomNumberGenerator.nextDouble()*(f.getNumberOfElements()));

		auxEdgeCrossover(f, m, childGenome1, st1,randomNumberGenerator);	
		do
		{
			st2 = (int) (randomNumberGenerator.nextDouble()*(f.getNumberOfElements()));

		} while (st1 == st2);
		auxEdgeCrossover(f, m, childGenome2, st2,randomNumberGenerator);
	
	}
	
	/**
	 * Aux edge crossover.
	 * 
	 * @param f the f
	 * @param m the m
	 * @param childGenome the child genome
	 * @param start the start
	 * @param randomNumberGenerator 
	 */
	public void auxEdgeCrossover (PermutationRepresentation f, PermutationRepresentation m,
			PermutationRepresentation childGenome, int start, IRandomNumberGenerator randomNumberGenerator)
	{
	 	int [][] edge_table= new int [f.getNumberOfElements()][];
		int i, j, k, next, previous, min, prev, cur=0;

	  /* building the edge table */

	      /* initializing */
		for(i=0; i<f.getNumberOfElements(); i++)
		{
			edge_table[i] = new int[5];
			edge_table[i][0] = 0;
		}

	      /* from first parent */

		for(i=0; i< f.getNumberOfElements(); i++)
		{
	   		next =  ( (i+1) % f.getNumberOfElements() );
			previous = ( (i==0)?(f.getNumberOfElements()-1):(i-1) );
	    	edge_table[f.getElement(i)][1] = f.getElement(next);
	    	edge_table[f.getElement(i)][2] = f.getElement(previous);
	    	edge_table[f.getElement(i)][0] = 2;
	  	}

	     /* from second parent */

	  	for(i=0; i<m.getNumberOfElements(); i++)
	  	{
	   		next = ((i+1) % m.getNumberOfElements());
	    	previous = ( (i==0)?(m.getNumberOfElements()-1):(i-1) );

	    	if(!PermutationUtils.is_in(edge_table[m.getElement(i)], m.getElement(next), 1, 3))
	    	{
	     		edge_table[m.getElement(i)][3] = m.getElement(next);
	     		edge_table[m.getElement(i)][0]++;
	    	}
	    	else
	    	{
	      		edge_table[m.getElement(i)][PermutationUtils.pos_in_arr(edge_table[m.getElement(i)], m.getElement(next), 1, 3) ] *= (-1);
	    	}
			if(!PermutationUtils.is_in(edge_table[m.getElement(i)], m.getElement(previous), 1, 1+edge_table[m.getElement(i)][0]))
	    	{
	    		edge_table[m.getElement(i)][edge_table[m.getElement(i)][0]+1] = m.getElement(previous);
	    		edge_table[m.getElement(i)][0] ++;
	    	}
	    	else
	     		edge_table[m.getElement(i)][PermutationUtils.pos_in_arr(edge_table[m.getElement(i)], m.getElement(previous), 1, 1+edge_table[m.getElement(i)][0])] *= (-1);
	  }

	  /* building the new indiv */

		childGenome.setElement(0, start);
		prev = childGenome.getElement(0);

		for(i=1; i<f.getNumberOfElements(); i++)
	  	{
	        /* clean references to previous node */
	    	for(j=0; j< f.getNumberOfElements(); j++)
	    	{
	     		if(edge_table[j][0] != 0)
		 		{
	       			if ( PermutationUtils.is_in(edge_table[j], prev, 1, 1+edge_table[j][0]) )
	       			{
	         			for(k=PermutationUtils.pos_in_arr (edge_table[j], prev, 1, 1+edge_table[j][0]);
	             			k<edge_table[j][0]; k++)
	                			edge_table[j][k] = edge_table[j][k+1];
	         			edge_table[j][0]--;
	       			}
                    if ( PermutationUtils.is_in(edge_table[j], -1*prev, 1, 1+edge_table[j][0]) )
	       			{
	         			for(k=PermutationUtils.pos_in_arr (edge_table[j], -1*prev, 1, 1+edge_table[j][0]);
	             			k<edge_table[j][0]; k++)
	                			edge_table[j][k] = edge_table[j][k+1];
	         			edge_table[j][0]--;
	       			}
                }
	    	}

	 		  /* choose next town */
			if(edge_table[prev][0] == 0)
			{
				do
					cur = (int) (randomNumberGenerator.nextDouble()*(f.getNumberOfElements()));       
				while (PermutationUtils.is_in( childGenome.getGenomeAsArray(), cur, i));
	    	}
			else
	    	{
				min = 5;
	    		for(j=1; j<=edge_table[prev][0]; j++)
	    		{
	     			if( edge_table[prev][j]<0 )
	     				if(edge_table[-1*edge_table[prev][j]][0] < min )
	     				{
	      					min = edge_table[-1*edge_table[prev][j]][0];
	      					cur = -1*edge_table[prev][j];
	     				}
                }

				if (min == 5)
	       			for(j=1; j<=edge_table[prev][0]; j++)
	       			{
	        			if(edge_table[edge_table[prev][j]][0] < min)
	        			{
	          				min = edge_table[edge_table[prev][j]][0];
	         				cur = edge_table[prev][j];
	       				}
		   			}
	    	}
	    	edge_table[prev][0] = 0;
			childGenome.setElement(i, cur);
	    	prev = cur;
	  	}
	
	}

	@Override
	public PermutationEdgeCrossover deepCopy() throws Exception {
		return new PermutationEdgeCrossover();
	}
	
}
