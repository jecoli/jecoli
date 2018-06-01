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

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCrossoverOperator.
 */
public abstract class AbstractCrossoverOperator<G extends IRepresentation, F extends ISolutionFactory<G>> implements IReproductionOperator<G,F>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5887592045178402142L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 2;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 2;
	
	

	@Override
	public List<ISolution<G>> apply(List<ISolution<G>> selectedSolutions,F solutionFactory,IRandomNumberGenerator randomNumberGenerator)	throws InvalidNumberOfInputSolutionsException,
			InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		List<ISolution<G>> solutionList = crossover(selectedSolutions,solutionFactory,randomNumberGenerator);
		
		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();
		
		return solutionList;
	}

	/**
	 * Crossover.
	 * 
	 * @param selectedSolutions the selected solutions
	 * @param solutionFactory 
	 * @param randomNumberGenerator 
	 * 
	 * @return the list solutions
	 */
	protected List<ISolution<G>> crossover(List<ISolution<G>> selectedSolutions, F solutionFactory, IRandomNumberGenerator randomNumberGenerator)
	{
		ISolution<G> parentSolution = selectedSolutions.get(0);
		ISolution<G> parentSolution1 = selectedSolutions.get(1);

		G parentGenome = parentSolution.getRepresentation();
		G parent1Genome = parentSolution1.getRepresentation();
		
						
		List<ISolution<G>> resultList = crossOverGenomes(parentGenome,parent1Genome,solutionFactory,randomNumberGenerator);

		
		return resultList;
	}
	
	/**
	 * Creates the solution.
	 * 
	 * @param genome the genome
	 * 
	 * @return the i solution
	 */
	protected ISolution<G> createSolution(G genome){
		
		return new Solution<G>(genome);
	}

	/**
	 * Cross over genomes.
	 * 
	 * @param parentGenome the parent genome
	 * @param parent1Genome the parent1 genome
	 * @param randomNumberGenerator TODO
	 */
	protected abstract List<ISolution<G>> crossOverGenomes(G parentGenome,G parent1Genome,F solutionFactory,IRandomNumberGenerator randomNumberGenerator);

	@Override
	public int getNumberOfInputSolutions(){
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	@Override
	public int getNumberOfOutputSolutions(){
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}
	
	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.CROSSOVER;
	}

}
