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
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;


public class EvolutionaryAlgorithmTest extends AbstractAlgorithmTest<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>{
	
	public EvolutionaryAlgorithmTest(){
		this.testName = "EvolutionaryAlgorithm";
		this.randomNumberGenerator = new DefaultRandomNumberGenerator();
	}
	
	protected void setCountingOnesConfigure()
	{
		try {
			EvolutionaryConfiguration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory> algorithmConfiguration = new EvolutionaryConfiguration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>();
			
			IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new AlgorithmCountingOnesPopulation(isMaximization);
			
			algorithmConfiguration.setRandomNumberGenerator(this.randomNumberGenerator);
			algorithmConfiguration.setProblemBaseDirectory("nullDirectory");
			algorithmConfiguration.setAlgorithmStateFile("nullFile");
			algorithmConfiguration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
			algorithmConfiguration.setPopulationSize(populationSize);
			algorithmConfiguration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Boolean>>>());
			algorithmConfiguration.setStatisticsConfiguration(new StatisticsConfiguration());
			
			algorithmConfiguration.setEvaluationFunction(evaluationFunction);
			BinaryRepresentationFactory solFactory = new BinaryRepresentationFactory(populationSize);
			algorithmConfiguration.setSolutionFactory(solFactory);
			
			algorithmConfiguration.setPopulationSize(populationSize);
			algorithmConfiguration.setInitialPopulation(new SolutionSet<ILinearRepresentation<Boolean>>(initialPopulation));
			
			ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberOfGenerations);
			algorithmConfiguration.setTerminationCriteria(terminationCriteria);
				
			RecombinationParameters recoParameters = new RecombinationParameters(populationSize);
		    algorithmConfiguration.setRecombinationParameters(recoParameters);
				
			algorithmConfiguration.setSelectionOperators(new TournamentSelection<ILinearRepresentation<Boolean>>(1, 2));
			
			
			ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer();
			operatorContainer.addOperator(0.5, new UniformCrossover<Boolean>());
			operatorContainer.addOperator(0.5,new BitFlipMutation(2));
			algorithmConfiguration.setReproductionOperatorContainer(operatorContainer);
			
			this.algorithm = new EvolutionaryAlgorithm<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>(algorithmConfiguration);
		}
		catch (InvalidNumberOfIterationsException e) {e.printStackTrace();} 
		catch (InvalidSelectionParameterException e) {e.printStackTrace();}
		catch (InvalidConfigurationException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();} 
	}
	
	@Override
	protected void specificAlgorithmTests() {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void setTestValues() {
		setCountingOnesConfigure();
		
	}

	@Override
	public void deepcopyTest() {
		
	}
	
	
}
