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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.IAlgorithmIterationStatisticCell;


// TODO: Auto-generated Javadoc
/**
 * The Interface IAlgorithmStatistics.
 */
public interface IAlgorithmStatistics<T extends IRepresentation> {
    
    /**
     * Gets the number of iterations.
     * 
     * @return the number of iterations
     */
    int getNumberOfIterations();
    
    /**
     * Gets the statistic cell.
     * 
     * @param i the i
     * 
     * @return the statistic cell
     */
    IAlgorithmIterationStatisticCell<T> getAlgorithmIterationStatisticCell(int iterationIndex);
	
	/**
	 * Calculate statistics.
	 * 
	 * @param algorithmState the algorithm state
	 */
    void calculateStatistics(AlgorithmState<T> algorithmState);
	
	/**
	 * Gets the total number of function evaluations.
	 * 
	 * @return the total number of function evaluations
	 */
	public int getTotalNumberOfFunctionEvaluations();
	
	/**
	 * Gets the total execution time.
	 * 
	 * @return the total execution time
	 */
	public long getTotalExecutionTime();
	
	/**
	 * Gets the solution container.
	 * 
	 * @return the solution container
	 */
	ISolutionContainer<T> getSolutionContainer();
	
	/**
	 * Gets the run max scalar fitness value.
	 * 
	 * @return the run max scalar fitness value
	 */
	public double getRunMaxScalarFitnessValue();
	
	/**
	 * Gets the run min scalar fitness value.
	 * 
	 * @return the run min scalar fitness value
	 */
	public double getRunMinScalarFitnessValue();
	
	/**
	 * Gets the run mean scalar fitness value.
	 * 
	 * @return the run mean scalar fitness value
	 */
	public double getRunMeanScalarFitnessValue();
	
	/**
	 * Gets the run objective max fitness value.
	 * 
	 * @param objectivePosition the objective position
	 * 
	 * @return the run objective max fitness value
	 */
	public double getRunObjectiveMaxFitnessValue(int objectivePosition);
	
	/**
	 * Gets the run objective min fitness value.
	 * 
	 * @param objectivePosition the objective position
	 * 
	 * @return the run objective min fitness value
	 */
	public double getRunObjectiveMinFitnessValue(int objectivePosition);
	
	/**
	 * Gets the run objective mean fitness value.
	 * 
	 * @param objectivePosition the objective position
	 * 
	 * @return the run objective mean fitness value
	 */
	public double getRunObjectiveMeanFitnessValue(int objectivePosition);
	
	/**
	 * Gets the number of objectives.
	 * 
	 * @return the number of objectives
	 */
	int getNumberOfObjectives();
	
	/**
	 * TODO
	 * @param container
	 */
	void setSolutionContainer(ISolutionContainer<T> container);
}