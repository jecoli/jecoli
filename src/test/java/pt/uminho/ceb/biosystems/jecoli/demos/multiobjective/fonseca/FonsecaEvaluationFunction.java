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
package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.fonseca;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;



/**
 * The Class FonsecaEvaluationFunction.
 */
public class FonsecaEvaluationFunction<T extends IRepresentation> extends AbstractMultiobjectiveEvaluationFunction<ILinearRepresentation<Double>>{

	
	private static final long serialVersionUID = 1621175605651718142L;

	/**
	 * Instantiates a new fonseca evaluation function.
	 * 
	 * @param isMaximization the is maximization
	 */
	public FonsecaEvaluationFunction(boolean isMaximization) {
		super();
	}

	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public Double[] evaluateMO(ILinearRepresentation<Double> solutionRepresentation) throws Exception {
		Double[] fitnesses = new Double[2]; // two objectives

		double sum1 = 0.0;
		double f0,f1;
		
		//objective 1
		for (int var = 0; var < solutionRepresentation.getNumberOfElements(); var++){
			sum1 += StrictMath.pow(solutionRepresentation.getElementAt(var) - (1.0/StrictMath.sqrt((double)solutionRepresentation.getNumberOfElements())),2.0);            
		}
		double exp1 = StrictMath.exp((-1.0)*sum1);
		f0 = 1 - exp1;

		//objective 2
		double sum2 = 0.0;        
		for (int var = 0; var < solutionRepresentation.getNumberOfElements(); var++){
			sum2 += StrictMath.pow(solutionRepresentation.getElementAt(var) + (1.0/StrictMath.sqrt((double)solutionRepresentation.getNumberOfElements())),2.0);
		}    
		double exp2 = StrictMath.exp((-1.0)*sum2);
		f1 = 1 - exp2;


		fitnesses[0]=f0;
		fitnesses[1]=f1;

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
