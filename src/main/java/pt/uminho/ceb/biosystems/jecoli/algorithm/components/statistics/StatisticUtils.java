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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;



// TODO: Auto-generated Javadoc
/**
 * The Class StatisticUtils.
 */
public class StatisticUtils<T extends IRepresentation> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Calculate objective statistic cell.
	 * 
	 * @param solutionSet the solution set
	 * @param currentIteration the current iteration
	 * 
	 * @return the list 
	 */
	public static List<ObjectiveStatisticCell> calculateObjectiveStatisticCell(ISolutionSet<?> solutionSet,int currentIteration){
//		int numberOfIndividuals = solutionSet.getNumberOfSolutions();
		int numberOfObjectives = solutionSet.getNumberOfObjectives();
		List<ObjectiveStatisticCell> objectiveStatisticCellList = new ArrayList<ObjectiveStatisticCell>();
		
		for(int j = 0; j < numberOfObjectives;j++){
			ObjectiveStatisticCell objectiveStatisticCell = processObjectiveStatistic(j,solutionSet);
			objectiveStatisticCellList.add(objectiveStatisticCell);
		}

		return objectiveStatisticCellList;		
	}
	
	/**
	 * Process scalar fitness.
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the objective statistic cell
	 */
	public static ObjectiveStatisticCell processScalarFitness(ISolutionSet<?> solutionSet){
		double maxValue = solutionSet.getHighestValuedSolutionsAt(0).getScalarFitnessValue();
		double minValue = solutionSet.getLowestValuedSolutionsAt(0).getScalarFitnessValue();
		double meanValue = solutionSet.calculateOverallFitness()/solutionSet.getNumberOfSolutions();
		
		double stdDev = calculateSclarFitnessStatisticCellStdDev(solutionSet,meanValue);
		
		return new ObjectiveStatisticCell(maxValue,minValue,meanValue,stdDev);
		
	}

	/**
	 * Process objective statistic.
	 * 
	 * @param objectivePosition the objective position
	 * @param solutionSet the solution set
	 * 
	 * @return the objective statistic cell
	 */
	protected static ObjectiveStatisticCell processObjectiveStatistic(int objectivePosition,ISolutionSet<?> solutionSet) {
		double maxValue = Double.NEGATIVE_INFINITY;
		double minValue = Double.POSITIVE_INFINITY;
		double meanValue = 0;
		
		for(int i = 0; i < solutionSet.getNumberOfSolutions();i++){
			 ISolution<?> solution = solutionSet.getSolution(i);
             double fitnessValue = solution.getFitnessValue(objectivePosition);
             maxValue = Math.max(maxValue, fitnessValue);
             minValue = Math.min(minValue, fitnessValue);
             meanValue += fitnessValue;
		}
		
		meanValue = meanValue/solutionSet.getNumberOfSolutions();
//		double stdDev = calculateObjectiveStatisticCell(objectivePosition,solutionSet,meanValue);
		
		return new ObjectiveStatisticCell(maxValue,minValue,meanValue,0);
	}

	/**
	 * Calculate objective statistic cell.
	 * 
	 * @param objectivePosition the objective position
	 * @param solutionSet the solution set
	 * @param meanValue the mean value
	 * 
	 * @return the double
	 */
	protected static double calculateObjectiveStatisticCell(int objectivePosition,ISolutionSet<?> solutionSet,double meanValue){
		double stdDev = 0;
		for(int i = 0; i < solutionSet.getMaxNumberOfSolutions();i++){
			ISolution<?> solution = solutionSet.getSolution(i);
			double solutionFitness = solution.getFitnessValue(objectivePosition);
			double meanDifference = solutionFitness - meanValue;
			stdDev += Math.pow(meanDifference,2);
		}
		stdDev /= solutionSet.getNumberOfSolutions();
		return Math.sqrt(stdDev);
	}
	
	/**
	 * Calculate sclar fitness statistic cell std dev.
	 * 
	 * @param solutionSet the solution set
	 * @param meanValue the mean value
	 * 
	 * @return the double
	 */
	protected static double calculateSclarFitnessStatisticCellStdDev(ISolutionSet<?> solutionSet,double meanValue){
		double stdDev = 0;
		for(int i = 0; i < solutionSet.getNumberOfSolutions();i++){
			ISolution<?> solution = solutionSet.getSolution(i);
			double solutionFitness = solution.getScalarFitnessValue();
			double meanDifference = solutionFitness - meanValue;
			stdDev += Math.pow(meanDifference,2);
		}
		stdDev /= solutionSet.getNumberOfSolutions();
		return Math.sqrt(stdDev);
	}
	
}
