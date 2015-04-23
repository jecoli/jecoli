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

import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.DistanceNode;


// TODO: Auto-generated Javadoc
/**
 * The Class DistanceNodeComparator.
 */
public class DistanceNodeComparator implements Comparator<DistanceNode>, Serializable {

	private static final long serialVersionUID = 1L;


	public int compare(DistanceNode node1, DistanceNode node2) {
		
		double distance1, distance2;
		distance1 = node1.getDistance();
		distance2 = node2.getDistance();

		if (distance1 < distance2)
			return -1;
		else if (distance1 > distance2)
			return 1;
		else
			return 0;
	}

}