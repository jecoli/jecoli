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
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.speameme;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.IAnnealingSchedule;

// TODO: Auto-generated Javadoc
/**
 * The Class SPEA2Configuration.
 */
public class SPEAMEMEConfiguration<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractConfiguration<T> {

	private static final long serialVersionUID = 7134936685534641743L;
	
	/** The recombination parameters. */
	protected RecombinationParameters recombinationParameters;
	
	/** The solution factory. */
	protected S solutionFactory;
	
	/** The selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> selectionOperator;
	
	/** The evaluation function. */
	protected IEvaluationFunction<T> evaluationFunction;
	
	/** The annealing schedule. */
	protected IAnnealingSchedule annealingSchedule;
	
	/** The reproduction jecoliunittest.operators.operator container. */
	protected ReproductionOperatorContainer<T,S> reproductionOperatorContainer;
	
	/** The crossover operators container. */
	protected ReproductionOperatorContainer<T,S> crossoverOperatorsContainer;
	
	/** The mutation operators container. */
	protected ReproductionOperatorContainer<T,S> mutationOperatorsContainer;
	
	/** The survivor selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> survivorSelectionOperator;
	
	/** The fixed population size. */
	protected boolean fixedPopulationSize;
	
	/** The environmental selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> environmentalSelectionOperator;
	
	/** The maximum archive size. */
	protected int maximumArchiveSize;
	
	/** The initial archive. */
	protected ISolutionSet<T> initialArchive;

	/**
	 * Instantiates a new sPE a2 configuration.
	 */
	public SPEAMEMEConfiguration(){
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

	
	@Override
	public void verifyConfiguration() throws InvalidConfigurationException{

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

		if((fixedPopulationSize) && (populationSize != newPopulationSize))
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
		return reproductionOperatorContainer.selectOperator();
	}

	/**
	 * Sets the reproduction jecoliunittest.operators.operator container.
	 * 
	 * @param reproductionOperatorContainer the new reproduction jecoliunittest.operators.operator container
	 */
	public void setReproductionOperatorContainer(ReproductionOperatorContainer<T,S> reproductionOperatorContainer){
		this.reproductionOperatorContainer = reproductionOperatorContainer;
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
		return reproductionOperatorContainer.selectOperator();
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

	/**
	 * Gets the crossover operators container.
	 * 
	 * @return the crossover operators container
	 * 
	 * @throws Exception the exception
	 */
	public ReproductionOperatorContainer<T,S> getCrossoverOperatorsContainer() throws Exception {
		
		if(crossoverOperatorsContainer==null) //singleton
			crossoverOperatorsContainer = reproductionOperatorContainer.getSubSet(reproductionOperatorContainer.getIndexesForReproductionType(ReproductionOperatorType.CROSSOVER));
		
		return crossoverOperatorsContainer;
	}

	/**
	 * Sets the crossover operators container.
	 * 
	 * @param crossoverOperatorsContainer the new crossover operators container
	 */
	public void setCrossoverOperatorsContainer(ReproductionOperatorContainer<T,S> crossoverOperatorsContainer) {
		this.crossoverOperatorsContainer = crossoverOperatorsContainer;
	}

	/**
	 * Gets the mutation operators container.
	 * 
	 * @return the mutation operators container
	 * 
	 * @throws Exception the exception
	 */
	public ReproductionOperatorContainer<T,S> getMutationOperatorsContainer() throws Exception {

		if(mutationOperatorsContainer==null) //singleton
			mutationOperatorsContainer = reproductionOperatorContainer.getSubSet(reproductionOperatorContainer.getIndexesForReproductionType(ReproductionOperatorType.MUTATION));
		
		return mutationOperatorsContainer;
	}

	/**
	 * Sets the mutation operators container.
	 * 
	 * @param mutationOperatorsContainer the new mutation operators container
	 */
	public void setMutationOperatorsContainer(ReproductionOperatorContainer<T,S> mutationOperatorsContainer) {
		this.mutationOperatorsContainer = mutationOperatorsContainer;
	}

	@Override
	public SPEAMEMEConfiguration<T,S> deepCopy() throws Exception {
		//TODO
		return null;
	}
	
	public IEvaluationFunction<T> getEvaluationFunction() {
		return evaluationFunction;
	}

	
	public void setEvaluationFunction(IEvaluationFunction<T> evaluationFunction) {
		this.evaluationFunction = evaluationFunction;
	}

	public IAnnealingSchedule getAnnealingSchedule() {
		return annealingSchedule;
	}

	public void setAnnealingSchedule(IAnnealingSchedule annealingSchedule) {
		this.annealingSchedule = annealingSchedule;
	}
	
	public IReproductionOperator<T,S> selectMutationOperator() {
		return mutationOperatorsContainer.selectOperator();
	}


}