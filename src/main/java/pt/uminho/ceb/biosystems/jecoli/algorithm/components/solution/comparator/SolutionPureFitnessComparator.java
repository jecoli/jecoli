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

/**
 * The Class SolutionPureFitnessComparator.
 */
public class SolutionPureFitnessComparator<T extends ISolution<?>> implements Comparator<T>, Serializable{
	
	private boolean useSelectionValues;

	public SolutionPureFitnessComparator(boolean useSelectionValues){
		this.useSelectionValues = useSelectionValues;
	}
	
	private static final long serialVersionUID = 1L;

	/**
	 * Compares two solutions.
	 * 
	 * @param o1 Object representing the first <code>Solution</code>.
	 * @param o2 Object representing the second <code>Solution</code>.
	 * 
	 * @return -1, or 0, or 1 if o1 is less than, equal, or greater than o2,
	 * respectively.
	 */
	@Override
	public int compare(T o1, T o2) {
		
		if(o1==null && o2 ==null){
			return 0;
		}else if (o1==null)
			return 1;
		else if (o2 == null)
			return -1;

		double fitness1 = 0;
		double fitness2 = 0;
		
		if(useSelectionValues){
			fitness1 = o1.getSelectionValue();
			fitness2 = o2.getSelectionValue();
		} else{			
			fitness1 = o1.getScalarFitnessValue();
			fitness2 = o2.getScalarFitnessValue();
		}
		
		if (fitness1 < fitness2) {
			return -1;
		}else if (fitness1 > fitness2) {
			return 1;
		}else
			return 0;
	}

}
