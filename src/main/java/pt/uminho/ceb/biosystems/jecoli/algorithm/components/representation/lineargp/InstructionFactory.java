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
 * A factory for creating Instruction objects.
 */
public class InstructionFactory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** The instruction list. */
	protected List<IInstruction> instructionList;
	
	/** The cpu. */
	protected CPU cpu;
	
	/**
	 * Instantiates a new instruction factory.
	 * 
	 * @param instructionList the instruction list
	 */
	public InstructionFactory(List<IInstruction> instructionList,CPU cpu) {
		this.instructionList = instructionList;
		this.cpu = cpu;
	}

	/**
	 * Gets the random instruction.
	 * 
	 * @return the random instruction
	 */
	public IInstruction getRandomInstruction(){
		int position = (int) (Math.random()*instructionList.size());
		IInstruction instruction = instructionList.get(position);
		IInstruction newInstruction = instruction.newInstance();
		return newInstruction;
	}

	public InstructionFactory deepCopy(){
		List<IInstruction> instructionListCopy = new ArrayList<IInstruction>();
		
		for(int i = 0; i < instructionList.size();i++){
			IInstruction instruction = instructionList.get(i);
			IInstruction instructionCopy = instruction.copy();
			instructionList.add(instructionCopy);
		}
		
		return new InstructionFactory(instructionListCopy, cpu.deepCopy());
	}

	public void setCPU(CPU cpu) {
		this.cpu = cpu;
	}
	
	public CPU getCPU(){
		return cpu;
	}
	
}
