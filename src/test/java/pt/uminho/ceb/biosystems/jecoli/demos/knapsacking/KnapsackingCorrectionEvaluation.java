/**
* Copyright 2009,
* CCTC - Computer Science and Technology Center
* IBB-CEB - Institute for Biotechnology and  Bioengineering - Centre of Biological Engineering
* University of Minho
*
* This is free software: you can redistribute it and/or modify
* it under the terms of the GNU Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Public License for more details.
*
* You should have received a copy of the GNU Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
* 
* Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
* University of Minho
*/
package pt.uminho.ceb.biosystems.jecoli.demos.knapsacking;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


/**
 * The Class KnapsackingCorrectionEvaluation. 
 * Evaluation function for the 0/1 knapsacking using a correction algorithm to fix unfeasible solutions.
 */
public class KnapsackingCorrectionEvaluation extends AbstractEvaluationFunction<ILinearRepresentation<Boolean>> 
{
	
	/** The knapsacking instance. */
	Knapsacking knapsackingInstance;
	
	/**
	 * Instantiates a new knapsacking correction evaluation.
	 * 
	 * @param knapsackingInstance the knapsacking instance
	 */
	public KnapsackingCorrectionEvaluation(Knapsacking knapsackingInstance){
		super(true);  // maximization
		this.knapsackingInstance = knapsackingInstance;
	}
	
	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public double evaluate(ILinearRepresentation<Boolean> solution) {
		
		double fitness = evaluateSolution(solution);
		return fitness;
	}

	/**
	 * Evaluate solution.
	 * 
	 * @param solution the solution
	 * 
	 * @return the double
	 */
	protected double evaluateSolution (ILinearRepresentation<Boolean> solution){
		boolean[] decodedSolution = decodeGenome(solution);
		double c = knapsackingInstance.totalProfit(decodedSolution);

		return c;
	}
	
	public static int irandom(int b) 
	// Random integer within range {0,...,b}    
	 { return (int)((b+1)*Math.random());}


	/**
	 * Decode genome.
	 * 
	 * @param solution the solution
	 * 
	 * @return the boolean[]
	 */
	protected boolean[] decodeGenome (ILinearRepresentation<Boolean> solution)
	{
		boolean [] res = new boolean[solution.getNumberOfElements()];
		for(int i = 0;i < res.length; i++)
			res[i] = solution.getElementAt(i);
		
		double deg = knapsackingInstance.totalWeight(res) - knapsackingInstance.getCapacity();
		int g = res.length - 1;
		while (deg > 0)
		{
			if(res[g])
			{
				res[g] = false;
				deg -= knapsackingInstance.getWeight(g); 
			}
			g--;
		}
		
		return res;
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Boolean>> deepCopy() throws Exception {
		return new KnapsackingCorrectionEvaluation(new Knapsacking(knapsackingInstance));
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfObjectives() {
		// TODO Auto-generated method stub
		return 0;
	}

}
