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

// TODO: Auto-generated Javadoc
/**
 * The Class OperatorContainerException.
 */
public class OperatorContainerException extends Exception{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new jecoliunittest.operators.operator container exception.
	 */
	public OperatorContainerException(){
		super();
	}
	
	/**
	 * Instantiates a new jecoliunittest.operators.operator container exception.
	 * 
	 * @param error the error
	 */
	public OperatorContainerException(String error){
		super(error);
	}
	
	/**
	 * Instantiates a new jecoliunittest.operators.operator container exception.
	 * 
	 * @param cause the cause
	 */
	public OperatorContainerException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Instantiates a new jecoliunittest.operators.operator container exception.
	 * 
	 * @param error the error
	 * @param cause the cause
	 */
	public OperatorContainerException(String error, Throwable cause){
		super(error,cause);
	}

}
