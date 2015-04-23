package pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;

public class ConvergenceTerminationCriteria implements ITerminationCriteria{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7131566205116568513L;
	protected int totalNumberOfIterations;
	protected int currentNumberOfIterations;
	protected double lastFitnessValue;
	
	public ConvergenceTerminationCriteria(int totalNumberOfIterations) throws InvalidTerminationCriteriaParameter {
		
		if(totalNumberOfIterations < 0)
			throw new InvalidTerminationCriteriaParameter("totalNumberOfIterations < 0");
		
		this.totalNumberOfIterations = totalNumberOfIterations;
		this.currentNumberOfIterations = 0;
		this.lastFitnessValue = 0;
	}

	protected  <T extends IRepresentation> ISolution<T> getBestSolution(IAlgorithm<T> algorithm,AlgorithmState<T> algorithmState){
		boolean isMaximization = algorithm.getConfiguration().getEvaluationFunction().isMaximization();
		
		if(isMaximization)
			return algorithmState.getSolutionSet().getHighestValuedSolutionsAt(0);
		
		return algorithmState.getSolutionSet().getLowestValuedSolutionsAt(0);
		
	}
	
	protected void updateFitnessValue(double fitnessValue){
		if(fitnessValue != lastFitnessValue){
			lastFitnessValue = fitnessValue;
			currentNumberOfIterations = 1;
		}else
			currentNumberOfIterations++;
	}
	
	@Override 
	public <T extends IRepresentation> boolean verifyAlgorithmTermination(IAlgorithm<T> algorithm, AlgorithmState<T> algorithmState) {
		
		ISolution<T> solution = getBestSolution(algorithm,algorithmState);
		double fitnessValue = solution.getScalarFitnessValue();
		
		updateFitnessValue(fitnessValue);
		
		if(totalNumberOfIterations == currentNumberOfIterations)
			return true;
		
		return false;
	}

	@Override
	public ITerminationCriteria deepCopy()	throws InvalidNumberOfIterationsException,	InvalidTerminationCriteriaParameter {
		return new ConvergenceTerminationCriteria(totalNumberOfIterations);
	}

	//@Override
	public Number getNumericTerminationValue() {
		return 0.0;
	}

}
