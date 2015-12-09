/**
x* Copyright 2009,
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

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.IOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionCellContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.CrowdingComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.SolutionCellRankComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;


// TODO: Auto-generated Javadoc
/**
 * Non-Dominated Sorting Genetic Algorith II - Deb et al, 2002.
 * 
 * @author pmaia
 */
public class NSGAII<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractAlgorithm<T,NSGAIIConfiguration<T,S>>{

	private static final long serialVersionUID = 8007277025380868304L;

	/**
	 * Instantiates a new NSGAII.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	public NSGAII(NSGAIIConfiguration<T,S> configuration) throws Exception {
		super(configuration);
	}

	
//	@Override
	public ISolutionSet<T> initialize(AlgorithmState<T> algorithmState) throws Exception {
		boolean verifyPopulationInitialization = configuration.verifyPopulationInitialization();

		ISolutionSet<T> solutionSet = null;
		
		if(verifyPopulationInitialization)
			solutionSet =  generateInitialPopulation();

		configuration.setInitialPopulation(solutionSet);
		
		return solutionSet;
	}

	/**
	 * Generate initial population.
	 * 
	 * @return the i solution set
	 */
	protected ISolutionSet<T> generateInitialPopulation() {
		int populationSize = configuration.getPopulationSize();
		ISolutionFactory<T> factory = configuration.getSolutionFactory();
		IRandomNumberGenerator randomNumberGenerator = configuration.getRandomNumberGenerator();
		ISolutionSet<T> population = factory.generateSolutionSet(populationSize,randomNumberGenerator);
        return population;
	}
	
	
	@Override
	public IAlgorithmResult<T> run() throws Exception {
        ITerminationCriteria terminationCriteria = configuration.getTerminationCriteria();
        IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();

        AlgorithmState<T> algorithmState = new AlgorithmState<T>(this);
        algorithmState.initializeState();
        ISolutionSet<T> solutionSet = initialize(algorithmState);        
        evaluationFunction.evaluate(solutionSet);
        algorithmState.setSolutionSet(solutionSet);        

//        int it = 0;
        while (!terminationCriteria.verifyAlgorithmTermination(this,algorithmState)) {
            ISolutionSet<T> newSolutionSet = iteration(algorithmState,solutionSet);            
//            System.out.println("Iteration: "+it);
            solutionSet = newSolutionSet;
            updateState(algorithmState,solutionSet);
//            algorithmState.setCurrentIteration(it);
//            it++;
        }        
        
        Ranker<T> ranker = new Ranker<T>(solutionSet,true);
		solutionSet = ranker.getSubFront(0); //pff :P
		
		 /** set the final solution set to equal the archive */
        updateState(algorithmState,solutionSet);
        updateLastState(algorithmState,solutionSet);
		
		//print final results
		StringBuffer sb = new StringBuffer();
		sb.append(	"\n\nResults:\n"+
							"===================================================\n");
//		int i = 0;
		for(ISolution<T> sol: solutionSet.getListOfSolutions()){
			sb.append(
//					"Ind <"+i+">," +
					sol.getFitnessValue(0)+","+
					sol.getFitnessValue(1)+					
					"\n");
//			i++;
		}

		System.out.println(sb.toString());

        IAlgorithmResult<T> algorithmResult = algorithmState.getAlgorithmResult();

        algorithmState = null;

        return algorithmResult;
    }
	
	private void updateLastState(AlgorithmState<T> algorithmState,ISolutionSet<T> archive) {
		ISolutionContainer<T> container = new SolutionContainer<T>(configuration.getPopulationSize(),new SolutionCellRankComparator<SolutionCellContainer<T>>());
		container.addSpecificSolutions(
				algorithmState.getSolutionSet(),
				algorithmState.getCurrentIteration(),
				true);
		algorithmState.getAlgorithmResult().setSolutionContainer(container);
		
	}

	
	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet) throws Exception{
		
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();		
		
		ISolutionSet<T> newGeneration = new SolutionSet<T>();					
		
		RecombinationParameters recombinationParameters = configuration.getRecombinationParameters();	
		int offSpringSize = recombinationParameters.getOffspringSize();
		
		serialRecombination(solutionSet,newGeneration,true,offSpringSize);		
		evaluationFunction.evaluate(newGeneration);
		updateState(algorithmState,solutionSet);
		
		ISolutionSet<T> union = solutionSet.union(newGeneration);
		
		Ranker<T> ranker = new Ranker<T>(union, true);
		
		int remain = solutionSet.getNumberOfSolutions();
		
		int index = 0;
		ISolutionSet<T> front = null;
		solutionSet.removeAll();
		
		front = ranker.getSubFront(index);
		
		while((remain > 0) && (remain >= front.getNumberOfSolutions())){
			MOUtils.assignCrowdingDistance(front.getListOfSolutions(), true);
			
			for(int k = 0; k < front.getNumberOfSolutions(); k++)
				solutionSet.add(front.getSolution(k));
							
			remain -= front.getNumberOfSolutions();
			index++;
			if(remain > 0)
				front = ranker.getSubFront(index);
		}
		
		if(remain > 0){
			MOUtils.assignCrowdingDistance(front.getListOfSolutions(), true);
			front.sort(new CrowdingComparator<ISolution<T>>(),true,false);
			
			for(int k = 0; k< remain ; k++)
				solutionSet.add(front.getSolution(k));
			
			remain = 0;
		}
		
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(offSpringSize);
		
//		for(ISolution sol : newGeneration.getListOfSolutions())
//        	System.out.println("Fitness: "+sol.getFitnessValue(0)+","+sol.getFitnessValue(1)+","+sol.getFitnessValue(2)+","+sol.getScalarFitnessValue());
		
		return solutionSet;
	}
	
	
	/**
	 * Serial recombination.
	 * 
	 * @param solutionSet the solution set
	 * @param newGeneration the new generation
	 * @param isMaximization the is maximization
	 * @param offSpringSize the off spring size
	 * 
	 * @throws Exception the exception
	 */
	protected void serialRecombination(ISolutionSet<T> solutionSet,ISolutionSet<T> newGeneration, boolean isMaximization, int offSpringSize) throws Exception {
		
		int currentNumberOfIndividuals = 0;
		List<ISolution<T>> offspring = null;
		
		IOperatorContainer<IReproductionOperator<T,S>> crossoverOperators = configuration.getCrossoverOperatorsContainer();
		IOperatorContainer<IReproductionOperator<T,S>> mutationOperators = configuration.getMutationOperatorsContainer();
		
		ISelectionOperator<T> selectionOperator = configuration.getSelectionOperator();
		
		/** serial application of crossover and mutation to breed offspring */
		while(currentNumberOfIndividuals < offSpringSize){
			IReproductionOperator<T,S> cxOperator = crossoverOperators.selectOperator();
			
			int numberOfInputIndividuals = cxOperator.getNumberOfInputSolutions();
			
			List<ISolution<T>> parents = selectionOperator.selectSolutions(numberOfInputIndividuals, solutionSet, isMaximization, null);
			
			offspring = cxOperator.apply(parents, configuration.getSolutionFactory(),configuration.getRandomNumberGenerator());
			
			for(ISolution<T> sol : offspring) {
				
				List<ISolution<T>> auxPopulation = new ArrayList<ISolution<T>>();
				auxPopulation.add(sol);
				
				IReproductionOperator<T,S> mutOperator = mutationOperators.selectOperator();
				int numberOfOutputIndividualsFromMutation = mutOperator.getNumberOfOutputSolutions();
				
				auxPopulation = mutOperator.apply(auxPopulation, configuration.getSolutionFactory(),configuration.getRandomNumberGenerator());				
				currentNumberOfIndividuals += numberOfOutputIndividualsFromMutation;				
				newGeneration.add(auxPopulation);
				
			}
		}
		
	}

	@Override
	public ISolutionSet<T> initialize() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAlgorithm<T> deepCopy() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}