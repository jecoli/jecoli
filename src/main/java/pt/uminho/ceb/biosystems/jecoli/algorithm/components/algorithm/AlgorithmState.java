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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm;

import java.io.Serializable;
import java.util.GregorianCalendar;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;



/**
 * The Class AlgorithmState.
 * Keeps the internal state for the optimization algorithms
 */
public class AlgorithmState<T extends IRepresentation> implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The current iteration. */
    protected int currentIteration;
    
    /** The total number of function evaluations. */
    protected int totalNumberOfFunctionEvaluations;
    
    /** The current iteration number of function evaluations. */
    protected int currentIterationNumberOfFunctionEvaluations;
    
    /** The last iteration time. */
    protected long lastIterationTime;
    
    /** The overall time. */
    protected long overallTime;
    
    /** The solution set. */
    protected ISolutionSet<T> solutionSet;
    
    /** The algorithm result. */
    protected IAlgorithmResult<T> algorithmResult;
	
	/** The algorithm. */
	protected IAlgorithm<T> algorithm;
	
	/** The current time. */
	protected long currentTime;
    
    /**
     * Instantiates a new algorithm state.
     * 
     * @param algorithm the algorithm
     */
    public AlgorithmState(IAlgorithm<T> algorithm) {
        this.algorithm = algorithm;
        algorithm.setAlgorithmState(this);
    }

    /**
     * Gets the current iteration.
     * 
     * @return the current iteration
     */
    public int getCurrentIteration() {
        return currentIteration;
    }

    /**
     * Sets the current iteration.
     * 
     * @param currentIteration the new current iteration
     */
    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    /**
     * Gets the last iteration time.
     * 
     * @return the last iteration time
     */
    public long getLastIterationTime() {
        return lastIterationTime;
    }

    /**
     * Gets the overall time.
     * 
     * @return the overall time
     */
    public long getOverallTime() {
        return overallTime;
    }

    /**
     * Sets the solution set.
     * 
     * @param solutionSet the new solution set
     */
    public void setSolutionSet(ISolutionSet<T> solutionSet) {
        this.solutionSet = solutionSet;
    }

    /**
     * Gets the algorithm result.
     * 
     * @return the algorithm result
     */
    public IAlgorithmResult<T> getAlgorithmResult() {
        return algorithmResult;
    }


    /**
     * Initialize state.
     */
    public void initializeState() {
        currentIteration = 0;
        currentTime = GregorianCalendar.getInstance().getTimeInMillis();
        overallTime = 0;
        lastIterationTime = 0;
        solutionSet = null;
        IConfiguration<T> configuration = algorithm.getConfiguration();
        StatisticsConfiguration statisticsConfiguration = configuration.getStatisticConfiguration();
        boolean isVerbose = statisticsConfiguration.isVerbose();
        algorithmResult = new AlgorithmResult<T>(statisticsConfiguration.getNumberOfBestSolutionsToKeepPerRun(),isVerbose);
    }

    /**
     * Update state.
     * 
     * @param solutionSet the solution set
     */
    public void updateState(ISolutionSet<T> solutionSet) {
        this.solutionSet = solutionSet;

        long newCurrentTime = GregorianCalendar.getInstance().getTimeInMillis();
        long currentIterationTime = newCurrentTime - currentTime;
        currentTime = newCurrentTime;
        lastIterationTime = currentIterationTime;
        overallTime += lastIterationTime;
        
        totalNumberOfFunctionEvaluations += currentIterationNumberOfFunctionEvaluations;
		algorithm.notifyAlgorithmStateListeners(AbstractAlgorithm.EVALUATION_FUNCTION_INCREMENT,""+totalNumberOfFunctionEvaluations);		

        algorithmResult.processAlgorithmState(this);
        
        currentIterationNumberOfFunctionEvaluations = 0;
        currentIteration++;
        algorithm.notifyAlgorithmStateListeners(AbstractAlgorithm.ITERATION_INCREMENT_EVENT,String.valueOf(currentIteration));
       
    }

    /**
     * Gets the solution set.
     * 
     * @return the solution set
     */
    public ISolutionSet<T> getSolutionSet() {
        return solutionSet;
    }
    
    /**
     * Gets the algorithm.
     * 
     * @return the algorithm
     */
    public IAlgorithm<T> getAlgorithm(){
    	return algorithm;
    }

	/**
	 * Gets the total number of function evaluations.
	 * 
	 * @return the total number of function evaluations
	 */
	public int getTotalNumberOfFunctionEvaluations() {
		return totalNumberOfFunctionEvaluations;
	}
	
	/**
	 * Gets the current iteration number of function evaluations.
	 * 
	 * @return the current iteration number of function evaluations
	 */
	public int getCurrentIterationNumberOfFunctionEvaluations(){
		return currentIterationNumberOfFunctionEvaluations;
	}
	
	/**
	 * Increment current iteration number of function evaluations.
	 */
	public void incrementCurrentIterationNumberOfFunctionEvaluations(){        
		currentIterationNumberOfFunctionEvaluations++;
	}

	/**
	 * Increment current iteration number of function evaluations.
	 * 
	 * @param numberOfSolutions the number of solutions
	 */
	public void incrementCurrentIterationNumberOfFunctionEvaluations(int numberOfSolutions) {
		currentIterationNumberOfFunctionEvaluations += numberOfSolutions;
	}
    
}