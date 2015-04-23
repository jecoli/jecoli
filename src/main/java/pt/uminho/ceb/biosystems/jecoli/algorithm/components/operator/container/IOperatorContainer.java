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

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IOperator;

/**
 * The Interface IOperatorContainer.
 */
public interface IOperatorContainer<T extends IOperator> extends IDeepCopy{

	/**
	 * randomly selects an jecoliunittest.operators.operator from the container, taking its probability into account.
	 * 
	 * @return the T
	 */
	T selectOperator();
	
	/**
	 * adds a new jecoliunittest.operators.operator to this container with a given probability.
	 * 
	 * @param probability the probability
	 * @param operator the jecoliunittest.operators.operator
	 * 
	 * @throws Exception the exception
	 */
	void addOperator(double probability, T operator) throws Exception;
	
	/**
	 * validates the accumulated probability of the contained operators.
	 * 
	 * @return true, if checks if is valid
	 */
	boolean isValid();
	
	IOperatorContainer<T> getSubSet(List<Integer> indexes) throws Exception;
	
	IOperatorContainer<T> deepCopy() throws Exception;
	
	

}