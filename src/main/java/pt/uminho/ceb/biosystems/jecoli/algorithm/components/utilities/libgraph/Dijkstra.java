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

import java.util.Collections;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * Implements the shortest path algorithm (Dijkstra) in a oriented weighted graph (WGraph).
 */
public class Dijkstra
{

	/** The graph. */
	WGraph graph; // weighted graph - should be oriented
	
	/** The solution. */
	Vector<DijkstraNode> solution; // Vector of nodes (DijkstraNode objects) - final solution of execute

	/** The settled_nodes. */
	private Vector<DijkstraNode> settled_nodes; // Vector of nodes - objects of class DijkstraNode
	
	/** The unsettled_nodes. */
	private Vector<DijkstraNode> unsettled_nodes; // Vector of nodes - objects of class DijkstraNode

	/** The check alternatives. */
	public boolean checkAlternatives = true; // check for alternative routes

	/**
	 * Instantiates a new dijkstra.
	 * 
	 * @param g the g
	 */
	public Dijkstra(WGraph g)
	{
		this.graph = g;
		settled_nodes = new Vector<DijkstraNode>();
		unsettled_nodes = new Vector<DijkstraNode>();
		solution = new Vector<DijkstraNode>();
	}

	/**
	 * Clear.
	 */
	public void clear ()
	{
		settled_nodes.clear();
		unsettled_nodes.clear();
	}

	/**
	 * Gets the solution.
	 * 
	 * @return the solution
	 */
	public Vector<DijkstraNode> getSolution()
	{
		return solution;
	}

	/**
	 * Execute.
	 */
	public void execute()
	// finds pairs of shortest routes from all pairs of nodes
	// result goes to solution
	{
		for (int i=0; i< graph.getDimension(); i++)
		{
			executeAux(i);
			for(int k=0; k < settled_nodes.size(); k++)
				solution.add( settled_nodes.get(k) );
			this.clear();
		}
	}

	/**
	 * Execute.
	 * 
	 * @param start the start
	 */
	public void execute(int start) // gets distances to all reachable destinations from node start
	{
		executeAux(start);
		solution = settled_nodes;
		this.clear();
	}


	/**
	 * Execute aux.
	 * 
	 * @param start the start
	 */
	private void executeAux(int start) // gets distances to all reachable destinations from node start
	{
		DijkstraNode s = new DijkstraNode(start, start, start, 0.0);
		unsettled_nodes.add(s);

		DijkstraNode current;

		while ( (current = extractNode()) != null )
		{
			// mark settled u
			//markSettled(current);
			if(current.getNode() != start) markSettled(current); // do not mark start
			else unsettled_nodes.remove(current);

			// relax neighbors
			relaxNeighbors(current);
		}
	}

	/**
	 * Execute.
	 * 
	 * @param start the start
	 * @param dest the dest
	 */
	public void execute(int start, int dest) //stops when dest is reached
	{
		DijkstraNode s = new DijkstraNode(start, start, start, 0.0);
		unsettled_nodes.add(s);

		DijkstraNode current;

		while ( (current = extractNode()) != null )
		{
			//markSettled(current);
			if(current.getNode() != start) markSettled(current); // do not mark start
			else unsettled_nodes.remove(current);

			if (current.getNode() == dest) 
			{
				solution = settled_nodes;
				this.clear();
				return;
			}

			// relax neighbors
			relaxNeighbors(current);
		}

		solution = null; // unreachable
	}

	/**
	 * Gets the path.
	 * 
	 * @param source the source
	 * @param dest the dest
	 * 
	 * @return the path
	 */
	public Vector<DijkstraNode> getPath (int source, int dest) // from the defined source
	{ // assumes execute is done before with a given start node
		DijkstraNode d = findNodeSol(source, dest);
		Vector<DijkstraNode> path = null;

		if (d != null) {
			path = new Vector<DijkstraNode>();
			path.add(d);

			DijkstraNode current = d;

			while (current != null) {
				current = getPredec (current);
				if(current != null) path.add(current);
			}
		}

		if (path != null) Collections.reverse(path);
		return path;
	}

	/**
	 * Gets the dist.
	 * 
	 * @param source the source
	 * @param dest the dest
	 * 
	 * @return the dist
	 */
	public double getDist (int source, int dest) // from the defined source
	{ // assumes execute is done previously
		DijkstraNode d = findNodeSol(source, dest);
		if(source == dest) return 0.0;
		else if(d != null) return d.getDist();	
		else return Double.MAX_VALUE;
	}

/**
 * returns a graph with all arcs in shortest paths to dest.
 * 
 * @param dest the dest
 * 
 * @return the arcs shortest path
 */
	public Graph getArcsShortestPath(int dest)
	{
		Graph res = new Graph(graph.getDimension());

		for(int i=0; i < graph.getDimension(); i++)
			for(int k=0; k < graph.getDimension(); k++)
				if (i!=k && graph.getConnection(i, k) && 
						( getDist(i,dest) - getDist(k,dest) == graph.getWeight(i,k) )  )
					res.setConnection(i,k,true);
				else res.setConnection(i,k,false);

		return res;
	}

	/**
	 * Gets the arcs shortest path.
	 * 
	 * @param s the s
	 * @param dest the dest
	 * @param sparse the sparse
	 * 
	 * @return the arcs shortest path
	 */
	public Graph getArcsShortestPath(int s, int dest, int[][] sparse)
	{
		Graph res = new Graph(graph.getDimension());

		for (int k = 0; k < sparse.length; k++)	
				if	( getDist(s,dest) - getDist(s,sparse[k][0]) - getDist(sparse[k][1],dest) == graph.getWeight(sparse[k][0],sparse[k][1]) )  
					res.setConnection(sparse[k][0],sparse[k][1],true);
				else res.setConnection(sparse[k][0],sparse[k][1],false);

		return res;
	}


	
/**
 * gets all DijkstraNodes with destination equal to dest.
 * Assumes execute() was done before to build solution
 * 
 * @param dest the dest
 * 
 * @return the nodes for dest
 */
	public Vector<DijkstraNode> getNodesForDest (int dest)
	{
		Vector<DijkstraNode> res = new Vector<DijkstraNode>();

		for (int i=0; i< solution.size(); i++)
		{
			DijkstraNode temp = (DijkstraNode)solution.get(i);
			if (temp.getNode() == dest) res.add(temp); 
		}

		return res;
	}

/**
 * gets next node to handle.
 * 
 * @return the dijkstra node
 */
	private DijkstraNode extractNode ()
	{
		DijkstraNode res= null;
		if (unsettled_nodes.size() > 0 ) {
			res = (DijkstraNode)(unsettled_nodes.get(0));
			// choose minimum distance
			for (int i=1; i< unsettled_nodes.size(); i++)
			{
				DijkstraNode temp = (DijkstraNode)unsettled_nodes.get(i);
				if (temp.getDist() < res.getDist()) res = temp;
			}
		}
		return res;
	}

/**
 * marks a node as settled.
 * 
 * @param n the n
 */
	private void markSettled(DijkstraNode n)
	{
		unsettled_nodes.remove(n);
		settled_nodes.add(n);
	}
	
	/**
	 * Relax neighbors.
	 * 
	 * @param n the n
	 */
	private void relaxNeighbors(DijkstraNode n)
	{
		int node = n.getNode();
		int dim = graph.getDimension();

		for(int i=0; i< dim; i++)
		{
			if( i!=node && !isSettled(i) && graph.getConnection(node, i) ) // neighbors
			{
				double d = graph.getWeight(node, i) + n.getDist();
				DijkstraNode t = isUnsettled(i);
				if ( t != null ) { // node is on the unsettled list
					if ( t.getDist() > d) {
						t.setDist(d);
						t.setPred(node);
						t.setAlternative(false);
					}
					else if ( t.getDist() == d ) 
					{
						t.setAlternative(true);
					}
				}
			 	else unsettled_nodes.add( new DijkstraNode(i, n.getSource(), node, d) );
			}
		}
	}	

/**
 * checks if a node is in the settled list.
 * 
 * @param node the node
 * 
 * @return true, if checks if is settled
 */
	private boolean isSettled (int node)
	{
		boolean res = false;
		for (int i=0; i< settled_nodes.size(); i++)
		{
			DijkstraNode temp = (DijkstraNode)settled_nodes.get(i);
			if (temp.getNode() == node) res = true;
		}
		return res;
	}

	/**
	 * Checks if is unsettled.
	 * 
	 * @param node the node
	 * 
	 * @return the dijkstra node
	 */
	private DijkstraNode isUnsettled (int node)
	{
		DijkstraNode res = null;
		for (int i=0; i< unsettled_nodes.size() && res == null; i++)
		{
			DijkstraNode temp = (DijkstraNode)unsettled_nodes.get(i);
			if (temp.getNode() == node) res = temp;
		}
		return res;
	}

/**
 * get predecessor node from a given node.
 * 
 * @param n the n
 * 
 * @return the predec
 */
	private DijkstraNode getPredec (DijkstraNode n)
	{
		int pred = n.getPred();
		DijkstraNode res = null;
	
		if(n.getNode() == n.getSource()) res = null;
		else res = findNodeSol(n.getSource(), pred); 

		return res;
	}

	/**
	 * Find node sol.
	 * 
	 * @param source the source
	 * @param dest the dest
	 * 
	 * @return the dijkstra node
	 */
	public DijkstraNode findNodeSol (int source, int dest)
	{
		DijkstraNode res = null;
	
		for (int i=0; i< solution.size() && res == null; i++)
		{
			DijkstraNode temp = (DijkstraNode)solution.get(i);
			if (temp.getNode() == dest && temp.getSource()==source) 
				res = temp;
		}
		return res;
	}


/* prints a solution; assumes execute is done before */
	/**
 * Prints the sol.
 */
public void printSol ()
	{
		for (int i=0; i< solution.size(); i++)
			((DijkstraNode)solution.get(i)).print();
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main (String[] args)
	{
		try{
			WGraph g = new WGraph("sj5-all-ospf.gr", true);
			Dijkstra d = new Dijkstra(g);
			d.execute();
			//Vector<DijkstraNode> v = d.getSolution();
			//DijkstraNode.print(v);
			//d.printSol();
	
			for(int i = 0; i < g.getDimension(); i++)
				for (int j=0; j < g.getDimension(); j++)
					if (i != j )
					{
						Vector<DijkstraNode> p1 = d.getPath(i,j);
						if (p1 != null)
						{
							//System.out.println("Path "+ i + " to " + j);
							DijkstraNode.print_simp(p1);
						}
						
					}
/*			System.out.println("Dist.:" + d.getDist(0,3) );

			Graph asp = d.getArcsShortestPath(3);
			System.out.println("Arcs on shortest paths to 3");
			asp.print();
*/		}
		catch (Exception E)
		{
			E.printStackTrace();
		}
	}
}
