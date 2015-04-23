package pt.uminho.ceb.biosystems.jecoli.demos.knapsacking;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;

public class KnapsackingOrdinalEvaluation extends AbstractEvaluationFunction<ILinearRepresentation<Integer>>{

	private static final long serialVersionUID = 1L;

	/** The knapsacking instance. */
	Knapsacking knapsackingInstance;

	/**
	 * Instantiates a new knapsacking permutation evaluation function.
	 * 
	 * @param knapsackingInstance the knapsacking instance
	 */
	public KnapsackingOrdinalEvaluation (Knapsacking knapsackingInstance){
		super(true);  // maximization
		this.knapsackingInstance = knapsackingInstance;
	}
	
	@Override
	public double evaluate(ILinearRepresentation<Integer> solution) 
	{
		int[] genome = new int[solution.getNumberOfElements()];
		for(int i=0; i < solution.getNumberOfElements(); i++)
			genome[i] = solution.getElementAt(i);
		
		boolean [] knsol = knapsackingInstance.ordinalKnap(genome);
		double fitness = knapsackingInstance.totalProfit(knsol);
		
		return fitness;
	}

	@Override
	public KnapsackingOrdinalEvaluation deepCopy() throws Exception {
		return new KnapsackingOrdinalEvaluation(new Knapsacking(knapsackingInstance));
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}
}
