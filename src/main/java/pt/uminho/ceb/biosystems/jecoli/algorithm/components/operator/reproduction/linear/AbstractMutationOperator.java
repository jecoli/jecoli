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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractMutationOperator.
 */
public abstract class AbstractMutationOperator<G extends IRepresentation,F extends ISolutionFactory<G>> implements IReproductionOperator<G,F> 
{
	private static final long serialVersionUID = -2505269781105912117L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 1;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 1;
	
	@Override
	public List<ISolution<G>> apply(List<ISolution<G>> selectedSolutions,F solutionFactory,IRandomNumberGenerator randomNumberGenerator)	throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		ISolution<G> parentSolution = selectedSolutions.get(0);
		ISolution<G> childSolution = solutionFactory.copySolution(parentSolution);
		
		List<ISolution<G>> solutionList = mutate(childSolution,solutionFactory,randomNumberGenerator);
//		System.out.println("NUM OBJS PARENT:"+parentSolution.getNumberOfObjectives());
//		System.out.println("NUM OBJS CHILD:"+childSolution.getNumberOfObjectives());
//		System.out.println("NUM OBJS FACT:"+solutionFactory.getNumberOfObjectives());
//		
		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();
		
		return solutionList;
	}

	/**
	 * Mutate.
	 * @param solutionFactory 
	 * @param childSolutionArray the child solution array
	 * 
	 * @return the list< i solution>
	 */
	protected  List<ISolution<G>> mutate (ISolution<G> childSolution,F solutionFactory, IRandomNumberGenerator randomNumberGenerator){
		List<ISolution<G>> resultList = new ArrayList<ISolution<G>>(NUMBER_OF_OUTPUT_SOLUTIONS);
	
		G childGenome = childSolution.getRepresentation();
		
		mutateGenome(childGenome,solutionFactory,randomNumberGenerator);
		
		resultList.add(childSolution);
		return resultList;
	}
	
	/**
	 * Mutate genome.
	 * 
	 * @param childGenome the child genome
	 * @param solutionFactory 
	 * @param randomNumberGenerator TODO
	 */
	protected abstract void mutateGenome (G childGenome, F solutionFactory, IRandomNumberGenerator randomNumberGenerator);

	@Override
	public int getNumberOfInputSolutions() {
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	@Override
	public int getNumberOfOutputSolutions() {
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}
	
	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.MUTATION;
	}
}
