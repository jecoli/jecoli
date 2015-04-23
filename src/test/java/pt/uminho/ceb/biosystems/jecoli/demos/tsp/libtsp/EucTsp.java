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
package pt.uminho.ceb.biosystems.jecoli.demos.tsp.libtsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class EucTsp.
 */
public class EucTsp extends SymTsp
{
	
	/**
	 * The Class node.
	 */
	class node
 	{
  		
		  /** The y. */
		  double x, y;
 	}

    /** The coords. */
	node [] coords;

	/**
	 * Instantiates a new euc tsp.
	 */
	public EucTsp()
	{

	}

	/**
	 * Instantiates a new euc tsp.
	 * 
	 * @param d the d
	 */
	public EucTsp(int d)
	{
		super(d);
		coords = new node[d];
		for(int i=0; i<d; i++)
			coords[i] = new node();
	}

	/**
	 * Instantiates a new euc tsp.
	 * 
	 * @param filename the filename
	 * 
	 * @throws Exception the exception
	 */
	public EucTsp (String filename) throws Exception
	{
		FileReader f = new FileReader(filename);
		BufferedReader B = new BufferedReader(f);
		String str;
		str = B.readLine();
		int dim = Integer.parseInt(str);
	
		n = dim;
		distances = new double [n][];
		for (int i = 0; i < n; i++) 
  			distances[i] = new double[i+1];
		for (int j = 0; j < n; j++)
   			distances[j][j] = 0.0;
		coords = new node[n];
		for(int i=0; i<n; i++)
			coords[i] = new node();

		for(int cont = 0; cont < dim; cont ++)
		{
			str = B.readLine();
			StringTokenizer st = new StringTokenizer(str);
			set_x(cont, Double.valueOf(st.nextToken()).doubleValue()); 
			set_y(cont, Double.valueOf(st.nextToken()).doubleValue()); 
		}
		set_all_distances();

	}

	// I/O

	/* (non-Javadoc)
	 * @see test.tsp.libtsp.SymTsp#read(java.io.BufferedReader)
	 */
	public void read (BufferedReader B) throws Exception
	{
		String str;
		str = B.readLine();
		int dim = Integer.parseInt(str);
	
		n = dim;
		distances = new double [n][];
		for (int i = 0; i < n; i++) 
  			distances[i] = new double[i+1];
		for (int j = 0; j < n; j++)
   			distances[j][j] = 0.0;

		for(int cont = 0; cont < dim; cont ++)
		{
			str = B.readLine();
			StringTokenizer st = new StringTokenizer(str);
			set_x(cont, Integer.parseInt(st.nextToken()));
			set_y(cont, Integer.parseInt(st.nextToken()));
		}
		set_all_distances();
	}

	/* (non-Javadoc)
	 * @see test.tsp.libtsp.SymTsp#write(java.io.BufferedWriter)
	 */
	public void write (BufferedWriter B) throws Exception
	{
		B.write(n+"\n");
		for(int cont = 0; cont < n; cont ++)
			B.write( get_x(cont) + " " + get_y(cont) + "\n");
	}

	// get / set functions

	/**
	 * Gets the _x.
	 * 
	 * @param cont the cont
	 * 
	 * @return the _x
	 */
	public double get_x (int cont)
	{
 		return coords[cont].x; 
	}

	/**
	 * Gets the _y.
	 * 
	 * @param cont the cont
	 * 
	 * @return the _y
	 */
	public double get_y (int cont)
	{
 		return coords[cont].y; 
	}

	/**
	 * Set_x.
	 * 
	 * @param node the node
	 * @param cx the cx
	 */
	public void set_x (int node, double cx)
	{
		coords[node].x = cx;
	}

	/**
	 * Set_y.
	 * 
	 * @param node the node
	 * @param cy the cy
	 */
	public void set_y (int node, double cy)
	{
 		coords[node].y = cy;
	}

	/**
	 * Euc_dist.
	 * 
	 * @param n1 the n1
	 * @param n2 the n2
	 * 
	 * @return the double
	 */
	private double euc_dist (int n1, int n2)
	{
		double dx = get_x (n1) - get_x (n2);
		double dy = get_y (n1) - get_y (n2);

		return (double)(Math.round(Math.sqrt(dx * dx + dy* dy)));
	}

	/**
	 * Set_all_distances.
	 */
	private void set_all_distances ()
	{
		for(int i = 0; i< n; i++)
			for (int j = 0; j < i; j++)
     			set_distance (i, j, euc_dist(i,j));
	}

	
}

