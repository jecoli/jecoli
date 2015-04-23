package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.kursawe;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.LinearGenomeRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.UniformCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.EnvironmentalSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting.Plot2DGUI;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.spea2.SPEA2;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.spea2.SPEA2Configuration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;

public class KursaweSPEA2ArchiveTest {


	public static void main(String[] args) {
		try {
			
			SPEA2Configuration<ILinearRepresentation<Double>,RealValueRepresentationFactory> configuration = new SPEA2Configuration<ILinearRepresentation<Double>,RealValueRepresentationFactory>();
			configuration.setStatisticsConfiguration(new StatisticsConfiguration());
			
			configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
			
			IEvaluationFunction<ILinearRepresentation<Double>> evaluationFunction = new KursaweEvaluationFunction<ILinearRepresentation<Double>>(true);
			configuration.setEvaluationFunction(evaluationFunction);
			configuration.setNumberOfObjectives(2);

			int solutionSize = 3;
			RealValueRepresentationFactory solutionFactory = new RealValueRepresentationFactory(solutionSize,-5.0,5.0,2);
			configuration.setSolutionFactory(solutionFactory);
			
			int populationSize = 300;
			int maximumArchiveSize = 300;
			configuration.setPopulationSize(populationSize);
			configuration.setMaximumArchiveSize(maximumArchiveSize);

			
			int numberGenerations = 5000;
			ITerminationCriteria terminationCriteria;
			terminationCriteria = new IterationTerminationCriteria(numberGenerations);
			configuration.setTerminationCriteria(terminationCriteria);
			
			RecombinationParameters recombinationParameters = new RecombinationParameters(0,populationSize,0,true);
			configuration.setRecombinationParameters(recombinationParameters);
			
			configuration.setSelectionOperator(new TournamentSelection<ILinearRepresentation<Double>>(1,2));
			configuration.setEnvironmentalSelectionOperator(new EnvironmentalSelection<ILinearRepresentation<Double>>());
			
			ReproductionOperatorContainer reproductionOperatorContainer = new ReproductionOperatorContainer();
			reproductionOperatorContainer.addOperator(0.5,new UniformCrossover<Double>());
			reproductionOperatorContainer.addOperator(0.5,new LinearGenomeRandomMutation<Double>(0.1));
			configuration.setReproductionOperatorContainer(reproductionOperatorContainer);
			
//			ReproductionOperatorContainer mutationOperatorContainer = new ReproductionOperatorContainer();
//			mutationOperatorContainer.addOperator(0.5,new LinearGenomeRandomMutation<Double>(1));
//			mutationOperatorContainer.addOperator(0.5,new RealValueRandomMutation(1));
//			configuration.setMutationOperatorsContainer(mutationOperatorContainer);
			
			IAlgorithm<ILinearRepresentation<Double>> algorithm = new SPEA2<ILinearRepresentation<Double>,RealValueRepresentationFactory>(configuration);
	
			Plot2DGUI<ILinearRepresentation<Double>> plotter = new Plot2DGUI<ILinearRepresentation<Double>>(algorithm);
			plotter.setObserveArchive(true);
			plotter.run();
			
			IAlgorithmResult<ILinearRepresentation<Double>> result =  algorithm.run();
			IAlgorithmStatistics<ILinearRepresentation<Double>> stats = result.getAlgorithmStatistics();

			
			
		} catch (InvalidNumberOfIterationsException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
