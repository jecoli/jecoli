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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulatedAnnealing.
 */
public class SimulatedAnnealing<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractAlgorithm<T, SimulatedAnnealingConfiguration<T, S>> {
	
	private static final long	serialVersionUID	= 8986765002502088453L;
	
	/** The debug. */
	private boolean				debug				= false;
	
	/**
	 * Instantiates a new simulated annealing.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 * @throws InvalidEvaluationFunctionInputDataException
	 */
	public SimulatedAnnealing(SimulatedAnnealingConfiguration<T, S> configuration) throws Exception {
		super(configuration);
	}
	
	public ISolutionSet<T> initialize() throws Exception {
		// algorithmState.initializeState();
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		if (debug) System.out.println("initializing ...");
		
		ISolutionFactory<T> solutionFactory = configuration.getSolutionFactory();
		IAnnealingSchedule annealingSchedule = configuration.getAnnelingSchedule();
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		annealingSchedule.reset();
		// TODO Se tiver pop predifinida utliza este
		ISolutionSet<T> solutionSet = null;
		
		if (configuration.getInitialPopulation() == null)
			solutionSet = solutionFactory.generateSolutionSet(1, randomGenerator);
		else
			solutionSet = configuration.getInitialPopulation();
		
		// SetRepresentation genome =
		// (SetRepresentation)(solutionSet.getSolution(0).getRepresentation());
		// System.out.println("Size = " + genome.getNumberOfElements());
		
		evaluationFunction.evaluate(solutionSet);
		if (_tracker != null) {
			for (int i = 0; i < solutionSet.getNumberOfSolutions(); i++) {
				_tracker.keepSolution(0, null, solutionSet.getSolution(i), null);
			}
			
			_tracker.flush();
		}
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(solutionSet.getNumberOfSolutions());
		
		if (debug) System.out.println("end initializing ...");
		
		if (debug) System.out.println("Tamanho INICIAL: " + solutionSet.getNumberOfSolutions());
		
		return solutionSet;
	}
	
	/**
	 * Creates the trial solution.
	 * 
	 * @param algorithmState
	 * 
	 * @param currentSolution the current solution
	 * 
	 * @return the i solution
	 * 
	 * @throws InvalidNumberOfInputSolutionsException the invalid number of
	 *             input solutions exception
	 * @throws InvalidNumberOfOutputSolutionsException the invalid number of
	 *             output solutions exception
	 */
	public ISolution<T> createTrialSolution(AlgorithmState<T> algorithmState, ISolution<T> currentSolution) throws InvalidNumberOfInputSolutionsException, InvalidNumberOfOutputSolutionsException {
		
		if (debug) System.out.println("creating trial solution ...");
		
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		
		if (debug) System.out.println("selecting mutation jecoliunittest.operators.operator ...");
		
		IReproductionOperator<T, S> reproductionOperator = configuration.selectMutationOperator();
		
		if (debug) System.out.println("done selecting mutation jecoliunittest.operators.operator ...");
		
		List<ISolution<T>> selectedSolutionList = new ArrayList<ISolution<T>>(0);
		selectedSolutionList.add(currentSolution);
		
		if (debug) System.out.println("added current solution; applying rep op ...");
		
		IRandomNumberGenerator randomNumberGenerator = configuration.getRandomNumberGenerator();
		
		S solutionFactory = configuration.getSolutionFactory();
		
		List<ISolution<T>> trialSolutionList = reproductionOperator.apply(selectedSolutionList, solutionFactory, randomNumberGenerator);
		
		if (debug) System.out.println("done applying rep op ...");
		
		ISolution<T> trialSolution = trialSolutionList.get(0);
		
		if (debug) System.out.println("evaluating ...");
		
		evaluationFunction.evaluateSingleSolution(trialSolution);
		
		if (_tracker != null) {
			_tracker.keepSolution(algorithmState.getCurrentIteration(), reproductionOperator.getClass().getSimpleName(), trialSolution, selectedSolutionList);
		}
		
		if (debug) System.out.println("done evaluating ...");
		
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations();
		
		if (debug) System.out.println("end creating trial solution ...");
		
		return trialSolution;
	}
	
	/*
	 * (non-Javadoc)
	 * @see core.AbstractAlgorithm#iteration(core.interfaces.ISolutionSet)
	 */
	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception {
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		
		if (debug) System.out.println("starting iteration ...");
		
		ISolutionSet<T> newSolutionSet = new SolutionSet<T>();
		IAnnealingSchedule annealingSchedule = configuration.getAnnelingSchedule();
		int numberOfAcceptedMoves = 0;
		int numberOfRejectedMoves = 0;
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		boolean isMaximization = evaluationFunction.isMaximization();
		
		ISolution<T> currentSolution = solutionSet.getSolution(0);
		
		while (!annealingSchedule.isEquilibriumState(numberOfAcceptedMoves, numberOfRejectedMoves)) {
			
			if (debug) System.out.println("starting trial cycle ...");
			
			ISolution<T> trialSolution = createTrialSolution(algorithmState, currentSolution);
			double currentSolutionFitnessValue = currentSolution.getScalarFitnessValue();
			double trialSolutionFitnessValue = trialSolution.getScalarFitnessValue();
			
			if (acceptSolution(currentSolutionFitnessValue, trialSolutionFitnessValue)) {
				
				if (debug) System.out.println("accepting solution ...");
				
				currentSolution = trialSolution;
				numberOfAcceptedMoves++;
				
				if (debug) System.out.println("number of accepted: " + numberOfAcceptedMoves);
				
			} else {
				double randomNumber = randomGenerator.nextDouble();
				double acceptSolutionProbability = 0;
				
				if (isMaximization)
					acceptSolutionProbability = annealingSchedule.caculateAcceptSolutionProbability(currentSolutionFitnessValue, trialSolutionFitnessValue);
				else
					acceptSolutionProbability = annealingSchedule.caculateAcceptSolutionProbability(trialSolutionFitnessValue, currentSolutionFitnessValue);
				
				if (randomNumber < acceptSolutionProbability) {
					
					if (debug) System.out.println("accepting worse solution ...");
					
					currentSolution = trialSolution;
					numberOfAcceptedMoves++;
					
					if (debug) System.out.println("number of accepted: " + numberOfAcceptedMoves);
				} else {
					if (debug) System.out.println("rejecting solution ...");
					numberOfRejectedMoves++;
					if (debug) System.out.println("number of rejected: " + numberOfRejectedMoves);
				}
			}
			// annealingSchedule.getFitnessFunctionData(currentSolution.getScalarFitnessValue());
			// // NAO FAZ NADA !!!
		}
		
		if(_tracker!=null)
			_tracker.flush();
		
		if (debug) System.out.println("ended trial cycle ...");
		
		newSolutionSet.add(currentSolution);
		
		if (debug) System.out.println("added current solution; calculating new temp");
		
		double newTemperature = annealingSchedule.calculateNewTemperature();
		if (debug) System.out.println("newTemperature..."+ newTemperature);
		if (debug) System.out.println("ending iteration ...");
		
		return newSolutionSet;
	}
	
	/**
	 * Accept solution.
	 * 
	 * @param currentFitnessValue the current fitness value
	 * @param trialSolutionFitnessValue the trial solution fitness value
	 * 
	 * @return true, if successful
	 */
	public boolean acceptSolution(double currentFitnessValue, double trialSolutionFitnessValue) {
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		boolean isMaximization = evaluationFunction.isMaximization();
		boolean isCurrentFitneesBigger = (currentFitnessValue >= trialSolutionFitnessValue);
		
		if(isMaximization) 
            return (currentFitnessValue <= trialSolutionFitnessValue);

		
		return isCurrentFitneesBigger;
	}
	
	@Override
	public IAlgorithm<T> deepCopy() throws InvalidConfigurationException, Exception {
		SimulatedAnnealingConfiguration<T, S> configurationCopy = configuration.deepCopy();
		return new SimulatedAnnealing<T, S>(configurationCopy);
	}
	
}
