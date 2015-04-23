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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;



// TODO: Auto-generated Javadoc
/**
 * The Class SolutionCellContainer.
 */
public class SolutionCellContainer<T extends IRepresentation> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    /** The iteration number. */
    protected int iterationNumber;
    
    /** The solution. */
    protected ISolution<T> solution;

    /**
     * Instantiates a new solution cell container.
     * 
     * @param iterationNumber the iteration number
     * @param solution the solution
     */
    public SolutionCellContainer(int iterationNumber, ISolution<T> solution) {
        this.iterationNumber = iterationNumber;
        this.solution = solution;
    }

    /**
     * Gets the iteration number.
     * 
     * @return the iteration number
     */
    public int getIterationNumber() {
        return iterationNumber;
    }

    /**
     * Sets the iteration number.
     * 
     * @param iterationNumber the new iteration number
     */
    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    /**
     * Gets the solution.
     * 
     * @return the solution
     */
    public ISolution<T> getSolution() {
        return solution;
    }

    /**
     * Sets the solution set.
     * 
     * @param solution the new solution set
     */
    public void setSolutionSet(ISolution<T> solution) {
        this.solution = solution;
    }


}