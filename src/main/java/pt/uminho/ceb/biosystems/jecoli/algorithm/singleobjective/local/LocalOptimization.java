package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.local;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public class LocalOptimization<T extends IRepresentation> extends AbstractAlgorithm<T,LocalOptConfiguration<T>>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5384751472052008406L;

	public LocalOptimization(LocalOptConfiguration<T> configuration) throws Exception {
		super(configuration);
	}
	
	@Override
	public ISolutionSet<T> initialize()  {
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		ISolutionFactory<T> solutionFactory = configuration.getSolutionFactory();
		ISolutionSet<T> solutionSet = new SolutionSet<T>(1) ;
		ISolution<T> solution = solutionFactory.generateSolution(randomGenerator); // random solution
		solutionSet.add(solution);
		return solutionSet;
	}

	@Override
	public ISolutionSet<T> iteration(AlgorithmState<T> algorithmState, ISolutionSet<T> solutionSet)  {
		
		// MUDAR ISTO .......
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		
		ISolutionSet<T> newSolutionSet = new SolutionSet<T>(1);
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		ISolutionFactory<T> solutionFactory = configuration.getSolutionFactory();

		ISolution<T> newSolution = solutionFactory.generateSolution(randomGenerator);
		evaluationFunction.evaluateSingleSolution(newSolution);
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations();

		return newSolutionSet;
	}

	@Override
	public IAlgorithm<T> deepCopy() throws Exception {
		LocalOptConfiguration<T> localOptConfigurationCopy = configuration.deepCopy();
		return new LocalOptimization<T>(localOptConfigurationCopy);
	}

}
