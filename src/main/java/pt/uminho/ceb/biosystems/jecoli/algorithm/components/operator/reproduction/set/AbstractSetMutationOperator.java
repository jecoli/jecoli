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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.AbstractMutationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSetMutationOperator.
 */
public abstract class AbstractSetMutationOperator<G> extends AbstractMutationOperator<ISetRepresentation<G>,ISetRepresentationFactory<G>> 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 912408826632081974L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 1;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 1;
	

	@Override
	public List<ISolution<ISetRepresentation<G>>> apply(List<ISolution<ISetRepresentation<G>>> selectedSolutions,ISetRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator)	
		throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		ISolution<ISetRepresentation<G>> parentSolution = selectedSolutions.get(0);
		ISolution<ISetRepresentation<G>> childSolution = solutionFactory.copySolution(parentSolution);
		
		List<ISolution<ISetRepresentation<G>>> solutionList = mutate(childSolution,solutionFactory,randomNumberGenerator);
		
		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();
		
		return solutionList;
	}
	

	protected List<ISolution<ISetRepresentation<G>>> mutate (ISolution<ISetRepresentation<G>> childSolution,ISetRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomGenerator)
	{
		List<ISolution<ISetRepresentation<G>>> resultList = new ArrayList<ISolution<ISetRepresentation<G>>>(NUMBER_OF_OUTPUT_SOLUTIONS);
		
		ISetRepresentation<G> childGenome = childSolution.getRepresentation();

		mutateGenome(childGenome,solutionFactory,randomGenerator);
		
		resultList.add(childSolution);
		return resultList;
	}
	
	/**
	 * Mutate genome.
	 * 
	 * @param childGenome the child genome
	 * @param randomNumberGenerator TODO
	 */
	protected abstract void mutateGenome(ISetRepresentation<G> childGenome,ISetRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator);

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

	/**
	 * Input solutions have same length.
	 * 
	 * @return true, if successful
	 */
	public boolean inputSolutionsHaveSameLength() {
		return false;
	}

	/**
	 * Produce equal size solution.
	 * 
	 * @return true, if successful
	 */
	public boolean produceEqualSizeSolution() {
		return false;
	}
	
	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.MUTATION;
	}

}
