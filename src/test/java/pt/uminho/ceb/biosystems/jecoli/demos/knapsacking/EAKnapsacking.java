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
package pt.uminho.ceb.biosystems.jecoli.demos.knapsacking;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.BitFlipMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.LinearGenomeRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.UniformCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.permutation.PermutationNonAdjacentSwapMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.permutation.PermutationOnePtCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.RankingSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.OrdinalRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;

/**
 * The Class EAKnapsacking.
 * Configuring and running EAs for the Knapsacking problem. 
 */
public class EAKnapsacking {

	/** The problem instance. */
	Knapsacking problemInstance;
	
	/** The representation type. */
	KnapsackingRepresentationType representationType = KnapsackingRepresentationType.BinaryPenalties;
	
	/** The algorithm. */
	EvolutionaryAlgorithm<?,?> algorithm;
	
	/** The results. */
	IAlgorithmResult results;
	
	/** The statistics. */
	IAlgorithmStatistics statistics;
	
	IRandomNumberGenerator randomGenerator = new DefaultRandomNumberGenerator();
	
	/**
	 * Instantiates a new eA knapsacking.
	 * 
	 * @param problemInstance the problem instance
	 */
	public EAKnapsacking(Knapsacking problemInstance)
	{
		this.problemInstance = problemInstance;
	}
	
		
	/**
	 * Instantiates a new eA knapsacking.
	 * 
	 * @param problemInstance the problem instance
	 * @param representationType the representation type
	 */
	public EAKnapsacking(Knapsacking problemInstance, KnapsackingRepresentationType representationType) {
		this.problemInstance = problemInstance;
		this.representationType = representationType;
	}

	
	/**
	 * Configure evolutionary algorithm.
	 * 
	 * @param populationSize the population size
	 * @param numberGenerations the number generations
	 * 
	 * @throws Exception the exception
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	public void configureEvolutionaryAlgorithm(int populationSize, int numberGenerations) 
		throws Exception, InvalidConfigurationException
	{
		IEvaluationFunction<?> evaluationFunction = null;
		ISolutionFactory<?> solutionFactory = null;
		ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer();
				
		int solutionSize = this.problemInstance.getSize();
		
		if(representationType.equals(KnapsackingRepresentationType.BinaryPenalties))
		{
			evaluationFunction = new KnapsackingBinaryEvaluationFunction(problemInstance, EnumPenaltyFunctions.LINEAR);
			solutionFactory = new BinaryRepresentationFactory(solutionSize);
			operatorContainer.addOperator(0.5, new UniformCrossover<Boolean>());
			operatorContainer.addOperator(0.5, new BitFlipMutation(1));	
		}
		else if(representationType.equals(KnapsackingRepresentationType.BinaryCorrection))
		{
			evaluationFunction = new KnapsackingCorrectionEvaluation(problemInstance);
			solutionFactory = new BinaryRepresentationFactory(solutionSize);
			operatorContainer.addOperator(0.5, new UniformCrossover<Boolean>());
			operatorContainer.addOperator(0.5, new BitFlipMutation(1));	
		}
		else if(representationType.equals(KnapsackingRepresentationType.Permutation))
		{
			evaluationFunction = new KnapsackingPermutationEvaluationFunction(problemInstance);
			solutionFactory = new PermutationRepresentationFactory(solutionSize);
			operatorContainer.addOperator(0.5,new PermutationOnePtCrossover());
			operatorContainer.addOperator(0.5,new PermutationNonAdjacentSwapMutation());	
		}
		else if(representationType.equals(KnapsackingRepresentationType.Ordinal))
		{
			evaluationFunction = new KnapsackingOrdinalEvaluation(problemInstance);
			solutionFactory = new OrdinalRepresentationFactory(solutionSize);
			operatorContainer.addOperator(0.5,new UniformCrossover<Integer>());
			operatorContainer.addOperator(0.5,new LinearGenomeRandomMutation<Integer>());	
		}
		else
			throw new Exception("Unsupported representation type");
		

		EvolutionaryConfiguration configuration = new EvolutionaryConfiguration();
		configuration.setEvaluationFunction(evaluationFunction);
		configuration.setSolutionFactory(solutionFactory);
		configuration.setReproductionOperatorContainer(operatorContainer);
		
		configuration.setPopulationSize(populationSize);
		
		RecombinationParameters recombinationParameters = new RecombinationParameters(populationSize);
		configuration.setRecombinationParameters(recombinationParameters);
		
		configuration.setRandomNumberGenerator(randomGenerator);
		configuration.setProblemBaseDirectory("nullDirectory");
		configuration.setAlgorithmStateFile("nullFile");
		configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
		configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<IRepresentation>>());
		configuration.setStatisticsConfiguration(new StatisticsConfiguration());
		configuration.getStatisticsConfiguration().setVerbose(true);
		
		configuration.setSelectionOperators(new RankingSelection());
		//configuration.setSelectionOperators(new TournamentSelection(1, 4));
		
		ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
		configuration.setTerminationCriteria(terminationCriteria);
				
		algorithm = new EvolutionaryAlgorithm(configuration);
	}
	
	
	
	/**
	 * Run.
	 * 
	 * @throws Exception the exception
	 */
	public void run() throws Exception
	{
		try 
		{
			results =  algorithm.run();
			statistics = results.getAlgorithmStatistics();

		}catch (Exception ignored) {
		}
	}

	
	/**
	 * Prints the stats per iteration.
	 */
	public void printStatsPerIteration ()
	{
		System.out.println("Iteration\tBest\t\tMean\n");
		for(int i=0; i < statistics.getNumberOfIterations(); i+=20)
		{
			System.out.print(i+"\t");
			System.out.print(statistics.getAlgorithmIterationStatisticCell(i).getScalarFitnessCell().getMaxValue()+"\t");
			System.out.print(statistics.getAlgorithmIterationStatisticCell(i).getScalarFitnessCell().getMean()+"\n");
		}	
	}
	
	/**
	 * Gets the best solution.
	 * 
	 * @return the best solution
	 */
	public boolean[] getBestSolution ()
	{
		ISolution bestSolution = results.getSolutionContainer().getBestSolutionCellContainer(true).getSolution();
		
		System.out.println("fitness:" + bestSolution.getScalarFitnessValue());
		
		boolean[] res = null; 
		if(representationType.equals(KnapsackingRepresentationType.BinaryPenalties))
		{
			ILinearRepresentation<Boolean> genome = 
				(ILinearRepresentation<Boolean>)bestSolution.getRepresentation();
			KnapsackingBinaryEvaluationFunction evaluationFunction = 
				(KnapsackingBinaryEvaluationFunction)(algorithm.getConfiguration().getEvaluationFunction());
			res = evaluationFunction.decodeGenome(genome);	
		}
		else if(representationType.equals(KnapsackingRepresentationType.BinaryCorrection))
		{
			ILinearRepresentation<Boolean> genome = 
				(ILinearRepresentation<Boolean>)bestSolution.getRepresentation();
			KnapsackingCorrectionEvaluation evaluationFunction = 
				(KnapsackingCorrectionEvaluation)(algorithm.getConfiguration().getEvaluationFunction());
			res = evaluationFunction.decodeGenome(genome);
		}
		else if(representationType.equals(KnapsackingRepresentationType.Permutation))
		{
			PermutationRepresentation genome = (PermutationRepresentation)bestSolution.getRepresentation();
			int [] genomeArray = genome.getGenomeAsArray();
			res = problemInstance.orderKnap(genomeArray);
		}
		else if(representationType.equals(KnapsackingRepresentationType.Ordinal))
		{
			ILinearRepresentation<Integer> genome = (ILinearRepresentation<Integer>)bestSolution.getRepresentation();
			int[] genomeArray = new int[genome.getNumberOfElements()];
			for(int i=0; i < genome.getNumberOfElements(); i++)
				genomeArray[i] = genome.getElementAt(i);
			res = problemInstance.ordinalKnap(genomeArray);
		}
		
		return res;
	}
	
	
	// args[0] - filename of problem instance
	// args[1] - constraint handling method: permutation, correction, penalties
	// args[2] - population size
	// args[3] - number of generations
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) 
	{
		
		if (args.length < 4)
		{
			System.out.println("Usage: EAKnapsacking filename constraintMethod populationSize numberGenerations");
			System.out.println("Constraint method: penalties / correction / permutations");
			System.exit(1);
		}
		
		try {
			Knapsacking problemInstance = new Knapsacking(args[0]);
			
			KnapsackingRepresentationType constraintMethod = null;
			
			if(args[1].equals("penalties"))
				constraintMethod = KnapsackingRepresentationType.BinaryPenalties;
			else if(args[1].equals("correction"))
				constraintMethod = KnapsackingRepresentationType.BinaryCorrection;
			else if(args[1].equals("permutations"))
				constraintMethod = KnapsackingRepresentationType.Permutation;
			else if(args[1].equals("ordinal"))
				constraintMethod = KnapsackingRepresentationType.Ordinal;
			else 
			{
				System.out.println("Unknown method for constraint handling");
				System.out.println("Usage: EAKnapsacking filename constraintMethod populationSize numberGenerations");
				System.exit(1);				
			}
			
			int populationSize = Integer.parseInt(args[2]);
				
			int numberGenerations = Integer.parseInt(args[3]);
			
			EAKnapsacking ea = new EAKnapsacking(problemInstance, constraintMethod);
			
			ea.configureEvolutionaryAlgorithm(populationSize, numberGenerations);
			ea.run();
			
			boolean [] bestSolution = ea.getBestSolution();
			
			System.out.println("Best solution:");
			problemInstance.printSolution(bestSolution);
			
		}
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
