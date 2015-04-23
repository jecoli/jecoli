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
 * The Class OverallRunStatistics.
 */
public class OverallRunStatistics implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** The best value. */
	protected double bestValue;
	
	/** The mean value. */
	protected double meanValue;
	
	/** The std dev. */
	protected double stdDev;
	
	/**
	 * Instantiates a new overall run statistics.
	 * 
	 * @param bestValue the best value
	 * @param meanValue the mean value
	 * @param stdDev the std dev
	 */
	public OverallRunStatistics(double bestValue, double meanValue,	double stdDev) 
	{
		this.bestValue = bestValue;
		this.meanValue = meanValue;
		this.stdDev = stdDev;
	}


	/**
	 * Gets the mean value.
	 * 
	 * @return the mean value
	 */
public double getMeanValue() {
		return meanValue;
	}

	/**
	 * Gets the std dev.
	 * 
	 * @return the std dev
	 */
	public double getStdDev() {
		return stdDev;
	}

	
	/**
	 * Gets the best value.
	 * 
	 * @return the best value
	 */
	public double getBestValue() {
		return bestValue;
	}
	
	
	
}
