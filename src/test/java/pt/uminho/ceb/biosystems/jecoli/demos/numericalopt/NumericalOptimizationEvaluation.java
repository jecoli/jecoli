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
package pt.uminho.ceb.biosystems.jecoli.demos.numericalopt;

import java.util.Arrays;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class NumericalOptimizationEvaluation.
 */
public class NumericalOptimizationEvaluation extends AbstractEvaluationFunction<ILinearRepresentation<Double>>
{

	/** The function. */
	int function; // defines function (see class Function)
	
	/** The number variables. */
	int numberVariables; // number of variables
	
	/** The function pars. */
	double [] functionPars; // function parameters

	/**
	 * Instantiates a new numerical optimization evaluation.
	 * 
	 * @param function the function
	 * @param numberVariables the number variables
	 * @param functionPars the function pars
	 */
	public NumericalOptimizationEvaluation(int function, int numberVariables, double[] functionPars) {
		super(false);
		this.function = function;
		this.numberVariables = numberVariables;
		this.functionPars = functionPars;
	}

	/**
	 * Instantiates a new numerical optimization evaluation.
	 * 
	 * @param function the function
	 * @param numberVariables the number variables
	 */
	public NumericalOptimizationEvaluation(int function, int numberVariables) {
		super(false);
		this.function = function;
		this.numberVariables = numberVariables;
	}
	
	
    @Override
    public double evaluate(ILinearRepresentation<Double> solution) {
    	
    	double [] genome = decodeGenome(solution);
    	double fitness = Functions.function(this.function, genome, this.numberVariables, this.functionPars);
    	
    	return fitness;
	}

	/**
	 * Decode genome.
	 * 
	 * @param solution the solution
	 * 
	 * @return the double[]
	 */
	public double[] decodeGenome(ILinearRepresentation<Double> solution)
	{
		double [] genome = new double[solution.getNumberOfElements()];
		for(int i=0; i < solution.getNumberOfElements(); i++)
			genome[i] = solution.getElementAt(i);
		return genome;	
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Double>> deepCopy() {
		double[] functionParsCopy = Arrays.copyOf(functionPars, functionPars.length);
		return new NumericalOptimizationEvaluation(function,numberVariables,functionParsCopy);
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}
	
}
