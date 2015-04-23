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
 * The Class KnapsackingBinaryEvaluationFunction. Evaluation function for the 0/1 knapsacking using penlties
 */
public class KnapsackingBinaryEvaluationFunction extends AbstractEvaluationFunction<ILinearRepresentation<Boolean>>{

	/** The knapsacking instance. */
	Knapsacking knapsackingInstance;
	
	/** The penalty function. */
	EnumPenaltyFunctions penaltyFunction = EnumPenaltyFunctions.LINEAR;
	
	/**
	 * Instantiates a new knapsacking binary evaluation function.
	 * 
	 * @param knapsackingInstance the knapsacking instance
	 */
	public KnapsackingBinaryEvaluationFunction(Knapsacking knapsackingInstance){
		super(true);  // maximization
		this.knapsackingInstance = knapsackingInstance;
	}
	
	/**
	 * Instantiates a new knapsacking binary evaluation function.
	 * 
	 * @param knapsackingInstance the knapsacking instance
	 * @param penaltyFunction the penalty function
	 */
	public KnapsackingBinaryEvaluationFunction(Knapsacking knapsackingInstance, EnumPenaltyFunctions penaltyFunction){
		super(true);  // maximization
		this.knapsackingInstance = knapsackingInstance;
		this.penaltyFunction = penaltyFunction;
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
 		double p = computePenalties(decodedSolution);
 		//System.out.println("C: " + c + " " + "P: " + p);

		return c-p;
	}
	
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
		return res;
	}
	
	/**
	 * Compute penalties.
	 * 
	 * @param solution the solution
	 * 
	 * @return the double
	 */
	protected double computePenalties(boolean[] solution)
	{
		double deg = knapsackingInstance.totalWeight(solution) - knapsackingInstance.getCapacity();
  		if (deg <= 0) return 0;

  		double[] aux = new double[knapsackingInstance.getSize()];
  		for (int k=0; k< knapsackingInstance.getSize(); k++)
     		aux[k] = knapsackingInstance.getProfit(k)/knapsackingInstance.getWeight(k);
  		double rho = maxarr(aux);
		
 		switch(penaltyFunction)
  		{
    		case LOGARITHMIC:
				return Math.log(1+rho*deg)/ Math.log(2); 
			case LINEAR:
				return rho*deg;
			case QUADRATIC:
				return (rho*deg)*(rho*deg);
  		}
 		
 		return 0.0;
	}
	
	public static double maxarr(double [] a)
	{
		double max = a[0];
		for(int i=1; i<a.length; i++)
			if(a[i]>max) max = a[i];

		return max;
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Boolean>> deepCopy() throws Exception {
		return new KnapsackingBinaryEvaluationFunction(new Knapsacking(knapsackingInstance),penaltyFunction);
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
