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


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractPermutationMutationOperator.
 */
public abstract class AbstractPermutationMutationOperator implements IReproductionOperator<PermutationRepresentation,PermutationRepresentationFactory> 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4672726995078887852L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 1;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 1;
	

	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#apply(java.util.List)
	 */
	public List<ISolution<PermutationRepresentation>> apply(List<ISolution<PermutationRepresentation>> selectedSolutions,PermutationRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator)	
		throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		ISolution<PermutationRepresentation> parentSolution = selectedSolutions.get(0);
		ISolution<PermutationRepresentation> childSolution = solutionFactory.copySolution(parentSolution);
		
		List<ISolution<PermutationRepresentation>> solutionList = mutate(childSolution,solutionFactory,randomNumberGenerator);
		
		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();
		
		return solutionList;
	}

	/**
	 * Mutate.
	 * @param solutionFactory 
	 * 
	 * @param childSolutionArray the child solution array
	 * 
	 * @return the list< i solution>
	 */
	protected List<ISolution<PermutationRepresentation>> mutate (ISolution<PermutationRepresentation> childSolution, PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		List<ISolution<PermutationRepresentation>> resultList = new ArrayList<ISolution<PermutationRepresentation>>(NUMBER_OF_OUTPUT_SOLUTIONS);
		//ISolution<PermutationRepresentation> childSolution = childSolutionArray[0];
		PermutationRepresentation childGenome = (PermutationRepresentation) childSolution.getRepresentation();

		mutateGenome(childGenome,solutionFactory,randomNumberGenerator);
		
		resultList.add(childSolution);
		return resultList;
	}
	
	/**
	 * Mutate genome.
	 * 
	 * @param childGenome the child genome
	 * @param randomNumberGenerator TODO
	 */
	protected abstract void mutateGenome(PermutationRepresentation childGenome,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator);

	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#getNumberOfInputSolutions()
	 */
	public int getNumberOfInputSolutions() {
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#getNumberOfOutputSolutions()
	 */
	public int getNumberOfOutputSolutions() {
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}
	
	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.CROSSOVER;
	}

}
