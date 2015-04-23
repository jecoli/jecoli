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

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectiveStatisticCell.
 */
public class ObjectiveStatisticCell implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** The max value. */
	protected double maxValue;
	
	/** The min value. */
	protected double minValue;
	
	/** The mean. */
	protected double mean;
	
	/** The std dev. */
	protected double stdDev;

	/**
	 * Instantiates a new objective statistic cell.
	 * 
	 * @param maxValue the max value
	 * @param minValue the min value
	 * @param mean the mean
	 * @param stdDev the std dev
	 */
	public ObjectiveStatisticCell(double maxValue, double minValue,double mean,double stdDev){
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.mean = mean;
		this.stdDev = stdDev;
	}

	/**
	 * Gets the max value.
	 * 
	 * @return the max value
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * Gets the min value.
	 * 
	 * @return the min value
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * Gets the mean.
	 * 
	 * @return the mean
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * Gets the std dev.
	 * 
	 * @return the std dev
	 */
	public double getStdDev() {
		return stdDev;
	}
	
	

}
