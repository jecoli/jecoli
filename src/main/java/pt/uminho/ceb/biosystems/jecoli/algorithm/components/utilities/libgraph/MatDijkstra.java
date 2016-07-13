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
public class MatDijkstra
{

	/** The graph. */
	WGraph graph; // weighted graph - should be oriented
	
	/** The dists. */
	double[][] dists;
	
	/** The preds. */
	int[][] preds;
	
	/** The alternatives. */
	boolean [][] alternatives;

	/** The settled. */
	private boolean [][] settled;

	/**
	 * Instantiates a new mat dijkstra.
	 * 
	 * @param g the g
	 */
	public MatDijkstra(WGraph g)
	{
		this.graph = g;
		preds = new int[graph.getDimension()][graph.getDimension()];
		dists = new double[graph.getDimension()][graph.getDimension()];
		settled = new boolean[graph.getDimension()][graph.getDimension()];
		alternatives = new boolean[graph.getDimension()][graph.getDimension()];
		init();
	}

	/**
	 * Instantiates a new mat dijkstra.
	 * 
	 * @param gr the gr
	 */
	public MatDijkstra(Graph gr) // build from a graph with no weights; put unit weights
	{
		int n = gr.getDimension();
		this.graph = new WGraph(n);
		for(int i=0; i < n; i++)
			for(int j=0; j < n; j++)
				if (gr.getConnection(i, j))
				{
					this.graph.setConnection(i, j, true);
					this.graph.setWeight(i, j, 1);
				}

		preds = new int[graph.getDimension()][graph.getDimension()];
		dists = new double[graph.getDimension()][graph.getDimension()];
		settled = new boolean[graph.getDimension()][graph.getDimension()];
		alternatives = new boolean[graph.getDimension()][graph.getDimension()];
		init();
	}
	
	/**
	 * Gets the sol preds.
	 * 
	 * @return the sol preds
	 */
	public int[][] getSolPreds()
	{
		return preds;
	}

	/**
	 * Gets the sol dists.
	 * 
	 * @return the sol dists
	 */
	public double[][] getSolDists()
	{
		return dists;
	}	

	/**
	 * Gets the sol alternatives.
	 * 
	 * @return the sol alternatives
	 */
	public boolean[][] getSolAlternatives()
	{
		return alternatives;
	}

	/**
	 * Inits the.
	 */
	public void init()
	{
		for(int i=0; i < this.graph.getDimension(); i++)
			for(int j=0; j < this.graph.getDimension(); j++)
			{
				preds[i][j] = -1;	
				settled[i][j] = false;
				alternatives[i][j] = false;
				dists[i][j] = 0.0;
			}
	}

	/**
	 * Execute.
	 */
	public void execute()
	// finds pairs of shortest routes from all pairs of nodes
	{
		init();
		for (int i=0; i< graph.getDimension(); i++)
			executeAux(i);
	}

	/**
	 * Execute.
	 * 
	 * @param st the st
	 */
	public void execute(int st)
	{
		init();
		executeAux(st);
	}

	/**
	 * Execute aux.
	 * 
	 * @param st the st
	 */
	public void executeAux(int st)
	{
		int dim = graph.getDimension();
		preds[st][st] = st;
		int cur = st;

		while (cur != -1)
		{
			relaxNeighbors(st, cur);

			settled[st][cur] = true;

			// get next node
			double min = Double.MAX_VALUE;
			int next = -1;
			for(int i=0; i< dim; i++)
				if (i != st && preds[st][i] >= 0 && !settled[st][i])
				{
					if (dists[st][i] < min) 
					{
						min = dists[st][i];
						next = i;
					}
				}
			//System.out.println("next node = " + next);
			cur = next;
		}

	}

	/**
	 * Relax neighbors.
	 * 
	 * @param s the s
	 * @param node the node
	 */
	private void relaxNeighbors(int s, int node)
	{
		int dim = graph.getDimension();

		for(int i=0; i< dim; i++)
		{
			if( i!=node && !settled[s][i] && graph.getConnection(node, i) ) // neighbors
			{
				double d = graph.getWeight(node, i) + dists[s][node];
				if ( preds[s][i] >= 0 ) { // node is on the unsettled list
					if ( dists[s][i] > d) {
						dists[s][i] = d;
						preds[s][i] = node;
						alternatives[s][i] = false;
					}
					else if ( dists[s][i] == d ) 
						alternatives[s][i]= true;
				}
			 	else 
				{
					preds[s][i] = node;	
					dists[s][i] = d;
					alternatives[s][i] = false;
					settled[s][i] = false;
				} 
			}
		}
	}	


	/**
	 * Gets the path.
	 * 
	 * @param source the source
	 * @param dest the dest
	 * 
	 * @return the path
	 */
	public Vector<Integer> getPath (int source, int dest) // from the defined source
	{ // assumes execute is done before with a given start node
		Vector<Integer> path = null;

		if (preds[source][dest] >= 0) {
			path = new Vector<Integer>();
			path.add(new Integer(dest) );

			int d = dest;
			do {
				d = preds[source][d];
				path.add(new Integer(d) );
			}
			while (d != source); 

			Collections.reverse(path);
		}
		else return null;

		return path;
	}

	/**
	 * Exists path.
	 * 
	 * @param source the source
	 * @param dest the dest
	 * 
	 * @return true, if successful
	 */
	public boolean existsPath (int source, int dest)
	{
		return (preds[source][dest] >= 0);
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
		if(preds[source][dest] >= 0) return dists[source][dest]; 
		else return Double.MAX_VALUE;
	}

	/**
	 * Gets the arcs shortest path.
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

	// gets all arcs in the shortest path tree from root to a set of nodes 
	/**
	 * Gets the arcs tree.
	 * 
	 * @param root the root
	 * @param listDest the list dest
	 * 
	 * @return the arcs tree
	 */
	public Graph getArcsTree(int root, int[] listDest)
	{
		Graph res = new Graph(graph.getDimension());

		for (int i=0; i < listDest.length; i++)
		{
			if (preds[root][listDest[i]] >= 0) {
				int d = preds[root][listDest[i]];
				res.setConnection(d, listDest[i], true);
				int p = d;
				do {
					d = preds[root][p];
					res.setConnection(d, p, true);
					p = d;
				}	
				while (d != root);
			}
		}
		return res;
	}
	
	/**
	 * Gets the nodes for dest.
	 * 
	 * @param dest the dest
	 * 
	 * @return the nodes for dest
	 */
	public Vector<Integer> getNodesForDest (int dest)
	{
		Vector<Integer> res = new Vector<Integer>();

		for (int i=0; i< preds.length; i++)
			if(i!= dest && preds[i][dest] >=0) res.add(new Integer(i)); 

		return res;
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main (String[] args)
	{
		try{
			WGraph g = new WGraph("or.gr", true);
			MatDijkstra d = new MatDijkstra(g);
			d.execute();
			
			int[][] p = d.getSolPreds();
			for(int i=0; i < p.length; i++)
			{
				for(int j=0; j < p[i].length; j++)
					System.out.print(" " + p[i][j]);
				System.out.println(" ");
			}

			double[][] dists = d.getSolDists();
			for(int i=0; i < dists.length; i++)
			{
				for(int j=0; j < dists[i].length; j++)
					System.out.print(" " + dists[i][j]);
				System.out.println(" ");
			}


			Vector<Integer> p1 = d.getPath(0,3);
			System.out.println("Path 0 a 3");
			for(int k=0; k < p1.size(); k++) System.out.println(" " + ((Integer)(p1.get(k))).intValue() );
			System.out.println("Dist.:" + d.getDist(0,3) );

			Graph asp = d.getArcsShortestPath(3);
			System.out.println("Arcs on shortest paths to 3");
			asp.print();

			Vector<Integer> v = d.getNodesForDest(1);
			for(int k=0; k < v.size(); k++) System.out.println(" " + ((Integer)(v.get(k))).intValue() );
		}
		catch (Exception E)
		{
			E.printStackTrace();
		}
	}

}
