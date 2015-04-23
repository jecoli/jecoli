package pt.uminho.ceb.biosystems.jecoli.demos.tsp.eatsp;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.demos.tsp.libtsp.Tsp;

public class TspOrdinalEvaluationFunction extends AbstractEvaluationFunction<ILinearRepresentation<Integer>>{


	private static final long serialVersionUID = 1L;

	/** The problem instance. */
	Tsp problemInstance;
	
	/**
	 * Instantiates a new tsp evaluation function.
	 * 
	 * @param problemInstance the problem instance
	 */
	public TspOrdinalEvaluationFunction(Tsp problemInstance){
		super(false);  // minimization
		this.problemInstance = problemInstance;
	}
	
	@Override
	public double evaluate(ILinearRepresentation<Integer> solution){
		int[] genome = new int[solution.getNumberOfElements()];
		for(int i=0; i < solution.getNumberOfElements(); i++)
			genome[i] = solution.getElementAt(i);
		
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i=0; i < genome.length; i++) order.add(i, i);
		
		int[] perm = new int[genome.length];
		for(int i= 0; i < genome.length; i++)
		{
			int index = genome[i];
			perm[i] = order.get(index);
			order.remove(index);
		}
		
		double fitness = problemInstance.cost(perm);
		
		return fitness;
	}
	
	@Override
	public TspOrdinalEvaluationFunction deepCopy() throws Exception {
		return new TspOrdinalEvaluationFunction(problemInstance);
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}
	
}
