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
 * The Class CWGraph.
 */
public class CWGraph extends WGraph
{


 /**
  * Instantiates a new cW graph.
  * 
  * @param d the d
  */
 public CWGraph(int d)
 {
 	this(d, true);
 }

 /**
  * Instantiates a new cW graph.
  * 
  * @param d the d
  * @param or the or
  */
 public CWGraph(int d, boolean or)
 {
	oriented = or;
	complete = true;
	allocate(d);
 }

 /**
  * Instantiates a new cW graph.
  * 
  * @param filename the filename
  * @param oriented the oriented
  * 
  * @throws Exception the exception
  */
 public CWGraph(String filename, boolean oriented) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	this.oriented = oriented;
	read(B);
 }

 /**
  * Instantiates a new cW graph.
  * 
  * @param filename the filename
  * @param format the format
  * 
  * @throws Exception the exception
  */
 public CWGraph(String filename, int format) throws Exception
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
 	n=d;
	connections = null;
	if(oriented)
		weights = new double [n][n]; 
	else
	{
		weights = new double[n][];
		for(int i=0; i<n; i++)
			weights[i] = new double[i+1];
	}
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
			B.write(getWeight(i,j)+" ");
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
			double w = Double.valueOf(token).doubleValue();
			setWeight(i,j,w);
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
			double w = Double.valueOf(token).doubleValue();
			setWeight(i,j,w);
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
			double w = Double.valueOf(token).doubleValue();
			setWeight(i,j,w);
		}
	}
 }

/* Writes the graph to a Buffered Writer stream given the format */
 /* (non-Javadoc)
 * @see utilities.libgraph.Graph#write(java.io.BufferedWriter, int)
 */
public void write(BufferedWriter B, int format) throws Exception
 {
	switch (format)
	{
		case ORIENTED:
		case NO_LOWER:
			writecons(B);
			break;
		case NO_UPPER:
			System.out.println("not implemented");
			break;
		case OR_DIMACS:
		case NO_DIMACS:
			throw new Exception("Wrong format: graph must be complete");
	}
 }


  /* (non-Javadoc)
   * @see utilities.libgraph.Graph#read(java.io.BufferedReader, int)
   */
  public void read (BufferedReader B, int format) throws Exception
 {
	switch (format)
	{
		case ORIENTED:
			oriented = true;
			readoriented(B);
			break;
		case NO_LOWER:
			oriented = false;
			read_lower(B);
			break;
		case NO_UPPER:
			oriented = false;
			read_upper(B);
			break;
		case OR_DIMACS:
		case NO_DIMACS:
			throw new Exception("Wrong format: graph must be complete");
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

		WGraph g = new WGraph("a.gr", false);
		g.print();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

 }


}
