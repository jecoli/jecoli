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
package pt.uminho.ceb.biosystems.jecoli.demos.countones;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


/**
 * The Class CountOnesEvaluationFunction.
 * Defines the evaluation function for the toy problem Count Ones.
 */
public class CountOnesEvaluationFunction extends AbstractEvaluationFunction<ILinearRepresentation<Boolean>> {

    
	private static final long serialVersionUID = 1L;


	/**
     * Instantiates a new count ones evaluation function.
     */
	public CountOnesEvaluationFunction(){
        super(true);
    }
	

    @Override
    public double evaluate(ILinearRepresentation<Boolean> solution) {
    	
    	int countOnesValue = countOnes(solution);

		return countOnesValue;
	}

	/**
	 * Count ones.
	 * 
	 * @param genomeRepresentation the genome representation
	 * 
	 * @return the int
	 */
	protected int countOnes(ILinearRepresentation<Boolean> genomeRepresentation){
		int countOneValues = 0;
		for(int i = 0;i < genomeRepresentation.getNumberOfElements();i++)
			if(genomeRepresentation.getElementAt(i))	countOneValues++;

		return countOneValues;
	}


	@Override
	public IEvaluationFunction<ILinearRepresentation<Boolean>> deepCopy() {
		return new CountOnesEvaluationFunction();
	}


	@Override
	public void verifyInputData() throws InvalidEvaluationFunctionInputDataException {
	}


	@Override
	public int getNumberOfObjectives() {
		return 1;
	}
}
