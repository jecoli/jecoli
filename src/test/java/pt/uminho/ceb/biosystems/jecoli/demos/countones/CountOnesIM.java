package pt.uminho.ceb.biosystems.jecoli.demos.countones;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.BitFlipMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.UniformCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel.IMTopology;
import pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel.IslandModel;
import pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel.IslandModelParams;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;

public class CountOnesIM {
	
	public static void main(String[] args) {
		try {
			final long elapsedTime;
			int numpop = 5;
			ArrayList<IAlgorithm> algorithms = new ArrayList<IAlgorithm>(numpop);
			
			while(algorithms.size()<numpop){
				//configuração do AE
				EvolutionaryConfiguration<ILinearRepresentation<Boolean>, BinaryRepresentationFactory> configuration = new EvolutionaryConfiguration<ILinearRepresentation<Boolean>, BinaryRepresentationFactory>();
				
				IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new CountOnesEvaluationFunction();
				configuration.setEvaluationFunction(evaluationFunction);
				
				int solutionSize = 1000;
				BinaryRepresentationFactory solutionFactory = new BinaryRepresentationFactory(solutionSize);
				configuration.setSolutionFactory(solutionFactory);
				
				int populationSize = 10;
				configuration.setPopulationSize(populationSize);
				
				int numberGenerations = 2000/numpop;
				//int numberGenerations = 10000;
				ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
				configuration.setTerminationCriteria(terminationCriteria);
				
				RecombinationParameters recombinationParameters = new RecombinationParameters(populationSize);
				configuration.setRecombinationParameters(recombinationParameters);
				
				configuration.setSelectionOperators(new TournamentSelection<ILinearRepresentation<Boolean>>(1, 4));
				
				configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
				configuration.setProblemBaseDirectory("nullDirectory");
				configuration.setAlgorithmStateFile("nullFile");
				configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
				configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Boolean>>>());
				configuration.setStatisticsConfiguration(new StatisticsConfiguration());
				configuration.getStatisticsConfiguration().setVerbose(true);
				
				ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer();
				operatorContainer.addOperator(0.5, new UniformCrossover<Boolean>());
				//operatorContainer.addOperator(0.5, new TwoPointCrossOver<Boolean>());
				operatorContainer.addOperator(0.5, new BitFlipMutation(5));
				configuration.setReproductionOperatorContainer(operatorContainer);
				
				IAlgorithm<ILinearRepresentation<Boolean>> algorithm = new EvolutionaryAlgorithm<ILinearRepresentation<Boolean>, BinaryRepresentationFactory>(configuration);
				
				//Set populations
				algorithms.add(algorithm);
			}	
			
			//while(algorithms.size()<numpop){
			//	algorithms.add(algorithm.deepCopy());
			//}
			
			//Configure Island Model
			int totalIters = 2000;//500; // number of iterations of the full process
			int totalIndv =240;
			int itStep = 4; // number of iterations in each step without migrations
			int migrationRate = 10; // number of migrants in each migration step
			boolean parallel = true,migration=true, syncronnized = true,mpi=false;
			int selectMigrationIndividualsType = 3;//0 = random; 1=best; 2=tornement; 3=ranking.
			int tradeMigrationIndividualsType = 1; // 0 = random; 1 = worst;
			int splitIslandsType = 0;//0=split generations; 1=split individuals
			
			if(totalIters%numpop==0){
				IslandModelParams mgp = new IslandModelParams(numpop,itStep,migrationRate,syncronnized,totalIters,totalIndv,parallel,selectMigrationIndividualsType,tradeMigrationIndividualsType,splitIslandsType,migration,true,mpi);
				IMTopology topol = new IMTopology(numpop, IMTopology.BIRING);// RING, STAR, MESH, BIRING
				IslandModel mga = new IslandModel(mgp,topol,algorithms);
				
				
				final long startTime = System.currentTimeMillis();
				final long stopTime;
				 
				//IAlgorithmResult<ILinearRepresentation<Boolean>> results = 
				mga.run();
				
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				
				System.out.println("Time in Milis = "+elapsedTime);
				mga.writeLastResults();
				IAlgorithmResult ar = mga.returnBest(true);
			}
			else{
				System.out.println("The resulting number of generations per island is not possible! Reconfigure your setup!");
			}
			
			
						
		} catch (InvalidNumberOfIterationsException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
