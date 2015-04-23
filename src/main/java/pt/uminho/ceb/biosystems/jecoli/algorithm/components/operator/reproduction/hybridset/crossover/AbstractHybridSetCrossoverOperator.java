package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.crossover;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.HybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;

public abstract class AbstractHybridSetCrossoverOperator<G,H> implements IReproductionOperator<IHybridSetRepresentation<G,H>,IHybridSetRepresentationFactory<G, H>>{

	private static final long serialVersionUID = 1247999675480639073L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 2;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 2;
	
	public abstract void crossoverGenomes(
			IHybridSetRepresentation<G,H> parentGenome1,
			IHybridSetRepresentation<G,H> parentGenome2, 
			IHybridSetRepresentation<G,H> childGenome1,
			IHybridSetRepresentation<G,H> childGenome2,
			IHybridSetRepresentationFactory<G,H> solutionFactory,
			IRandomNumberGenerator randomNumberGenerator
			);
	
	public List<ISolution<IHybridSetRepresentation<G,H>>> apply(
			List<ISolution<IHybridSetRepresentation<G,H>>> selectedSolutions,
			IHybridSetRepresentationFactory<G,H> solutionFactory,
			IRandomNumberGenerator randomGenerator) throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException{

		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();

		List<ISolution<IHybridSetRepresentation<G,H>>> solutionList = crossover(selectedSolutions,solutionFactory,randomGenerator);

		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();

		return solutionList;
	}
	
	public List<ISolution<IHybridSetRepresentation<G,H>>> crossover(
			List<ISolution<IHybridSetRepresentation<G,H>>> selectedSolutions,
			IHybridSetRepresentationFactory<G,H> solutionFactory,
			IRandomNumberGenerator randomGenerator){
		
		List<ISolution<IHybridSetRepresentation<G,H>>> resultList = new ArrayList<ISolution<IHybridSetRepresentation<G,H>>>(NUMBER_OF_OUTPUT_SOLUTIONS);
		
		ISolution<IHybridSetRepresentation<G,H>> parentSolution1 = selectedSolutions.get(0);
		ISolution<IHybridSetRepresentation<G,H>> parentSolution2 = selectedSolutions.get(1);
		
		IHybridSetRepresentation<G,H> parentGenome1 = parentSolution1.getRepresentation();
		IHybridSetRepresentation<G,H> parentGenome2 = parentSolution2.getRepresentation();
		
		IHybridSetRepresentation<G,H> childGenome1 = new HybridSetRepresentation<G,H>();
		IHybridSetRepresentation<G,H> childGenome2 = new HybridSetRepresentation<G,H>();
		
		crossoverGenomes(parentGenome1,parentGenome2,childGenome1,childGenome2,solutionFactory,randomGenerator);
		
		resultList.add(new Solution<IHybridSetRepresentation<G,H>>(childGenome1,parentSolution1.getNumberOfObjectives()));
		resultList.add(new Solution<IHybridSetRepresentation<G,H>>(childGenome2,parentSolution1.getNumberOfObjectives()));
		
		return resultList;
	}

	public int getNumberOfInputSolutions() {
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	public int getNumberOfOutputSolutions() {
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}
	
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.CROSSOVER;
	}
}
