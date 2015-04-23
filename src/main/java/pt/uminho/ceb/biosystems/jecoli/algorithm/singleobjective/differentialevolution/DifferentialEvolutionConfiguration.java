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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.differentialevolution;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class DifferentialEvolutionConfiguration.
 */
public class DifferentialEvolutionConfiguration extends AbstractConfiguration<ILinearRepresentation<Double>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4952314433701681521L;
	
	/** The solution factory. */
	protected ISolutionFactory<ILinearRepresentation<Double>> solutionFactory;

	/** The scale factor f. */
	protected double scaleFactorF;
	
	/** The crossover probability. */
	protected double crossoverProbability;
	
	/** The base vector selection type. */
	protected BaseVectorSelectionType baseVectorSelectionType;
	
	
	/**
	 * Instantiates a new differential evolution configuration.
	 */
	public DifferentialEvolutionConfiguration() 
	{	numberOfObjectives = 1;
		statisticsConfiguration = new StatisticsConfiguration();
	}
	

	/**
	 * Gets the scale factor f.
	 * 
	 * @return the scale factor f
	 */
	public double getScaleFactorF() {
		return scaleFactorF;
	}

	/**
	 * Sets the scale factor f.
	 * 
	 * @param scaleFactorF the new scale factor f
	 */
	public void setScaleFactorF(double scaleFactorF) {
		this.scaleFactorF = scaleFactorF;
	}


	/**
	 * Sets the solution factory.
	 * 
	 * @param solutionFactory the new solution factory
	 */
	public void setSolutionFactory(ISolutionFactory<ILinearRepresentation<Double>> solutionFactory) {
		this.solutionFactory = solutionFactory;
	}

	/**
	 * Sets the crossover probability.
	 * 
	 * @param crossoverProbability the new crossover probability
	 */
	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	/**
	 * Sets the base vector selection type.
	 * 
	 * @param baseVectorSelectionType the new base vector selection type
	 */
	public void setBaseVectorSelectionType(BaseVectorSelectionType baseVectorSelectionType) {
		this.baseVectorSelectionType = baseVectorSelectionType;
	}

	/**
	 * Gets the solution factory.
	 * 
	 * @return the solution factory
	 */
	public ISolutionFactory<ILinearRepresentation<Double>> getSolutionFactory(){
		return solutionFactory;
	}

	/**
	 * Gets the base vector selection type.
	 * 
	 * @return the base vector selection type
	 */
	public BaseVectorSelectionType getBaseVectorSelectionType(){
		return baseVectorSelectionType;
	}

	/**
	 * Gets the scale factor.
	 * 
	 * @return the scale factor
	 */
	public double getScaleFactor(){
		return scaleFactorF;
	}

	/**
	 * Gets the crossover probability.
	 * 
	 * @return the crossover probability
	 */
	public double getCrossoverProbability(){
		return crossoverProbability;
	}
	
	@Override
	public void verifyConfiguration() throws InvalidConfigurationException{
		verifyDefaultConfigurationAttributes();

		if(solutionFactory == null)
			throw new InvalidConfigurationException("Solution Factory is null") ;
		
		if(baseVectorSelectionType == null)
			throw new InvalidConfigurationException("Base Vector Selection Type is null") ;

		if((crossoverProbability < 0) || (crossoverProbability > 1))
			throw new InvalidConfigurationException("((crossoverProbability < 0) || (crossoverProbability > 1))") ;
		
		if(scaleFactorF < 0)
			throw new InvalidConfigurationException("scale Factor (F) < 0") ;
		
	}
	
	@Override
	public DifferentialEvolutionConfiguration deepCopy() throws Exception {
		DifferentialEvolutionConfiguration configurationCopy = new DifferentialEvolutionConfiguration();
		
		configurationCopy.setPopulationSize(populationSize);
		configurationCopy.setSolutionFactory(solutionFactory.deepCopy());
		configurationCopy.setEvaluationFunction(evaluationFunction.deepCopy());
		configurationCopy.setTerminationCriteria(terminationCriteria.deepCopy());
		configurationCopy.setDoPopulationInitialization(doPopulationInitialization);
		
		if(initialPopulation != null){
			ISolutionSet<ILinearRepresentation<Double>> initialPopulationCopy = copyInitialPopulation(initialPopulation,solutionFactory);
			configurationCopy.setInitialPopulation(initialPopulationCopy);
		}
		
		configurationCopy.setScaleFactorF(scaleFactorF);
		configurationCopy.setCrossoverProbability(crossoverProbability);
		configurationCopy.setBaseVectorSelectionType(baseVectorSelectionType);
		configurationCopy.setStatisticsConfiguration(statisticsConfiguration.deepCopy());
		
		configurationCopy.setUID(new String(UID));
		
		configurationCopy.setProblemBaseDirectory(new String(problemBaseDirectory));
		configurationCopy.setAlgorithmStateFile(new String(saveAlgorithmStateFile));
		configurationCopy.setSaveAlgorithmStateIterationInterval(new Integer(saveAlgorithmStateIterationInterval));
		configurationCopy.setSaveAlgorithmStateDirectoryPath(new String(saveAlgorithmStateDirectoryPath));
		
		List<IAlgorithmResultWriter<ILinearRepresentation<Double>>> algorithmResultWriterListCopy = new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Double>>>();
		for(IAlgorithmResultWriter<ILinearRepresentation<Double>> algorithmResultWriter:algorithmResultWriterList)
			algorithmResultWriterListCopy.add(algorithmResultWriter.deepCopy());
		
		configurationCopy.setAlgorithmResultWriterList(algorithmResultWriterListCopy);

		return configurationCopy;
	}
	
}
