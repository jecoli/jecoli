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
package pt.uminho.ceb.biosystems.jecoli.demos.tsp.eatsp;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.demos.tsp.libtsp.Tsp;



/**
 * The Class TspGreedyCrossover: defines a crossover jecoliunittest.operators.operator specific for the TSP
 */
public class TspGreedyCrossover implements IReproductionOperator<PermutationRepresentation,PermutationRepresentationFactory>
//extends AbstractPermutationCrossoverOperator
{
	
	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 2;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 1;

	/** The problem instance. */
	protected Tsp problemInstance;

	/** The solution factory. */
	//protected PermutationRepresentationFactory solutionFactory;

	
	/**
	 * Instantiates a new tsp greedy crossover.
	 * 
	 * @param solutionFactory the solution factory
	 * @param problemInstance the problem instance
	 */
	public TspGreedyCrossover(Tsp problemInstance){
		//this.solutionFactory  = solutionFactory;
		this.problemInstance = problemInstance;
	}

	
	/**
	 * Crossover genomes.
	 * 
	 * @param parentGenome1 the parent genome1
	 * @param parentGenome2 the parent genome2
	 * @param childGenome the child genome
	 */
	public void crossoverGenomes(PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome) 
	{
		  int[] cand_pool = new int[4]; 
		  int[] np = new int[2];
		  int min, i, candidates, isize = parentGenome1.getNumberOfElements();
		  double fit, minfit;

		  int last = parentGenome1.getElement(0);
		  childGenome.setElement(0, last);
		  for(int visited=1; visited<isize; visited++)
		  {
			candidates = 0; 
			next_and_prev (parentGenome1.getGenomeAsArray(), last, np);
			if(!is_in(childGenome.getGenomeAsArray(), np[0], visited))
				cand_pool[candidates++] = np[0];
			if(!is_in(childGenome.getGenomeAsArray(), np[1], visited))
				cand_pool[candidates++] = np[1];

			next_and_prev (parentGenome2.getGenomeAsArray(), last, np);
			if(!is_in(childGenome.getGenomeAsArray(), np[0], visited))
				cand_pool[candidates++] = np[0];
			if(!is_in(childGenome.getGenomeAsArray(), np[1], visited))
				cand_pool[candidates++] = np[1];

			if(candidates == 0)
			{
		        minfit = Double.MAX_VALUE; 
				min = 0;
				for(i=0; i<isize; i++)
				{
					if(!is_in(childGenome.getGenomeAsArray(), i, visited))
					{
						fit = problemInstance.get_distance(last, i);
						if( fit < minfit)
		         		{
							min = i;
		           			minfit = fit;
		         		}
		        	}
		      	}
			}       
			else
		   	{
		        minfit = problemInstance.get_distance(last, cand_pool[0]);
				min = cand_pool[0];
				for(i=1; i<candidates; i++)
		    	{
		      		fit = problemInstance.get_distance(last, cand_pool[i]); 
		      		if( fit < minfit)
		      		{
						min = cand_pool[i];
		        		minfit = fit;
		      		}
		     	}
		    }
			childGenome.setElement(visited, min);
			last = min;
		  }
	}

	
	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#apply(java.util.List)
	 */
	public List<ISolution<PermutationRepresentation>> apply(List<ISolution<PermutationRepresentation>> selectedSolutions, PermutationRepresentationFactory solutioFactory, IRandomNumberGenerator randomNumberGenerator)	throws InvalidNumberOfInputSolutionsException,
	InvalidNumberOfOutputSolutionsException {

			if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
					throw new InvalidNumberOfInputSolutionsException();

			List<ISolution<PermutationRepresentation>> solutionList = crossover(selectedSolutions,solutioFactory, randomNumberGenerator);

			if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
					throw new InvalidNumberOfOutputSolutionsException();

			return solutionList;
	}	

	/**
	 * Crossover.
	 * 
	 * @param selectedSolutions the selected solutions
	 * 
	 * @return the list< i solution>
	 */
	public List<ISolution<PermutationRepresentation>> crossover(List<ISolution<PermutationRepresentation>> selectedSolutions, PermutationRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator)
	{
		List<ISolution<PermutationRepresentation>> resultList = new ArrayList<ISolution<PermutationRepresentation>>(NUMBER_OF_OUTPUT_SOLUTIONS);

		ISolution<PermutationRepresentation> parentSolution = selectedSolutions.get(0);
		ISolution<PermutationRepresentation> parentSolution1 = selectedSolutions.get(1);

		PermutationRepresentation parentGenome1 = (PermutationRepresentation) parentSolution.getRepresentation();
		PermutationRepresentation parentGenome2 = (PermutationRepresentation) parentSolution1.getRepresentation();

		int isize = parentGenome1.getNumberOfElements();

		ISolution<PermutationRepresentation> child = solutionFactory.generateSolution(isize,randomNumberGenerator);

		PermutationRepresentation childGenome = (PermutationRepresentation) child.getRepresentation();
		
		crossoverGenomes(parentGenome1, parentGenome2, childGenome);

		resultList.add(child);

		return resultList;
	}


	public int getNumberOfInputSolutions(){
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	public int getNumberOfOutputSolutions(){
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}

	/**
	 * Input solutions have same length.
	 * 
	 * @return true, if successful
	 */
	public boolean inputSolutionsHaveSameLength(){
		return false;
	}

	private boolean is_in (int [] array, int elem, int endpos)
	{
 		int i=0;
		boolean found=false;

		if(array!=null) 
		{
			while (i<endpos && !found )
 			{
   				if(array[i]== elem) found = true;
   				else i++;
 			} 
 		}
		return found;
	}

	private void next_and_prev(int [] array, int elem, int [] np)
	// np[0] - next; np[1] - prev.
	{
  		int j =0;
		boolean found = false;

  		while (!found && (j<array.length) )
  		{
    		if(array[j] == elem) found = true;
    		else j++;
 		}
  		if(found) 
		{
			np[1]= array[(j==0)?(array.length-1):(j-1)];
			np[0]= array[(j+1)%array.length];
		}
  		else 
		{
			np[1]= -1;
			np[0]= -1;
		}
	}


	@Override
	public ReproductionOperatorType getReproductionType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IReproductionOperator<PermutationRepresentation, PermutationRepresentationFactory> deepCopy()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
