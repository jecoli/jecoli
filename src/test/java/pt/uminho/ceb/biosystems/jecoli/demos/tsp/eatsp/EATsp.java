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
package pt.uminho.ceb.biosystems.jecoli.demos.tsp.eatsp;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionCellContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticTypeMask;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;
import pt.uminho.ceb.biosystems.jecoli.demos.tsp.libtsp.EucTsp;
import pt.uminho.ceb.biosystems.jecoli.demos.tsp.libtsp.Tsp;


/**
 * The Class EATsp: implements EAs to solve the TSP problem.
 */
public class EATsp {

	/** The problem instance. */
	Tsp problemInstance;
	
	/** The algorithm. */
	IAlgorithm<PermutationRepresentation> algorithm;
	
	/** The results. */
	IAlgorithmResult<PermutationRepresentation> results;
	
	/** The statistics. */
	IAlgorithmStatistics<PermutationRepresentation> statistics;
	
	/**
	 * Instantiates a new eA tsp.
	 * 
	 * @param problemInstance the problem instance
	 */
	public EATsp(Tsp problemInstance)
	{
		this.problemInstance = problemInstance;
	}
	

	/**
	 * Configure ea.
	 * 
	 * @param populationSize the population size
	 * @param numberGenerations the number generations
	 * 
	 * @throws Exception the exception
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	public void configureEA (int populationSize, int numberGenerations) throws Exception, InvalidConfigurationException
	{
		EvolutionaryConfiguration<PermutationRepresentation,PermutationRepresentationFactory> configuration = new EvolutionaryConfiguration<PermutationRepresentation,PermutationRepresentationFactory>();
				
		IEvaluationFunction<PermutationRepresentation> evaluationFunction = new TspEvaluationFunction(problemInstance);
		configuration.setEvaluationFunction(evaluationFunction);
		
		int solutionSize = problemInstance.getDimension();
		PermutationRepresentationFactory solutionFactory = new PermutationRepresentationFactory(solutionSize);
		configuration.setSolutionFactory(solutionFactory);

		configuration.setPopulationSize(populationSize);
		ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
		configuration.setTerminationCriteria(terminationCriteria);
		
		configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
		configuration.setProblemBaseDirectory("nullDirectory");
		configuration.setAlgorithmStateFile("nullFile");
		configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
		configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<PermutationRepresentation>>());
		configuration.setStatisticsConfiguration(new StatisticsConfiguration());
		configuration.getStatisticsConfiguration().setVerbose(true);
		RecombinationParameters recombinationParameters = new RecombinationParameters(populationSize);
		
		configuration.setRecombinationParameters(recombinationParameters);
		
		configuration.setSelectionOperators(new TournamentSelection<PermutationRepresentation>(1,2));
		
		ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer();
//		operatorContainer.addOperator(0.5, new PermutationEdgeCrossover());
		operatorContainer.addOperator(0.5, new TspGreedyCrossover(problemInstance));		
//		operatorContainer.addOperator(0.5, new PermutationNonAdjacentSwapMutation());
		operatorContainer.addOperator(0.5, new TwoOptLocalOptimizationOperator((PermutationRepresentationFactory)solutionFactory,problemInstance,true));
		configuration.setReproductionOperatorContainer(operatorContainer);
		
		this.algorithm = new EvolutionaryAlgorithm<PermutationRepresentation,PermutationRepresentationFactory>(configuration);

	}
	
	
	/**
	 * Run.
	 * 
	 * @throws Exception the exception
	 */
	public double run() throws Exception
	{
		results =  algorithm.run();
		statistics = results.getAlgorithmStatistics();
		
		return results.getSolutionContainer().getBestSolutionCellContainer(false).getSolution().getScalarFitnessValue();
	}
	

	/**
	 * Multiple algorithm runs.
	 * 
	 * @param baseDirectoryPath the base directory path
	 * @param jobId the job id
	 * @param numberOfRuns the number of runs
	 * 
	 * @throws Exception the exception
	 */
	public void multipleAlgorithmRuns(String baseDirectoryPath, String jobId, int numberOfRuns) throws Exception
	{
		algorithm.getConfiguration().getStatisticConfiguration().setNumberOfBestSolutionsToKeepPerIteration(0);
		algorithm.getConfiguration().getStatisticConfiguration().setNumberOfBestSolutionsToKeepPerRun(1);
		
		algorithm.getConfiguration().getStatisticConfiguration().setScreenStatisticsMask(new StatisticTypeMask(false));
		
		List<IAlgorithmResult<PermutationRepresentation>> algorithmResultList = new ArrayList<IAlgorithmResult<PermutationRepresentation>>(numberOfRuns);

				
	}
	
	
	/**
	 * Gets the best solution.
	 * 
	 * @return the best solution
	 */
	public int[] getBestSolution()
	{
		SolutionCellContainer<PermutationRepresentation> container = results.getSolutionContainer().getBestSolutionCellContainer(false);
		
		ISolution<PermutationRepresentation> solution = container.getSolution();
		
		return ((PermutationRepresentation)solution.getRepresentation()).getGenomeAsArray();
	}
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {

			try 
			{
				
				Tsp problemInstance = new EucTsp("./tests/jecoli/test/tsp/eil51.cit");
				
				EATsp ea = new EATsp(problemInstance);
				
				int populationSize = 100;
				int numberGenerations = 100;
				
				ea.configureEA(populationSize, numberGenerations);
				
				double result = ea.run();
				
			} 
			catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
		}

}
