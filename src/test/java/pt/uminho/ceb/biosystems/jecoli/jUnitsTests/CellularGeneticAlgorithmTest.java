package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.CellularAutomataType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.CellularGeneticAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.CellularGeneticAlgorithmConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.cellulargeneticalgorithm.NeighborhoodType;


public class CellularGeneticAlgorithmTest extends AbstractAlgorithmTest<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>{

	public CellularGeneticAlgorithmTest()
	{
		this.testName = "CellularGeneticAlgorithm";
		this.randomNumberGenerator = new DefaultRandomNumberGenerator();
	}
	
	protected void setCountingOnesConfigure(boolean isMaximization)
	{
		try {
			
			CellularGeneticAlgorithmConfiguration<ILinearRepresentation<Boolean>, BinaryRepresentationFactory> algorithmConfiguration = new CellularGeneticAlgorithmConfiguration<ILinearRepresentation<Boolean>, BinaryRepresentationFactory>();
		
			IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new AlgorithmCountingOnesPopulation(isMaximization);
			algorithmConfiguration.setEvaluationFunction(evaluationFunction);
			
			algorithmConfiguration.setRandomNumberGenerator(this.randomNumberGenerator);
			algorithmConfiguration.setProblemBaseDirectory("nullDirectory");
			algorithmConfiguration.setAlgorithmStateFile("nullFile");
			algorithmConfiguration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
			algorithmConfiguration.setPopulationSize(populationSize);
			algorithmConfiguration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Boolean>>>());
			algorithmConfiguration.setStatisticsConfiguration(new StatisticsConfiguration());
			
			BinaryRepresentationFactory solutionFactory = new BinaryRepresentationFactory(this.populationSize);
			algorithmConfiguration.setInitialPopulation(new SolutionSet<ILinearRepresentation<Boolean>>(this.initialPopulation));
			algorithmConfiguration.setSolutionFactory(solutionFactory);			
			
			ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(this.numberOfGenerations);
			algorithmConfiguration.setTerminationCriteria(terminationCriteria);
			
			algorithmConfiguration.setCellularAutomataType(CellularAutomataType.SYNCHRONOUS);
			algorithmConfiguration.setNeighborhoodType(NeighborhoodType.NEWS);
			algorithmConfiguration.setWidth(20);
			algorithmConfiguration.setHeight(20);
			algorithmConfiguration.setRadius(1);
			
			algorithmConfiguration.setSelectionOperator(new TournamentSelection<ILinearRepresentation<Boolean>>(1, 2));
			
			ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer(); 
			operatorContainer.addOperator(0.5, new UniformCrossover<Boolean>());
			operatorContainer.addOperator(0.5,new BitFlipMutation(2));
			algorithmConfiguration.setReproductionOperatorContainer(operatorContainer);
			
			this.algorithm = new CellularGeneticAlgorithm<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>(algorithmConfiguration);
		}
		catch (InvalidNumberOfIterationsException e) {e.printStackTrace();} 
		catch (InvalidSelectionParameterException e) {e.printStackTrace();}
		catch (InvalidConfigurationException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();} 
	}
	
	@Override
	public void setTestValues() {
		setCountingOnesConfigure(true);
	}
	
	@Override
	public void specificAlgorithmTests() {
		
		
	}

	@Override
	public void deepcopyTest() {
		
	}
	

}
