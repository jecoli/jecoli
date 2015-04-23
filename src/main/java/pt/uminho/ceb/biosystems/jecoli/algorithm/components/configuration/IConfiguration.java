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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration;

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;

/**
 * The Interface IConfiguration.
 */
public interface IConfiguration<T extends IRepresentation> extends IDeepCopy  {
	void setUID(String UID);
	String getUID();
	
	void setProblemBaseDirectory(String directoryPath);
	String getProblemBaseDirectory();
	
	void setSaveAlgorithmStateDirectoryPath(String directoryPath);
	String getSaveAlgorithmStateDirectoyPath();
	
	void setAlgorithmStateFile(String fileName);
	String getSaveAlgorithmStateFile();
	
	void setSaveAlgorithmStateIterationInterval(int iterationInterval);
	int getSaveAlgorithmIterationInterval();
	
	
	void setRandomNumberGenerator(IRandomNumberGenerator randomNumberGenerator);
	IRandomNumberGenerator getRandomNumberGenerator();
	
	/**
	 * Gets the statistic configuration.
	 * 
	 * @return the statistic configuration
	 */
	StatisticsConfiguration getStatisticConfiguration();
	
	/**
	 * Sets the statistics configuration.
	 * 
	 * @param statisticsConfiguration the new statistics configuration
	 */
	void setStatisticsConfiguration(StatisticsConfiguration statisticsConfiguration);
    
    /**
     * Gets the termination criteria.
     * 
     * @return the termination criteria
     */
    ITerminationCriteria getTerminationCriteria();
    
    /**
     * Sets the termination criteria.
     * 
     * @param terminationCriteria the new termination criteria
     */
    void setTerminationCriteria(ITerminationCriteria terminationCriteria);
    
    /**
     * Verify configuration.
     * @throws InvalidEvaluationFunctionInputDataException 
     */
    void verifyConfiguration() throws InvalidConfigurationException;
    
    /**
     * Sets the evaluation function.
     * 
     * @param evaluationFunction the new evaluation function
     */
    void  setEvaluationFunction(IEvaluationFunction<T> evaluationFunction);
    
    /**
     * Gets the evaluation function.
     * 
     * @return the evaluation function
     */
    IEvaluationFunction<T> getEvaluationFunction();
	
	/**
	 * Gets the number of objectives.
	 * 
	 * @return the number of objectives
	 */
	Integer getNumberOfObjectives();
	
	ISolutionSet<T> getInitialPopulation();
	void setInitialPopulation(ISolutionSet<T> initialPopulation);
	
	int getPopulationSize();
	void setPopulationSize(int populationSize);
	
	IConfiguration<T> deepCopy() throws InvalidNumberOfIterationsException, Exception;
	

	List<IAlgorithmResultWriter<T>> getAlgorithmResultWriterList();
	void setAlgorithmResultWriterList(List<IAlgorithmResultWriter<T>> algorithmWriterList);
	
}