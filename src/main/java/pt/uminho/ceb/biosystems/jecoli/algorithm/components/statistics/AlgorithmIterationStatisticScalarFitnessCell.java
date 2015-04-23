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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;



// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmIterationStatisticScalarFitnessCell.
 */
public class AlgorithmIterationStatisticScalarFitnessCell<T extends IRepresentation> implements IAlgorithmIterationStatisticCell<T>, Serializable  {
	
	private static final long serialVersionUID = 1L;

	 
 	/** The scalar fitness statistic cell. */
 	protected ObjectiveStatisticCell scalarFitnessStatisticCell;
	 
 	/** The iteration number. */
 	protected int iterationNumber;
	 
 	/** The number of best solutions to keep. */
 	protected int numberOfBestSolutionsToKeep;
	 
 	/** The total number of function evaluations. */
 	protected int totalNumberOfFunctionEvaluations;
	 
 	/** The total execution time. */
 	protected long totalExecutionTime;
	 
 	/** The best solution list. */
 	protected ISolutionSet<T> bestSolutionList;
	
	 /* (non-Javadoc)
 	 * @see core.statistics.IAlgorithmIterationStatisticCell#getBestSolution(int)
 	 */
 	@Override
	public ISolution<T> getBestSolution(int solutionPosition) {
		return  bestSolutionList.getSolution(solutionPosition);
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getExecutionTime()
	 */
	@Override
	public long getExecutionTime() {
		return totalExecutionTime;
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getIterationNumber()
	 */
	@Override
	public int getIterationNumber() {
		return iterationNumber;
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getNumberOfBestSolutions()
	 */
	@Override
	public int getNumberOfBestSolutions() {
		return bestSolutionList.getNumberOfSolutions();
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getNumberOfFunctionEvaluations()
	 */
	@Override
	public int getNumberOfFunctionEvaluations() {
		return totalNumberOfFunctionEvaluations;
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getNumberOfObjectives()
	 */
	@Override
	public int getNumberOfObjectives() {
		return bestSolutionList.getNumberOfObjectives();
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getObjectiveStatisticCell(int)
	 */
	@Override
	public ObjectiveStatisticCell getObjectiveStatisticCell(int objectivePosition) {
		return scalarFitnessStatisticCell;
	}

	/* (non-Javadoc)
	 * @see core.statistics.IAlgorithmIterationStatisticCell#getScalarFitnessCell()
	 */
	@Override
	public ObjectiveStatisticCell getScalarFitnessCell() {
		return scalarFitnessStatisticCell;
	}

}
