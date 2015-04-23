package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;

public class InvalidEvaluationFunctionInputDataException extends InvalidConfigurationException {
	
	protected String message;
	
	public InvalidEvaluationFunctionInputDataException(String message) {
		this.message = message;
	}

	public String getMessage(){
    	return message;
    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -701692853123408414L;
	

}
