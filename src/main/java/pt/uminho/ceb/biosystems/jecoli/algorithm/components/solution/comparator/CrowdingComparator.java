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
 * The Class CrowdingComparator.
 */
public class CrowdingComparator<T extends ISolution<?>> implements Comparator<T>,Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The rank comparator. */
	private Comparator<T> rankComparator;
	
	/**
	 * Instantiates a new crowding comparator.
	 */
	public CrowdingComparator(){
		rankComparator = new RankComparator<T>();
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(T o1, T o2) {
		
		if(o1 == null)
			return 1;
		else if (o2 == null)
			return -1;
		
		int flagRank = rankComparator.compare(o1, o2);
		if(flagRank!=0)
			return flagRank;
		
		//If the rank is equal use the crowding distance
		double dist1 = o1.getCrowdingDistance();
		double dist2 = o2.getCrowdingDistance();
		
		if(dist1 > dist2)
			return -1;
		
		if(dist1 < dist2)
			return 1;
		
		return 0;		
	}

}
