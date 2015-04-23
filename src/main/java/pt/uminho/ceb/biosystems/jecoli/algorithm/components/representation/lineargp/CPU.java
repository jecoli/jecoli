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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class CPU.
 */
public class CPU implements Serializable{
    
	private static final long serialVersionUID = 1L;

    /** The registers. */
    protected CPURegisters registers;
    
    /** The instruction pointer. */
    protected int instructionPointer;
    
    /** The number of output registers. */
    protected int numberOfOutputRegisters;


    //TODO Pre: numberOfOutputRegister <= numberOfReadWriteRegisters
    /**
     * Instantiates a new cPU.
     * 
     * @param numberOfReadWriteRegisters the number of read write registers
     * @param numberOfReadOnlyRegisters the number of read only registers
     * @param numberOfOutputRegisters the number of output registers
     */
    public CPU(int numberOfReadWriteRegisters, int numberOfReadOnlyRegisters, int numberOfOutputRegisters) {
        registers = new CPURegisters(numberOfReadWriteRegisters, numberOfReadOnlyRegisters);
        this.numberOfOutputRegisters = numberOfOutputRegisters;
    }

    /**
     * Evaluate program.
     * 
     * @param program the program
     * 
     * @return the list< double>
     */
    public List<Double> evaluateProgram(ILinearRepresentation<IInstruction> program) {
        int numberOfInstructions = program.getNumberOfElements();
        instructionPointer = 0;

        while (instructionPointer < numberOfInstructions) {
            IInstruction instruction = program.getElementAt(instructionPointer);
            instruction.execute(registers);
            instructionPointer += instruction.getInstructionPointerStepNumber();
        }

        return outputRegisterValues();
    }

    /**
     * Output register values.
     * 
     * @return the list< double>
     */
    protected List<Double> outputRegisterValues() {
        List<Double> outputRegisterValues = new ArrayList<Double>(numberOfOutputRegisters);
        for (int i = 0; i < numberOfOutputRegisters; i++)
            outputRegisterValues.add(registers.getRegisterValue(i));

        return outputRegisterValues;
    }
    
    

    /**
     * Sets the input value.
     * 
     * @param registerPosition the register position
     * @param value the value
     */
    public void setInputValue(int registerPosition, double value) {
        registers.setReadOnlyRegisterValue(registerPosition, value);
    }

    /**
     * Gets the number of read write registers.
     * 
     * @return the number of read write registers
     */
    public int getNumberOfReadWriteRegisters() {
        return registers.getNumberOfReadWriteRegisters();
    }

    /**
     * Gets the number of read only registers.
     * 
     * @return the number of read only registers
     */
    public int getNumberOfReadOnlyRegisters(){
       return registers.getNumberOfReadOnlyRegisters();
    }

    /**
     * Gets the total number of registers.
     * 
     * @return the total number of registers
     */
    public int getTotalNumberOfRegisters() {
        return getNumberOfReadWriteRegisters()+getNumberOfReadOnlyRegisters();
    }

	/**
	 * Clear all registers.
	 */
	public void clearAllRegisters() {
		registers.clearRegisters();
	}

	public CPU deepCopy(){
		return null;
	}

	
    
}