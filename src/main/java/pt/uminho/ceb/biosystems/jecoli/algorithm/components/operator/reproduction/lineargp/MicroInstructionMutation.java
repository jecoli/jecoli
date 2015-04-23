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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.lineargp;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.AbstractMutationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.lineargp.IInstruction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.lineargp.ProgramRepresentationFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroInstructionMutation.
 */
public class MicroInstructionMutation extends AbstractMutationOperator<ILinearRepresentation<IInstruction>,ILinearRepresentationFactory<IInstruction>> {
    
	
  
    /**
	 * 
	 */
	private static final long serialVersionUID = -6544312573513172387L;

	/**
     * Mutate instruction.
     * 
     * @param instruction the instruction
     * @param randomNumberGenerator 
     * 
     * @return the i instruction
     * 
     * @throws Exception the exception
     */
    public IInstruction mutateInstruction(IInstruction instruction,ILinearRepresentationFactory<IInstruction> solutionFactory,IRandomNumberGenerator randomNumberGenerator) throws Exception{
        IInstruction resultInstruction = instruction.copy();

        int instructionPositionToChange = (int) (randomNumberGenerator.nextDouble() * 3);

        switch (instructionPositionToChange) {
            case 0:
                mutateAssignmentOperand(resultInstruction,solutionFactory,randomNumberGenerator);
                break;
            case 1:
                mutateOperand(resultInstruction,solutionFactory,randomNumberGenerator);
                break;
            case 2:
                mutateConstant(resultInstruction,solutionFactory,randomNumberGenerator);
                break;
        }

        return resultInstruction;
    }

    /**
     * Mutate constant.
     * 
     * @param resultInstruction the result instruction
     * @param randomNumberGenerator 
     * 
     * @throws Exception the exception
     */
    protected void mutateConstant(IInstruction resultInstruction,ILinearRepresentationFactory<IInstruction> solutionFactory,IRandomNumberGenerator randomNumberGenerator) throws Exception{
        if(resultInstruction.getNumberOfConstants() > 0){
        	int numberOfOperands = resultInstruction.getNumberOfConstants();
        	int operandPosition = (int) (randomNumberGenerator.nextDouble()*(numberOfOperands));
        	ProgramRepresentationFactory programFactory = (ProgramRepresentationFactory) solutionFactory;
        	double operandValue = randomNumberGenerator.nextDouble()*(programFactory.getConstantUpperValue()-programFactory.getConstantLowerValue())+programFactory.getConstantLowerValue();
        	resultInstruction.setConstant(operandPosition,operandValue);
        }
    }

    /**
     * Mutate operand.
     * 
     * @param resultInstruction the result instruction
     * @param randomNumberGenerator 
     * 
     * @throws Exception the exception
     */
    protected void mutateOperand(IInstruction resultInstruction,ILinearRepresentationFactory<IInstruction> solutionFactory,IRandomNumberGenerator randomNumberGenerator) throws Exception{
    	if(resultInstruction.getNumberOfOperands() > 0){
    		int numberOfOperands = resultInstruction.getNumberOfOperands();
    		int operandPosition = (int) (randomNumberGenerator.nextDouble()*(numberOfOperands));
    		ProgramRepresentationFactory programFactory = (ProgramRepresentationFactory) solutionFactory;
    		int operandValue = (int) (randomNumberGenerator.nextDouble()* (programFactory.getCpuNumberOfRegisters()));
    		resultInstruction.setOperand(operandPosition,operandValue);
        }
    }

    /**
     * Mutate assignment operand.
     * 
     * @param resultInstruction the result instruction
     * 
     * @throws Exception the exception
     */
    protected void mutateAssignmentOperand(IInstruction resultInstruction,ILinearRepresentationFactory<IInstruction> solutionFactory,IRandomNumberGenerator randomNumberGenerator) throws Exception{
    	if(resultInstruction.getNumberOfAssignmentOperands() > 0){
    		int numberOfOperands = resultInstruction.getNumberOfAssignmentOperands();
    		int operandPosition = (int) (randomNumberGenerator.nextDouble()*(numberOfOperands));
    		ProgramRepresentationFactory programFactory = (ProgramRepresentationFactory) solutionFactory;
    		int operandValue = (int) (randomNumberGenerator.nextDouble()* programFactory.getCpuNumberOfReadWriteRegisters());
    		resultInstruction.setAssignmentOperand(operandPosition,operandValue);
    	}
    }

	
	@Override
	protected void mutateGenome(ILinearRepresentation<IInstruction> childGenome,ILinearRepresentationFactory<IInstruction> solutionFactory,IRandomNumberGenerator randomNumberGenerator) {
		 int programLastPosition = childGenome.getNumberOfElements();
	     int currentInstruction = (int) (randomNumberGenerator.nextDouble()*programLastPosition);

	     IInstruction instruction = childGenome.getElementAt(currentInstruction);
	     IInstruction newInstruction =  null;
		try {
			newInstruction = mutateInstruction(instruction,solutionFactory,randomNumberGenerator);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		assert(instruction != null);
	    childGenome.setElement(currentInstruction,newInstruction);
	}

	@Override
	public MicroInstructionMutation deepCopy() throws Exception {
		return new MicroInstructionMutation();
	}

}