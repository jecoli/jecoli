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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.IOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class SimulatedAnnealingConfiguration.
 */
public class SimulatedAnnealingConfiguration<T extends IRepresentation,S extends ISolutionFactory<T>> extends AbstractConfiguration<T>{
	
	
	private static final long serialVersionUID = 7732621539664603707L;

	/** The mutation jecoliunittest.operators.operator container. */
	protected IOperatorContainer<IReproductionOperator<T,S>> mutationOperatorContainer;
	
	/** The solution factory. */
	protected S solutionFactory;
	
	protected IRandomNumberGenerator randomNumberGenerator;
	
	/**
	 * Instantiates a new simulated annealing configuration.
	 */
	public SimulatedAnnealingConfiguration(){
		doPopulationInitialization = true;
		populationSize = 1;
		numberOfObjectives = 1;
		statisticsConfiguration = new StatisticsConfiguration();
		randomNumberGenerator = new DefaultRandomNumberGenerator();
	}
	
	public SimulatedAnnealingConfiguration(long seed){
		doPopulationInitialization = true;
		populationSize = 1;
		numberOfObjectives = 1;
		statisticsConfiguration = new StatisticsConfiguration();
		randomNumberGenerator = new DefaultRandomNumberGenerator(seed);
	}
	
	/**
	 * Sets the mutation jecoliunittest.operators.operator container.
	 * 
	 * @param mutationOperatorContainer the new mutation jecoliunittest.operators.operator container
	 */
	public void setMutationOperatorContainer(IOperatorContainer<IReproductionOperator<T,S>> mutationOperatorContainer) {
		this.mutationOperatorContainer = mutationOperatorContainer;
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
	 * Sets the annealing schedule.
	 * 
	 * @param annealingSchedule the new annealing schedule
	 */
	public void setAnnealingSchedule(IAnnealingSchedule annealingSchedule) {
		this.annealingSchedule = annealingSchedule;
	}

	/** The annealing schedule. */
	protected IAnnealingSchedule annealingSchedule;
	

	/**
	 * Gets the solution factory.
	 * 
	 * @return the solution factory
	 */
	public S getSolutionFactory() {
		return solutionFactory;
	}

	/**
	 * Select mutation jecoliunittest.operators.operator.
	 * 
	 * @return the i reproduction jecoliunittest.operators.operator
	 */
	public IReproductionOperator<T,S> selectMutationOperator() {
		return mutationOperatorContainer.selectOperator();
	}

	/**
	 * Gets the anneling schedule.
	 * 
	 * @return the anneling schedule
	 */
	public IAnnealingSchedule getAnnelingSchedule() {
		return annealingSchedule;
	}

	@Override
	public void verifyConfiguration() throws InvalidConfigurationException, InvalidEvaluationFunctionInputDataException {
	    verifyDefaultConfigurationAttributes();
		if(solutionFactory == null)
			throw new InvalidConfigurationException("Solution Factory is null") ;
		
		if(terminationCriteria == null)
			throw new InvalidConfigurationException("Termination Criteria is null") ;
		
		if(evaluationFunction == null)
			throw new InvalidConfigurationException("Evaluation Function is null") ;
		
		if(mutationOperatorContainer == null)
			throw new InvalidConfigurationException("Operator mutation container is null") ;
		
		if(annealingSchedule == null)
			throw new InvalidConfigurationException("Annealing schedule is null") ;
		
	}

	
	@Override
	public SimulatedAnnealingConfiguration<T,S> deepCopy() throws Exception {
		SimulatedAnnealingConfiguration<T,S> configurationCopy = new SimulatedAnnealingConfiguration<T,S>();
		configurationCopy.setEvaluationFunction(evaluationFunction.deepCopy());
		configurationCopy.setTerminationCriteria(terminationCriteria.deepCopy());
		configurationCopy.setMutationOperatorContainer(mutationOperatorContainer.deepCopy());
		configurationCopy.setSolutionFactory((S)solutionFactory.deepCopy());
		configurationCopy.setRandomNumberGenerator(randomNumberGenerator.deepCopy());
		return configurationCopy;
	}


}
