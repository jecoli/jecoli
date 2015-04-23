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
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOSA;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.IOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.IAnnealingSchedule;

// TODO: Auto-generated Javadoc
/**
 * The Class SPEA2Configuration.
 */
public class MOSAConfiguration<T extends IRepresentation,S extends ISolutionFactory<T>> extends AbstractConfiguration<T> {

	private static final long serialVersionUID = 2213364825807521887L;

	/** The statistics configuration. */
	protected StatisticsConfiguration statisticsConfiguration;
	
	/** The recombination parameters. */
	protected RecombinationParameters recombinationParameters;
	
	/** The solution factory. */
	protected S solutionFactory;
	
	/** The initial population size. */
	protected int initialPopulationSize;
	
	/** The selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> selectionOperator;
	
	/** The evaluation function. */
	protected IEvaluationFunction<T> evaluationFunction;
	
	/** The mutation operators container. */
	protected IOperatorContainer<IReproductionOperator<T,S>> mutationOperatorsContainer;

	/** The annealing schedule. */
	protected IAnnealingSchedule annealingSchedule;
	
	/** The do population initialization. */
	protected boolean doPopulationInitialization;	
	
	/** The survivor selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> survivorSelectionOperator;
	
	/** The termination criteria. */
	protected ITerminationCriteria terminationCriteria;
	
	/** The fixed population size. */
	protected boolean fixedPopulationSize;
	
	/** The number of objectives. */
	protected int numberOfObjectives;
	
	/** The environmental selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> environmentalSelectionOperator;
	
	/** The maximum archive size. */
	protected int maximumArchiveSize;

	/** The initial population. */
	protected ISolutionSet<T> initialPopulation;
	
	/** The initial archive. */
	protected ISolutionSet<T> initialArchive;

	
	public MOSAConfiguration(){
		fixedPopulationSize = true;
		doPopulationInitialization = true;
	}

	/**
	 * Sets the fixed population size.
	 * 
	 * @param fixedPopulationSize the new fixed population size
	 */
	protected void setFixedPopulationSize(boolean fixedPopulationSize){
		this.fixedPopulationSize = fixedPopulationSize;
	}

	/**
	 * Checks if is population size fixed.
	 * 
	 * @return true, if is population size fixed
	 */
	protected boolean isPopulationSizeFixed(){
		return fixedPopulationSize;
	}

	/* (non-Javadoc)
	 * @see core.interfaces.IConfiguration#getTerminationCriteria()
	 */
	@Override
	public ITerminationCriteria getTerminationCriteria() {
		return terminationCriteria;
	}

	/* (non-Javadoc)
	 * @see core.interfaces.IConfiguration#setTerminationCriteria(core.interfaces.ITerminationCriteria)
	 */
	public void setTerminationCriteria(ITerminationCriteria terminationCriteria){
		this.terminationCriteria = terminationCriteria;
	}


	/* (non-Javadoc)
	 * @see core.interfaces.IConfiguration#verifyConfiguration()
	 */
	@Override
	public void verifyConfiguration() throws InvalidConfigurationException{

		//		if(terminationCriteria == null)
		//            return false;
		//
		//		if(evaluationFunction == null)
		//			return false;
		//
		//		if(recombinationParameters == null)
		//			return false;
		//
		//		if(selectionOperator == null)
		//			return false;
		//
		//		if(survivorSelectionOperator == null)
		//			return false;
		//
		//		if(initialPopulationSize < 0)
		//			return false;
		//
		//		if(!verifyRecombinationParametersValidity())
		//			return false;
		//
		//		if((doPopulationInitialization == false) && (initialPopulation == null))
		//			return false;
		//
		//		if(reproductionOperatorContainter == null)
		//			return false;
		//
		//		if(!reproductionOperatorContainter.isValid())
		//			return false;

	}

	/**
	 * Verify recombination parameters validity.
	 * 
	 * @return true, if successful
	 */
	protected boolean verifyRecombinationParametersValidity(){
		int elitismValue = recombinationParameters.getElitismValue();
		int offspringSize = recombinationParameters.getOffspringSize();
		int numberOfSurvivors = recombinationParameters.getNumberOfSurvivors();
		int newPopulationSize = elitismValue + offspringSize + numberOfSurvivors;

		if(elitismValue < 0)
			return false;

		if((fixedPopulationSize) && (initialPopulationSize != newPopulationSize))
			return false;

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
	 * Gets the initial population size.
	 * 
	 * @return the initial population size
	 */
	public int getInitialPopulationSize() {
		return initialPopulationSize;
	}

	/**
	 * Sets the initial population size.
	 * 
	 * @param initialPopulationSize the new initial population size
	 */
	public void setInitialPopulationSize(int initialPopulationSize) {
		this.initialPopulationSize = initialPopulationSize;
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

	
	public IEvaluationFunction<T> getEvaluationFunction() {
		return evaluationFunction;
	}

	
	public void setEvaluationFunction(IEvaluationFunction<T> evaluationFunction) {
		this.evaluationFunction = evaluationFunction;
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
	 * Gets the user initial population.
	 * 
	 * @return the user initial population
	 */
	public ISolutionSet<T> getUserInitialPopulation() {
		return initialPopulation;
	}

	/**
	 * Sets the user initial population.
	 * 
	 * @param initialPopulation the new user initial population
	 */
	public void setUserInitialPopulation(ISolutionSet<T> initialPopulation){
		this.initialPopulation = initialPopulation;
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
	 * Select survivor selection jecoliunittest.operators.operator.
	 * 
	 * @return the i selection jecoliunittest.operators.operator
	 */
	public ISelectionOperator<T> selectSurvivorSelectionOperator() {
		return survivorSelectionOperator;
	}

	@Override
	public Integer getNumberOfObjectives() {
		return numberOfObjectives;
	}

	/**
	 * Sets the number of objectives.
	 * 
	 * @param numObjectives the new number of objectives
	 */
	public void setNumberOfObjectives(int numObjectives){
		this.numberOfObjectives = numObjectives;
	}

	/**
	 * Gets the environmental selection jecoliunittest.operators.operator.
	 * 
	 * @return the environmental selection jecoliunittest.operators.operator
	 */
	public ISelectionOperator<T> getEnvironmentalSelectionOperator() {
		return environmentalSelectionOperator;
	}

	/**
	 * Sets the environmental selection jecoliunittest.operators.operator.
	 * 
	 * @param environmentalSelectionOperator the new environmental selection jecoliunittest.operators.operator
	 */
	public void setEnvironmentalSelectionOperator(ISelectionOperator<T> environmentalSelectionOperator) {
		this.environmentalSelectionOperator = environmentalSelectionOperator;
	}

	/**
	 * Gets the maximum archive size.
	 * 
	 * @return the maximum archive size
	 */
	public int getMaximumArchiveSize() {
		return maximumArchiveSize;
	}

	/**
	 * Sets the maximum archive size.
	 * 
	 * @param maximumArchiveSize the new maximum archive size
	 */
	public void setMaximumArchiveSize(int maximumArchiveSize) {
		this.maximumArchiveSize = maximumArchiveSize;
	}

	@Override
	public StatisticsConfiguration getStatisticConfiguration() {
		return statisticsConfiguration;
	}

	@Override
	public void setStatisticsConfiguration(StatisticsConfiguration statisticsConfiguration) {
		this.statisticsConfiguration = statisticsConfiguration;

	}

	public IReproductionOperator<T,S> selectMutationOperator() {
		return mutationOperatorsContainer.selectOperator();
	}
	
	/**
	 * Gets the mutation operators container.
	 * 
	 * @return the mutation operators container
	 * 
	 * @throws Exception the exception
	 */
	public IOperatorContainer<IReproductionOperator<T,S>> getMutationOperatorsContainer() throws Exception {
		return mutationOperatorsContainer;
	}

	/**
	 * Sets the mutation operators container.
	 * 
	 * @param mutationOperatorsContainer the new mutation operators container
	 */
	public void setMutationOperatorsContainer(IOperatorContainer<IReproductionOperator<T,S>> mutationOperatorsContainer) {
		this.mutationOperatorsContainer = mutationOperatorsContainer;
	}

	public IAnnealingSchedule getAnnealingSchedule() {
		return annealingSchedule;
	}

	public void setAnnealingSchedule(IAnnealingSchedule annealingSchedule) {
		this.annealingSchedule = annealingSchedule;
	}

	@Override
	public IConfiguration<T> deepCopy() throws InvalidNumberOfIterationsException, Exception {
		return null;
	}



}