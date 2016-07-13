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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.utilities.libgraph;

import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * Holds information about graph nodes within the shortest path algorithm (Dijkstra).
 */
public class DijkstraNode 
{
	
	/** The node. */
	int node; // number of node in Graph (Graph should be a instance of WGraph)
	
	/** The source. */
	int source; // source node from the algorithm (redundant ?)
	
	/** The predec. */
	int predec; // predecessor of node
	
	/** The distance. */
	double distance; // distance between
	
	/** The alternative. */
	boolean alternative; // if true there is an alternative path between source and node with equal distance

	/**
	 * Instantiates a new dijkstra node.
	 * 
	 * @param n the n
	 * @param s the s
	 * @param pred the pred
	 * @param d the d
	 */
	public DijkstraNode(int n, int s, int pred, double d)
	{
		this.node = n;
		this.source = s;
		this.predec = pred;
		this.distance = d;
		this.alternative = false;
	}	

	/**
	 * Gets the node.
	 * 
	 * @return the node
	 */
	public int getNode()
	{	
		return this.node;
	}

	/**
	 * Sets the source.
	 * 
	 * @param s the new source
	 */
	public void setSource(int s)
	{
		this.source = s;
	}

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public int getSource()
	{	
		return this.source;
	}

	/**
	 * Sets the pred.
	 * 
	 * @param p the new pred
	 */
	public void setPred(int p)
	{
		this.predec = p;
	}

	/**
	 * Gets the pred.
	 * 
	 * @return the pred
	 */
	public int getPred()
	{	
		return this.predec;
	}

	/**
	 * Sets the dist.
	 * 
	 * @param d the new dist
	 */
	public void setDist(double d)
	{
		this.distance = d;
	}

	/**
	 * Gets the dist.
	 * 
	 * @return the dist
	 */
	public double getDist()
	{	
		return this.distance;
	}

	/**
	 * Checks if is alternative.
	 * 
	 * @return true, if is alternative
	 */
	public boolean isAlternative ()
	{
		return this.alternative;
	}

	/**
	 * Sets the alternative.
	 * 
	 * @param val the new alternative
	 */
	public void setAlternative (boolean val)
	{
		this.alternative = val;
	}	

	/**
	 * Prints the.
	 */
	public void print()
	{
		System.out.println("Source: " + source + "; Node: " + node + "; Pred. " + predec + "; Dist.:" + distance);  
	}

	/**
	 * Print_simp.
	 */
	public void print_simp ()
	{
		System.out.print(predec + "-" + node);  	
	}
	
	/**
	 * Prints the.
	 * 
	 * @param nodes the nodes
	 */
	public static void print (Vector<DijkstraNode> nodes)
	{
		for (int i=0; i< nodes.size(); i++)
				((DijkstraNode)nodes.get(i)).print();
	}
	
	/**
	 * Print_simp.
	 * 
	 * @param nodes the nodes
	 */
	public static void print_simp (Vector<DijkstraNode> nodes)
	{
		for (int i=0; i< nodes.size(); i++)
		{
				((DijkstraNode)nodes.get(i)).print_simp();
				System.out.print(" ");
		}
		System.out.println("");
	}

}
