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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IOperator;

/**
 * The Class OperatorContainerCell.
 */
public abstract class AbstractOperatorContainerCell<T extends IOperator> implements IOperatorContainerCell<T>,IDeepCopy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3997058658795880521L;

	/** The jecoliunittest.operators.operator. */
	protected T operator;
	
	/** The probability. */
	protected double probability;

	/**
	 * Instantiates a new jecoliunittest.operators.operator container cell.
	 * 
	 * @param probability the probability
	 * @param operator the jecoliunittest.operators.operator
	 * 
	 * @throws Exception the exception
	 */
	public AbstractOperatorContainerCell(double probability,T operator) throws Exception{
		if(probability > 1)
			throw new Exception("Invalid probability");

		this.operator = operator;
		this.probability = probability;
	}

	/**
	 * Gets the jecoliunittest.operators.operator.
	 * 
	 * @return the jecoliunittest.operators.operator
	 */
	public T getOperator(){
		return operator;
	}

	/**
	 * Sets the jecoliunittest.operators.operator.
	 * 
	 * @param operator the new jecoliunittest.operators.operator
	 */
	public void setOperator(T operator){
		this.operator = operator;
	}

	/**
	 * Gets the probability.
	 * 
	 * @return the probability
	 */
	public double getProbability(){
		return probability;
	}

	/**
	 * Sets the probability.
	 * 
	 * @param probability the new probability
	 */
	public void setProbability(double probability){
		this.probability = probability;
	}


	public abstract AbstractOperatorContainerCell<T> deepCopy() throws Exception;
}