package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;

public abstract class AbstractHybridSetMutationOperator <G,H> implements IReproductionOperator<IHybridSetRepresentation<G,H>,IHybridSetRepresentationFactory<G, H>>{


	private static final long serialVersionUID = -7845002209819846153L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 1;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 1;
	
	public abstract void mutateGenome(IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator);


	public abstract int computeNumberOfGenesToModify(int childNumberOfGenes,IRandomNumberGenerator randomNumberGenerator);

	@Override
	public List<ISolution<IHybridSetRepresentation<G,H>>> apply(List<ISolution<IHybridSetRepresentation<G,H>>> selectedSolutions,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator) throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException {

		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		ISolution<IHybridSetRepresentation<G,H>> parentSolution = selectedSolutions.get(0);
		ISolution<IHybridSetRepresentation<G,H>> childSolution = solutionFactory.copySolution(parentSolution);
		
		List<ISolution<IHybridSetRepresentation<G,H>>> solutionList = mutate(childSolution,solutionFactory,randomNumberGenerator);
		
		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();
		
		return solutionList;
	}
	
	protected List<ISolution<IHybridSetRepresentation<G,H>>> mutate(ISolution<IHybridSetRepresentation<G,H>> childSolution,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomGenerator)
	{
		List<ISolution<IHybridSetRepresentation<G,H>>> resultList = new ArrayList<ISolution<IHybridSetRepresentation<G,H>>>(NUMBER_OF_OUTPUT_SOLUTIONS);
		IHybridSetRepresentation<G,H> childGenome = childSolution.getRepresentation();
		
		mutateGenome(childGenome, solutionFactory, randomGenerator);
		
		resultList.add(new Solution<IHybridSetRepresentation<G,H>>(childGenome,childSolution.getNumberOfObjectives()));
		return resultList;
	}

	@Override
	public int getNumberOfInputSolutions() {
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	@Override
	public int getNumberOfOutputSolutions() {
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}

	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.MUTATION;
	}
	

}
