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
package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.countones;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class CountOnesMOEvaluationFunction.
 */
public class CountOnesMOEvaluationFunction<T extends IRepresentation> extends AbstractMultiobjectiveEvaluationFunction<ILinearRepresentation<Boolean>>{

	private static final long serialVersionUID = 859184750181754860L;

	/**
	 * Instantiates a new count ones mo evaluation function.
	 * 
	 * @param isMaximization the is maximization
	 */
	public CountOnesMOEvaluationFunction(boolean isMaximization) {
		super();
	}

	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public Double[] evaluateMO(ILinearRepresentation<Boolean> solutionRepresentation) throws Exception {
		
		Double[] fitnesses = new Double[2];		
		int ones=0;
		int zeros=0;
		
		for(int i=0;i< solutionRepresentation.getNumberOfElements();i++)
			if(solutionRepresentation.getElementAt(i))
				ones++;
			else zeros++;
		
		
		fitnesses[0]=(ones*1.0);
		fitnesses[1]=(zeros*1.0);
		
		return fitnesses;
	}

	@Override
	public void verifyInputData() throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub		
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Boolean>> deepCopy() throws Exception {
		return null;
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	
}
