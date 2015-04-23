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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;


/**
 * The Class RunTimeTerminationCriteria.
 */
public class RunTimeTerminationCriteria implements ITerminationCriteria{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The execution time. */
	protected long executionTime;

	/**
	 * Instantiates a new run time termination criteria.
	 * 
	 * @param executionTime the execution time
	 */
	public RunTimeTerminationCriteria(long executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public <T extends IRepresentation> boolean verifyAlgorithmTermination(IAlgorithm<T> algorithm, AlgorithmState<T> algorithmState) {

		long currentExecutionTime = algorithmState.getOverallTime();

        return currentExecutionTime >= executionTime;

    }

	@Override
	public ITerminationCriteria deepCopy(){
		return new RunTimeTerminationCriteria(executionTime);
	}

	@Override
	public Number getNumericTerminationValue() {
		return executionTime;
	}

}
