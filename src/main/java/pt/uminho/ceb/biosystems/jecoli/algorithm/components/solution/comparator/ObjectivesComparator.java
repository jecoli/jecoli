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

package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator;

import java.io.Serializable;
import java.util.Comparator;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;




// TODO: Auto-generated Javadoc
/**
 * Class to perform the comparison of one objective between two individuals of a given population.
 * 
 * @author paulo maia, May 5, 2008 - generics update and maximization/minimization support
 */
public class ObjectivesComparator<T extends ISolution<?>> implements Comparator<T>, Serializable{
	
	private static final long serialVersionUID = 1L;

	/** The numobj. */
	private int numobj;
	
	/** The maximization. */
	private boolean maximization;

	/**
	 * Instantiates a new objectives comparator.
	 * 
	 * @param numobjs the numobjs
	 * @param maximization the maximization
	 */
	public ObjectivesComparator(int numobjs,boolean maximization){
		this.numobj = numobjs;
		this.maximization = maximization;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(T o1, T o2) {
		if(o1 == null)
			return (maximization) ? -1 : 1;
		else if(o2 == null)
			return (maximization) ? 1 : -1;
		
		double obj1 = o1.getFitnessValue(this.numobj);
		double obj2 = o2.getFitnessValue(this.numobj);
		
		if(obj1 < obj2)
			return (maximization) ? 1 : -1;
		else if(obj2 < obj1)
			return (maximization) ? -1 : 1;
		else
			return 0;
	}

	/**
	 * Gets the numobjs.
	 * 
	 * @return the numobjs
	 */
	public int getNumobjs() {
		return numobj;
	}

	/**
	 * Sets the numobjs.
	 * 
	 * @param numobjs the numobjs to set
	 */
	public void setNumobjs(int numobjs) {
		this.numobj = numobjs;
	}

	/**
	 * Checks if is maximization.
	 * 
	 * @return the maximization
	 */
	public boolean isMaximization() {
		return maximization;
	}

	/**
	 * Sets the maximization.
	 * 
	 * @param maximization the maximization to set
	 */
	public void setMaximization(boolean maximization) {
		this.maximization = maximization;
	}

}
