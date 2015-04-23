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
 * The Class StatisticsConfiguration.
 */
public class StatisticsConfiguration implements Serializable, IDeepCopy{
	
	private static final long serialVersionUID = 1L;
	
	/** The screen statistics mask. */
	protected StatisticTypeMask screenStatisticsMask;
	
	/** The screen iteration interval. */
	protected int screenIterationInterval;
	
	/** The number of best solutions to keep per iteration. */
	protected int numberOfBestSolutionsToKeepPerIteration;
	
	/** The number of best solutions to keep per run. */
	protected int numberOfBestSolutionsToKeepPerRun;
	
	/** Verbosity option. If false nothing is printed*/
	protected boolean verbose;
	
	/**
	 * Instantiates a new statistics configuration.
	 */
	public StatisticsConfiguration(){
		screenStatisticsMask = new StatisticTypeMask();
		screenIterationInterval = 1;
		numberOfBestSolutionsToKeepPerIteration = 0;
		numberOfBestSolutionsToKeepPerRun = 1;
		verbose = false;
	}

    public StatisticsConfiguration(Boolean verbose){
        screenStatisticsMask = new StatisticTypeMask();
        screenIterationInterval = 1;
        numberOfBestSolutionsToKeepPerIteration = 0;
        numberOfBestSolutionsToKeepPerRun = 1;
        this.verbose = verbose;
    }

	
	/**
	 * Gets the screen statistics mask.
	 * 
	 * @return the screen statistics mask
	 */
	public StatisticTypeMask getScreenStatisticsMask() {
		return screenStatisticsMask;
	}
	
	/**
	 * Sets the screen statistics mask.
	 * 
	 * @param screenStatisticsMask the new screen statistics mask
	 * @throws InvalidStatisticsParameterException 
	 */
	public void setScreenStatisticsMask(StatisticTypeMask screenStatisticsMask) throws InvalidStatisticsParameterException {
		if(screenStatisticsMask == null)
			throw new InvalidStatisticsParameterException("screenStatisticsMask is null");
		this.screenStatisticsMask = screenStatisticsMask;
	}
	
	/**
	 * Gets the screen iteration interval.
	 * 
	 * @return the screen iteration interval
	 */
	public int getScreenIterationInterval() {
		return screenIterationInterval;
	}
	
	/**
	 * Sets the screen iteration interval.
	 * 
	 * @param screenIterationInterval the new screen iteration interval
	 * @throws InvalidStatisticsParameterException 
	 */
	public void setScreenIterationInterval(int screenIterationInterval) throws InvalidStatisticsParameterException {
		if(screenIterationInterval < 0)
			throw new InvalidStatisticsParameterException("screen Iteration Interval <0");
		
		this.screenIterationInterval = screenIterationInterval;
	}
	
	/**
	 * Gets the number of best solutions to keep per iteration.
	 * 
	 * @return the number of best solutions to keep per iteration
	 */
	public int getNumberOfBestSolutionsToKeepPerIteration() {
		return numberOfBestSolutionsToKeepPerIteration;
	}
	
	/**
	 * Sets the number of best solutions to keep per iteration.
	 * 
	 * @param numberOfBestSolutionsToKeepPerIteration the new number of best solutions to keep per iteration
	 * @throws InvalidStatisticsParameterException 
	 */
	public void setNumberOfBestSolutionsToKeepPerIteration(int numberOfBestSolutionsToKeepPerIteration) throws InvalidStatisticsParameterException {
		if(numberOfBestSolutionsToKeepPerIteration < 0)
			throw new InvalidStatisticsParameterException("numberOfBestSolutionsToKeepPerIteration < 0");
		this.numberOfBestSolutionsToKeepPerIteration = numberOfBestSolutionsToKeepPerIteration;
	}
	
	/**
	 * Gets the number of best solutions to keep per run.
	 * 
	 * @return the number of best solutions to keep per run
	 */
	public int getNumberOfBestSolutionsToKeepPerRun() {
		return numberOfBestSolutionsToKeepPerRun;
	}
	
	/**
	 * Sets the number of best solutions to keep per run.
	 * 
	 * @param numberOfBestSolutionsToKeepPerRun the new number of best solutions to keep per run
	 */
	public void setNumberOfBestSolutionsToKeepPerRun(int numberOfBestSolutionsToKeepPerRun) {
		this.numberOfBestSolutionsToKeepPerRun = numberOfBestSolutionsToKeepPerRun;
	}

	public StatisticsConfiguration deepCopy() throws InvalidStatisticsParameterException{
		StatisticsConfiguration statisticsConfigurationCopy = new StatisticsConfiguration();
		statisticsConfigurationCopy.setNumberOfBestSolutionsToKeepPerIteration(numberOfBestSolutionsToKeepPerIteration);
		statisticsConfigurationCopy.setNumberOfBestSolutionsToKeepPerRun(numberOfBestSolutionsToKeepPerRun);
		statisticsConfigurationCopy.setScreenIterationInterval(screenIterationInterval);
		statisticsConfigurationCopy.setScreenStatisticsMask(screenStatisticsMask.deepCopy());
		return statisticsConfigurationCopy;
	}

	/**
	 * @return the verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * @param verbose the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}	
}
