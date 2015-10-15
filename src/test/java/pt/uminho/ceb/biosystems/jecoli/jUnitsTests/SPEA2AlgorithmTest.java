package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.BitFlipMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.OnePointCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.EnvironmentalSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.spea2.SPEA2;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.spea2.SPEA2Configuration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;
import pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.countones.CountOnesMOEvaluationFunction;


public class SPEA2AlgorithmTest extends AbstractAlgorithmTest<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>{

	public SPEA2AlgorithmTest()
	{
		this.testName = "SPEA2Algorithm";
	}
	
	@Override
	protected void specificAlgorithmTests() {
		// TODO Auto-generated method stub
		
	}
	
	protected void setCountingOnesConfigure(boolean isMaximization)
	{
		try {
			
			SPEA2Configuration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory> algorithmConfiguration = new SPEA2Configuration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>();
			
			algorithmConfiguration.setStatisticsConfiguration(new StatisticsConfiguration());
			algorithmConfiguration.setRandomNumberGenerator(randomNumberGenerator);
			
			IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new CountOnesMOEvaluationFunction<ILinearRepresentation<Boolean>>(true);
			algorithmConfiguration.setEvaluationFunction(evaluationFunction);
			
			this.populationSize=3;
			BinaryRepresentationFactory solFactory = new BinaryRepresentationFactory(this.populationSize,2);
			algorithmConfiguration.setSolutionFactory(solFactory);
			
			this.populationSize = 3;
			int maximumArchiveSize = 3;
			algorithmConfiguration.setPopulationSize(populationSize);
			algorithmConfiguration.setMaximumArchiveSize(maximumArchiveSize);
			
			this.numberOfGenerations = 20;
			ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(this.numberOfGenerations);
			algorithmConfiguration.setTerminationCriteria(terminationCriteria);

			RecombinationParameters recombinationParameters = new RecombinationParameters(0,populationSize,0,true);
			algorithmConfiguration.setRecombinationParameters(recombinationParameters);

			algorithmConfiguration.setSelectionOperator(new TournamentSelection<ILinearRepresentation<Boolean>>(1,2));
			algorithmConfiguration.setEnvironmentalSelectionOperator(new EnvironmentalSelection<ILinearRepresentation<Boolean>>());
			
			ReproductionOperatorContainer reproductionOperatorContainer = new ReproductionOperatorContainer();
			reproductionOperatorContainer.addOperator(0.5,new OnePointCrossover<Boolean>());
			reproductionOperatorContainer.addOperator(0.5,new BitFlipMutation(1));
			algorithmConfiguration.setReproductionOperatorContainer(reproductionOperatorContainer);
			
			this.algorithm = new SPEA2<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>(algorithmConfiguration);
		}
		catch (InvalidConfigurationException e) {e.printStackTrace();}
		catch (InvalidNumberOfIterationsException e) {e.printStackTrace();} 
		catch (Exception e) {e.printStackTrace();} 
	}

	@Override
	public void setTestValues() {
		setCountingOnesConfigure(true);
		
	}

}
