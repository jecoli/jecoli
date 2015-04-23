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
package pt.uminho.ceb.biosystems.jecoli.demos.symbolicregression.lgpinstruction;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.lineargp.CPURegisters;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.lineargp.IInstruction;

// TODO: Auto-generated Javadoc
/**
 * The Class AddInstruction.
 */
public class AddInstruction implements IInstruction {
	
	/** The assignment operand value. */
	protected int assignmentOperandValue;
    
    /** The operand values. */
    protected int[] operandValues;
	
    /**
     * Instantiates a new adds the instruction.
     */
    public AddInstruction() {
        this.operandValues = new int[2];
    }
    
	/**
	 * Instantiates a new adds the instruction.
	 * 
	 * @param assignmentOperandValue the assignment operand value
	 * @param operandValues the operand values
	 */
	public AddInstruction(int assignmentOperandValue, int[] operandValues) {
		this.assignmentOperandValue = assignmentOperandValue;
		this.operandValues = operandValues;
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#copy()
	 */
	@Override
	public IInstruction copy() {
		int[] newOperandArray = new int[operandValues.length];
		for(int i = 0; i < operandValues.length;i++)
			newOperandArray[i] = operandValues[i];
		return new AddInstruction(assignmentOperandValue,operandValues);
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#execute(core.representation.lineargp.CPURegisters)
	 */
	@Override
	public void execute(CPURegisters registers) {
        int assignmentRegister = assignmentOperandValue;
        int operand = operandValues[0];
        int operand1 = operandValues[1];
        double operandValue = registers.getRegisterValue(operand);
        double operandValue1 = registers.getRegisterValue(operand1);
        double result = operandValue + operandValue1;
        registers.setRegisterValue(assignmentRegister,result);
    }
	
	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#getInstructionPointerStepNumber()
	 */
	@Override
	public int getInstructionPointerStepNumber() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#getNumberOfAssignmentOperands()
	 */
	@Override
	public int getNumberOfAssignmentOperands() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#getNumberOfConstants()
	 */
	@Override
	public int getNumberOfConstants() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#getNumberOfOperands()
	 */
	@Override
	public int getNumberOfOperands() {
		return 2;
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#getStringRepresentation()
	 */
	@Override
	public void getStringRepresentation() {
		System.out.println(" ADD "+assignmentOperandValue+" "+operandValues[0]+" "+operandValues[1]);
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#newInstance()
	 */
	@Override
	public IInstruction newInstance() {
		return new AddInstruction();
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#setAssignmentOperand(int, int)
	 */
	@Override
	public void setAssignmentOperand(int operandPosition, int registerPosition)	throws Exception {
		if(operandPosition != 0)
            throw new Exception("Wrong assignment operand position");
		
		assignmentOperandValue = registerPosition;
	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#setConstant(int, double)
	 */
	@Override
	public void setConstant(int constantPosition, double value)  throws Exception {
		 throw new Exception("No constant operands");

	}

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#setOperand(int, int)
	 */
	@Override
	public void setOperand(int operandPosition, int registerPosition) 	throws Exception {
		 if(operandPosition >= operandValues.length)
	            throw new Exception("Invalid Operand Position");
		 
		 operandValues[operandPosition] = registerPosition;

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return " ADD "+assignmentOperandValue+" "+operandValues[0]+" "+operandValues[1];
	}

}
