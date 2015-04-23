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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.AbstractCrossoverOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSetCrossoverOperator.
 */
public abstract class AbstractSetCrossoverOperator<G> extends AbstractCrossoverOperator<ISetRepresentation<G>, ISetRepresentationFactory<G>>

{
	
	private static final long serialVersionUID = 1L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 2;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 2;

	
	@Override
	public List<ISolution<ISetRepresentation<G>>> apply(List<ISolution<ISetRepresentation<G>>> selectedSolutions,ISetRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomGenerator)	throws InvalidNumberOfInputSolutionsException,
			InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		List<ISolution<ISetRepresentation<G>>> solutionList = crossover(selectedSolutions,solutionFactory,randomGenerator);
		
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
	public List<ISolution<ISetRepresentation<G>>> crossover(List<ISolution<ISetRepresentation<G>>> selectedSolutions,ISetRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomGenerator)
	{
	
		ISolution<ISetRepresentation<G>> parentSolution = selectedSolutions.get(0);
		ISolution<ISetRepresentation<G>> parentSolution1 = selectedSolutions.get(1);

		ISetRepresentation<G> parentGenome1 = parentSolution.getRepresentation();
		ISetRepresentation<G> parentGenome2 = parentSolution1.getRepresentation();

		return crossOverGenomes(parentGenome1, parentGenome2, solutionFactory, randomGenerator);
	}
	
	
	public List<ISolution<ISetRepresentation<G>>> crossOverGenomes(ISetRepresentation<G> parentGenome1, ISetRepresentation<G> parentGenome2, ISetRepresentationFactory<G> solutionFactory, IRandomNumberGenerator randomGenerator) {

		List<ISolution<ISetRepresentation<G>>> resultList = new ArrayList<ISolution<ISetRepresentation<G>>>(NUMBER_OF_OUTPUT_SOLUTIONS);
		
		ISolution<ISetRepresentation<G>> child1 = solutionFactory.generateSolution(0,randomGenerator);
		ISolution<ISetRepresentation<G>> child2 = solutionFactory.generateSolution(0,randomGenerator);
		
		ISetRepresentation<G> childGenome1 = child1.getRepresentation();
		ISetRepresentation<G> childGenome2 = child2.getRepresentation();
		
		crossoverGenomes(parentGenome1, parentGenome2, childGenome1, childGenome2,solutionFactory,randomGenerator);
		
		resultList.add(child1);
		resultList.add(child2);
//		
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
	public abstract void crossoverGenomes (ISetRepresentation<G> parentGenome1, ISetRepresentation<G> parentGenome2,
			ISetRepresentation<G> childGenome1, ISetRepresentation<G> childGenome2,ISetRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator);
	
	
	@Override
	public int getNumberOfInputSolutions(){
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	@Override
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

	/**
	 * Produce equal size solution.
	 * 
	 * @return true, if successful
	 */
	public boolean produceEqualSizeSolution(){
		return false;
	}
	
	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.CROSSOVER;
	}

}
