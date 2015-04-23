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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm;

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AlgorithmStateEvent.
 */
public class AlgorithmStateEvent extends EventObject{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	protected String id;
	
	/** The message. */
	protected String message;
	
	/**
	 * Instantiates a new algorithm state event.
	 * 
	 * @param source the source
	 * @param id the id
	 * @param message the message
	 */
	public AlgorithmStateEvent(Object source,String id,String message) {
		super(source);
		this.id = id;
		this.message = message;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Message.
	 * 
	 * @return the string
	 */
	public String getMessage(){
		return message;
	}

}
