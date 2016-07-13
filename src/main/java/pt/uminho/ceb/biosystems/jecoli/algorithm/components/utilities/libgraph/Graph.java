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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;


// TODO: Auto-generated Javadoc
/* Graphs with boolean connections, oriented or not, no weights */
/**
 * The Class Graph.
 */
public class Graph implements java.io.Serializable
{

/** dimension - number of nodes in the graph. */
 protected int n; 

/** connections in the graph; it is null if the graph is complete. */
 boolean[][] connections;

/** defines if the graph is complete. */
 boolean complete;

/** defines if the graph is oriented. */
 boolean oriented;

// graph formats
 /** The Constant ORIENTED. */
public static final int ORIENTED = 0; // oriented graph (NxN 0/1 matrix)
 
 /** The Constant OR_DIMACS. */
 public static final int OR_DIMACS = 1; // DIMACS format (specify edges)
 
 /** The Constant NO_DIMACS. */
 public static final int NO_DIMACS = 2; // DIMACS format (specify edges)
 
 /** The Constant NO_UPPER. */
 public static final int NO_UPPER = 3; // not oriented - upper triangular matrix 
 
 /** The Constant NO_LOWER. */
 public static final int NO_LOWER = 4; // not oriented - lower triangular matrix

 /**
  * Instantiates a new graph.
  */
 public Graph()
 {
 }

/**
 * Constructor from dimension. Assumes a oriented graph
 * 
 * @param d the d
 */
 public Graph(int d)
 {
 	this(d, true);
 }

/**
 * Construction from dimension and flag: oriented or not.
 * 
 * @param d the d
 * @param or the or
 */
 public Graph(int d, boolean or)
 {
	oriented = or;
	complete = false;
	allocate(d);
 }

/**
 * Reads a graph from file - graph specified as 0/1 matrix with dimension in first row.
 * 
 * @param filename the filename
 * @param oriented the oriented
 * 
 * @throws Exception the exception
 */
 public Graph(String filename, boolean oriented) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	this.oriented = oriented;
	read(B);
 }

/**
 * Reads a graph from file given the selected format.
 * 
 * @param filename the filename
 * @param format the format
 * 
 * @throws Exception the exception
 */
 public Graph(String filename, int format) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	read(B, format);
 }

/**
 * Allocates the connections matrix given the dimension.
 * 
 * @param d the d
 */
 public void allocate(int d)
 {
 	n = d;
	if(oriented)
		connections = new boolean [n][n]; 
	else
	{
		connections = new boolean[n][];
		for(int i=0; i<n; i++)
			connections[i] = new boolean[i+1];
	}
 }
 
/**
 * Returns the dimension (number of nodes) of the graph).
 * 
 * @return the dimension
 */
 public int getDimension()
 {
	return n;
 }

/**
 * Returns true if a connection between n1 and n2 exists and false otherwise.
 * 
 * @param n1 the n1
 * @param n2 the n2
 * 
 * @return the connection
 */
 public boolean getConnection(int n1, int n2)
 {
 	boolean res;
	if(complete) res =  true;
	if(oriented) res = connections[n1][n2];
	else
	{
		if(n1>n2) res = connections[n1][n2];
		else res = connections[n2][n1];
	}
	return res;
 }

/* Sets a connection between nodes n1 and n2 to true or false */
 /**
 * Sets the connection.
 * 
 * @param n1 the n1
 * @param n2 the n2
 * @param v the v
 */
public void setConnection(int n1, int n2, boolean v)
 {
	if(oriented) connections[n1][n2] = v;
	else
		if(n1>n2) connections[n1][n2] = v;
		else connections[n2][n1]=v;
 }


 /**
  * Sets the complete.
  */
 public void setComplete()
 {
	this.complete = true; 
	for(int i=0; i < connections.length; i++)
		for(int j=0; j<connections[i].length; j++)
			connections[i][j] = true;
 }
 
/* Returns the number of connections = true */
 /**
 * Count edges.
 * 
 * @return the int
 */
public int countEdges()
 {
 	int cont = 0;
 	int nrows = connections.length;
	for(int i=0; i< nrows; i++)
	{
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) cont++;
	}
	return cont;
 }

 /**
  * Gets the all edges.
  * 
  * @return the all edges
  */
 public int[][] getAllEdges()
 {
   int ne = countEdges();
	int [][] res = new int[ne][2];
 	int cont = 0;
 	int nrows = connections.length;
	for(int i=0; i< nrows; i++)
	{
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) 
			{	
				res[cont][0] = i;
				res[cont][1] = j;
				cont++;
			}
	}
	return res;
 }

/* returns all destinations nodes leaving from s*/
 /**
 * Gets the destinations.
 * 
 * @param s the s
 * 
 * @return the destinations
 */
public Vector<Integer> getDestinations(int s)
 {
	Vector<Integer> res = new Vector<Integer>(); 
	for(int i=0; i<n; i++)
			if (i!=s && getConnection(s,i)) res.add(new Integer(i));	
	return res;
 }

/**
 * returns number of edges out of a node.
 * 
 * @param node the node
 * 
 * @return the int
 */
 public int outDegree(int node)
 {
	int res = 0;
	for(int i=0; i<n; i++)
			if (getConnection(node,i)) res++;	
	return res;	
 }

 /**
  * In degree.
  * 
  * @param node the node
  * 
  * @return the int
  */
 public int inDegree(int node)
 {
	int res = 0;
	for(int i=0; i<n; i++)
			if (getConnection(i, node)) res++;	
	return res;	
 }


 /**
  * Gets the all paths.
  * 
  * @param origin the origin
  * @param dest the dest
  * 
  * @return the all paths
  */
 public Vector<Vector<Integer>> getAllPaths(int origin, int dest)
 {
  Vector<Integer> cp =new Vector<Integer>();
  cp.add(new Integer(origin));
  Vector<Vector<Integer>> cs = new Vector<Vector<Integer>>();
  getAllPaths(origin, dest, cp, cs); 
  return cs;
 }

 /**
  * Gets the all paths.
  * 
  * @param origin the origin
  * @param dest the dest
  * @param cp the cp
  * @param cs the cs
  * 
  * @return the all paths
  */
 public void getAllPaths(int origin, int dest, Vector<Integer> cp, Vector<Vector<Integer>> cs)
 {
   if (origin==dest) {cs.add(cp);}
	else 
	{
		for(int i=0; i<n; i++)
			if( getConnection(origin, i) )
			{
				Vector<Integer> newpp = (Vector<Integer>)cp.clone(); 
				newpp.add(new Integer(i));
				getAllPaths(i, dest, newpp, cs);
			}	
	}
 }

// IO FUNCTIONS
 
/* Writes the graph to a Buffered Writer stream */
 /**
 * Write.
 * 
 * @param B the b
 * 
 * @throws Exception the exception
 */
public void write(BufferedWriter B) throws Exception
 {
	if(oriented)
		write(B, ORIENTED);
	else
		write(B, NO_LOWER);
	
 }

/* Writes the graph to a Buffered Writer stream given the format */
 /**
 * Write.
 * 
 * @param B the b
 * @param format the format
 * 
 * @throws Exception the exception
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
			writedimacs(B);
			break;
	}
 }

 /**
  * Writecons.
  * 
  * @param B the b
  * 
  * @throws Exception the exception
  */
 public void writecons(BufferedWriter B) throws Exception
 {
	B.write(n+"\n");
	int nrows = connections.length;
	for(int i=0; i< nrows; i++)
	{
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) B.write("1 ");
			else B.write("0 ");
		B.write("\n");
	}
	B.flush();
 }

 /**
  * Writedimacs.
  * 
  * @param B the b
  * 
  * @throws Exception the exception
  */
 public void writedimacs(BufferedWriter B) throws Exception
 {
	B.write(n+"\n");
	B.write(countEdges()+"\n");
	int nrows = connections.length;
	for(int i=0; i< nrows; i++)
	{
		int ncols = connections[i].length; 
		for(int j=0; j< ncols; j++)
			if( getConnection(i,j) ) B.write(i + " " + j + "\n");
	}
	B.flush();
 }

 /**
  * Read.
  * 
  * @param B the b
  * 
  * @throws Exception the exception
  */
 public void read (BufferedReader B) throws Exception
 {
	if(oriented)
		read(B, ORIENTED);
	else
		read(B, NO_LOWER);
 }

 /**
  * Read.
  * 
  * @param B the b
  * @param format the format
  * 
  * @throws Exception the exception
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
			oriented = true;
			readimacs(B);
			break;
		case NO_DIMACS:
			oriented = false;
			readimacs(B);
			break;
	}
 }

 /**
  * Readoriented.
  * 
  * @param B the b
  * 
  * @throws Exception the exception
  */
 public void readoriented (BufferedReader B) throws Exception
 {
	// read dimension
	String str = B.readLine();
	//allocates space
	allocate(Integer.parseInt(str));

	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=n) 
				throw new Exception("Error in file format: class Graph"); 
		for(int j=0; j< n; j++)
		{
			int c = Integer.parseInt(st.nextToken());
			if(c==1) setConnection(i,j,true);
			else if(c==0) setConnection(i,j,false);
			else throw new Exception("Error in file format: class Graph");
		}
	}
 }

/**
 * Reads oriented graph in lower triangular matrix.
 * 
 * @param B the b
 * 
 * @throws Exception the exception
 */
 public void read_lower(BufferedReader B) throws Exception
 {
	// read dimension
	String str = B.readLine();
	//allocates space
	allocate(Integer.parseInt(str));

	for(int i=0; i<n; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=i+1) 
				throw new Exception("Error in file format: class Graph"); 
		for(int j=0; j<=i; j++)
		{
			int c = Integer.parseInt(st.nextToken());
			if(c==1) setConnection(i,j,true);
			else if(c==0) setConnection(i,j,false);
			else throw new Exception("Error in file format: class Graph");
		}
	}
 }

/**
 * Reads oriented graph in upper triangular matrix.
 * 
 * @param B the b
 * 
 * @throws Exception the exception
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
			int c = Integer.parseInt(st.nextToken());
			if(c==1) setConnection(i,j+i,true);
			else if(c==0) setConnection(i,j+i,false);
			else throw new Exception("Error in file format: class Graph");
		}
	}
 }

/**
 * Reads graph in list of edges (DIMACS) format.
 * 
 * @param B the b
 * 
 * @throws Exception the exception
 */
 public void readimacs(BufferedReader B) throws Exception
 {
	// read dimension
	String str = B.readLine();
	allocate(Integer.parseInt(str));
	//initialize connections to false	
	for(int i=0; i<n; i++)
	{
		int ncols = (oriented? n: i+1);
		for(int j=0; j< ncols; j++)
			setConnection(i,j,false);
	}

	str = B.readLine();
	int nedges = Integer.parseInt(str);
	for(int i=0; i<nedges; i++)
	{	
		str = B.readLine();
		StringTokenizer st = new StringTokenizer(str);
		if(st.countTokens()!=2) 
				throw new Exception("Error in file format: class Graph"); 
		int c1 = Integer.parseInt(st.nextToken());
		int c2 = Integer.parseInt(st.nextToken());
		setConnection(c1, c2, true);
	}
 }

 /**
  * Save.
  * 
  * @param filename the filename
  * 
  * @throws Exception the exception
  */
 public void save(String filename) throws Exception
 {
	FileWriter f = new FileWriter(filename);
	BufferedWriter b = new BufferedWriter(f);
	write(b);
 }

 /**
  * Save.
  * 
  * @param filename the filename
  * @param format the format
  * 
  * @throws Exception the exception
  */
 public void save(String filename, int format) throws Exception
 {
	FileWriter f = new FileWriter(filename);
	BufferedWriter b = new BufferedWriter(f);
	write(b, format);
 }

 /**
  * Load.
  * 
  * @param filename the filename
  * 
  * @throws Exception the exception
  */
 public void load(String filename) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	read(B);
 }

 /**
  * Load.
  * 
  * @param filename the filename
  * @param format the format
  * 
  * @throws Exception the exception
  */
 public void load(String filename, int format) throws Exception
 {
	FileReader f = new FileReader(filename);
	BufferedReader B = new BufferedReader(f);
	read(B, format);
 }

 /**
  * Prints the.
  * 
  * @throws Exception the exception
  */
 public void print() throws Exception
 {
	PrintWriter p = new PrintWriter(System.out);
	BufferedWriter b = new BufferedWriter(p);
	write(b);
 }

 /**
  * Prints the.
  * 
  * @param format the format
  * 
  * @throws Exception the exception
  */
 public void print(int format) throws Exception
 {
	PrintWriter p = new PrintWriter(System.out);
	BufferedWriter b = new BufferedWriter(p);
	write(b, format);
 }

 /**
  * The main method.
  * 
  * @param args the arguments
  */
 public static void main(String [] args)
 {

	try{
		WGraph g = new WGraph("pedro.or", ORIENTED);
		g.print();
		//g.print(NO_LOWER);
		Vector<Vector<Integer>> v = g.getAllPaths(0,6);
		for (int k=0; k < v.size(); k++)
		{
			Vector<Integer> ca = v.get(k);
			System.out.println("Caminho:");
			for(int r=0; r < ca.size(); r++) System.out.println(" " + ((Integer)ca.get(r)).intValue());
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

 }

}

