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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;



// TODO: Auto-generated Javadoc
/**
 * The Class StatisticTypeMask.
 */
public class StatisticTypeMask implements Serializable,IDeepCopy{
	
	private static final long serialVersionUID = 1L;

	/** The iteration. */
	protected boolean iteration;
	
	/** The number of function evaluations. */
	protected boolean numberOfFunctionEvaluations;
	
	/** The total execution time. */
	protected boolean totalExecutionTime;
	
	/** The iteration execution time. */
	protected boolean iterationExecutionTime;
	
	/** The max fitness value. */
	protected boolean maxFitnessValue;
	
	/** The mean fitness value. */
	protected boolean meanFitnessValue;
	
	/** The min fitness value. */
	protected boolean minFitnessValue;
	
	/** The std dev value. */
	protected boolean stdDevValue;	
	
	/**
	 * Instantiates a new statistic type mask.
	 */
	public StatisticTypeMask(){
		iteration = true;
		numberOfFunctionEvaluations = true;
		totalExecutionTime = true;
		iterationExecutionTime = false;
		maxFitnessValue = true;
		meanFitnessValue = true;
		minFitnessValue = true;
		stdDevValue = true;
	}
	
	/**
	 * Instantiates a new statistic type mask.
	 * 
	 * @param valueForAll the value for all
	 */
	public StatisticTypeMask(boolean valueForAll){
		iteration = valueForAll;
		numberOfFunctionEvaluations = valueForAll;
		totalExecutionTime = valueForAll;
		iterationExecutionTime = valueForAll;
		maxFitnessValue = valueForAll;
		meanFitnessValue = valueForAll;
		minFitnessValue = valueForAll;
		stdDevValue = valueForAll;
	}
	
	/**
	 * Checks if is mean fitness value.
	 * 
	 * @return true, if is mean fitness value
	 */
	public boolean isMeanFitnessValue(){
		return meanFitnessValue;
	}

	/**
	 * Sets the mean fitness value.
	 * 
	 * @param meanFitnessValue the new mean fitness value
	 */
	public void setMeanFitnessValue(boolean meanFitnessValue) {
		this.meanFitnessValue = meanFitnessValue;
	}

	/**
	 * Checks if is iteration.
	 * 
	 * @return true, if is iteration
	 */
	public boolean isIteration() {
		return iteration;
	}
	
	/**
	 * Sets the iteration.
	 * 
	 * @param iteration the new iteration
	 */
	public void setIteration(boolean iteration) {
		this.iteration = iteration;
	}
	
	/**
	 * Checks if is number of function evaluations.
	 * 
	 * @return true, if is number of function evaluations
	 */
	public boolean isNumberOfFunctionEvaluations() {
		return numberOfFunctionEvaluations;
	}
	
	/**
	 * Sets the number of function evaluations.
	 * 
	 * @param numberOfFunctionEvaluations the new number of function evaluations
	 */
	public void setNumberOfFunctionEvaluations(boolean numberOfFunctionEvaluations) {
		this.numberOfFunctionEvaluations = numberOfFunctionEvaluations;
	}
	
	/**
	 * Checks if is total execution time.
	 * 
	 * @return true, if is total execution time
	 */
	public boolean isTotalExecutionTime() {
		return totalExecutionTime;
	}
	
	/**
	 * Sets the total execution time.
	 * 
	 * @param totalExecutionTime the new total execution time
	 */
	public void setTotalExecutionTime(boolean totalExecutionTime) {
		this.totalExecutionTime = totalExecutionTime;
	}
	
	/**
	 * Checks if is iteration execution time.
	 * 
	 * @return true, if is iteration execution time
	 */
	public boolean isIterationExecutionTime() {
		return iterationExecutionTime;
	}
	
	/**
	 * Sets the iteration execution time.
	 * 
	 * @param iterationExecutionTime the new iteration execution time
	 */
	public void setIterationExecutionTime(boolean iterationExecutionTime) {
		this.iterationExecutionTime = iterationExecutionTime;
	}
	
	/**
	 * Checks if is max fitness value.
	 * 
	 * @return true, if is max fitness value
	 */
	public boolean isMaxFitnessValue() {
		return maxFitnessValue;
	}
	
	/**
	 * Sets the max fitness value.
	 * 
	 * @param maxFitnessValue the new max fitness value
	 */
	public void setMaxFitnessValue(boolean maxFitnessValue) {
		this.maxFitnessValue = maxFitnessValue;
	}
	
	/**
	 * Checks if is min fitness value.
	 * 
	 * @return true, if is min fitness value
	 */
	public boolean isMinFitnessValue() {
		return minFitnessValue;
	}
	
	/**
	 * Sets the min fitness value.
	 * 
	 * @param minFitnessValue the new min fitness value
	 */
	public void setMinFitnessValue(boolean minFitnessValue) {
		this.minFitnessValue = minFitnessValue;
	}
	
	/**
	 * Checks if is std dev value.
	 * 
	 * @return true, if is std dev value
	 */
	public boolean isStdDevValue() {
		return stdDevValue;
	}
	
	/**
	 * Sets the std dev value.
	 * 
	 * @param stdDevValue the new std dev value
	 */
	public void setStdDevValue(boolean stdDevValue) {
		this.stdDevValue = stdDevValue;
	}

	public StatisticTypeMask deepCopy() {
		StatisticTypeMask maskCopy = new StatisticTypeMask();
		maskCopy.setIteration(iteration);
		maskCopy.setIterationExecutionTime(iterationExecutionTime);
		maskCopy.setMaxFitnessValue(maxFitnessValue);
		maskCopy.setMeanFitnessValue(meanFitnessValue);
		maskCopy.setMinFitnessValue(minFitnessValue);
		maskCopy.setNumberOfFunctionEvaluations(numberOfFunctionEvaluations);
		maskCopy.setStdDevValue(stdDevValue);
		maskCopy.setTotalExecutionTime(totalExecutionTime);
		return maskCopy;
	}

}
