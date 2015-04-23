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
 * The Class AbstractPermutationCrossoverOperator.
 */
public abstract class AbstractPermutationCrossoverOperator implements IReproductionOperator<PermutationRepresentation,PermutationRepresentationFactory>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4050993227594834634L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 2;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 2;

	
	public List<ISolution<PermutationRepresentation>> apply(List<ISolution<PermutationRepresentation>> selectedSolutions,PermutationRepresentationFactory solutioFactory,IRandomNumberGenerator randomNumberGenerator)	throws InvalidNumberOfInputSolutionsException,
			InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		List<ISolution<PermutationRepresentation>> solutionList = crossover(selectedSolutions,solutioFactory,randomNumberGenerator);
		
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
	public List<ISolution<PermutationRepresentation>> crossover(List<ISolution<PermutationRepresentation>> selectedSolutions,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomGenerator)
	{

		List<ISolution<PermutationRepresentation>> resultList = new ArrayList<ISolution<PermutationRepresentation>>(NUMBER_OF_OUTPUT_SOLUTIONS);
	
		ISolution<PermutationRepresentation> parentSolution = selectedSolutions.get(0);
		ISolution<PermutationRepresentation> parentSolution1 = selectedSolutions.get(1);

		PermutationRepresentation parentGenome1 = (PermutationRepresentation) parentSolution.getRepresentation();
		PermutationRepresentation parentGenome2 = (PermutationRepresentation) parentSolution1.getRepresentation();

		int isize = parentGenome1.getNumberOfElements();
	
		ISolution<PermutationRepresentation> child1 = solutionFactory.generateSolution(isize,randomGenerator);
		ISolution<PermutationRepresentation> child2 = solutionFactory.generateSolution(isize,randomGenerator);
	
		PermutationRepresentation childGenome1 = (PermutationRepresentation) child1.getRepresentation();
		PermutationRepresentation childGenome2 = (PermutationRepresentation) child2.getRepresentation();

		crossoverGenomes(parentGenome1, parentGenome2, childGenome1, childGenome2,solutionFactory,randomGenerator);
		
		resultList.add(child1);
		resultList.add(child2);
	
		return resultList;
	}
	
	/**
	 * Crossover genomes.
	 * 
	 * @param parentGenome1 the parent genome1
	 * @param parentGenome2 the parent genome2
	 * @param childGenome1 the child genome1
	 * @param childGenome2 the child genome2
	 * @param randomNumberGenerator TODO
	 */
	public abstract void crossoverGenomes (PermutationRepresentation parentGenome1, PermutationRepresentation parentGenome2,
			PermutationRepresentation childGenome1, PermutationRepresentation childGenome2,PermutationRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator);
	
	public int getNumberOfInputSolutions(){
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	public int getNumberOfOutputSolutions(){
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}

	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.CROSSOVER;
	}
}
