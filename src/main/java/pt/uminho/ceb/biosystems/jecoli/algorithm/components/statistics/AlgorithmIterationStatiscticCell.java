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
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;



// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmIterationStatiscticCell.
 */
public class AlgorithmIterationStatiscticCell<T extends IRepresentation> implements IAlgorithmIterationStatisticCell<T>, Serializable  {
	
	private static final long serialVersionUID = 1L;

    
    /** The iteration. */
    protected int iteration;
    
    /** The number of function evaluations. */
    protected int numberOfFunctionEvaluations;
    
    /** The execution time. */
    protected long executionTime;
    
    /** The objective statistics list. */
    protected List<ObjectiveStatisticCell> objectiveStatisticsList;
    
    /** The scalar fitness value. */
    protected ObjectiveStatisticCell scalarFitnessValue;
    
    /** The best solution list. */
    protected ISolutionSet<T> bestSolutionList;
    
    
    /**
     * Instantiates a new algorithm iteration statisctic cell.
     * 
     * @param iteration the iteration
     * @param numberOfFunctionEvaluations the number of function evaluations
     * @param executionTime the execution time
     * @param objectiveStatisticsList the objective statistics list
     * @param scalarFitnessStatisticCell the scalar fitness statistic cell
     * @param bestSolutionSet the best solution set
     */
    public AlgorithmIterationStatiscticCell(int iteration,int numberOfFunctionEvaluations,long executionTime,
    										List<ObjectiveStatisticCell> objectiveStatisticsList,ObjectiveStatisticCell scalarFitnessStatisticCell, ISolutionSet<T> bestSolutionSet){
    	this.iteration = iteration;
    	this.numberOfFunctionEvaluations = numberOfFunctionEvaluations;
    	this.executionTime = executionTime;
    	this.objectiveStatisticsList = objectiveStatisticsList;
    	this.bestSolutionList = bestSolutionSet;
    	this.scalarFitnessValue = scalarFitnessStatisticCell;
    }
    
    public int getIterationNumber(){
		return iteration;
    }
   
    public int getNumberOfFunctionEvaluations(){
    	return numberOfFunctionEvaluations;
    }
    
    public long getExecutionTime(){
    	return executionTime;
    }
    
    public int getNumberOfObjectives(){
    	return objectiveStatisticsList.size();
    }
    
    public ObjectiveStatisticCell getObjectiveStatisticCell(int objectivePosition){
    	return objectiveStatisticsList.get(objectivePosition);
    }
    
    public int getNumberOfBestSolutions(){
    	return bestSolutionList.getNumberOfSolutions();
    }
    
    public ISolution<T> getBestSolution(int solutionPosition){
    	return bestSolutionList.getSolution(solutionPosition);
    }

	@Override
	public ObjectiveStatisticCell getScalarFitnessCell() {
		return scalarFitnessValue;
	}
    
}