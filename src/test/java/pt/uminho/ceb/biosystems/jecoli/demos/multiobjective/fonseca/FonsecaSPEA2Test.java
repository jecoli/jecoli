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
package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.fonseca;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.LinearGenomeRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.RealValueSumCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.EnvironmentalSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
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

/**
 * The Class FonsecaSPEA2Test.
 * 
 * @author pmaia
 */
public class FonsecaSPEA2Test {

	/**
	 * The main method.
	 * 
	 * @param args the args
	 */
	public static void main(String[] args) {
		try {
			SPEA2Configuration<ILinearRepresentation<Double>,RealValueRepresentationFactory> configuration = new SPEA2Configuration<ILinearRepresentation<Double>,RealValueRepresentationFactory>();
			StatisticsConfiguration statConf = new StatisticsConfiguration();
			statConf.setVerbose(true);
			configuration.setStatisticsConfiguration(statConf);
			
			IRandomNumberGenerator randomNumberGenerator = new DefaultRandomNumberGenerator();
			configuration.setRandomNumberGenerator(randomNumberGenerator);
			
			IEvaluationFunction<ILinearRepresentation<Double>> evaluationFunction = new FonsecaEvaluationFunction<ILinearRepresentation<Double>>(false); //minimization
			configuration.setEvaluationFunction(evaluationFunction);
			
			int solutionSize = 3;
			RealValueRepresentationFactory solutionFactory = new RealValueRepresentationFactory(solutionSize,-4.0,4.0,2);
			configuration.setSolutionFactory(solutionFactory);
			
			int populationSize = 500;
			int maximumArchiveSize = 500;
			configuration.setPopulationSize(populationSize);
			configuration.setMaximumArchiveSize(maximumArchiveSize);

			int numberGenerations = 300;
			ITerminationCriteria terminationCriteria;
			terminationCriteria = new IterationTerminationCriteria(numberGenerations);
			configuration.setTerminationCriteria(terminationCriteria);
			
			RecombinationParameters recombinationParameters = new RecombinationParameters(0,populationSize,0,true);
			configuration.setRecombinationParameters(recombinationParameters);
			
			configuration.setSelectionOperator(new TournamentSelection<ILinearRepresentation<Double>>(1,2));
			configuration.setEnvironmentalSelectionOperator(new EnvironmentalSelection<ILinearRepresentation<Double>>());
			
			configuration.setNumberOfObjectives(2);
			
			ReproductionOperatorContainer reproductionOperatorContainer = new ReproductionOperatorContainer();
//			reproductionOperatorContainer.addOperator(0.5,new UniformCrossover<Double>());
			reproductionOperatorContainer.addOperator(0.5,new RealValueSumCrossover());
			reproductionOperatorContainer.addOperator(0.5,new LinearGenomeRandomMutation<Double>(1));
			configuration.setReproductionOperatorContainer(reproductionOperatorContainer);
			
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
