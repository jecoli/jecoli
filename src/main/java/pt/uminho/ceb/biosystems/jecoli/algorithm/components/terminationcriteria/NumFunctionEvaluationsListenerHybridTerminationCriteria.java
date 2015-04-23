/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

/**
 * @author pmaia_import
 *
 */
public class NumFunctionEvaluationsListenerHybridTerminationCriteria extends NumberOfFunctionEvaluationsTerminationCriteria implements TerminationListener{
	
	
	private static final long serialVersionUID = 2766119342079800499L;

	protected boolean terminationFlag = false;

	/**
	 * @param maximumNumberOfFunctionEvaluations
	 * @throws InvalidTerminationCriteriaParameter
	 */
	public NumFunctionEvaluationsListenerHybridTerminationCriteria(int maximumNumberOfFunctionEvaluations) throws InvalidTerminationCriteriaParameter {
		super(maximumNumberOfFunctionEvaluations);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see jecoli.algorithm.components.terminationcriteria.TerminationListener#processTerminationEvent(jecoli.algorithm.components.terminationcriteria.TerminationEvent)
	 */
	@Override
	public void processTerminationEvent(TerminationEvent terminationEvent) {
		String id = terminationEvent.getId();
		String message = terminationEvent.getMessage();
		
		if(id.equals(TerminationEvent.TERMINATE_IMMEDIATELY_EVENT))
			this.terminationFlag = true;
		
		System.out.println("Process terminated due to event ["+id+"] overriding NUM. FUNCTION EVALUATIONS CRITERIA with message ["+message+"]");		
	}
	
	@Override
	public <T extends IRepresentation> boolean verifyAlgorithmTermination(IAlgorithm<T> algorithm, AlgorithmState<T> algorithmState) {
		
		int totalNumberOfFunctionEvaluation = algorithmState.getTotalNumberOfFunctionEvaluations();
		
		if(terminationFlag || (totalNumberOfFunctionEvaluation >= maximumNumberOfFunctionEvaluations))
			return true;
		
		return false;
	}

}
