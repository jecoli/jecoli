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

// TODO: Auto-generated Javadoc
/**
 * The Class IterationTerminationCriteria.
 */
public class IterationTerminationCriteria implements ITerminationCriteria {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The max number of iterations. */
    protected int maxNumberOfIterations;
    protected boolean initializationIteration;

    /**
     * Gets the max number of iterations.
     * 
     * @return the max number of iterations
     */
    public int getMaxNumberOfIterations() {
		return maxNumberOfIterations;
	}

	/**
	 * Sets the max number of iterations.
	 * 
	 * @param maxNumberOfIterations the new max number of iterations
	 */
	public void setMaxNumberOfIterations(int maxNumberOfIterations) {
		this.maxNumberOfIterations = maxNumberOfIterations;
	}

	/**
	 * Instantiates a new iteration termination criteria.
	 * 
	 * @param maxNumberOfIterations the max number of iterations
	 * 
	 * @throws InvalidNumberOfIterationsException the invalid number of iterations exception
	 */
	public IterationTerminationCriteria(int maxNumberOfIterations) throws InvalidNumberOfIterationsException {
        if (maxNumberOfIterations < 1)
            throw new InvalidNumberOfIterationsException();

        this.maxNumberOfIterations = maxNumberOfIterations;
    }


    @Override
    public <T extends IRepresentation> boolean verifyAlgorithmTermination(IAlgorithm<T> algorithm, AlgorithmState<T> algorithmState) {

        return algorithmState.getCurrentIteration() > maxNumberOfIterations + 1;

    }

	@Override
	public ITerminationCriteria deepCopy() throws InvalidNumberOfIterationsException {
		return new IterationTerminationCriteria(maxNumberOfIterations);
	}

	@Override
	public Number getNumericTerminationValue() {
		return maxNumberOfIterations;
	}

}