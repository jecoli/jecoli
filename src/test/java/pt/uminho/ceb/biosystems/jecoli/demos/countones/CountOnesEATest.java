/**
 * Copyright 2009,
 * CCTC - Computer Science and Technology Center
 * IBB-CEB - Institute for Biotechnology and Bioengineering - Centre of
 * Biological Engineering
 * University of Minho
 * 
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License
 * along with this code. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
 * University of Minho
 */
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
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;

// TODO: Auto-generated Javadoc
/**
 * Class to solve the count ones toy problem using Evolutionary Algorithms.
 */
public class CountOnesEATest {
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	
	public static void main(String[] args) {
		try {
			
			EvolutionaryConfiguration<ILinearRepresentation<Boolean>, BinaryRepresentationFactory> configuration = new EvolutionaryConfiguration<ILinearRepresentation<Boolean>, BinaryRepresentationFactory>();
			
			IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new CountOnesEvaluationFunction();
			configuration.setEvaluationFunction(evaluationFunction);
			
			int solutionSize = 1000;
			BinaryRepresentationFactory solutionFactory = new BinaryRepresentationFactory(solutionSize);
			configuration.setSolutionFactory(solutionFactory);
			
			int populationSize = 10;
			configuration.setPopulationSize(populationSize);
			
			int numberGenerations = 10000;
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
			
			//IEvolutionTracker<ILinearRepresentation<Boolean>> tracker = new EvolutionTrackerFile<ILinearRepresentation<Boolean>>("/home/pmaia/Desktop/testtracker.txt");
			TestBinaryDecoder decoder = new TestBinaryDecoder();
			//tracker.setSolutionDecoder(decoder);
			
			//algorithm.setEvolutionTracker(tracker);
			
			//Plot2DGUI<ILinearRepresentation<Boolean>> plotter = new Plot2DGUI<ILinearRepresentation<Boolean>>(algorithm);
			//plotter.run();
			IAlgorithmResult<ILinearRepresentation<Boolean>> results = algorithm.run();
			//tracker.terminate();
			
		} catch (InvalidNumberOfIterationsException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// }
	
}
