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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.lineargp;

// TODO: Auto-generated Javadoc
/**
 * The Interface IInstruction.
 */
public interface IInstruction{
	
	/**
	 * Gets the number of assignment operands.
	 * 
	 * @return the number of assignment operands
	 */
	int getNumberOfAssignmentOperands();
	
	/**
	 * Gets the number of operands.
	 * 
	 * @return the number of operands
	 */
	int getNumberOfOperands();
	
	/**
	 * Gets the number of constants.
	 * 
	 * @return the number of constants
	 */
	int getNumberOfConstants();
	
	/**
	 * Sets the assignment operand.
	 * 
	 * @param operandPosition the operand position
	 * @param registerPosition the register position
	 * 
	 * @throws Exception the exception
	 */
	void setAssignmentOperand(int operandPosition,int registerPosition) throws Exception;
	
	/**
	 * Sets the operand.
	 * 
	 * @param operandPosition the operand position
	 * @param registerPosition the register position
	 * 
	 * @throws Exception the exception
	 */
	void setOperand(int operandPosition,int registerPosition) throws Exception;
	
	/**
	 * Sets the constant.
	 * 
	 * @param constantPosition the constant position
	 * @param value the value
	 * 
	 * @throws Exception the exception
	 */
	void setConstant(int constantPosition,double value) throws Exception;
	
	/**
	 * Gets the string representation.
	 * 
	 */
	void getStringRepresentation();
	
	/**
	 * Execute.
	 * 
	 * @param registers the registers
	 */
	void execute(CPURegisters registers);
	
	/**
	 * Copy.
	 * 
	 * @return the i instruction
	 */
	IInstruction copy();
	
	/**
	 * Gets the instruction pointer step number.
	 * 
	 * @return the instruction pointer step number
	 */
	int getInstructionPointerStepNumber();
	
	/**
	 * New instance.
	 * 
	 * @return the i instruction
	 */
	IInstruction newInstance();
}
