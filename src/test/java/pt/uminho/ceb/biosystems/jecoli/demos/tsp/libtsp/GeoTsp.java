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
import java.io.FileReader;
import java.util.StringTokenizer;


// TODO: Auto-generated Javadoc
/**
 * The Class GeoTsp.
 */
public class GeoTsp extends EucTsp
{

/**
 * Instantiates a new geo tsp.
 * 
 * @param d the d
 */
public GeoTsp(int d)
{
	super(d);
}

/**
 * Instantiates a new geo tsp.
 * 
 * @param filename the filename
 * 
 * @throws Exception the exception
 */
public GeoTsp(String filename) throws Exception
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

/**
 * Set_all_distances.
 */
public void set_all_distances ()
{
	double RRR = 6378.388;

	double [] lats = new double[n];
	double [] longs = new double[n];

	for(int i=0; i<n; i++)
	{
		int deg = (int)(get_x(i));
 		double	min =  get_x(i) -  deg;
		lats[i] = Math.PI * (deg+ 5.0* min/3.0) / 180.0;
		deg = (int)(get_y(i));
		min =  get_y(i) - deg;
		longs[i] = Math.PI * (deg+ 5.0* min/3.0) / 180.0;
	}

	for(int i=0; i<n; i++)
		for(int j=i+1; j<n; j++)
		{
			double q1 = Math.cos(longs[i]-longs[j]);
			double q2 = Math.cos(lats[i]-lats[j]);
			double q3 = Math.cos(lats[i]+lats[j]);
			int dist = (int)(RRR*Math.acos(0.5*((1.0+q1)*q2-(1.0-q1)*q3))+1.0);
			set_distance(i, j, dist);
		}
}


}

