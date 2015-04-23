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
package pt.uminho.ceb.biosystems.jecoli.demos.targetlist;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class TargetListEvaluationFunction.
 */
public class TargetListEvaluationFunction extends AbstractEvaluationFunction<ILinearRepresentation<Integer>> {

	/** The target list. */
	List<Integer> targetList;
	IRandomNumberGenerator randomNumberGenerator;
	
	/**
	 * Instantiates a new target list evaluation function.
	 * 
	 * @param targetList the target list
	 */
	public TargetListEvaluationFunction (List<Integer> targetList,IRandomNumberGenerator randomNumberGenerator)
	{
		super(true);
		this.targetList = targetList;
		this.randomNumberGenerator = randomNumberGenerator;
	}
	
	/**
	 * Instantiates a new target list evaluation function.
	 * 
	 * @param size the size
	 * @param minimum the minimum
	 * @param maximum the maximum
	 */
	public TargetListEvaluationFunction (int size, int minimum, int maximum,IRandomNumberGenerator randomNumberGenerator)
	{
		super(true);
		this.randomNumberGenerator = randomNumberGenerator;
		targetList = new ArrayList<Integer>(size);
		
		// generate list randomly
		for(int i=0; i<size; i++)
		{
			int value = minimum+(int)(randomNumberGenerator.nextDouble()*((maximum-minimum)+1));			
			targetList.add(i,value);
			System.out.print(" " + targetList.get(i));
		}
		System.out.print("\n");
    }
	
	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public double evaluate(ILinearRepresentation<Integer> solution) {
			
		double fitness = 0.0;
		for(int i=0; i < targetList.size(); i++)
			if (solution.getElementAt(i).equals(targetList.get(i)))
				fitness += 1.0;
	  
		return fitness;
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Integer>> deepCopy() throws Exception {
		List<Integer> targetListCopy = new ArrayList<Integer>();
		for(Integer targetValue:targetList)
			targetListCopy.add(targetValue);
		
		return new TargetListEvaluationFunction(targetListCopy,randomNumberGenerator.deepCopy());
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}


}
