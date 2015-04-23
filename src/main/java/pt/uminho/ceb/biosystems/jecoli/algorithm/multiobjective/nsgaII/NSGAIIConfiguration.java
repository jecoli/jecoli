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
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.nsgaII;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;

// TODO: Auto-generated Javadoc
/**
 * The Class NSGAIIConfiguration.
 */
public class NSGAIIConfiguration<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractConfiguration<T> {

	private static final long serialVersionUID = 6466088543144715394L;
	
	/** The recombination parameters. */
	protected RecombinationParameters recombinationParameters;
	
	/** The solution factory. */
	protected S solutionFactory;
	
	/** The selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> selectionOperator;
	
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

	/**
	 * Instantiates a new nSGAII configuration.
	 */
	public NSGAIIConfiguration(){
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
	public void verifyConfiguration() throws InvalidConfigurationException {

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
	 * Gets the crossover operators container.
	 * 
	 * @return the crossover operators container
	 * 
	 * @throws Exception the exception
	 */
	public ReproductionOperatorContainer<T,S> getCrossoverOperatorsContainer() throws Exception {
		
		if(crossoverOperatorsContainer==null)
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

		if(mutationOperatorsContainer==null)
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
	public NSGAIIConfiguration<T,S> deepCopy() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}