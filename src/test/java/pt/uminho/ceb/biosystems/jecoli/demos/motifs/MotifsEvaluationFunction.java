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
package pt.uminho.ceb.biosystems.jecoli.demos.motifs;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class MotifsEvaluationFunction.
 */
public class MotifsEvaluationFunction extends AbstractEvaluationFunction<ILinearRepresentation<Integer>> 
{

	/** The sequences. */
	protected SeqMotifs sequences;
	
	/**
	 * Instantiates a new motifs evaluation function.
	 * 
	 * @param seqMotifs the seq motifs
	 */
	public MotifsEvaluationFunction(SeqMotifs seqMotifs)
	{
		super(true);
		this.sequences = seqMotifs;
	}
	
	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public double evaluate(ILinearRepresentation<Integer> solution) {
			
		int[] positions = decode(solution);
		
		double fitness = sequences.score(positions);
	        
		return fitness;
	}
	
	/**
	 * Decode.
	 * 
	 * @param solution the solution
	 * 
	 * @return the int[]
	 */
	protected int[] decode (ILinearRepresentation<Integer> solution)
	{
		int[] res = new int[solution.getNumberOfElements()];
		for(int i=0; i < solution.getNumberOfElements(); i++)
		{
			res[i] = solution.getElementAt(i);
		}
		return res;
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Integer>> deepCopy() {
		return new MotifsEvaluationFunction(sequences.deepCopy());
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}
	
}
