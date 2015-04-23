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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;

// TODO: Auto-generated Javadoc
/**
 * The Class FitnessTargetTerminationCriteria.
 */
public class FitnessTargetTerminationCriteria implements ITerminationCriteria{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The fitness target. */
	protected double fitnessTarget;
	
	/**
	 * Instantiates a new fitness target termination criteria.
	 * 
	 * @param fitnessTarget the fitness target
	 */
	public FitnessTargetTerminationCriteria(double fitnessTarget) {
		this.fitnessTarget = fitnessTarget;
	}
	
	public <T extends IRepresentation> boolean verifyAlgorithmTermination(IAlgorithm<T> algorithm, AlgorithmState<T> algorithmState){
		IConfiguration<T> configuration = algorithm.getConfiguration();
		IEvaluationFunction<T> evaluationFunction = configuration.getEvaluationFunction();
		boolean isMaximization = evaluationFunction.isMaximization();
		ISolutionSet<T> solutionSet = algorithmState.getSolutionSet();
		ISolution<T> bestSolution = getBestSolution(isMaximization, solutionSet);
		double bestSolutionFitness = bestSolution.getScalarFitnessValue();
		
		if(isMaximization && bestSolutionFitness >= fitnessTarget)
			return true;

        return !isMaximization && bestSolutionFitness <= fitnessTarget;

    }
	
	/**
	 * Gets the best solution.
	 * 
	 * @param isMaximization the is maximization
	 * @param solutionSet the solution set
	 * 
	 * @return the best solution
	 */
	protected <T extends IRepresentation> ISolution<T> getBestSolution(boolean isMaximization,ISolutionSet<T> solutionSet){
		if(isMaximization)
			return solutionSet.getHighestValuedSolutionsAt(0);
		
		return solutionSet.getLowestValuedSolutionsAt(0);
	}

	@Override
	public ITerminationCriteria deepCopy() {
		return new FitnessTargetTerminationCriteria(fitnessTarget);
	}

	@Override
	public Number getNumericTerminationValue() {
		return fitnessTarget;
	}

}
