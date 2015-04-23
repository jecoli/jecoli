/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria;

/**
 * @author pmaia
 *
 */
public class TerminationEvent {
	
	public static String TERMINATE_IMMEDIATELY_EVENT = "terminateProcessImmediately";
	
	protected String id;
	protected String message;

	public TerminationEvent(String id, String message){
		this.id = id;
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
