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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.utilities;

// TODO: Auto-generated Javadoc
/**
 * The Class DistanceNode.
 * 
 * @author The JMetal team
 * @author paulo maia, Jan 22, 2009 (adaptation to JECoLi)
 */
public class DistanceNode<T> {
	
	/** The index. */
	private int index;
	
	/** The distance. */
	private double distance;
	
	/**
	 * Instantiates a new distance node.
	 * 
	 * @param distance the distance
	 * @param index the index
	 */
	public DistanceNode(double distance,int index){
		this.distance = distance;
		this.index =index;
	}

	/**
	 * Gets the index.
	 * 
	 * @return the reference
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index.
	 * 
	 * @param index the index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Gets the distance.
	 * 
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 * 
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

}