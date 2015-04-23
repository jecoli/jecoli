package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.wrapper;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.GaussianPerturbationMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.LinearGenomeRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.RealValueRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.aggregation.IAggregationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.aggregation.SimpleAdditiveAggregation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.ArchiveManager;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.InsertionStrategy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.ProcessingStrategy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting.IPlotter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting.Plot2DGUI;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming.ITrimmingFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming.ZitzlerTruncation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.AnnealingSchedule;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.SimulatedAnnealing;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.SimulatedAnnealingConfiguration;
import pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.kursawe.KursaweEvaluationFunction;

public class SAMOGenericTest {


	public static void main(String[] args) {
		try {
			
			SimulatedAnnealingConfiguration<ILinearRepresentation<Double>,RealValueRepresentationFactory> configuration = new SimulatedAnnealingConfiguration<ILinearRepresentation<Double>,RealValueRepresentationFactory>();
			configuration.setStatisticsConfiguration(new StatisticsConfiguration());
			configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
			configuration.setProblemBaseDirectory("nullDirectory");
			configuration.setAlgorithmStateFile("nullFile");
			configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
			
			AbstractMultiobjectiveEvaluationFunction<ILinearRepresentation<Double>> evaluationFunction = new KursaweEvaluationFunction<ILinearRepresentation<Double>>(false);
			IAggregationFunction aggregation = new SimpleAdditiveAggregation();
			evaluationFunction.setFitnessAggregation(aggregation);
			configuration.setEvaluationFunction(evaluationFunction);
			configuration.setNumberOfObjectives(2);

			int solutionSize = 3;
			RealValueRepresentationFactory solutionFactory = new RealValueRepresentationFactory(solutionSize,-5.0,5.0,2);
			configuration.setSolutionFactory(solutionFactory);
			
			int maximumArchiveSize = 200;		

			AnnealingSchedule as = new AnnealingSchedule(1, 0.000001, 100, 200000);
			configuration.setAnnealingSchedule(as);
			
			int numberGenerations = 500;
			ITerminationCriteria terminationCriteria;
			terminationCriteria = new IterationTerminationCriteria(numberGenerations);
			configuration.setTerminationCriteria(terminationCriteria);	
			
			ReproductionOperatorContainer reproductionOperatorContainer = new ReproductionOperatorContainer();
			reproductionOperatorContainer.addOperator(0.5,new GaussianPerturbationMutation(0.2));
			reproductionOperatorContainer.addOperator(0.25,new LinearGenomeRandomMutation<Double>(0.1));
			reproductionOperatorContainer.addOperator(0.25,new RealValueRandomMutation(1));
			configuration.setMutationOperatorContainer(reproductionOperatorContainer);			
			
			IAlgorithm<ILinearRepresentation<Double>> algorithm = new SimulatedAnnealing<ILinearRepresentation<Double>,RealValueRepresentationFactory>(configuration);			
			
			ArchiveManager<Double, ILinearRepresentation<Double>> archive = new ArchiveManager<Double, ILinearRepresentation<Double>>(
					algorithm,
					InsertionStrategy.ADD_ON_SINGLE_EVALUATION_FUNCTION_EVENT,
					InsertionStrategy.ADD_ALL,
					ProcessingStrategy.PROCESS_ARCHIVE_ON_ITERATION_INCREMENT
					);
			
			ITrimmingFunction<ILinearRepresentation<Double>> trimmer = new ZitzlerTruncation<ILinearRepresentation<Double>>(maximumArchiveSize, evaluationFunction);
			archive.addTrimmingFunction(trimmer);
			
			IPlotter<ILinearRepresentation<Double>> plotter = new Plot2DGUI<ILinearRepresentation<Double>>();
			archive.setPlotter(plotter);			
			
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
