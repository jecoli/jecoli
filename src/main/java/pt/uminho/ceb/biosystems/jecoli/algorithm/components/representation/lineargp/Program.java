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
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.LinearRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class Program.
 */
public class Program extends LinearRepresentation<IInstruction> implements Serializable{
	
	private static final long serialVersionUID = 1L;
   
    /**
     * Instantiates a new program.
     * 
     * @param instructionList the instruction list
     */
    public Program(List<IInstruction> instructionList){
    	super(instructionList);
    }
    
    /**
     * Instantiates a new program.
     */
    public Program(){
    	super();
    }

   /**
    * Gets the string representation.
    * 
    * @return the string representation
    */
   public void getStringRepresentation() {
	   for(IInstruction instruction:genome)
		   instruction.getStringRepresentation();
    }
}