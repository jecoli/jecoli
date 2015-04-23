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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class KnapsackingPermutationEvaluationFunction.
 */
public class KnapsackingPermutationEvaluationFunction 
extends AbstractEvaluationFunction<PermutationRepresentation> {
	
	/** The knapsacking instance. */
	Knapsacking knapsackingInstance;
	
	/**
	 * Instantiates a new knapsacking permutation evaluation function.
	 * 
	 * @param knapsackingInstance the knapsacking instance
	 */
	public KnapsackingPermutationEvaluationFunction(Knapsacking knapsackingInstance){
		super(true);  // maximization
		this.knapsackingInstance = knapsackingInstance;
	}
	
	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public double evaluate(PermutationRepresentation solution) 
	{
		int [] genome = solution.getGenomeAsArray();
		
		boolean [] knsol = knapsackingInstance.orderKnap(genome);
		double fitness = knapsackingInstance.totalProfit(knsol);
		
		return fitness;
	}

	@Override
	public IEvaluationFunction<PermutationRepresentation> deepCopy() throws Exception {
		return new KnapsackingPermutationEvaluationFunction(new Knapsacking(knapsackingInstance));
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}


}
