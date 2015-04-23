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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionCellContainer;


// TODO: Auto-generated Javadoc
/**
 * The Class RankComparator.
 */
public class SolutionCellRankComparator<T extends SolutionCellContainer<?>> implements Comparator<T>, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(T c1, T c2) {	
		ISolution<?> o1 = c1.getSolution();
		ISolution<?> o2 = c2.getSolution();
		if(o1==null)
			return 1;
		else if (o2==null)
			return -1;

		if(o1.getRank() < o2.getRank())
			return -1;

		if(o1.getRank() > o2.getRank())
			return 1;

		return 0;
	}

}
