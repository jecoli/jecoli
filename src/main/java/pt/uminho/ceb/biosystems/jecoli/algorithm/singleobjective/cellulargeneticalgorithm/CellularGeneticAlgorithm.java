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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;


// TODO: Auto-generated Javadoc
/**
 * The Class CellularGeneticAlgorithm.
 */
public class CellularGeneticAlgorithm<T extends IRepresentation,S extends ISolutionFactory<T>> extends AbstractAlgorithm<T,CellularGeneticAlgorithmConfiguration<T,S>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6424517469731117550L;

	/**
	 * Instantiates a new cellular genetic algorithm.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 * @throws InvalidEvaluationFunctionInputDataException 
	 */
	public CellularGeneticAlgorithm(CellularGeneticAlgorithmConfiguration<T,S> configuration) throws Exception {
		super(configuration);
	}


	@Override
	public ISolutionSet<T> initialize() throws Exception {
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		ISolutionFactory<T> solutionFactory = configuration.getSolutionFactory();
		int numberOfSolutions = configuration.getPopulationSize();
		ISolutionSet<T> solutionSet = solutionFactory.generateSolutionSet(numberOfSolutions,randomGenerator);
		evaluationFunction.evaluate(solutionSet);
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(solutionSet.getNumberOfSolutions());
		return solutionSet;
	}
	

	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception {
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		CellularAutomataType cellularAutomataType = configuration.getCellularAutomataType();
		
		ISolutionSet<T> newSolutionSet = null;
		
		if(cellularAutomataType == CellularAutomataType.SYNCHRONOUS)
			newSolutionSet = synchronousCellularAutomataGA(algorithmState,solutionSet);
		else
			newSolutionSet = asynchronousCellularAutomataGA(algorithmState,solutionSet,cellularAutomataType,randomGenerator);
		
		return newSolutionSet;
	}

	/**
	 * Asynchronous cellular automata ga.
	 * @param algorithmState 
	 * 
	 * @param solutionSet the solution set
	 * @param cellularAutomataType the cellular automata type
	 * 
	 * @return the i solution set
	 * 
	 * @throws Exception the exception
	 */
	private ISolutionSet<T> asynchronousCellularAutomataGA(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet, CellularAutomataType cellularAutomataType,IRandomNumberGenerator randomGenerator) throws Exception {
		
		switch (cellularAutomataType) {
			case ASYNCHRONOUS_UNIFORM_CHOICE:
				return asynchronousUniformChoiceAutomataGA(algorithmState,solutionSet,randomGenerator);
			case ASYNCHRONOUS_LINE_SWEEP:
				return asynchronousLineSweepAutomataGA(algorithmState,solutionSet);
			case ASYNCHRONOUS_NEW_RANDOM_SWEEP:
				return asynchronousRandomSweepAutomataGA(algorithmState,solutionSet);
			case ASYNCHRONOUS_FIXED_RANDOM_SWEEP:
				return asynchronousFixedRandomSweepAutomataGA(algorithmState,solutionSet);
		default:
			break;
		}
		
		return null;
	}

	/**
	 * Asynchronous fixed random sweep automata ga.
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution set
	 * 
	 * @throws Exception the exception
	 */
	protected ISolutionSet<T> asynchronousFixedRandomSweepAutomataGA(AlgorithmState<T> algorithmState,ISolutionSet<T> solutionSet) throws Exception{
		NeighborhoodType neighborhoodType = configuration.getNeighborhoodType();
		List<Integer> solutionIndexList = configuration.getSolutionIndexList();
		
		for(Integer solutionIndex:solutionIndexList){
			ISolution<T> solution = solutionSet.getSolution(solutionIndex);
			ISolution<T> newSolution = processSolution(algorithmState,solutionIndex,solution,solutionSet,neighborhoodType);
			solutionSet.setSolution(solutionIndex,newSolution);
		}
		
		return solutionSet;
	}

	/**
	 * Asynchronous random sweep automata ga.
	 * @param algorithmState 
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution set
	 * 
	 * @throws Exception the exception
	 */
	protected ISolutionSet<T> asynchronousRandomSweepAutomataGA(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception {
		NeighborhoodType neighborhoodType = configuration.getNeighborhoodType();
		List<Integer> solutionIndexList = configuration.getSolutionIndexList();
		
		for(Integer solutionIndex:solutionIndexList){
			ISolution<T> solution = solutionSet.getSolution(solutionIndex);
			ISolution<T> newSolution = processSolution(algorithmState,solutionIndex,solution,solutionSet,neighborhoodType);
			solutionSet.setSolution(solutionIndex,newSolution);
		}
		
		return solutionSet;
	}

	

	/**
	 * Asynchronous line sweep automata ga.
	 * @param algorithmState 
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution set
	 * 
	 * @throws Exception the exception
	 */
	protected ISolutionSet<T> asynchronousLineSweepAutomataGA(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception {
		NeighborhoodType neighborhoodType = configuration.getNeighborhoodType();
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		
		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<T> solution = solutionSet.getSolution(i);
			ISolution<T> newSolution = processSolution(algorithmState,i,solution,solutionSet,neighborhoodType);
			solutionSet.setSolution(i,newSolution);
		}
		
		return solutionSet;
	}

	/**
	 * Asynchronous uniform choice automata ga.
	 * @param algorithmState 
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution set
	 * 
	 * @throws Exception the exception
	 */
	protected ISolutionSet<T> asynchronousUniformChoiceAutomataGA(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet,IRandomNumberGenerator randomGenerator) throws Exception{
		NeighborhoodType neighborhoodType = configuration.getNeighborhoodType();
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		
		for(int i = 0;i < numberOfSolutions;i++){
			int solutionIndex = randomGenerator.nextInt(numberOfSolutions);
			ISolution<T> solution = solutionSet.getSolution(solutionIndex);
			ISolution<T> newSolution = processSolution(algorithmState,solutionIndex, solution,solutionSet,neighborhoodType);
			solutionSet.setSolution(solutionIndex,newSolution);
		}
		return solutionSet;
	}

	/**
	 * Synchronous cellular automata ga.
	 * @param algorithmState 
	 * 
	 * @param currentGenerationSolutionSet the current generation solution set
	 * 
	 * @return the i solution set
	 * 
	 * @throws Exception the exception
	 */
	protected ISolutionSet<T> synchronousCellularAutomataGA(AlgorithmState<T> algorithmState, ISolutionSet<T> currentGenerationSolutionSet) throws Exception{
		NeighborhoodType neighborhoodType = configuration.getNeighborhoodType();
		int numberOfSolutions = currentGenerationSolutionSet.getNumberOfSolutions();
		List<ISolution<T>> solutionList = new ArrayList<ISolution<T>>(numberOfSolutions);
		initializeList(solutionList,numberOfSolutions);
		
		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<T> solution = currentGenerationSolutionSet.getSolution(i); 
			ISolution<T> newSolution = processSolution(algorithmState,i,solution,currentGenerationSolutionSet,neighborhoodType);
			solutionList.set(i,newSolution);
		}
		
		ISolutionSet<T> nextGenerationSolutionSet = new SolutionSet<T>(solutionList);
		return nextGenerationSolutionSet;
	}

	/**
	 * Process solution.
	 * @param algorithmState 
	 * 
	 * @param index the index
	 * @param solution the solution
	 * @param solutionSet the solution set
	 * @param neighborhoodType the neighborhood type
	 * 
	 * @return the i solution
	 * 
	 * @throws Exception the exception
	 */
	protected ISolution<T> processSolution(AlgorithmState<T> algorithmState, int index,ISolution<T> solution,ISolutionSet<T> solutionSet,NeighborhoodType neighborhoodType) throws Exception {
		SolutionSet<T> neighborhoodSolutionSet = createNeighborhoodSet(index,solution,solutionSet,neighborhoodType);
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		ISelectionOperator<T> selectionOperator = configuration.getSelectionOperator();
		IReproductionOperator<T,S> reproductionOperator = configuration.selectReproductionOperator();
		
		
		int numberOfSolutionsToSelect = reproductionOperator.getNumberOfInputSolutions();
		IRandomNumberGenerator randomNumberGenerator = configuration.getRandomNumberGenerator();
		List<ISolution<T>> selectedSolutionsList = selectionOperator.selectSolutions(numberOfSolutionsToSelect,neighborhoodSolutionSet, true, randomNumberGenerator);
		S solutionFactory = configuration.getSolutionFactory();
		List<ISolution<T>> reproductionSolutionList = reproductionOperator.apply(selectedSolutionsList,solutionFactory,randomNumberGenerator);
	    evaluationFunction.evaluateSingleSolution(reproductionSolutionList.get(0));
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations();
	    return reproductionSolutionList.get(0);
	}

	/**
	 * Creates the neighborhood set.
	 * 
	 * @param index the index
	 * @param solution the solution
	 * @param solutionSet the solution set
	 * @param neighborhoodType the neighborhood type
	 * 
	 * @return the solution set
	 */
	protected SolutionSet<T> createNeighborhoodSet(int index,ISolution<T> solution,ISolutionSet<T> solutionSet, NeighborhoodType neighborhoodType) {
		int width = configuration.getWidth();
		int height = configuration.getHeight();
		
		
		if(neighborhoodType == NeighborhoodType.NEWS)
				return createNEWSSolutionSet(index,solutionSet,width,height);
		return createCompactSolutionSet(index,solutionSet,width,height);
	}
	
	

	/**
	 * Creates the compact solution set.
	 * 
	 * @param index the index
	 * @param solutionSet the solution set
	 * @param width the width
	 * @param height the height
	 * 
	 * @return the solution set
	 */
	protected SolutionSet<T> createCompactSolutionSet(int index,ISolutionSet<T> solutionSet, int width, int height) {
		List<ISolution<T>> neighborhoodSolutionList = new ArrayList<ISolution<T>>();
		int radius = configuration.getRadius();
		int currentX = index/width;
		int xLowerLimit = currentX - radius;
		int xUpperLimit = currentX + radius;
		int currentY = index % width;
		int yLowerLimit = currentY - radius;
		int yUpperLimit = currentY + radius;
		
		for(int i = xLowerLimit; i <= xUpperLimit;i++)
			for(int j = yLowerLimit; j <= yUpperLimit;j++)
				if((i != currentX) || (j != currentY))
					neighborhoodSolutionList.add(getSolution(solutionSet,i,currentY,width,height));
		
		return new SolutionSet<T>(neighborhoodSolutionList);
	}

	/**
	 * Creates the news solution set.
	 * 
	 * @param index the index
	 * @param solutionSet the solution set
	 * @param width the width
	 * @param height the height
	 * 
	 * @return the solution set
	 */
	private SolutionSet<T> createNEWSSolutionSet(int index,ISolutionSet<T> solutionSet,int width,int height) {
		List<ISolution<T>> neighborhoodSolutionList = new ArrayList<ISolution<T>>();
		int radius = configuration.getRadius();
		int currentX = index/width;
		int xLowerLimit = currentX - radius;
		int xUpperLimit = currentX + radius;
		int currentY = index % width;
		int yLowerLimit = currentY - radius;
		int yUpperLimit = currentY + radius;
		
		
		for(int i = xLowerLimit; i <= xUpperLimit;i++)
			if(i != currentX)
				neighborhoodSolutionList.add(getSolution(solutionSet,i,currentY,width,height));
		
		for(int j= yLowerLimit;j <= yUpperLimit;j++)
			if(j != currentY)
				neighborhoodSolutionList.add(getSolution(solutionSet,currentX,j,width,height));
		
		return new SolutionSet<T>(neighborhoodSolutionList);
	}

	
	
	/**
	 * Gets the solution.
	 * 
	 * @param solutionSet the solution set
	 * @param currentX the current x
	 * @param currentY the current y
	 * @param width the width
	 * @param height the height
	 * 
	 * @return the solution
	 */
	protected ISolution<T> getSolution(ISolutionSet<T> solutionSet,int currentX, int currentY,int width, int height){
		int solutionRadius = configuration.getRadius();
		int xValue = correctXValue(currentX,width,solutionRadius);
		int yValue = correctYValue(currentY,height,solutionRadius);
		int linearPosition = xValue + yValue*width;
		//System.out.println("X:"+xValue+" Y:"+yValue+" L:"+linearPosition);
		return solutionSet.getSolution(linearPosition);
	}

	/**
	 * Correct y value.
	 * 
	 * @param currentY the current y
	 * @param height the height
	 * @param solutionRadius the solution radius
	 * 
	 * @return the int
	 */
	protected int correctYValue(int currentY, int height, int solutionRadius) {
		if(currentY >= height)
			return currentY-height;
		
		if(currentY < 0)
			return currentY+height;
		
		return currentY;
	}

	/**
	 * Correct x value.
	 * 
	 * @param currentX the current x
	 * @param width the width
	 * @param solutionRadius the solution radius
	 * 
	 * @return the int
	 */
	protected int correctXValue(int currentX, int width, int solutionRadius) {
		if(currentX >= width)
			return currentX-width;
		
		if(currentX < 0)
			return currentX+width;
		
		return  currentX;
	}

	/**
	 * Initialize list.
	 * 
	 * @param solutionList the solution list
	 * @param numberOfPositions the number of positions
	 */
	protected void initializeList(List<ISolution<T>> solutionList,int numberOfPositions){
		for(int i = 0;i< numberOfPositions;i++)
			solutionList.add(null);
	}

	@Override
	public IAlgorithm<T> deepCopy() throws Exception {
		CellularGeneticAlgorithmConfiguration<T,S> configurationCopy = configuration.deepCopy();
		return new CellularGeneticAlgorithm<T,S>(configurationCopy);
	}

	

}
