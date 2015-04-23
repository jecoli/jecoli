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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IOperator;



// TODO: Auto-generated Javadoc
/**
 * The Class OperatorContainer.
 */
public abstract class AbstractOperatorContainer<T extends IOperator> implements IOperatorContainer<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6531489104017772898L;
	
	/** The jecoliunittest.operators.operator container. */
	protected List<AbstractOperatorContainerCell<T>> operatorContainer;

	/**
	 * Instantiates a new jecoliunittest.operators.operator container.
	 */
	public AbstractOperatorContainer(){
		operatorContainer = new ArrayList<AbstractOperatorContainerCell<T>>();
	}
	
	protected AbstractOperatorContainer(List<AbstractOperatorContainerCell<T>> operatorContainerList){
		this.operatorContainer = operatorContainerList;
	}

	public boolean isValid(){
		double probability = calculateProbability();
		return (probability >= 0.999999) && (probability <= 1.000001);
	}

	/**
	 * Calculate probability.
	 * 
	 * @return the double
	 */
	protected double calculateProbability(){
		double currentValue = 0;
		for(AbstractOperatorContainerCell<T> cell:operatorContainer)
			currentValue += cell.getProbability();

		return currentValue;
	}
	
	protected void verifyProbability(double operatorProbability) throws Exception{
		double currentProbabiltySum = calculateProbability();
		double newProbabilitySum = currentProbabiltySum + operatorProbability;
		if(newProbabilitySum > 1)
			throw new Exception("Probability > 1");
	}

	public abstract void addOperator(double probability, T operator) throws Exception;

	public T selectOperator(){
		double accumulatedProbability = 0;
		double randomNumber = Math.random();

		for(AbstractOperatorContainerCell<T> cell:operatorContainer){
			accumulatedProbability += cell.getProbability();
			if(accumulatedProbability >= randomNumber){
				return cell.getOperator();
			}
		}
		return operatorContainer.get(operatorContainer.size()-1).getOperator();
	}
	
	/**
	 * <p>Returns a new instance of <code>IOperatorContainer</code>, containing a subset of the original operators</p>
	 * <p>The probabilities for selection are recalculated, keeping the old relative values valid</p>.
	 * 
	 * @param indexes the indexes
	 * 
	 * @return the sub set
	 * 
	 * @throws Exception the exception
	 */
	@Override
	public abstract IOperatorContainer<T> getSubSet(List<Integer> indexes) throws Exception;

}