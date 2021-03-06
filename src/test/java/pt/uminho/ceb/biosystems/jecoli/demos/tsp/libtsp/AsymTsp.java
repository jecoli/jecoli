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
 * The Class AsymTsp.
 */
public class AsymTsp extends Tsp
{
	
	/**
	 * Instantiates a new asym tsp.
	 */
	public AsymTsp()
	{

	}

	/**
	 * Instantiates a new asym tsp.
	 * 
	 * @param d the d
	 */
	public AsymTsp(int d)
	{
		super(d);
		for (int i = 0; i < n; i++) 
  			distances[i] = new double[n];
		for (int j = 0; j < n; j++)
   			distances[j][j] = 0.0;
	}

	/**
	 * Instantiates a new asym tsp.
	 * 
	 * @param filename the filename
	 * 
	 * @throws Exception the exception
	 */
	public AsymTsp (String filename) throws Exception
	{
		FileReader f = new FileReader(filename);
		BufferedReader B = new BufferedReader(f);

		String str = B.readLine();
		n = Integer.parseInt(str);
		distances = new double [n][];
		for (int i = 0; i < n; i++) 
  			distances[i] = new double[n];
		for (int j = 0; j < n; j++)
   			distances[j][j] = 0.0;

		for(int i=0; i< n; i++)
		{
			str = B.readLine();
			StringTokenizer st = new StringTokenizer(str);
			if(st.countTokens()!=n) 
				throw new Exception("Error in file format: class ASymTsp");
			for(int j=0; j<n; j++)
			{
				double dist = Double.valueOf(st.nextToken()).doubleValue();
				set_distance(i, j, dist);
			}
		}

	}

	/* (non-Javadoc)
	 * @see test.tsp.libtsp.Tsp#get_distance(int, int)
	 */
	public double get_distance (int n1, int n2)
	{
 		if (n1 != n2) return distances[n1][n2];
		else return 0;
	}

	/* (non-Javadoc)
	 * @see test.tsp.libtsp.Tsp#set_distance(int, int, double)
	 */
	public void set_distance (int n1, int n2, double d)
	{
		if (n1 != n2) distances[n1][n2] = d;
	}

	// I/O

	/* (non-Javadoc)
	 * @see test.tsp.libtsp.Tsp#read(java.io.BufferedReader)
	 */
	public void read (BufferedReader B) throws Exception
	{
		String str = B.readLine();
		n = Integer.parseInt(str);
		for(int i=0; i< n; i++)
		{
			str = B.readLine();
			StringTokenizer st = new StringTokenizer(str);
			if(st.countTokens()!=n) 
				throw new Exception("Error in file format: class ASymTsp");
			for(int j=0; j< n; j++)
			{
				double dist = Double.valueOf(st.nextToken()).doubleValue();
				set_distance(i, j, dist);
			}
		}
	}

	/* (non-Javadoc)
	 * @see test.tsp.libtsp.Tsp#write(java.io.BufferedWriter)
	 */
	public void write (BufferedWriter B) throws Exception
	{
		B.write(n+"\n");
		for(int i=0; i< n; i++)
			{
				for(int j=0; j<n; j++)
					B.write( get_distance(i, j)+ " ");
			}
			B.write("\n");
	}

}

