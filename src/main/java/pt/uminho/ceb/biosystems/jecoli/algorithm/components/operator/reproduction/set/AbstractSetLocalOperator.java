package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ILocalOptimizationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


public abstract class AbstractSetLocalOperator<E,G extends ISetRepresentation<E>, F extends ISetRepresentationFactory<?>> implements ILocalOptimizationOperator<ISetRepresentation<E>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8210373055680825778L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 1;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 1;
	
	
	IEvaluationFunction<ISetRepresentation<E>> evalFunction;
	
	/**
	 * Instantiates a new abstract set mutation jecoliunittest.operators.operator.
	 * 
	 * @param solutionFactory the solution factory
	 */
	public AbstractSetLocalOperator(IEvaluationFunction<ISetRepresentation<E>> evalFunction) {
		this.evalFunction = evalFunction;
	}

	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#apply(java.util.List)
	 */
	public List<ISolution<ISetRepresentation<E>>> apply(List<ISolution<ISetRepresentation<E>>> selectedSolutions,F solutionFactory,IRandomNumberGenerator randomNumberGenerator)	
		throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		ISolution<ISetRepresentation<E>> parentSolution = selectedSolutions.get(0);
		
		List<ISolution<ISetRepresentation<E>>> solutionList;
		solutionList = new ArrayList<ISolution<ISetRepresentation<E>>>(1);
		
		ISolution<ISetRepresentation<E>> solution = localopt(parentSolution,solutionFactory,randomNumberGenerator);
		
		solutionList.add(solution);
		
		return solutionList;
	}
	
	protected abstract ISolution<ISetRepresentation<E>> localopt(ISolution<ISetRepresentation<E>> originalSolution, F solutionFactory,IRandomNumberGenerator randomNumberGenerator);
	
	
	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#getNumberOfInputSolutions()
	 */
	public int getNumberOfInputSolutions() {
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#getNumberOfOutputSolutions()
	 */
	public int getNumberOfOutputSolutions() {
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}

	/**
	 * Input solutions have same length.
	 * 
	 * @return true, if successful
	 */
	public boolean inputSolutionsHaveSameLength() {
		return false;
	}

	@Override
	public IEvaluationFunction<ISetRepresentation<E>> getEvaluationFunction() {
		return this.evalFunction;
	}

	@Override
	public abstract int getNumberFunctionEvaluationPerfomed();
	
	public abstract boolean hasImproved();


}
