/**
 * Copyright 2009,
 * CCTC - Computer Science and Technology Center
 * IBB-CEB - Institute for Biotechnology and Bioengineering - Centre of Biological Engineering
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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmRunStatistics.
 */
public class AlgorithmRunStatistics<T extends IRepresentation> implements IAlgorithmStatistics<T>, Serializable {
	
	private static boolean verbose = false;
	
	private static final long serialVersionUID = 1L;
	
	/** The statistics list. */
	protected List<IAlgorithmIterationStatisticCell<T>> statisticsList;
	
	/** The solution container. */
	protected ISolutionContainer<T> solutionContainer;
	
	/** The total number of function evaluations. */
	protected int totalNumberOfFunctionEvaluations;
	
	/** The total execution time. */
	protected long totalExecutionTime;
	
	/**
	 * Instantiates a new algorithm run statistics.
	 */
	public AlgorithmRunStatistics() {
		statisticsList = new ArrayList<IAlgorithmIterationStatisticCell<T>>();
		solutionContainer = new SolutionContainer<T>(1);
	}
	
	/**
	 * Instantiates a new algorithm run statistics.
	 * 
	 * @param numberOfSolutionsToKeep the number of solutions to keep
	 */
	public AlgorithmRunStatistics(int numberOfSolutionsToKeep, boolean isVerbose) {
		statisticsList = new ArrayList<IAlgorithmIterationStatisticCell<T>>();
		solutionContainer = new SolutionContainer<T>(numberOfSolutionsToKeep);
		verbose = isVerbose;
	}
	
	@Override
	public int getNumberOfIterations() {
		return statisticsList.size() - 1;
	}
	
	@Override
	public IAlgorithmIterationStatisticCell<T> getAlgorithmIterationStatisticCell(int i) {
		return statisticsList.get(i);
	}
	
	@Override
	public void calculateStatistics(AlgorithmState<T> algorithmState) {
		
		ISolutionSet<T> solutionSet = algorithmState.getSolutionSet();
		int currentIteration = algorithmState.getCurrentIteration();
		int numberOfFunctionEvaluations = algorithmState.getCurrentIterationNumberOfFunctionEvaluations();
		long executionTime = algorithmState.getLastIterationTime();
		IAlgorithm<T> algorithm = algorithmState.getAlgorithm();
		IConfiguration<T> configuration = algorithm.getConfiguration();
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		StatisticsConfiguration statisticsConfiguration = configuration.getStatisticConfiguration();
		int statisticsPrintInterval = statisticsConfiguration.getScreenIterationInterval();
		
		ObjectiveStatisticCell scalarFitnessStatisticCell = null;
		
		int numObjectives = configuration.getNumberOfObjectives();
		List<ObjectiveStatisticCell> objectiveStatistiCellList = new ArrayList<ObjectiveStatisticCell>();
		
		if (numObjectives == 1)
			scalarFitnessStatisticCell = StatisticUtils.processScalarFitness(solutionSet);
		else
			objectiveStatistiCellList = StatisticUtils.calculateObjectiveStatisticCell(solutionSet, currentIteration);
			
		ISolutionSet<T> bestSolutionSet = calculateBestSolutionSet(algorithmState);
		
		IAlgorithmIterationStatisticCell<T> cell = new AlgorithmIterationStatiscticCell<T>(currentIteration, numberOfFunctionEvaluations, executionTime,
				objectiveStatistiCellList, scalarFitnessStatisticCell, bestSolutionSet);
				
		totalExecutionTime += executionTime;
		totalNumberOfFunctionEvaluations += numberOfFunctionEvaluations;
		
		if (statisticsPrintInterval > 0 && currentIteration % statisticsPrintInterval == 0)
			if (numObjectives == 1)
				printStatistics(currentIteration, executionTime, scalarFitnessStatisticCell, statisticsConfiguration);
			else
				printStatistics(currentIteration, executionTime, objectiveStatistiCellList, statisticsConfiguration);
				
		if (numObjectives == 1)
			solutionContainer.addSpecificSolutions(solutionSet, currentIteration, true);
			
		statisticsList.add(cell);
	}
	
	/**
	 * Prints the statistics.
	 * 
	 * @param currentIteration the current iteration
	 * @param executionTime the execution time
	 * @param objectiveStatisticCell the objective statistic cell
	 * @param statisticsConfiguration the statistics configuration
	 */
	protected void printStatistics(int currentIteration, long executionTime, ObjectiveStatisticCell objectiveStatisticCell, StatisticsConfiguration statisticsConfiguration) {
		StatisticTypeMask mask = statisticsConfiguration.getScreenStatisticsMask();
		
		boolean someTrue = false;
		if (verbose) {
			if (mask.isIteration()) {
				System.out.print("It:" + currentIteration);
				someTrue = true;
			}
			
			if (mask.isNumberOfFunctionEvaluations()) {
				System.out.print("\tNFE:" + totalNumberOfFunctionEvaluations);
				someTrue = true;
			}
			if (mask.isTotalExecutionTime()) {
				System.out.print("\tTime:" + totalExecutionTime);
				someTrue = true;
			}
			if (mask.isIterationExecutionTime()) {
				System.out.print("\tITime:" + executionTime);
				someTrue = true;
			}
			if (mask.isMaxFitnessValue()) {
				System.out.print("\tMAX:" + objectiveStatisticCell.getMaxValue());
				someTrue = true;
			}
			if (mask.isMinFitnessValue()) {
				System.out.print("\tMIN:" + objectiveStatisticCell.getMinValue());
				someTrue = true;
			}
			if (mask.isMeanFitnessValue()) {
				System.out.print("\tMEAN:" + objectiveStatisticCell.getMean());
				someTrue = true;
			}
			if (mask.isStdDevValue()) {
				System.out.print("\tSTD:" + objectiveStatisticCell.getStdDev());
				someTrue = true;
			}
			if (someTrue)
				System.out.println("");
		}
	}
	
	protected void printStatistics(int currentIteration, long executionTime, List<ObjectiveStatisticCell> objectiveStatisticCellList, StatisticsConfiguration statisticsConfiguration) {
		StatisticTypeMask mask = statisticsConfiguration.getScreenStatisticsMask();
		
		boolean someTrue = false;
		if (verbose) {
			if (mask.isIteration()) {
				System.out.print("It:" + currentIteration);
				someTrue = true;
			}
			
			if (mask.isNumberOfFunctionEvaluations()) {
				System.out.print("\tNFE:" + totalNumberOfFunctionEvaluations);
				someTrue = true;
			}
			if (mask.isTotalExecutionTime()) {
				System.out.print("\tTime:" + totalExecutionTime);
				someTrue = true;
			}
			if (mask.isIterationExecutionTime()) {
				System.out.print("\tITime:" + executionTime);
				someTrue = true;
			}
			for (int i = 0; i < objectiveStatisticCellList.size(); i++) {
				
				ObjectiveStatisticCell cell = objectiveStatisticCellList.get(i);
				
				System.out.print("\n\t\tObjective " + i + ":");
				
				if (mask.isMaxFitnessValue()) {
					System.out.print("\tMAX:" + cell.getMaxValue());
					someTrue = true;
				}
				if (mask.isMinFitnessValue()) {
					System.out.print("\tMIN:" + cell.getMinValue());
					someTrue = true;
				}
				if (mask.isMeanFitnessValue()) {
					System.out.print("\tMEAN:" + cell.getMean());
					someTrue = true;
				}
				if (mask.isStdDevValue()) {
					System.out.print("\tSTD:" + cell.getStdDev());
					someTrue = true;
				}
			}
			if (someTrue)
				System.out.println("");
		}
	}
	
	/**
	 * Calculate best solution set.
	 * 
	 * @param algorithmState the algorithm state
	 * 			
	 * @return the i solution set
	 */
	protected ISolutionSet<T> calculateBestSolutionSet(AlgorithmState<T> algorithmState) {
		ISolutionSet<T> solutionSet = algorithmState.getSolutionSet();
		IAlgorithm<T> algorithm = algorithmState.getAlgorithm();
		IConfiguration<T> configuration = algorithm.getConfiguration();
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		StatisticsConfiguration statisticsConfiguration = configuration.getStatisticConfiguration();
		int numberOfBestSolutionsToKeepPerIteration = statisticsConfiguration.getNumberOfBestSolutionsToKeepPerIteration();
		
		if (solutionSet.getNumberOfObjectives() > 1)
			return solutionSet;
			
		List<ISolution<T>> solutionList = solutionSet.getHighestValuedSolutions(numberOfBestSolutionsToKeepPerIteration);
		return new SolutionSet<T>(solutionList);
	}
	
	public int getTotalNumberOfFunctionEvaluations() {
		return totalNumberOfFunctionEvaluations;
	}
	
	public long getTotalExecutionTime() {
		return totalExecutionTime;
	}
	
	public ISolutionContainer<T> getSolutionContainer() {
		return solutionContainer;
	}
	
	public double getRunMaxScalarFitnessValue() {
		double maxValue = Double.NEGATIVE_INFINITY;
		
		for (int i = 0; i < statisticsList.size(); i++) {
			IAlgorithmIterationStatisticCell<T> iterationStatistics = statisticsList.get(i);
			ObjectiveStatisticCell scalarFitnessStatisticCell = iterationStatistics.getScalarFitnessCell();
			double maxFitnessValue = scalarFitnessStatisticCell.getMaxValue();
			maxValue = Math.max(maxValue, maxFitnessValue);
		}
		
		return maxValue;
	}
	
	public double getRunMinScalarFitnessValue() {
		double minValue = Double.POSITIVE_INFINITY;
		
		for (int i = 0; i < statisticsList.size(); i++) {
			IAlgorithmIterationStatisticCell<T> iterationStatistics = statisticsList.get(i);
			ObjectiveStatisticCell scalarFitnessStatisticCell = iterationStatistics.getScalarFitnessCell();
			double minFitnessValue = scalarFitnessStatisticCell.getMinValue();
			minValue = Math.min(minValue, minFitnessValue);
		}
		
		return minValue;
	}
	
	public double getRunMeanScalarFitnessValue() {
		double meanValue = 0;
		
		for (int i = 0; i < statisticsList.size(); i++) {
			IAlgorithmIterationStatisticCell<T> iterationStatistics = statisticsList.get(i);
			ObjectiveStatisticCell scalarFitnessStatisticCell = iterationStatistics.getScalarFitnessCell();
			double fitnessValue = scalarFitnessStatisticCell.getMean();
			meanValue += fitnessValue;
		}
		
		return meanValue / statisticsList.size();
	}
	
	@Override
	public int getNumberOfObjectives() {
		IAlgorithmIterationStatisticCell<T> cell = statisticsList.get(0);
		return cell.getNumberOfObjectives();
	}
	
	@Override
	public double getRunObjectiveMaxFitnessValue(int objectivePosition) {
		double maxValue = Double.NEGATIVE_INFINITY;
		
		for (int i = 0; i < statisticsList.size(); i++) {
			IAlgorithmIterationStatisticCell<T> iterationStatistics = statisticsList.get(i);
			ObjectiveStatisticCell objectiveStatisticCell = iterationStatistics.getObjectiveStatisticCell(objectivePosition);
			double maxFitnessValue = objectiveStatisticCell.getMaxValue();
			maxValue = Math.max(maxValue, maxFitnessValue);
		}
		
		return maxValue;
	}
	
	@Override
	public double getRunObjectiveMeanFitnessValue(int objectivePosition) {
		double meanValue = 0;
		
		for (int i = 0; i < statisticsList.size(); i++) {
			IAlgorithmIterationStatisticCell<T> iterationStatistics = statisticsList.get(i);
			ObjectiveStatisticCell objectiveStatisticCell = iterationStatistics.getObjectiveStatisticCell(objectivePosition);
			double fitnessValue = objectiveStatisticCell.getMean();
			meanValue += fitnessValue;
		}
		
		return meanValue / statisticsList.size();
	}
	
	@Override
	public double getRunObjectiveMinFitnessValue(int objectivePosition) {
		double minValue = Double.POSITIVE_INFINITY;
		
		for (int i = 0; i < statisticsList.size(); i++) {
			IAlgorithmIterationStatisticCell<T> iterationStatistics = statisticsList.get(i);
			ObjectiveStatisticCell objectiveStatisticCell = iterationStatistics.getObjectiveStatisticCell(objectivePosition);
			double minFitnessValue = objectiveStatisticCell.getMinValue();
			minValue = Math.min(minValue, minFitnessValue);
		}
		
		return minValue;
	}
	
	@Override
	public void setSolutionContainer(ISolutionContainer<T> container) {
		this.solutionContainer = container;
	}
	
	/**
	 * @return the verbose
	 */
	public static boolean isVerbose() {
		return verbose;
	}
	
	/**
	 * @param verbose the verbose to set
	 */
	public static void setVerbose(boolean verbose) {
		AlgorithmRunStatistics.verbose = verbose;
	}
	
}