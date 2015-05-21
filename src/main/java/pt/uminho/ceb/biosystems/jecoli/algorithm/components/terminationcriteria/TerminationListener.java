/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria;

import java.io.Serializable;

/**
 * @author pmaia_import
 *
 */
public interface TerminationListener extends Serializable {
	
	public void processTerminationEvent(TerminationEvent terminationEvent);

}
