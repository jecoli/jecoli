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


// TODO: Auto-generated Javadoc
/**
 * The Class WNGraph.
 */
public class WNGraph extends Graph
{

/** weights of the nodes. */
 double [] wnodes;


 /**
  * Instantiates a new wN graph.
  * 
  * @param d the d
  */
 public WNGraph(int d)
 {
	super(d);
	wnodes = new double[d];
 }

 /**
  * Instantiates a new wN graph.
  * 
  * @param d the d
  * @param or the or
  */
 public WNGraph(int d, boolean or)
 {
	super(d, or);
	wnodes = new double[d];
 }

 /**
  * Instantiates a new wN graph.
  * 
  * @param filename the filename
  * @param oriented the oriented
  * 
  * @throws Exception the exception
  */
 public WNGraph(String filename, boolean oriented) throws Exception
 {
 	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	this.oriented = oriented;
	read(B);
 }

 /**
  * Instantiates a new wN graph.
  * 
  * @param filename the filename
  * @param format the format
  * 
  * @throws Exception the exception
  */
 public WNGraph(String filename, int format) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	read(B, format);
 }

 /**
  * Read_weights.
  * 
  * @param B the b
  * 
  * @throws Exception the exception
  */
 public void read_weights(BufferedReader B) throws Exception
 {
	String str;
	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		setWeight(i, Double.valueOf(str).doubleValue() ); 		
	}
 }

 /**
  * Gets the weight.
  * 
  * @param node the node
  * 
  * @return the weight
  */
 public double getWeight(int node)
 {
	return wnodes[node];
 }

 /**
  * Sets the weight.
  * 
  * @param node the node
  * @param w the w
  */
 public void setWeight(int node, double w)
 {
	wnodes[node] = w;
 }

 /* (non-Javadoc)
  * @see utilities.libgraph.Graph#write(java.io.BufferedWriter, int)
  */
 public void write(BufferedWriter B, int format) throws Exception
 {
	super.write(B, format);
	for(int i=0; i<n; i++)
		B.write( getWeight(i) + "\n");
	B.flush();
 }

 /* (non-Javadoc)
  * @see utilities.libgraph.Graph#read(java.io.BufferedReader, int)
  */
 public void read(BufferedReader B, int format) throws Exception
 {
	super.read(B, format);
	wnodes = new double[n];
	read_weights(B);
 }

 /**
  * The main method.
  * 
  * @param args the arguments
  */
 public static void main(String [] args)
 {
	try{

		WNGraph g = new WNGraph("b.gr", NO_DIMACS);
		g.print();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

 }

}
