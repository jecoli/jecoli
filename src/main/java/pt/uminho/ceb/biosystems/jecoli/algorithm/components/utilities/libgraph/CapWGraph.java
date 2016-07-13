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


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * Graph with weights on arcs and capacities on arcs.
 */

public class CapWGraph extends WGraph
{


/** capacities of the edges. */
 double[][] capacities;

 /**
  * Instantiates a new cap w graph.
  */
 public CapWGraph()
 {}

 /**
  * Instantiates a new cap w graph.
  * 
  * @param d the d
  */
 public CapWGraph(int d)
 {
	super(d);
	capacities = new double [n][n]; // oriented 
 }

 /**
  * Instantiates a new cap w graph.
  * 
  * @param d the d
  * @param or the or
  */
 public CapWGraph(int d, boolean or)
 {
	oriented = or;
	complete = false;
	allocate(d);
 }

 /**
  * Instantiates a new cap w graph.
  * 
  * @param filename the filename
  * @param oriented the oriented
  * 
  * @throws Exception the exception
  */
 public CapWGraph(String filename, boolean oriented) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	this.oriented = oriented;
	read(B);
 }

 /**
  * Instantiates a new cap w graph.
  * 
  * @param filename the filename
  * @param format the format
  * 
  * @throws Exception the exception
  */
 public CapWGraph(String filename, int format) throws Exception
 {
 	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	read(B, format);
 }


 /* (non-Javadoc)
  * @see utilities.libgraph.WGraph#allocate(int)
  */
 public void allocate (int d)
 {
 	super.allocate(d);
	if(oriented)
		capacities = new double [n][n]; 
	else
	{
		capacities = new double[n][];
		for(int i=0; i<n; i++)
			capacities[i] = new double[i+1];
	}
 }
 

 /**
  * Gets the capacity.
  * 
  * @param n1 the n1
  * @param n2 the n2
  * 
  * @return the capacity
  */
 public double getCapacity(int n1, int n2)
 {
 	double res;
	if(oriented) res = capacities[n1][n2];
	else
	{
		if(n1>n2) res = capacities[n1][n2];
		else res = capacities[n2][n1];
	}
	return res;
 }

 /**
  * Sets the capacity.
  * 
  * @param n1 the n1
  * @param n2 the n2
  * @param w the w
  */
 public void setCapacity(int n1, int n2, double w)
 {
	if(oriented) capacities[n1][n2] = w;
	else
	{
		if(n1>n2) capacities[n1][n2]=w;
		else capacities[n2][n1]=w;
	}
 }

 /**
  * Average capacity.
  * 
  * @return the double
  */
 public double averageCapacity()
 {
	double sum = 0.0;
	int ne = 0;

	int nrows = connections.length;
	for(int i=0; i< nrows; i++) {
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) {
				sum += getCapacity(i,j); 
				ne++;
			}
	}

	return (sum/ne);
 }

 /* (non-Javadoc)
  * @see utilities.libgraph.WGraph#writecons(java.io.BufferedWriter)
  */
 public void writecons(BufferedWriter B) throws Exception
 {
	B.write(n+"\n");
	int nrows = connections.length;
	for(int i=0; i< nrows; i++)
	{
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) B.write(getWeight(i,j)+" ");
			else B.write("X ");
		B.write("\n");
	}
	for(int i=0; i< nrows; i++)
	{
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) B.write(getCapacity(i,j)+" ");
			else B.write("X ");
		B.write("\n");
	}
	B.flush();
 }

 /* (non-Javadoc)
  * @see utilities.libgraph.WGraph#read_lower(java.io.BufferedReader)
  */
 public void read_lower(BufferedReader B) throws Exception
 {
 	// read dimension
	String str = B.readLine();
	allocate(Integer.parseInt(str));

	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=i+1) 
				throw new Exception("Error in file format: class WGraph"); 
		for(int j=0; j<=i ; j++)
		{
			String token = st.nextToken();
			if(token.equals("X")) setConnection(i,j,false);
			else
			{
				setConnection(i,j,true);
				double w = Double.valueOf(token).doubleValue();
				setWeight(i,j,w);
			}
		}
	}
	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=i+1) 
				throw new Exception("Error in file format: class WGraph"); 
		for(int j=0; j<=i ; j++)
		{
			String token = st.nextToken();
			if(!token.equals("X")) 
			{
				double w = Double.valueOf(token).doubleValue();
				setCapacity(i,j,w);
			}
		}
	}
 }

 /* (non-Javadoc)
  * @see utilities.libgraph.WGraph#readoriented(java.io.BufferedReader)
  */
 public void readoriented(BufferedReader B) throws Exception
 {
 	// read dimension
	String str = B.readLine();
	allocate(Integer.parseInt(str));

	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=n) 
				throw new Exception("Error in file format: class Graph"); 
		for(int j=0; j< n; j++)
		{
			String token = st.nextToken();
			if(token.equals("X")) setConnection(i,j,false);
			else
			{
				setConnection(i,j,true);
				double w = Double.valueOf(token).doubleValue();
				setWeight(i,j,w);
			}
		}
	}
	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=n) 
				throw new Exception("Error in file format: class Graph"); 
		for(int j=0; j< n; j++)
		{
			String token = st.nextToken();
			if(!token.equals("X")) 
			{
				double w = Double.valueOf(token).doubleValue();
				setCapacity(i,j,w);
			}
		}
	}
 }

 /* (non-Javadoc)
  * @see utilities.libgraph.WGraph#read_upper(java.io.BufferedReader)
  */
 public void read_upper(BufferedReader B) throws Exception
 {
	// read dimension
	String str = B.readLine();
	allocate(Integer.parseInt(str));

	for(int j=0; j<n; j++)
		setConnection(j,j, false);
	
	for(int i=0; i<n-1; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=n-i-1) 
				throw new Exception("Error in file format: class Graph"); 
		for(int j=1; j< n-i; j++)
		{
			String token = st.nextToken();
			if(token.equals("X")) setConnection(i,j,false);
			else
			{
				setConnection(i,j,true);
				double w = Double.valueOf(token).doubleValue();
				setWeight(i,j,w);
			}
		}
	}
	for(int i=0; i<n-1; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=n-i-1) 
				throw new Exception("Error in file format: class Graph"); 
		for(int j=1; j< n-i; j++)
		{
			String token = st.nextToken();
			if(!token.equals("X")) 
			{
				double w = Double.valueOf(token).doubleValue();
				setCapacity(i,j,w);
			}
		}
	}
 }


 /**
  * The main method.
  * 
  * @param args the arguments
  */
 public static void main(String [] args)
 {

	try{

		CapWGraph g = new CapWGraph("cor.gr", true);
		g.print();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

 }


}
