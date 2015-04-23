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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;

// TODO: Auto-generated Javadoc
/**
 * The Class Tsp.
 */
abstract public class Tsp
{

	/** The distances. */
	protected double [][] distances;
	
	/** The n. */
	protected int n;

	// Constructors

	/**
	 * Instantiates a new tsp.
	 */
	public Tsp ()
	{

	}
	
	public Tsp(Tsp tspOriginal){
		n = tspOriginal.n;
		distances = new double[tspOriginal.distances.length][tspOriginal.distances[0].length];
		for(int i = 0; i < tspOriginal.distances.length;i++)
			for(int j = 0; j < tspOriginal.distances[0].length;j++)
				distances[i][j] = tspOriginal.distances[i][j];
	}

	/**
	 * Instantiates a new tsp.
	 * 
	 * @param d the d
	 */
	public Tsp (int d)
	{
		n = d;
		distances = new double [n][];
	}

	/**
	 * Gets the dimension.
	 * 
	 * @return the dimension
	 */
	public int getDimension()
	{
		return n;
	}

	/**
	 * Sets the dimension.
	 * 
	 * @param d the new dimension
	 */
	public void setDimension(int d)
	{
		n = d;
	}
	
	// distances  

	/**
	 * Gets the _distance.
	 * 
	 * @param n1 the n1
	 * @param n2 the n2
	 * 
	 * @return the _distance
	 */
	abstract public double get_distance(int n1, int n2);

	/**
	 * Set_distance.
	 * 
	 * @param n1 the n1
	 * @param n2 the n2
	 * @param d the d
	 */
	abstract public void set_distance(int n1, int n2, double d);

	/**
	 * Cost.
	 * 
	 * @param sol the sol
	 * 
	 * @return the double
	 */
	public double cost (int [] sol)
	{
		int n = sol.length;
		double ac = 0.0;

		for(int k = 0; k < n; k++)
			ac += get_distance(sol[k], sol[(k+1)%n]); 
		return ac;
	}


	/**
	 * Avg_distances.
	 * 
	 * @return the double
	 */
	public double avg_distances()
	{
 		double sum = 0;

 		for(int k = 0; k < n; k++)
  			for(int j =0; j<n; j++)
				sum += get_distance(k,j);	
 		int tot_weights = n*(n-1)/2;

 		return (sum/tot_weights);
	}


	// heuristics

	/**
	 * Nearest_neighbour.
	 * 
	 * @param start the start
	 * 
	 * @return the int[]
	 */
	public int [] nearest_neighbour(int start)
	{
 		int[] res = new int[n];
		res[0] = start;
 		for (int i=1; i < n; i++)
 		{
  			double min = 0;
  			int nn = -1;
  			for (int k = 0; k < n; k++)
   				if ( !is_in(res, k, i) ) 
   				{
    				double dist = get_distance(res[i-1], k);
    				if ( nn == -1 || dist < min)
    				{
     					min = dist;
     					nn = k;
    				}
   				}
  			res[i] = nn;
 		}
 		return res;
	}

	// uses the previous heuristic by trying all diferent start nodes 
	// and keeping the best result

	/**
	 * Nearest_neighbour.
	 * 
	 * @return the int[]
	 */
	public int[] nearest_neighbour()
	{
 		int[] sol = new int[n];
		double min = 0;

 		for (int k = 0; k < n; k++)
		{
  			int[] s = nearest_neighbour(k);
  			double c = cost(s);
  			if (k==0 || c < min )
  			{
   				sol = s;
   				min = c;
  			}
 		}
 		return sol;
	}

// minimum cost insertion
// first creates a tour with 2 nodes with minimum cost
// then iteratively inserts nodes that minimize the inserion cost
// for the overall tour 

	/**
 * Min_insertion.
 * 
 * @return the int[]
 */
public int[] min_insertion ()
	{
  		double min = 0;
  		int[] res = new int[n];
  		int selA = 0, selB = 0;
  
  		// calculate first pair of nodes 
  		for (int i = 0; i< n; i++) 
   			for (int j = 0; j < i; j++)
   			{
      			double d = get_distance(i, j) + get_distance(j, i);
      			if ( (i == 1 && j == 0) || d < min )
      			{
       				min = d;
       				selA = i; 
       				selB = j;
      			}
   			}
  		res[0] = selA;
  		res[1] = selB;

 		// insert the rest of the nodes by the minimum insertion cost criterion   
  		for(int i = 2; i <n; i++)
  		{
   			double minck = 0;
   			int place=-1, in = 0;
    		// find minimum cost node to insert (in) and proper place in sequence(place)
   			for (int k = 0; k < n; k++)
    			if (!is_in(res, k, i))
     				for (int j = 0; j< i; j++)
     				{
      					double ck = get_distance(res[j],k)+ 
									get_distance(k,res[(j+1)%i])
			  						- get_distance(res[j],res[(j+1)%i]);
      					if (place == -1 || ck < minck)
      					{
        					minck = ck;
        					place = j;
        					in = k;
      					}
     				}
   			// insert the node 
   			for (int k = i; k > place+1; k--)
     			res[k] = res[k-1];
   			res[place+1] = in;
  		}

   		return res;
	}

// k_opt heuristic with k = 2
// one_pass means the initial tour is improved by removing two edges 
// and reconecting the tour in a different way

	/**
 * One_pass_2opt_s.
 * 
 * @param sol the sol
 * 
 * @return the int[]
 */
public int[] one_pass_2opt_s (int[] sol)
	{
		int n = sol.length;
 		int [] res = new int[n];
 		double c = cost(sol);  
 		copy_arr(sol,res);

  		for (int j=0; j<n-2; j++)
  		{
  			int max;
   			if (j==0) max = n-1;
   			else max = n;    
   			for (int k=j+2 ; k<max; k++)
   			{
   				double f = s_2opt(res, j, k, c);
				c = f;
   			}
  		} 
  		return res;
	}

	/**
	 * One_pass_2opt.
	 * 
	 * @param sol the sol
	 * 
	 * @return the double
	 */
	public double one_pass_2opt (int [] sol)
	{
  		double c = cost(sol);  
		return (one_pass_2opt(sol, c));
	}

	/**
	 * One_pass_2opt.
	 * 
	 * @param sol the sol
	 * @param c the c
	 * 
	 * @return the double
	 */
	public double one_pass_2opt (int[] sol, double c)
	{
		int n = sol.length;
  		double f = c;
  		for (int j=0; j<n-2; j++)
  		{
   			int max;
   			if (j==0) max = n-1;
   			else max = n;
   			for (int k=j+2 ; k<max; k++)
   			{
   				double f1 = s_2opt(sol, j, k, f);
				f = f1;
   			}
  		} 
  		return f;
	}

	// uses previous method with a random start solution
	/**
	 * One_pass_2opt.
	 * 
	 * @return the int[]
	 */
	public int [] one_pass_2opt()
	{
		int [] sol = give_rand_perm (n);
 		one_pass_2opt(sol);
 		return sol;
	}

	// best 2 opt finds the larger improvement possible in a 2opt move

	/**
	 * Best_2opt.
	 * 
	 * @param sol the sol
	 * @param inicost the inicost
	 * 
	 * @return the double
	 */
	public double best_2opt (int[] sol, double inicost)
	{
		int n = sol.length;
  		int n1 = 0, n2 = 0;
  		double bi = 0.0; // best improvement found
  		double cost = inicost;

  		for (int j=0; j<n-2; j++)
  		{
   			int max;
   			if (j==0) max = n-1;
   			else max = n;    
   			for (int k=j+2 ; k<max; k++)
   			{
	 			double f = get_distance(sol[j],sol[j+1])
 				+ get_distance(sol[k],sol[(k+1)%n])
 				- get_distance(sol[j+1],sol[(k+1)%n])
 				- get_distance(sol[j],sol[k]);
				if(f > bi)
				{
					bi = f; 
					n1 = j;
					n2 = k;
				}
   			}
  		} 

  		if (bi > 0.00001*inicost)
   			// reversing the sequence from position j to position k
   		for (int l = 0; l<(n2-n1)/2; l++)
   		{
			int aux = sol[n1+l+1];
    		sol[n1+l+1] = sol[n2-l];
			sol[n2-l] = aux;
			cost = inicost - bi;
   		}
  
  		return cost;
	}


// complete 2-opt implements one-pass 2 opt iteratively
// until no improvement is possible

	/**
 * Complete_2opt_s.
 * 
 * @param ini the ini
 * 
 * @return the int[]
 */
public int [] complete_2opt_s(int [] ini)
	{
		int n = ini.length;
 		int [] res = new int[n];
 		copy_arr(res,ini);

 		double f = cost(ini);
 		double f1 = one_pass_2opt(res, f);
 		while (f > f1)
 		{
  			f = f1;
  			f1 = one_pass_2opt(res, f);
 		}
 		return res;
	}

	/**
	 * Complete_2opt.
	 * 
	 * @param sol the sol
	 * 
	 * @return the double
	 */
	public double complete_2opt(int[] sol)
	{
 		double f = cost(sol);
 		double f1 = one_pass_2opt (sol, f);
 		while (f > f1)
 		{
  			f = f1;
  			f1 = one_pass_2opt(sol, f);
 		}
 		return f1;
	}

	/**
	 * Complete_2opt.
	 * 
	 * @return the int[]
	 */
	public int[] complete_2opt()
	{
 		int[] initial = give_rand_perm (n);
 		complete_2opt(initial);
 
 		return initial;
	}


// n-2opt implements n attempts to do a 2-opt improvement

	/**
 * N_2opt.
 * 
 * @param ini the ini
 * @param cost the cost
 * @param attempts the attempts
 * 
 * @return the double
 */
public double n_2opt (int[] ini, double cost, int attempts)
	{
		int n = ini.length;
  		double best = cost;  
  		for (int x = 0; x < attempts; x++)
  		{
    		int j,k;
    		// select first edge 
    		j = irandom(n-1);
    		// select second edge
    		do k = irandom(n-1);
    		while ((k == j) || (k == j-1) || (j == k-1) || (j==0 && k==n) || (k==0 && j==n));

			double f = s_2opt(ini,j,k,best);
			best = f;
  		}
  		return best;
	}

	/**
	 * N_2opt.
	 * 
	 * @param ini the ini
	 * @param attempts the attempts
	 * 
	 * @return the double
	 */
	public double n_2opt (int[] ini, int attempts)
	{ 
  		double c = cost(ini);  
		return ( n_2opt(ini, c, attempts) );
	}


	/**
	 * N_2opt_s.
	 * 
	 * @param ini the ini
	 * @param attempts the attempts
	 * 
	 * @return the int[]
	 */
	public int [] n_2opt_s (int [] ini, int attempts)
	{ // fast 2 opt
		int n = ini.length;
  		int[] res = new int[n];
  		copy_arr(ini, res);

  		double best = cost(res);  

  		for (int x = 0; x < attempts; x++)
  		{
    		int j,k;
    		// select first edge 
    		j = irandom(n-1);
    		// select second edge
    		do k = irandom(n-1);
    		while ((k == j) || (k == j-1) || (j == k-1) || (j==0 && k==n) || (k==0 && j==n));

			double f = s_2opt(res,j,k,best);
			best = f;
  		}
  		return res;
	}

	
// single 2 opt attempt

	/**
 * S_2opt.
 * 
 * @param sol the sol
 * @param node1 the node1
 * @param node2 the node2
 * @param cost the cost
 * 
 * @return the double
 */
public double s_2opt(int[] sol, int node1, int node2, double cost)
	{
		int n = sol.length;
 		if (node1==node2) return cost;

 		if (node1>node2) 
 		{
  		int aux = node1;
  		node1 = node2;
  		node2 = aux;
 		}

 		double f = cost - get_distance(sol[node1],sol[node1+1])
 		- get_distance(sol[node2],sol[(node2+1)%n])
 		+ get_distance(sol[node1+1],sol[(node2+1)%n])
 		+ get_distance(sol[node1],sol[node2]);

  		if (cost-f > 0.00001*cost)
  		{
   			// reversing the sequence from position j to position k
   			for (int l = 0; l<(node2-node1)/2; l++)
   			{
				int aux = sol[node1+l+1];
    			sol[node1+l+1] = sol[node2-l];
				sol[node2-l] = aux;
   			}
  		 return f; 
  		}
  		else return cost;
	}



// disturbance functions - for dynamic problems


	/**
 * Disturbance.
 * 
 * @param pert the pert
 */
public void disturbance (double pert)
	{
 		for (int k=0; k < n; k++)
 			for(int j=0; j<k; j++)
			{
				double d = get_distance(k,j);
				double f = (Math.random()*2)-1;
				set_distance(k, j, d + f*pert); 
			}
	}

	/**
	 * Disturbance.
	 * 
	 * @param pert the pert
	 * @param nweights the nweights
	 */
	public void disturbance(double pert, int nweights)
	{
 		for (int k=0; k < nweights; k++)
 		{
			int m1 = irandom(n-1);
			int m2;
			do m2 = irandom(n-1);
			while (m1==m2);
			double d = get_distance(m1,m2);
			double f = (Math.random()*2)-1;
			set_distance(m1, m2, d + f*pert); 
 		}
	}

	/**
	 * Bounded_disturbance.
	 * 
	 * @param pert the pert
	 * @param minx the minx
	 * @param maxx the maxx
	 * @param miny the miny
	 * @param maxy the maxy
	 * @param nnodes the nnodes
	 */
	public void bounded_disturbance(double pert, double minx, double maxx, double miny,
	double maxy, int nnodes)
	{
	}


	// I/O

	/**
	 * Read.
	 * 
	 * @param B the b
	 * 
	 * @throws Exception the exception
	 */
	public abstract void read (BufferedReader B) throws Exception;

	/**
	 * Write.
	 * 
	 * @param B the b
	 * 
	 * @throws Exception the exception
	 */
	public abstract void write (BufferedWriter B) throws Exception;

	/**
	 * Save.
	 * 
	 * @param filename the filename
	 * 
	 * @throws Exception the exception
	 */
	public void save (String filename) throws Exception
	{
		FileWriter f = new FileWriter(filename);
		BufferedWriter b = new BufferedWriter(f);
		write(b);
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
		b.flush();
	}

	/**
	 * Checks if is _in.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * 
	 * @return true, if is _in
	 */
	public static boolean is_in (int [] array, int elem)
	{
 		int i=0;
		boolean found=false;

		if(array!=null) 
		{
			while (i<array.length && !found )
 			{
   				if(array[i]== elem) found = true;
   				else i++;
 			} 
 		}
		return found;
	}

	/**
	 * Checks if is _in.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * @param endpos the endpos
	 * 
	 * @return true, if is _in
	 */
	public static boolean is_in (int [] array, int elem, int endpos)
	{
 		int i=0;
		boolean found=false;

		if(array!=null) 
		{
			while (i<endpos && !found )
 			{
   				if(array[i]== elem) found = true;
   				else i++;
 			} 
 		}
		return found;
	}

	/**
	 * Checks if is _in.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * @param stpos the stpos
	 * @param endpos the endpos
	 * 
	 * @return true, if is _in
	 */
	public static boolean is_in (int [] array, int elem, int stpos, int endpos)
	{
 		int i=stpos;
		boolean found=false;

		if(array!=null) 
		{
			while (i<endpos && !found )
 			{
   				if(array[i]== elem) found = true;
   				else i++;
 			} 
 		}
		return found;
	}
	
  
		/**
	 * Copy_arr.
	 * 
	 * @param a1 the a1
	 * @param a2 the a2
	 */
	public static void copy_arr(int [] a1, int [] a2)
	{
		for(int i=0; i< a1.length; i++)
			a2[i] = a1[i];
	}
	
	/**
	 * Give_rand_perm.
	 * 
	 * @param k the k
	 * 
	 * @return the int[]
	 */
	public static int [] give_rand_perm (int k)
	{
  		int i, j, l;
  		int[] res = new int[k];
  		int[] aux = new int[k];

  		for(i=0;i<k;i++)
      		aux[i]=i;
  		for(j=k-1,i=0;j>=0;j--,i++)
  		{
    		int r=irandom(j); /* dar um numero entre 0 e j */ 
    		res[i]=aux[r];
    		for(l=r;l<j;l++)
         		aux[l]=aux[l+1];
  		}
 		return(res);
	}
		
	  /**
	   * Irandom.
	   * 
	   * @param a the a
	   * @param b the b
	   * 
	   * @return the int
	   */
	  public static int irandom(int a, int b) 
		// Random integer within range {a,...,b}    
		 { 
		 return (int)((b+1-a)*Math.random())+a;
		 }
	  
	  /**
	   * Irandom.
	   * 
	   * @param b the b
	   * 
	   * @return the int
	   */
	  public static int irandom(int b) 
		// Random integer within range {0,...,b}    
		 { return (int)((b+1)*Math.random());}
		
	// tests

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main (String [] args)
	{

		try{
		Tsp p = new SymTsp(args[0]);
		int maxtime = Integer.parseInt(args[1]);
		int numruns = 30;
		int[] s; 
		int[] best;

		double sum_sqxi = 0.0, sum_xi = 0.0;

		for(int j=0; j<numruns; j++)
		{
			GregorianCalendar Cal=new GregorianCalendar();
		    Date D=Cal.getTime();
   			int ini_time = (int)(D.getTime()/1000); 

    		best = p.complete_2opt(); 
			double lowcost = p.cost(best);
			Cal=new GregorianCalendar();
		    D=Cal.getTime();
			int t = (int)(D.getTime()/1000) - ini_time;
 			while (t < maxtime)	
			{
    			s = p.complete_2opt(); 
				double f1 = p.cost(s);	
				if(lowcost - f1 > 0.00001*lowcost) 
				{
					lowcost = f1;
					best = s;
				}
				Cal=new GregorianCalendar();
		    	D=Cal.getTime();
				t = (int)(D.getTime()/1000) - ini_time;
			}
			System.out.println("Melhor solucao do run " + j + " : " + lowcost);
			sum_xi += lowcost;
			sum_sqxi += (lowcost*lowcost);
		}
		double variance = (sum_sqxi - sum_xi*sum_xi/numruns)/(numruns-1);
		double stdev = Math.sqrt(variance);
		System.out.println(sum_xi/numruns + " " + 1.96 * stdev/Math.sqrt(numruns) ); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
