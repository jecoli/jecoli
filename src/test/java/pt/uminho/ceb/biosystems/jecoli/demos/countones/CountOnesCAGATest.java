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
package pt.uminho.ceb.biosystems.jecoli.demos.countones;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
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
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.CellularAutomataType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.CellularGeneticAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.CellularGeneticAlgorithmConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.NeighborhoodType;

// TODO: Auto-generated Javadoc
/**
 * The Class CountOnesCAGATest.
 */
public class CountOnesCAGATest{
	
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) 
	{
		try {
			CellularGeneticAlgorithmConfiguration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory> configuration = new CellularGeneticAlgorithmConfiguration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>();
			
			IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new CountOnesEvaluationFunction();
			configuration.setEvaluationFunction(evaluationFunction);
			
			int solutionSize = 1000;
			BinaryRepresentationFactory solutionFactory = new BinaryRepresentationFactory(solutionSize);
			configuration.setSolutionFactory(solutionFactory);
			
			int numberGenerations = 1000;
			ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
			configuration.setTerminationCriteria(terminationCriteria);
			
			configuration.setCellularAutomataType(CellularAutomataType.SYNCHRONOUS);
			configuration.setNeighborhoodType(NeighborhoodType.NEWS);
			configuration.setWidth(20);
			configuration.setHeight(20);
			configuration.setRadius(1);
			
			configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
			configuration.setProblemBaseDirectory("nullDirectory");
			configuration.setAlgorithmStateFile("nullFile");
			configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
			configuration.setPopulationSize(1000);
			configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Boolean>>>());
			configuration.setStatisticsConfiguration(new StatisticsConfiguration());
			configuration.getStatisticConfiguration().setVerbose(true);

			configuration.setSelectionOperator(new TournamentSelection<ILinearRepresentation<Boolean>>(1,2));
					
			ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer();
			operatorContainer.addOperator(0.5,new UniformCrossover<Boolean>());
			operatorContainer.addOperator(0.5,new BitFlipMutation(2));	
			configuration.setReproductionOperatorContainer(operatorContainer);
			
			IAlgorithm<ILinearRepresentation<Boolean>> algorithm = new CellularGeneticAlgorithm<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>(configuration);
			IAlgorithmResult<ILinearRepresentation<Boolean>> result = algorithm.run();
			
		} catch (InvalidNumberOfIterationsException e) {
			e.printStackTrace();
		} 
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		catch (InvalidSelectionParameterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
