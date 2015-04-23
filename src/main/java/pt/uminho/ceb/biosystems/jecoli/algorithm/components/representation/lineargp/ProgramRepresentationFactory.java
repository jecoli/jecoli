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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.AbstractLinearRepresentationFactory;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating ProgramRepresentation objects.
 */
public class ProgramRepresentationFactory extends AbstractLinearRepresentationFactory<IInstruction> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** The instruction factory. */
	protected InstructionFactory instructionFactory;
	
	/** The cpu. */
	protected CPU cpu;
	
	/** The constant upper value. */
	protected double constantUpperValue;
	
	/** The constant lower value. */
	protected double constantLowerValue;
	
	/**
	 * Instantiates a new program representation factory.
	 * 
	 * @param programSize the program size
	 */
	public ProgramRepresentationFactory(int programSize) {
		super(programSize);
	}

	/**
	 * Instantiates a new program representation factory.
	 * 
	 * @param programSize the program size
	 * @param evaluationFunction the evaluation function
	 * @param instructionFactory the instruction factory
	 */
	public ProgramRepresentationFactory(int programSize,InstructionFactory instructionFactory) {
		super(programSize);
		this.instructionFactory = instructionFactory;
		constantUpperValue = 10000;
		constantLowerValue = -10000;
		this.cpu = instructionFactory.getCPU();
	}

	/* (non-Javadoc)
	 * @see core.representation.AbstractLinearRepresentationFactory#copyGeneValue(java.lang.Object)
	 */
	@Override
	protected IInstruction copyGeneValue(IInstruction geneValueToCopy) {
		return geneValueToCopy.copy();
	}

	/* (non-Javadoc)
	 * @see core.representation.ILinearRepresentationFactory#generateGeneValue(int)
	 */
	@Override
	public IInstruction generateGeneValue(int genePosition,IRandomNumberGenerator randomGenerator){
		IInstruction instruction = instructionFactory.getRandomInstruction();
		try {
			mutateInstruction(instruction);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return instruction;
	}

	/**
	 * Mutate instruction.
	 * 
	 * @param instruction the instruction
	 * 
	 * @throws Exception the exception
	 */
	protected void mutateInstruction(IInstruction instruction) throws Exception{
		
		for(int i = 0; i < instruction.getNumberOfAssignmentOperands();i++){
			int registerPosition = (int) (Math.random()* cpu.getNumberOfReadWriteRegisters()-1);
			instruction.setAssignmentOperand(i, registerPosition);
		}
		
		for(int i = 0; i < instruction.getNumberOfConstants();i++){
			int value =  (int) (Math.random()*(constantUpperValue-constantLowerValue)+constantLowerValue);
			instruction.setConstant(i,value);
		}
		
		for(int w = 0;  w < instruction.getNumberOfOperands();w++){
			int numberOfRegisters = cpu.getTotalNumberOfRegisters();
			int registerPosition = (int) (Math.random()*numberOfRegisters);
			instruction.setOperand(w,registerPosition);
		}
		
		
	}

	/**
	 * Gets the constant lower value.
	 * 
	 * @return the constant lower value
	 */
	public double getConstantLowerValue(){
		return constantLowerValue;
	}
	
	/**
	 * Gets the constant upper value.
	 * 
	 * @return the constant upper value
	 */
	public double getConstantUpperValue(){
		return constantUpperValue;
	}

	/**
	 * Gets the cpu number of registers.
	 * 
	 * @return the cpu number of registers
	 */
	public double getCpuNumberOfRegisters() {
		return cpu.getTotalNumberOfRegisters();
	}

	/**
	 * Gets the cpu number of read write registers.
	 * 
	 * @return the cpu number of read write registers
	 */
	public double getCpuNumberOfReadWriteRegisters() {
		return cpu.getNumberOfReadWriteRegisters();
	}

	@Override
	public ProgramRepresentationFactory deepCopy(){
		InstructionFactory instructionFactoryCopy = instructionFactory.deepCopy();
		return new ProgramRepresentationFactory(solutionSize,instructionFactoryCopy);
	}

	@Override
	public int getMaximumNumberOfGenes() {
		return solutionSize;
	}

	@Override
	public int getMinimumNumberOfGenes() {
		return solutionSize;
	}
	
	

}
