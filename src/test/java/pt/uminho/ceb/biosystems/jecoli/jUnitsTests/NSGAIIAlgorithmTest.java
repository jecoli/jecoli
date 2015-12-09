package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.BitFlipMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.OnePointCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection2;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.nsgaII.NSGAII;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.nsgaII.NSGAIIConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;
import pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.countones.CountOnesMOEvaluationFunction;


public class NSGAIIAlgorithmTest extends AbstractAlgorithmTest<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>{

	public NSGAIIAlgorithmTest(boolean isMaximization)
	{
		this.testName = "NSGAIIAlgorithm";
	}
	
	protected void setCountingOnesConfigure(boolean isMaximization)
	{
		try {
			
			NSGAIIConfiguration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory> algorithmConfiguration = new NSGAIIConfiguration<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>();
			
			algorithmConfiguration.setRandomNumberGenerator(randomNumberGenerator);
			
			IEvaluationFunction<ILinearRepresentation<Boolean>> evaluationFunction = new CountOnesMOEvaluationFunction<ILinearRepresentation<Boolean>>(true);
			algorithmConfiguration.setEvaluationFunction(evaluationFunction);
			
			int solutionSize = 3;
			BinaryRepresentationFactory solFactory = new BinaryRepresentationFactory(solutionSize,2);
			algorithmConfiguration.setSolutionFactory(solFactory);
			
			algorithmConfiguration.setPopulationSize(this.populationSize);
			
			ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(this.numberOfGenerations);
			algorithmConfiguration.setTerminationCriteria(terminationCriteria);

			RecombinationParameters recombinationParameters = new RecombinationParameters(0,populationSize,0,true);
			algorithmConfiguration.setRecombinationParameters(recombinationParameters);

			algorithmConfiguration.setSelectionOperator(new TournamentSelection2<ILinearRepresentation<Boolean>>(1,2,randomNumberGenerator));
			
			ReproductionOperatorContainer reproductionOperatorContainer = new ReproductionOperatorContainer();
			reproductionOperatorContainer.addOperator(0.5,new OnePointCrossover<Boolean>());
			reproductionOperatorContainer.addOperator(0.5,new BitFlipMutation(1));
			algorithmConfiguration.setReproductionOperatorContainer(reproductionOperatorContainer);
			
			algorithm = new NSGAII<ILinearRepresentation<Boolean>,BinaryRepresentationFactory>(algorithmConfiguration);
			
		}
		catch (InvalidConfigurationException e) {e.printStackTrace();}
		catch (InvalidNumberOfIterationsException e) {e.printStackTrace();} 
		catch (Exception e) {e.printStackTrace();} 
		
	}
	
	@Override
	protected void specificAlgorithmTests() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTestValues() {
		setCountingOnesConfigure(true);
		
	}

}
