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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.randomsearch;


import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

// TODO: Auto-generated Javadoc
/**
 * The Class RandomSearch.
 */
public class RandomSearch<T extends IRepresentation> extends AbstractAlgorithm<T,RandomSearchConfiguration<T>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 762865726770023602L;
	
	/** The Constant NUMBER_OF_SOLUTIONS. */
	protected static final int NUMBER_OF_SOLUTIONS = 1;
	
	/**
	 * Instantiates a new random search.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 * @throws InvalidEvaluationFunctionInputDataException 
	 */
	public RandomSearch(RandomSearchConfiguration<T> configuration) throws Exception {
		super(configuration);
	}

	@Override
	public ISolutionSet<T> initialize()  {
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		ISolutionFactory<T> solutionFactory = configuration.getSolutionFactory();
		ISolutionSet<T> solutionSet = new SolutionSet<T>(NUMBER_OF_SOLUTIONS) ;
		ISolution<T> solution = solutionFactory.generateSolution(randomGenerator);
		solutionSet.add(solution);
		return solutionSet;
	}

	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet)  {
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		ISolutionSet<T> newSolutionSet = new SolutionSet<T>(NUMBER_OF_SOLUTIONS);
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		boolean isMaximization = evaluationFunction.isMaximization();
		ISolutionFactory<T> solutionFactory = configuration.getSolutionFactory();

		ISolution<T> newSolution = solutionFactory.generateSolution(randomGenerator);
		evaluationFunction.evaluateSingleSolution(newSolution);
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations();
		//ISolution<T> oldSolution = solutionSet.getSolution(0);

		//ISolution<T> finalSolution = calculateBestSolution(isMaximization,oldSolution,newSolution);

		newSolutionSet.add(newSolution);

		return newSolutionSet;
	}

	/**
	 * Calculate best solution.
	 * 
	 * @param isMaximization the is maximization
	 * @param oldSolution the old solution
	 * @param newSolution the new solution
	 * 
	 * @return the i solution
	 */
	protected ISolution<T> calculateBestSolution(boolean isMaximization,ISolution<T> oldSolution, ISolution<T> newSolution) {
		if(isMaximization){
			if(oldSolution.getScalarFitnessValue() >= newSolution.getScalarFitnessValue())
				return oldSolution;
			else
				return newSolution;
		}else if(oldSolution.getScalarFitnessValue() >= newSolution.getScalarFitnessValue())
				return newSolution;

		return oldSolution;
	}

	@Override
	public IAlgorithm<T> deepCopy() throws InvalidConfigurationException, Exception {
		RandomSearchConfiguration<T> randomSearchConfigurationCopy = configuration.deepCopy();
		return new RandomSearch<T>(randomSearchConfigurationCopy);
	}




}