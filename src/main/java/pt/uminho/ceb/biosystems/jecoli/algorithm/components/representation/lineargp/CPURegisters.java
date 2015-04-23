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

// TODO: Auto-generated Javadoc
/**
 * The Class CPURegisters.
 */
public class CPURegisters implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
    /** The read write registers. */
    protected List<Double> readWriteRegisters;
    
    /** The read only registers. */
    protected List<Double> readOnlyRegisters;

    /**
     * Instantiates a new cPU registers.
     * 
     * @param numberOfReadWriteRegisters the number of read write registers
     * @param numberOfReadOnlyRegisters the number of read only registers
     */
    public CPURegisters(int numberOfReadWriteRegisters, int numberOfReadOnlyRegisters) {
        readOnlyRegisters = new ArrayList<Double>(numberOfReadWriteRegisters);
        readWriteRegisters = new ArrayList<Double>(numberOfReadOnlyRegisters);
        initializeRegisters(readOnlyRegisters, numberOfReadOnlyRegisters);
        initializeRegisters(readWriteRegisters, numberOfReadWriteRegisters);
    }

    /**
     * Initialize registers.
     * 
     * @param registers the registers
     * @param numberOfRegisters the number of registers
     */
    protected void initializeRegisters(List<Double> registers, int numberOfRegisters) {
        for (int i = 0; i < numberOfRegisters; i++)
            registers.add(0.0);
    }

    /**
     * Gets the register value.
     * 
     * @param registerPosition the register position
     * 
     * @return the register value
     */
    public double getRegisterValue(int registerPosition) {
        if (registerPosition < readWriteRegisters.size())
            return readWriteRegisters.get(registerPosition);

        int newRegisterPosition = registerPosition - readWriteRegisters.size();
        return readOnlyRegisters.get(newRegisterPosition);
    }

    /**
     * Sets the register value.
     * 
     * @param registerPosition the register position
     * @param value the value
     */
    public void setRegisterValue(int registerPosition, double value) {
        readWriteRegisters.set(registerPosition, value);
    }

    /**
     * Sets the read only register value.
     * 
     * @param registerPosition the register position
     * @param value the value
     */
    public void setReadOnlyRegisterValue(int registerPosition, double value) {
        readOnlyRegisters.set(registerPosition, value);
    }

    /**
     * Gets the number of read write registers.
     * 
     * @return the number of read write registers
     */
    public int getNumberOfReadWriteRegisters() {
        return readWriteRegisters.size();
    }

    /**
     * Gets the number of read only registers.
     * 
     * @return the number of read only registers
     */
    public int getNumberOfReadOnlyRegisters() {
        return readOnlyRegisters.size();
    }

	/**
	 * Clear registers.
	 */
	public void clearRegisters() {
		int numberOfReadOnlyRegisters = readOnlyRegisters.size();
		int numberOfReadWriteRegisters = readWriteRegisters.size();
		 clearRegisters(readOnlyRegisters, numberOfReadOnlyRegisters);
	     clearRegisters(readWriteRegisters, numberOfReadWriteRegisters);		
	}

	/**
	 * Clear registers.
	 * 
	 * @param registers the registers
	 * @param numberOfReadWriteRegisters the number of read write registers
	 */
	protected void clearRegisters(List<Double> registers,int numberOfReadWriteRegisters) {
		
		for(int i = 0; i < numberOfReadWriteRegisters;i++)
			registers.set(i,0.0);
		
	}
}