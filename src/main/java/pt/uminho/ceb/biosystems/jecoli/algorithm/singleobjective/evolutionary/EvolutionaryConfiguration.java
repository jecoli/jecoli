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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.niching.NichingConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.IOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class EvolutionaryConfiguration.
 */
public class EvolutionaryConfiguration<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractConfiguration<T> {
	
	private static final long serialVersionUID = 1L;

	/** The recombination parameters. */
	protected RecombinationParameters recombinationParameters;
	
	/** The solution factory. */
	protected S solutionFactory;
	
	/** The selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> selectionOperator;
	
	/** The reproduction jecoliunittest.operators.operator containter. */
	protected IOperatorContainer<IReproductionOperator<T,S>> reproductionOperatorContainter;
	
	/** The survivor selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> survivorSelectionOperator;
	
	/** The fixed population size. */
	//protected boolean fixedPopulationSize;

	/** The refresh evaluation. */
	protected boolean refreshEvalutation = false; // true if all individuals have to be reevalauted each generation 
	

	protected boolean isSteadyStateReplacement;

	protected boolean isNichingEnabled;

	protected NichingConfiguration<T> nichingConfiguration;//TODO por no validate
	/**
	 * Instantiates a new evolutionary configuration.
	 */
	public EvolutionaryConfiguration(){
		//fixedPopulationSize = true;
		doPopulationInitialization = true;
		isSteadyStateReplacement = false;
		this.statisticsConfiguration = new StatisticsConfiguration();
		numberOfObjectives = 1;
	}

	public NichingConfiguration<T> getNichingConfiguration() {
		return nichingConfiguration;
	}

	public void setNichingConfiguration(NichingConfiguration<T> nichingConfiguration) {
		this.nichingConfiguration = nichingConfiguration;
	}

	public boolean isNichingEnabled() {
		return isNichingEnabled;
	}

	public void setNichingEnabled(boolean isNichingEnabled) {
		this.isNichingEnabled = isNichingEnabled;
	}
//
//	/**
//	 * Sets the fixed population size.
//	 * 
//	 * @param fixedPopulationSize the new fixed population size
//	 */
//	protected void setFixedPopulationSize(boolean fixedPopulationSize){
//		this.fixedPopulationSize = fixedPopulationSize;
//	}

//	/**
//	 * Checks if is population size fixed.
//	 * 
//	 * @return true, if is population size fixed
//	 */
//	protected boolean isPopulationSizeFixed(){
//		return fixedPopulationSize;
//	}


	@Override
	public void verifyConfiguration() throws InvalidConfigurationException, InvalidEvaluationFunctionInputDataException {
		verifyDefaultConfigurationAttributes();
		
		if(statisticsConfiguration == null)
			throw new InvalidConfigurationException("Statistics Configuration is null") ;
			
		if(terminationCriteria == null)
			throw new InvalidConfigurationException("Termination Criteria is null") ;

		if(evaluationFunction == null)
			throw new InvalidConfigurationException("Evaluation Function is null") ;

		if(recombinationParameters == null)
			throw new InvalidConfigurationException("Recombination Parameters are null") ;

		if(selectionOperator == null)
			throw new InvalidConfigurationException("Selection Operator is null") ;

		if(survivorSelectionOperator == null)
			throw new InvalidConfigurationException("Survival Selection Operator is null") ;

		if(populationSize < 0)
			throw new InvalidConfigurationException("InitialPopulationSize < 0") ;

		if(!verifyRecombinationParametersValidity())
			throw new InvalidConfigurationException("Recombination Parameters are Invalid") ;

		

		if(reproductionOperatorContainter == null)
			throw new InvalidConfigurationException("Reproduction Operator Container is null") ;

		if(!reproductionOperatorContainter.isValid())
			throw new InvalidConfigurationException("Reproduction Operator Container is Invalid") ;

	}

	/**
	 * Verify recombination parameters validity.
	 * 
	 * @return true, if successful
	 */
	
//	FIXME: the offspringSize, numberOfSurvivors, newPopulationSize are never used...
	protected boolean verifyRecombinationParametersValidity(){
		int elitismValue = recombinationParameters.getElitismValue();
//		int offspringSize = recombinationParameters.getOffspringSize();
//		int numberOfSurvivors = recombinationParameters.getNumberOfSurvivors();
//		int newPopulationSize = elitismValue + offspringSize + numberOfSurvivors;

		if(elitismValue < 0)
			return false;

		//if((fixedPopulationSize) && (populationSize != newPopulationSize))
		//	return false;

		return true;
	}


	/**
	 * Gets the recombination parameters.
	 * 
	 * @return the recombination parameters
	 */
	public RecombinationParameters getRecombinationParameters() {
		return recombinationParameters;
	}

    /**
     * Sets the recombination parameters.
     * 
     * @param recombinationParameters the new recombination parameters
     */
    public void setRecombinationParameters(RecombinationParameters recombinationParameters) {
		this.recombinationParameters = recombinationParameters;
	}


	/**
	 * Gets the solution factory.
	 * 
	 * @return the solution factory
	 */
	public S getSolutionFactory() {
		return solutionFactory;
	}

	/**
	 * Sets the solution factory.
	 * 
	 * @param solutionFactory the new solution factory
	 */
	public void setSolutionFactory(S solutionFactory) {
		this.solutionFactory = solutionFactory;
	}


	/**
	 * Gets the selection jecoliunittest.operators.operator.
	 * 
	 * @return the selection jecoliunittest.operators.operator
	 */
	public ISelectionOperator<T> getSelectionOperator() {
		return selectionOperator;
	}


	/**
	 * Sets the selection jecoliunittest.operators.operator.
	 * 
	 * @param selectionOperator the new selection jecoliunittest.operators.operator
	 */
	public void setSelectionOperator(ISelectionOperator<T> selectionOperator) {
		this.selectionOperator = selectionOperator;
	}

	/**
	 * Gets the reproduction jecoliunittest.operators.operator.
	 * 
	 * @return the reproduction jecoliunittest.operators.operator
	 */
	public IReproductionOperator<T,S> getReproductionOperator() {
		return reproductionOperatorContainter.selectOperator();
	}

	/**
	 * Sets the reproduction jecoliunittest.operators.operator container.
	 * 
	 * @param reproductionOperatorContainer the new reproduction jecoliunittest.operators.operator container
	 */
	public void setReproductionOperatorContainer(IOperatorContainer<IReproductionOperator<T,S>> reproductionOperatorContainer){
		this.reproductionOperatorContainter = reproductionOperatorContainer;
	}

	/**
	 * Sets the population initialization.
	 * 
	 * @param doPopulationInitialization the new population initialization
	 */
	public void setPopulationInitialization(boolean doPopulationInitialization){
		this.doPopulationInitialization = doPopulationInitialization;
	}

	/**
	 * Verify population initialization.
	 * 
	 * @return true, if successful
	 */
	public boolean verifyPopulationInitialization() {
		return doPopulationInitialization;
	}


	

	/**
	 * Select reproduction jecoliunittest.operators.operator.
	 * 
	 * @return the i reproduction jecoliunittest.operators.operator
	 */
	public IReproductionOperator<T,S> selectReproductionOperator() {
		return reproductionOperatorContainter.selectOperator();
	}

	/**
	 * Sets the survivor selection jecoliunittest.operators.operator.
	 * 
	 * @param survivorSelectionOperator the new survivor selection jecoliunittest.operators.operator
	 */
	public void setSurvivorSelectionOperator(ISelectionOperator<T> survivorSelectionOperator){
		this.survivorSelectionOperator = survivorSelectionOperator;
	}

	/**
	 * Sets the selection operators.
	 * 
	 * @param selectionOperator the new selection operators
	 */
	public void setSelectionOperators (ISelectionOperator<T> selectionOperator)
	{
		this.survivorSelectionOperator = selectionOperator;
		this.selectionOperator = selectionOperator;
	}
	
	/**
	 * Select survivor selection jecoliunittest.operators.operator.
	 * 
	 * @return the i selection jecoliunittest.operators.operator
	 */
	public ISelectionOperator<T> selectSurvivorSelectionOperator() {
		return survivorSelectionOperator;
	}
	
	/**
	 * Gets the refresh evalutation.
	 * 
	 * @return the refresh evalutation
	 */
	public boolean getRefreshEvalutation() {
		return refreshEvalutation;
	}

	/**
	 * Sets the refresh evalutation.
	 * 
	 * @param refreshEvalutation the new refresh evalutation
	 */
	public void setRefreshEvalutation(boolean refreshEvalutation) {
		this.refreshEvalutation = refreshEvalutation;
	}
	
	public synchronized EvolutionaryConfiguration<T,S> deepCopy() throws Exception{
		EvolutionaryConfiguration<T,S> configurationCopy = new EvolutionaryConfiguration<T,S>();
		configurationCopy.setStatisticsConfiguration(statisticsConfiguration.deepCopy());
		configurationCopy.setRandomNumberGenerator(randomNumberGenerator.deepCopy());
		configurationCopy.setSteadyStateReplacement(isSteadyStateReplacement);
		configurationCopy.setRecombinationParameters(recombinationParameters.deepCopy());
		
		configurationCopy.setSolutionFactory((S)solutionFactory.deepCopy());
		configurationCopy.setPopulationSize(populationSize);
		configurationCopy.setSelectionOperator(selectionOperator.deepCopy());
		configurationCopy.setEvaluationFunction(evaluationFunction.deepCopy());
		configurationCopy.setReproductionOperatorContainer(reproductionOperatorContainter.deepCopy());
		
		configurationCopy.setPopulationInitialization(doPopulationInitialization);
		
		if(initialPopulation != null){
			ISolutionSet<T> initialPopulationCopy  = copyInitialPopulation(initialPopulation,solutionFactory);
			configurationCopy.setInitialPopulation(initialPopulationCopy);
		}
		
		configurationCopy.setUID(new String(UID));
		
		configurationCopy.setProblemBaseDirectory(new String(problemBaseDirectory));
		configurationCopy.setAlgorithmStateFile(new String(saveAlgorithmStateFile));
		configurationCopy.setSaveAlgorithmStateIterationInterval(new Integer(saveAlgorithmStateIterationInterval));
		configurationCopy.setSaveAlgorithmStateDirectoryPath(new String(saveAlgorithmStateDirectoryPath));
		
		List<IAlgorithmResultWriter<T>> algorithmResultWriterListCopy = new ArrayList<IAlgorithmResultWriter<T>>();
		for(IAlgorithmResultWriter<T> algorithmResultWriter:algorithmResultWriterList)
			algorithmResultWriterListCopy.add(algorithmResultWriter.deepCopy());
		
		configurationCopy.setAlgorithmResultWriterList(algorithmResultWriterListCopy);
		configurationCopy.setSurvivorSelectionOperator(survivorSelectionOperator.deepCopy());
		configurationCopy.setTerminationCriteria(terminationCriteria.deepCopy());
		//configurationCopy.setFixedPopulationSize(fixedPopulationSize);
		configurationCopy.setRefreshEvalutation(refreshEvalutation);
		
		return configurationCopy;
	}

	public void setSteadyStateReplacement(boolean isSteadyStateReplacement) {
		this.isSteadyStateReplacement = isSteadyStateReplacement;
	}

	public boolean steadyStateReplacement() {
		return isSteadyStateReplacement;
	}

	
}