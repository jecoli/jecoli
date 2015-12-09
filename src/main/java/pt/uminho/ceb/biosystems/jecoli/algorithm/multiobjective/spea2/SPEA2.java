/**
 * Copyright 2009,
 * CCTC - Computer Science and Technology Center
 * IBB-CEB - Institute for Biotechnology and Bioengineering - Centre of
 * Biological Engineering
 * University of Minho
 * 
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License
 * along with this code. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
 * University of Minho
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.spea2;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.IOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;

// TODO: Auto-generated Javadoc
/**
 * The Class SPEA2.
 */
public class SPEA2<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractAlgorithm<T, SPEA2Configuration<T, S>> {
	
	private static final long	serialVersionUID	= -8491514562204344881L;
	
	/**
	 * Instantiates a new SPEA2 algorithm.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 * @throws InvalidEvaluationFunctionInputDataException
	 */
	public SPEA2(SPEA2Configuration<T, S> configuration) throws Exception {
		super(configuration);
	}
	
	public ISolutionSet<T> initialize(SPEA2AlgorithmState<T> algorithmState) throws Exception {
		
		// boolean verifyPopulationInitialization =
		configuration.verifyPopulationInitialization();
		ISolutionSet<T> solutionSet = null;
		
		// if(verifyPopulationInitialization)
		solutionSet = generateInitialPopulation();
		
		ISolutionSet<T> archive = new SolutionSet<T>();
		configuration.getEvaluationFunction().evaluate(solutionSet);
		
		if (_tracker != null) {
			for (int i = 0; i < solutionSet.getNumberOfSolutions(); i++) 
				_tracker.keepSolution(0, null, solutionSet.getSolution(i), null);
			
			_tracker.flush();
		}
		algorithmState.setSolutionSet(solutionSet);
		algorithmState.setArchive(archive);
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(solutionSet.getNumberOfSolutions());
		
		configuration.setInitialPopulation(solutionSet);
		return solutionSet;
	}
	
	/**
	 * Generate initial population.
	 * 
	 * @return the i solution set
	 */
	protected ISolutionSet<T> generateInitialPopulation() {
		int populationSize = configuration.getPopulationSize();
		ISolutionFactory<T> factory = configuration.getSolutionFactory();
		IRandomNumberGenerator randomNumberGenerator = configuration.getRandomNumberGenerator();
		ISolutionSet<T> population = factory.generateSolutionSet(populationSize, randomNumberGenerator);
		return population;
	}
	
	@Override
	public IAlgorithmResult<T> run() throws Exception {
		ITerminationCriteria terminationCriteria = configuration.getTerminationCriteria();
		SPEA2AlgorithmState<T> algorithmState = new SPEA2AlgorithmState<T>(this);
		
		algorithmState.initializeState();
		ISolutionSet<T> solutionSet = initialize(algorithmState);
		
		while (!terminationCriteria.verifyAlgorithmTermination(this, algorithmState)) {
			ISolutionSet<T> newSolutionSet = iteration(algorithmState, solutionSet);
			solutionSet = newSolutionSet;
		}
		
		/** set the final solution set to equal the archive */
		if(algorithmState.getArchive()!=null && algorithmState.getArchive().getNumberOfSolutions()>0)
			updateState(algorithmState, algorithmState.getArchive());
		// updateLastState(algorithmState,algorithmState.getArchive());
		
		IAlgorithmResult<T> algorithmResult = algorithmState.getAlgorithmResult();
		
		algorithmState = null;
		
		return algorithmResult;
	}
	
	@Deprecated
	private void updateLastState(SPEA2AlgorithmState<T> algorithmState, ISolutionSet<T> archive) {
		ISolutionContainer<T> container = new SolutionContainer<T>(configuration.getMaximumArchiveSize());
		container.addSpecificSolutions(algorithmState.getSolutionSet(), algorithmState.getCurrentIteration(), true);
		algorithmState.getAlgorithmResult().setSolutionContainer(container);
		
	}
	
	/**
	 * Update archive state.
	 * 
	 * @param archive the archive
	 */
	public void updateArchiveState(SPEA2AlgorithmState<T> algorithmState, ISolutionSet<T> archive) {
		algorithmState.updateArchiveState(archive);
	}
	
	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception {
		
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		ISolutionSet<T> newGeneration = new SolutionSet<T>();
		
		ISolutionSet<T> union = ((SPEA2AlgorithmState<T>) algorithmState).getArchive().union(solutionSet);
		MOUtils.assignSelectionValue(union, true);
		updateState(algorithmState, solutionSet);
		SolutionSet<T> newArchive = this.environmentalSelection(union, configuration.getMaximumArchiveSize(), true);
		updateArchiveState((SPEA2AlgorithmState<T>) algorithmState, newArchive);
		
		RecombinationParameters recombinationParameters = configuration.getRecombinationParameters();
		int offSpringSize = recombinationParameters.getOffspringSize();
		
		serialRecombination(((SPEA2AlgorithmState<T>) algorithmState).getArchive(), newGeneration, true, offSpringSize);
		
		evaluationFunction.evaluate(newGeneration);
		
		if(_tracker!=null)
			_tracker.flush();
		
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(offSpringSize);
		
		return newGeneration;
	}
	
	/**
	 * Serial recombination.
	 * 
	 * @param solutionSet the solution set
	 * @param newGeneration the new generation
	 * @param isMaximization the is maximization
	 * @param offSpringSize the off spring size
	 * 
	 * @throws Exception the exception
	 */
	protected void serialRecombination(ISolutionSet<T> solutionSet, ISolutionSet<T> newGeneration, boolean isMaximization, int offSpringSize) throws Exception {
		
		int currentNumberOfIndividuals = 0;
		List<ISolution<T>> offspring = null;
		
		IOperatorContainer<IReproductionOperator<T, S>> crossoverOperators = configuration.getCrossoverOperatorsContainer();
		IOperatorContainer<IReproductionOperator<T, S>> mutationOperators = configuration.getMutationOperatorsContainer();
		
		ISelectionOperator<T> selectionOperator = configuration.getSelectionOperator();
		
		/** serial application of crossover and mutation to breed offspring */
		while (currentNumberOfIndividuals < offSpringSize) {
			IReproductionOperator<T, S> cxOperator = crossoverOperators.selectOperator();
			
			int numberOfInputIndividuals = cxOperator.getNumberOfInputSolutions();
			
			List<ISolution<T>> parents = selectionOperator.selectSolutions(numberOfInputIndividuals, solutionSet, isMaximization, configuration.getRandomNumberGenerator());
			
			offspring = cxOperator.apply(parents, configuration.getSolutionFactory(), configuration.getRandomNumberGenerator());
			
			for (ISolution<T> sol : offspring) {								
				
				List<ISolution<T>> auxPopulation = new ArrayList<ISolution<T>>();
				auxPopulation.add(sol);
				
				IReproductionOperator<T, S> mutOperator = mutationOperators.selectOperator();
				int numberOfOutputIndividualsFromMutation = mutOperator.getNumberOfOutputSolutions();
				
				List<ISolution<T>> mutAuxPopulation = mutOperator.apply(auxPopulation, configuration.getSolutionFactory(), configuration.getRandomNumberGenerator());
				
				if(_tracker!=null)
					for(ISolution<T> auxsol : mutAuxPopulation){
						String operators = cxOperator.getClass().getSimpleName()+","+mutOperator.getClass().getSimpleName();
						_tracker.keepSolution(algorithmState.getCurrentIteration(), operators, auxsol, parents);
					}
				
				currentNumberOfIndividuals += numberOfOutputIndividualsFromMutation;
				newGeneration.add(mutAuxPopulation);
				
			}
		}
		
	}
	
	/**
	 * Environmental selection.
	 * 
	 * @param solutionSet the solution set
	 * @param size the size
	 * @param isMaximization the is maximization
	 * 
	 * @return the solution set
	 * 
	 * @throws Exception the exception
	 */
	protected SolutionSet<T> environmentalSelection(ISolutionSet<T> solutionSet, int size, boolean isMaximization) throws Exception {
		ISelectionOperator<T> selectionOperator = configuration.getEnvironmentalSelectionOperator();
		List<ISolution<T>> selected = selectionOperator.selectSolutions(size, solutionSet, isMaximization, null);
		
		return new SolutionSet<T>(selected);
	}
	
	@Override
	public ISolutionSet<T> initialize() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IAlgorithm<T> deepCopy() throws Exception {
		return null;
	}
	
}