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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionCellContainer;

// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmOverallRunsStatistics.
 */
public class AlgorithmOverallRunsStatistics<T extends IRepresentation> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** The algorithm. */
	protected IAlgorithm<T> algorithm;
	
	/** The algorithm result list. */
	protected List<IAlgorithmResult<T>> algorithmResultList;
	
	/**
	 * Instantiates a new algorithm overall runs statistics.
	 * 
	 * @param algorithm the algorithm
	 * @param algorithmRunStatisticsList the algorithm run statistics list
	 * 			
	 * @throws InvalidStatisticsParameterException the invalid statistics parameter exception
	 */
	public AlgorithmOverallRunsStatistics(IAlgorithm<T> algorithm, List<IAlgorithmResult<T>> algorithmRunStatisticsList) throws InvalidStatisticsParameterException {
		if (algorithm == null)
			throw new InvalidStatisticsParameterException("algorithm == NULL");
			
		if (algorithmRunStatisticsList.size() < 0)
			throw new InvalidStatisticsParameterException("numberOfRuns < 0");
			
		this.algorithm = algorithm;
		algorithmResultList = algorithmRunStatisticsList;
	}
	
	/**
	 * Gets the number of runs.
	 * 
	 * @return the number of runs
	 */
	public int getNumberOfRuns() {
		return algorithmResultList.size();
	}
	
	/**
	 * Gets the algorithm.
	 * 
	 * @return the algorithm
	 */
	public IAlgorithm<T> getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * Gets the number of objectives.
	 * 
	 * @return the number of objectives
	 */
	public int getNumberOfObjectives() {
		IConfiguration<T> configuration = algorithm.getConfiguration();
		int numberOfObjectives = configuration.getNumberOfObjectives();
		return numberOfObjectives;
	}
	
	/**
	 * Gets the overall scalar fitness run statistics.
	 * 
	 * @return the overall scalar fitness run statistics
	 */
	public OverallRunStatistics getOverallScalarFitnessRunStatistics() {
		//double maxValue = Double.NEGATIVE_INFINITY;
		//double minValue = Double.POSITIVE_INFINITY;
		
		double bestValue = Double.NEGATIVE_INFINITY;
		double meanValue = 0;
		
		for (int i = 0; i < algorithmResultList.size(); i++) {
			IAlgorithmResult<T> algorithmResult = algorithmResultList.get(i);
			IAlgorithmStatistics<T> runStatistics = algorithmResult.getAlgorithmStatistics();
			
			double currentBestValue;
			currentBestValue = runStatistics.getRunMaxScalarFitnessValue();
			bestValue = Math.max(currentBestValue, bestValue);
			
			meanValue += currentBestValue;
			
			//double currentMinValue = runStatistics.getRunMinScalarFitnessValue();
			//double currentMeanValue = runStatistics.getRunMeanScalarFitnessValue();
			//maxValue = Math.max(maxValue,currentMaxValue);
			//minValue = Math.min(minValue,currentMinValue);
			//meanValue += currentMeanValue;
		}
		meanValue /= algorithmResultList.size();
		
		double stdDev = calculateStdDev(meanValue);
		
		return new OverallRunStatistics(bestValue, meanValue, stdDev);
	}
	
	/**
	 * Calculate std dev.
	 * 
	 * @param meanValue the mean value
	 * 			
	 * @return the double
	 */
	protected double calculateStdDev(double meanValue) {
		double stdDev = 0;
		
		for (IAlgorithmResult<T> algorithmResult : algorithmResultList) {
			IAlgorithmStatistics<T> runStatistics = algorithmResult.getAlgorithmStatistics();
			double currentValue = 0;
			currentValue = runStatistics.getRunMaxScalarFitnessValue();
			double differenceValue = currentValue - meanValue;
			stdDev += Math.pow(differenceValue, 2);
		}
		stdDev /= algorithmResultList.size();
		return Math.sqrt(stdDev);
	}
	
	/**
	 * Gets the best solution for each run.
	 * 
	 * @return the best solution for each run
	 */
	public List<ISolution<T>> getBestSolutionForEachRun() {
		List<ISolution<T>> bestSolutionList = new ArrayList<ISolution<T>>();
		
		for (int i = 0; i < algorithmResultList.size(); i++) {
			IAlgorithmResult<T> algorithmResult = algorithmResultList.get(i);
			ISolutionContainer<T> solutionContainer = algorithmResult.getSolutionContainer();
			SolutionCellContainer<T> container = solutionContainer.getBestSolutionCellContainer(true);
			ISolution<T> solution = container.getSolution();
			bestSolutionList.add(solution);
		}
		
		return bestSolutionList;
	}
	
	/**
	 * Adds the solutions to list.
	 * 
	 * @param solutionSet the solution set
	 * @param bestSolutionList the best solution list
	 */
	protected void addSolutionsToList(ISolutionSet<T> solutionSet, List<ISolution<T>> bestSolutionList) {
		for (ISolution<T> solution : bestSolutionList)
			solutionSet.add(solution);
	}
	
	/**
	 * Gets the run statistics.
	 * 
	 * @param objectivePosition the objective position
	 * 			
	 * @return the run statistics
	 */
	public IAlgorithmStatistics<T> getRunStatistics(int objectivePosition) {
		IAlgorithmResult<T> algorithmResult = algorithmResultList.get(objectivePosition);
		return algorithmResult.getAlgorithmStatistics();
	}
	
	/**
	 * Gets the objective overall run statistics.
	 * 
	 * @param objectivePosition the objective position
	 * 			
	 * @return the objective overall run statistics
	 */
	public OverallRunStatistics getObjectiveOverallRunStatistics(int objectivePosition) {
		//double maxValue = Double.NEGATIVE_INFINITY;
		//double minValue = Double.POSITIVE_INFINITY;
		double meanValue = 0;
		
		double bestValue = Double.NEGATIVE_INFINITY;
		;
		
		for (int i = 0; i < algorithmResultList.size(); i++) {
			IAlgorithmResult<T> algorithmResult = algorithmResultList.get(i);
			IAlgorithmStatistics<T> runStatistics = algorithmResult.getAlgorithmStatistics();
			
			double currentBestValue;
			currentBestValue = runStatistics.getRunObjectiveMaxFitnessValue(objectivePosition);
			bestValue = Math.max(currentBestValue, bestValue);
			
			meanValue += currentBestValue;
			
//			double currentMaxValue = runStatistics.getRunObjectiveMaxFitnessValue(objectivePosition);
//			double currentMinValue = runStatistics.getRunObjectiveMinFitnessValue(objectivePosition);
//			double currentMeanValue = runStatistics.getRunObjectiveMeanFitnessValue(objectivePosition);
//			maxValue = Math.max(maxValue,currentMaxValue);
//			minValue = Math.min(minValue,currentMinValue);
//			meanValue += currentMeanValue;
		}
		meanValue /= algorithmResultList.size();
		
		double stdDev = calculateStdDev(meanValue);
		
		return new OverallRunStatistics(bestValue, meanValue, stdDev);
		//return new OverallRunStatistics(maxValue,minValue,meanValue,stdDev);
	}
	
}
