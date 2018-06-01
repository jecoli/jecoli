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

import java.io.IOException;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.tracker.IEvolutionTracker;


// TODO: Auto-generated Javadoc
/**
 * The Interface IAlgorithm.
 */
public interface IAlgorithm<T extends IRepresentation> extends IDeepCopy {
    
    /**
     * Run.
     * 
     * @return the i algorithm result
     * 
     * @throws Exception the exception
     * @throws InvalidConfigurationException 
     */
	IAlgorithmResult<T> run() throws Exception, InvalidConfigurationException;
	
//         * @param algorithmState TODO
    /**
     * Initialize.
     * 
     * @return the i solution set
     * 
     * @throws Exception the exception
     */
	ISolutionSet<T> initialize() throws Exception;
    
    /**
     * Update state.
     * @param algorithmState TODO
     * @param solutionSet the solution set
     */
	void updateState(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet);
	
	/**
	 * Gets the configuration.
	 * 
	 * @return the configuration
	 */
	IConfiguration<T> getConfiguration();
	
	/**
	 * Adds the algorithm state listener.
	 * 
	 * @param algorithmStateListener the algorithm state listener
	 */
	void addAlgorithmStateListener(IAlgorithmStateListener algorithmStateListener);
	
	/**
	 * Notify algorithm state listeners.
	 * 
	 * @param iterationIncrementEvent the iteration increment event
	 * @param string the string
	 */
	void notifyAlgorithmStateListeners(String iterationIncrementEvent,String string);
	
	/**
	 * Save current state.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void saveCurrentState() throws Exception;
	
	IAlgorithm<T> deepCopy() throws Exception;
	
	AlgorithmState<T> getAlgorithmState();
	
	void setAlgorithmState(AlgorithmState<T> state);
	
	void setEvolutionTracker(IEvolutionTracker<T> tracker);
	
}