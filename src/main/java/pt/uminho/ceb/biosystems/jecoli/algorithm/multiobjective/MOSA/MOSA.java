/**
* Copyright 2011,
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
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOSA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.IAnnealingSchedule;

// TODO: Auto-generated Javadoc
/**
 * The Class MOSA - MultiObjective Simulated Annealing.
 * 
 * @author Paulo Maia 
 */
public class MOSA<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractAlgorithm<T,MOSAConfiguration<T,S>> {

	private static final long serialVersionUID = 7525372774387784639L;

	/** The debug. */
	private boolean debug = false;
	
	protected Random random = new Random();
	
	/**
	 * Instantiates a new SPEA2 algorithm.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	public MOSA(MOSAConfiguration<T,S> configuration) throws InvalidConfigurationException {
		super(configuration);
	}
	
	public ISolutionSet<T> initialize(MOSAAlgorithmState<T> algorithmState) throws Exception{
		algorithmState.initializeState();
		
		IAnnealingSchedule annealingSchedule = configuration.getAnnealingSchedule();
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		annealingSchedule.reset();
		ISolutionSet<T> solutionSet = generateInitialPopulation();
		
		evaluationFunction.evaluate(solutionSet);
		algorithmState.setSolutionSet(solutionSet);
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(solutionSet.getNumberOfSolutions());
		
		ISolutionSet<T> archive = new SolutionSet<T>();
        algorithmState.setArchive(archive);            
        configuration.setInitialPopulation(solutionSet);
        configuration.setUserInitialPopulation(solutionSet);
        
		return solutionSet;
	}

	/**
	 * Generate initial population.
	 * 
	 * @return the i solution set
	 */
	protected ISolutionSet<T> generateInitialPopulation() {
		int populationSize = configuration.getInitialPopulationSize();
		ISolutionFactory<T> factory = configuration.getSolutionFactory();
		IRandomNumberGenerator randomNumberGenerator = configuration.getRandomNumberGenerator();
		ISolutionSet<T> population = factory.generateSolutionSet(populationSize,randomNumberGenerator);
        return population;
	}
	
	@Override
	public IAlgorithmResult<T> run() throws Exception {
        ITerminationCriteria terminationCriteria = configuration.getTerminationCriteria();
        MOSAAlgorithmState<T> algorithmState = new MOSAAlgorithmState<T>(this);      
        setAlgorithmState(algorithmState);
        
//        algorithmState.initializeState();
        ISolutionSet<T> solutionSet = initialize(algorithmState);        
        for(ISolution<T> sol : solutionSet.getListOfSolutions()){
        	System.out.print(sol.getFitnessValue(0));
        	for(int i=1; i<sol.getNumberOfObjectives();i++)
        		System.out.print(","+sol.getFitnessValue(i));
        	System.out.print("\n");
        }

        while (!terminationCriteria.verifyAlgorithmTermination(this,algorithmState)) {
            ISolutionSet<T> newSolutionSet = iteration(algorithmState,solutionSet);
            solutionSet = newSolutionSet;            
        }
        
        /** set the final solution set to equal the archive */
        updateState(algorithmState,((MOSAAlgorithmState<T>)algorithmState).getArchive());
//        updateLastState(algorithmState,((MOSAAlgorithmState)algorithmState).getArchive());
        

        System.out.println("FINAL SET:");        
        for(ISolution<T> sol : algorithmState.getSolutionSet().getListOfSolutions()){
        	System.out.print(sol.getFitnessValue(0));
        	for(int i=1; i<sol.getNumberOfObjectives();i++)
        		System.out.print(","+sol.getFitnessValue(i));
        	System.out.print("\n");
        }

        IAlgorithmResult<T> algorithmResult = algorithmState.getAlgorithmResult();
        

        algorithmState = null;

        return algorithmResult;
    }
	
	

	/**
	 * Update archive state.
	 * 
	 * @param archive the archive
	 */
	public void updateArchiveState(AlgorithmState<T> algorithmState,ISolutionSet<T> archive){
		((MOSAAlgorithmState<T>)algorithmState).updateArchiveState(archive);
	}

	/* (non-Javadoc)
	 * @see core.AbstractAlgorithm#iteration(core.interfaces.ISolutionSet)
	 */
	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception{
		
		updateState(algorithmState,solutionSet);
		
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		ISolutionSet<T> newGeneration = new SolutionSet<T>();
		
		ISolutionSet<T> union = ((MOSAAlgorithmState<T>)algorithmState).getArchive().union(solutionSet);
		MOUtils.assignSelectionValue(union, true);
		SolutionSet<T> newArchive = this.environmentalSelection(union, configuration.getMaximumArchiveSize(), true);
		updateArchiveState((MOSAAlgorithmState<T>)algorithmState,newArchive);
		
		newGeneration = anneal(algorithmState,newArchive);
		
		return newGeneration;
	}
	
	public SolutionSet<T> anneal(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws InvalidNumberOfInputSolutionsException, InvalidNumberOfOutputSolutionsException{
		IAnnealingSchedule annealingSchedule = configuration.getAnnealingSchedule();
		int numberOfAcceptedMoves = 0;
		int numberOfRejectedMoves = 0;
		IEvaluationFunction<?> evaluationFunction = configuration.getEvaluationFunction();
		
		int currSolIndex = random.nextInt(solutionSet.getNumberOfSolutions());
		ISolution<T> currentSolution = solutionSet.getSolution(currSolIndex);
		
		
		while(!annealingSchedule.isEquilibriumState(numberOfAcceptedMoves,numberOfRejectedMoves)){
			
			if (debug) System.out.println("starting trial cycle ...");
			
			ISolution<T> trialSolution = createTrialSolution(algorithmState,currentSolution,solutionSet,currSolIndex);
			double currentSolutionFitnessValue = currentSolution.getScalarFitnessValue();
			double trialSolutionFitnessValue = trialSolution.getScalarFitnessValue();
		
			if (acceptSolution(currentSolutionFitnessValue,	trialSolutionFitnessValue)){
				
				if (debug) System.out.println("accepting solution ...");
				
				currentSolution = trialSolution;
				numberOfAcceptedMoves++;
				
				if (debug) System.out.println("number of accepted: " + numberOfAcceptedMoves);
				
			}else {
				//numberOfRejectedMoves++; // doesn't make sense
				double randomNumber = Math.random();
				double acceptSolutionProbability = 0; 
				
				acceptSolutionProbability = annealingSchedule.caculateAcceptSolutionProbability(currentSolutionFitnessValue,trialSolutionFitnessValue);
				
				if (randomNumber < acceptSolutionProbability) {
					
					if (debug) System.out.println("accepting worse solution ...");
					
					currentSolution = trialSolution;
					numberOfAcceptedMoves++;
					if (debug) System.out.println("number of accepted: " + numberOfAcceptedMoves);
				} else
				{
					if (debug) System.out.println("rejecting solution ...");
					numberOfRejectedMoves++;
					if (debug) System.out.println("number of rejected: " + numberOfRejectedMoves);
				}
			}
//			annealingSchedule.getFitnessFunctionData(currentSolution.getScalarFitnessValue()); // NAO FAZ NADA !!!
		}
		
		if (debug) System.out.println("ended trial cycle ...");
		
		solutionSet.add(currentSolution);
		
		if (debug) System.out.println("added current solution; calculating new temp");
		
		annealingSchedule.calculateNewTemperature();

		if (debug) System.out.println("ending iteration ...");
		
		return (SolutionSet<T>) solutionSet;
	}
	
	public ISolution<T> createTrialSolution(AlgorithmState<T> algorithmState, ISolution<T> currentSolution, ISolutionSet<T> solutionSet, int currSolIndex) throws InvalidNumberOfInputSolutionsException, InvalidNumberOfOutputSolutionsException
	{
	
		if (debug) System.out.println("creating trial solution ...");
		
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		
		if (debug) System.out.println("selecting mutation jecoliunittest.operators.operator ...");
		
		IReproductionOperator<T,S> reproductionOperator = configuration.selectMutationOperator();
		
		if (debug) System.out.println("done selecting mutation jecoliunittest.operators.operator ...");
		
		List<ISolution<T>> selectedSolutionList = new ArrayList<ISolution<T>>(0);
		selectedSolutionList.add(currentSolution);
		
		if (debug) System.out.println("added current solution; applying rep op ...");
		
		List<ISolution<T>> trialSolutionList = reproductionOperator.apply(selectedSolutionList,configuration.getSolutionFactory(),configuration.getRandomNumberGenerator());
		
		if (debug) System.out.println("done applying rep op ...");

		ISolution<T> trialSolution = trialSolutionList.get(0);
		
		
		if (debug) System.out.println("evaluating ...");
		
		// context specific evaluation
		evaluationFunction.evaluateSingleSolution(trialSolution);
		
		
		// backup solution
		ISolution<T> backSol = solutionSet.getSolution(currSolIndex);
		
		// replace and recompute fitnesses
		solutionSet.setSolution(currSolIndex, trialSolution);
//		MOUtils.assignFitness(solutionSet, evaluationFunction.isMaximization());

		double fit = MOUtils.computeZitzlerSelection4SingleSolution(trialSolution, solutionSet, true);
		trialSolution.setScalarFitnessValue(fit);
		
		// place old solution again and recompute fitnesses
		solutionSet.setSolution(currSolIndex, backSol);
//		MOUtils.assignFitness(solutionSet, evaluationFunction.isMaximization());

				
		if (debug) System.out.println("done evaluating ...");
		
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations();
		
		if (debug) System.out.println("end creating trial solution ...");
		
		return trialSolution;
	}
	
	protected boolean acceptSolution(double currentFitnessValue,double trialSolutionFitnessValue) {
		boolean isCurrentFitnessBigger =  (currentFitnessValue > trialSolutionFitnessValue);		
		return !isCurrentFitnessBigger;		
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
	protected SolutionSet<T> environmentalSelection(ISolutionSet<T> solutionSet, int size,boolean isMaximization) throws Exception{
		ISelectionOperator<T> selectionOperator = configuration.getEnvironmentalSelectionOperator();
		List<ISolution<T>> selected = selectionOperator.selectSolutions(size, solutionSet, isMaximization,configuration.getRandomNumberGenerator());
		
		return new SolutionSet<T>(selected);
	}

	@Override
	public ISolutionSet<T> initialize() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAlgorithm<T> deepCopy() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}