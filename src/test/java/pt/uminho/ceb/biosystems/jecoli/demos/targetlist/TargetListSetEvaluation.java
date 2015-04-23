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

import java.util.Iterator;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class TargetListSetEvaluation.
 */
public class TargetListSetEvaluation extends AbstractEvaluationFunction<ISetRepresentation<Integer>> {

	/** The target list. */
	TreeSet<Integer> targetList;
	
	IRandomNumberGenerator randomNumberGenerator;
	
	/**
	 * Instantiates a new target list set evaluation.
	 * 
	 * @param targetList the target list
	 */
	public TargetListSetEvaluation (TreeSet<Integer> targetList, IRandomNumberGenerator randomNumberGenerator)
	{
		super(true);
		this.targetList = targetList;
		this.randomNumberGenerator = randomNumberGenerator;
	}
	
	/**
	 * Instantiates a new target list set evaluation.
	 * 
	 * @param size the size
	 * @param minimum the minimum
	 * @param maximum the maximum
	 */
	public TargetListSetEvaluation (int size, int minimum, int maximum, IRandomNumberGenerator randomNumberGenerator)
	{
		super(true);
		targetList = new TreeSet<Integer>();
		this.randomNumberGenerator = randomNumberGenerator;
		
		// generate list randomly
		
		for(int i=0; i<size; i++)
		{
			int value; 
			do {
				value = minimum+(int)(randomNumberGenerator.nextDouble()*((maximum-minimum)+1));
			}
			while (targetList.contains(value));
			targetList.add(value);
		}
		
		Iterator<Integer> it = targetList.iterator();
		while(it.hasNext())
			System.out.print(" " + it.next());
		System.out.print("\n");
	}
	
	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public double evaluate(ISetRepresentation<Integer> solution) {
		
		double fitness = 0.0;
		
		Iterator<Integer> it = targetList.iterator();
		while(it.hasNext())
			if (solution.containsElement(it.next())) 
				fitness += 1.0;
	        
		return fitness;
	}

	@Override
	public IEvaluationFunction<ISetRepresentation<Integer>> deepCopy()  {
		TreeSet<Integer> targetListCopy = new TreeSet<Integer>();
		for(Integer targetValue:targetList)
			targetListCopy.add(targetValue);
		
		return new TargetListSetEvaluation(targetListCopy, randomNumberGenerator);
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}
}
