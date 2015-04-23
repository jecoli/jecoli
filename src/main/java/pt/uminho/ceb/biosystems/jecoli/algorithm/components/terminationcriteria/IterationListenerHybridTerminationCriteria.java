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
public class IterationListenerHybridTerminationCriteria extends IterationTerminationCriteria implements TerminationListener{

	private static final long serialVersionUID = -2333386778907619651L;
	
	protected boolean terminationFlag = false; 
	
	/**
	 * @param maxNumberOfIterations
	 * @throws InvalidNumberOfIterationsException
	 */
	public IterationListenerHybridTerminationCriteria(int maxNumberOfIterations) throws InvalidNumberOfIterationsException {
		super(maxNumberOfIterations);
	}

	@Override
	public void processTerminationEvent(TerminationEvent terminationEvent) {		
		String id = terminationEvent.getId();
		String message = terminationEvent.getMessage();
		
		if(id.equals(TerminationEvent.TERMINATE_IMMEDIATELY_EVENT))
			this.terminationFlag = true;
		
		System.out.println("Process terminated due to event ["+id+"] overriding ITERATION CRITERIA with message ["+message+"]");
	}
	
	@Override
    public <T extends IRepresentation> boolean verifyAlgorithmTermination(IAlgorithm<T> algorithm, AlgorithmState<T> algorithmState) {
        
        if (terminationFlag || (algorithmState.getCurrentIteration() > maxNumberOfIterations+1))
            return true;
        
        return false;
    }

}
