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
 * The Class ConstantInstruction.
 */
public class ConstantInstruction implements IInstruction{
    
    /** The assigment register. */
    protected int assigmentRegister;
    
    /** The value. */
    protected double value;

    /**
     * Instantiates a new constant instruction.
     */
    public ConstantInstruction(){
    	assigmentRegister = 0;
    	value = 0;
    }
    
    /**
     * Instantiates a new constant instruction.
     * 
     * @param assignmentRegister the assignment register
     * @param value the value
     */
    public ConstantInstruction(int assignmentRegister,double value){
    	this.assigmentRegister = assignmentRegister;
    	this.value = value;
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#execute(core.representation.lineargp.CPURegisters)
     */
    public void execute(CPURegisters registers) {
        registers.setRegisterValue(assigmentRegister,value);
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#getNumberOfAssignmentOperands()
     */
    public int getNumberOfAssignmentOperands() {
        return 1;
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#getNumberOfOperands()
     */
    public int getNumberOfOperands() {
        return 0;
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#getNumberOfConstants()
     */
    public int getNumberOfConstants() {
        return 1;  
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#getInstructionPointerStepNumber()
     */
    public int getInstructionPointerStepNumber() {
        return 1;
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#setAssignmentOperand(int, int)
     */
    public void setAssignmentOperand(int operandPosition, int registerPosition) throws Exception {
        if(operandPosition != 0)
            throw new Exception("Wrong assignment operand position");

        assigmentRegister = registerPosition;
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#setOperand(int, int)
     */
    public void setOperand(int operandPosition, int registerPosition) throws Exception {
        throw new Exception("Invalid Operand Position");
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#setConstant(int, double)
     */
    public void setConstant(int constantPosition, double value) throws Exception {
       if(constantPosition != 0)
            throw new Exception("Wrong assignment operand position");

        this.value = value;
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#getStringRepresentation()
     */
    public void getStringRepresentation() {
        System.out.println(" CONST "+ assigmentRegister+" " +value);
    }

    /* (non-Javadoc)
     * @see core.representation.lineargp.IInstruction#newInstance()
     */
    public IInstruction newInstance() {
        return new ConstantInstruction();
    }

	/* (non-Javadoc)
	 * @see core.representation.lineargp.IInstruction#copy()
	 */
	@Override
	public IInstruction copy() {
		return new ConstantInstruction(assigmentRegister,value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return " CONST "+ assigmentRegister+" " +value;
	}
	
}
