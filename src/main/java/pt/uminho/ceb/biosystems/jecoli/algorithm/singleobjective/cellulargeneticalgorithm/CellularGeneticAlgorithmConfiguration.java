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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
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


// TODO: Auto-generated Javadoc
/**
 * The Class CellularGeneticAlgorithmConfiguration.
 */
public class CellularGeneticAlgorithmConfiguration<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractConfiguration<T> {
	
	private static final long serialVersionUID = 1L;

	/** The statistics configuration. */
	protected StatisticsConfiguration statisticsConfiguration;
	
	/** The radius. */
	protected int radius;
	
	/** The solution factory. */
	protected S solutionFactory;
	
	/** The neighborhood type. */
	protected NeighborhoodType neighborhoodType;
	
	/** The cellular automata type. */
	protected CellularAutomataType cellularAutomataType;
	
	/** The reproduction jecoliunittest.operators.operator container. */
	protected IOperatorContainer<IReproductionOperator<T,S>> reproductionOperatorContainer;
	
	/** The selection jecoliunittest.operators.operator. */
	protected ISelectionOperator<T> selectionOperator;
	
	/** The width. */
	protected int width;
	
	/** The height. */
	protected int height;
	
	/** The solution index list. */
	protected List<Integer> solutionIndexList;
	
	/** The do population initialization. */
	protected boolean doPopulationInitialization;
	
	/** The fixed population size. */
	protected boolean fixedPopulationSize;
	
	/**
	 * Instantiates a new cellular genetic algorithm configuration.
	 */
	public CellularGeneticAlgorithmConfiguration(){
		fixedPopulationSize = true;
		doPopulationInitialization = true;
		statisticsConfiguration = new StatisticsConfiguration();
		numberOfObjectives = 1;
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
	 * Gets the population size.
	 * 
	 * @return the population size
	 */
	public int getPopulationSize() {
		return width*height;
	}

	/**
	 * Gets the neighborhood type.
	 * 
	 * @return the neighborhood type
	 */
	public NeighborhoodType getNeighborhoodType() {
		return neighborhoodType;
	}

	/**
	 * Gets the cellular automata type.
	 * 
	 * @return the cellular automata type
	 */
	public CellularAutomataType getCellularAutomataType() {
		return cellularAutomataType;
	}

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
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
	 * Select reproduction jecoliunittest.operators.operator.
	 * 
	 * @return the i reproduction jecoliunittest.operators.operator
	 */
	public IReproductionOperator<T,S> selectReproductionOperator(){
		return reproductionOperatorContainer.selectOperator();
	}

	/**
	 * Gets the reproduction jecoliunittest.operators.operator container.
	 * 
	 * @return the reproduction jecoliunittest.operators.operator container
	 */
	public IOperatorContainer<IReproductionOperator<T,S>> getReproductionOperatorContainer() {
		return reproductionOperatorContainer;
	}

	/**
	 * Sets the reproduction jecoliunittest.operators.operator container.
	 * 
	 * @param reproductionOperatorContainer the new reproduction jecoliunittest.operators.operator container
	 */
	public void setReproductionOperatorContainer(IOperatorContainer<IReproductionOperator<T,S>> reproductionOperatorContainer) {
		this.reproductionOperatorContainer = reproductionOperatorContainer;
	}

	/**
	 * Checks if is do population initialization.
	 * 
	 * @return true, if is do population initialization
	 */
	public boolean isDoPopulationInitialization() {
		return doPopulationInitialization;
	}

	/**
	 * Sets the do population initialization.
	 * 
	 * @param doPopulationInitialization the new do population initialization
	 */
	public void setDoPopulationInitialization(boolean doPopulationInitialization) {
		this.doPopulationInitialization = doPopulationInitialization;
	}

	/**
	 * Checks if is fixed population size.
	 * 
	 * @return true, if is fixed population size
	 */
	public boolean isFixedPopulationSize() {
		return fixedPopulationSize;
	}

	/**
	 * Sets the fixed population size.
	 * 
	 * @param fixedPopulationSize the new fixed population size
	 */
	public void setFixedPopulationSize(boolean fixedPopulationSize) {
		this.fixedPopulationSize = fixedPopulationSize;
	}

	/**
	 * Gets the solution index list.
	 * 
	 * @return the solution index list
	 */
	public List<Integer> getSolutionIndexList() {
		int populationSize = getPopulationSize();
		
		if(cellularAutomataType == CellularAutomataType.ASYNCHRONOUS_NEW_RANDOM_SWEEP)
		return generateIndexList(populationSize);
		
		if(cellularAutomataType == CellularAutomataType.ASYNCHRONOUS_FIXED_RANDOM_SWEEP){
			if(solutionIndexList == null)
				solutionIndexList = generateIndexList(populationSize);
			
			return solutionIndexList;
		}
		
		return null;
	}
	
	/**
	 * Generate index list.
	 * 
	 * @param numberOfSolutions the number of solutions
	 * 
	 * @return the list
	 */
	protected List<Integer> generateIndexList(int numberOfSolutions) {
		List<Integer> indexList = new ArrayList<Integer>(numberOfSolutions);
		List<Integer> randomIndexList = new ArrayList<Integer>(numberOfSolutions);
		
		for(int i=0;i < numberOfSolutions;i++)
			indexList.add(i);
		
		for(int i=0;i<numberOfSolutions;i++){
			int randomPosition = (int) (Math.random()*indexList.size());
			int solutionIndex = indexList.get(randomPosition);
			randomIndexList.add(solutionIndex);
		}
			
		return randomIndexList;
	}
	

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the radius.
	 * 
	 * @param radius the new radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
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
	 * Sets the neighborhood type.
	 * 
	 * @param neighborhoodType the new neighborhood type
	 */
	public void setNeighborhoodType(NeighborhoodType neighborhoodType) {
		this.neighborhoodType = neighborhoodType;
	}

	/**
	 * Sets the cellular automata type.
	 * 
	 * @param cellularAutomataType the new cellular automata type
	 */
	public void setCellularAutomataType(CellularAutomataType cellularAutomataType) {
		this.cellularAutomataType = cellularAutomataType;
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
	 * Sets the width.
	 * 
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void verifyConfiguration() throws InvalidConfigurationException{
		if(statisticsConfiguration == null)
			throw new InvalidConfigurationException("Statistics Configuration is null") ;
		
		if(terminationCriteria == null)
			throw new InvalidConfigurationException("Termination Criteria is null") ;
		
		if(evaluationFunction == null)
			throw new InvalidConfigurationException("Evaluation Function is null") ;
		
		if(solutionFactory == null)
			throw new InvalidConfigurationException("Solution Factory is null") ;
		
		if(selectionOperator == null)
			throw new InvalidConfigurationException("Selection Operator is null") ;
		
		if(reproductionOperatorContainer == null)
			throw new InvalidConfigurationException("Reproduction Operator Container is null") ;
	
		if(!reproductionOperatorContainer.isValid())
			throw new InvalidConfigurationException("Invalid Reproduction Operator Container") ;
		
		if((doPopulationInitialization == false) && (initialPopulation == null))
			throw new InvalidConfigurationException("Initial Population not Defined") ;
		
		if(neighborhoodType == null)
			throw new InvalidConfigurationException("Neighborhood type is null") ;
		
		if(getPopulationSize() <= 0)
			throw new InvalidConfigurationException("Population Size <= 0") ;
		
		if(radius <= 0)
			throw new InvalidConfigurationException("Radius <= 0") ;
		
		if(width <= 0)
			throw new InvalidConfigurationException("Width <= 0") ;
		
		if(height <= 0)
			throw new InvalidConfigurationException("height <= 0") ;
		
		if ((width - radius <= 1) ||(height - radius <= 1))
			throw new InvalidConfigurationException("((width - radius <= 1) ||(height - radius <= 1))");	
	}


	public CellularGeneticAlgorithmConfiguration<T,S> deepCopy() throws Exception {
		CellularGeneticAlgorithmConfiguration<T,S> configurationCopy = new CellularGeneticAlgorithmConfiguration<T,S>();
		StatisticsConfiguration statisticsConfigurationCopy = statisticsConfiguration.deepCopy();
		configurationCopy.setStatisticsConfiguration(statisticsConfigurationCopy);
		
		configurationCopy.setRadius(radius);
		
		IEvaluationFunction<T> evaluationFunctionCopy = evaluationFunction.deepCopy();
		configurationCopy.setEvaluationFunction(evaluationFunctionCopy);
		
		ITerminationCriteria terminationCriteriaCopy = terminationCriteria.deepCopy();
		configurationCopy.setTerminationCriteria(terminationCriteriaCopy);
		
		ISolutionFactory<T> solutionFactoryCopy = solutionFactory.deepCopy();
		configurationCopy.setSolutionFactory((S)solutionFactoryCopy);
	
		configurationCopy.setNeighborhoodType(neighborhoodType);
		
		configurationCopy.setCellularAutomataType(cellularAutomataType);
		
		IOperatorContainer<IReproductionOperator<T,S>> reproductionOperatorContainerCopy = reproductionOperatorContainer.deepCopy();
		configurationCopy.setReproductionOperatorContainer(reproductionOperatorContainerCopy);
		
		ISelectionOperator<T> selectionOperatorCopy = selectionOperator.deepCopy();
		configurationCopy.setSelectionOperator(selectionOperatorCopy);
		
		configurationCopy.setWidth(width);
		
		configurationCopy.setHeight(height);
		
		List<Integer> solutionIndexListCopy = copySolutionIndexList(); 
		configurationCopy.solutionIndexList = solutionIndexListCopy;
		
		configurationCopy.setDoPopulationInitialization(doPopulationInitialization);
		
		configurationCopy.setFixedPopulationSize(fixedPopulationSize);
		
		if(initialPopulation != null){
			ISolutionSet<T> initialSolutionSetCopy = copyInitialPopulation(initialPopulation,solutionFactory);
			configurationCopy.setInitialPopulation(initialSolutionSetCopy);
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
		
		return configurationCopy;
	}

	
	protected List<Integer> copySolutionIndexList(){
		List<Integer> solutionIndexCopy = new ArrayList<Integer>();
		
		for(Integer index:solutionIndexList){
			int indexCopy = index;
			solutionIndexCopy.add(indexCopy);
		}
		
		return solutionIndexCopy;
	}

}
