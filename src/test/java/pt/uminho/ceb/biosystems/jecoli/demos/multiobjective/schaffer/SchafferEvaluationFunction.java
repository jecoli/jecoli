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
package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.schaffer;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;



/**
 * The Class SchafferEvaluationFunction.
 */
public class SchafferEvaluationFunction<T extends IRepresentation> extends AbstractMultiobjectiveEvaluationFunction<ILinearRepresentation<Double>>{

	private static final long serialVersionUID = 3902508954819732302L;

	/**
	 * Instantiates a new schaffer evaluation function.
	 * 
	 * @param isMaximization the is maximization
	 */
	public SchafferEvaluationFunction(boolean isMaximization) {
		super();
	}

	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public Double[] evaluateMO(ILinearRepresentation<Double> solutionRepresentation) throws Exception {
		Double[] fitnesses = new Double[2]; // two objectives
		Double variable = solutionRepresentation.getElementAt(0);
		
		fitnesses[0]=(variable*variable); // obj 1 
		fitnesses[1]=( (variable - 2.0) * (variable - 2.0) ); // obj 2 		
		
		return fitnesses;		
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Double>> deepCopy()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}


}
