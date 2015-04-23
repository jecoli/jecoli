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
package pt.uminho.ceb.biosystems.jecoli.demos.knapsacking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class Knapsacking.
 */
public class Knapsacking {

	/** The Constant UNCORRELATED. */
	public static final int UNCORRELATED = 1;
	
	/** The Constant WEAKLY. */
	public static final int WEAKLY = 2;
	
	/** The Constant STRONGLY. */
	public static final int STRONGLY = 3;

	/** The Constant RESTRICTIVE. */
	public static final int RESTRICTIVE = 1;
	
	/** The Constant AVERAGE. */
	public static final int AVERAGE = 2;


	/** The n. */
	int n; // number of items of the problem
	
	/** The weights. */
	double [] weights; // weights of the items
	
	/** The profits. */
	double [] profits; // profits of the items
	
	/** The capacity. */
	double capacity; // capacity of the container

  
	/**
	 * Instantiates a new knapsacking.
	 * 
	 * @param itens the itens
	 */
	public Knapsacking(int itens)
	{
 		n = itens;
 		weights = new double[n];
 		profits = new double[n];
	}
	
	public Knapsacking(Knapsacking originalProblem){
		this.n = originalProblem.n;
		this.weights = Arrays.copyOf(originalProblem.weights,originalProblem.weights.length);
		this.profits = Arrays.copyOf(originalProblem.profits,originalProblem.profits.length);
	}
	
	public static double frandom(double a, double b) 
	// Random real within range [a,b[
        {return (b-a)*Math.random()+a;}

/**
 * * Builds a new knapsacking instance randomly.
 * 
 * @param items number of objects in the knapsack
 * @param correl defines the correlation between weights and profits
 * @param restriction defines the restrictiveness of the knapsack
 * @param lim_w limit of the weights interval being generated
 * @param lim_p used to generate profit from weight according to method
 */
	public Knapsacking(int items, int correl, int restriction, double lim_w, double lim_p)
	{
		this(items);
 		switch (correl)
 		{
			case UNCORRELATED:
				for (int k=0; k<n; k++)
				{
					weights[k]= frandom(0.0,lim_w);
					profits[k]= frandom(0.0,lim_p);
				}
				break;
			case WEAKLY:
				for (int k=0; k<n; k++)
				{
					weights[k]= frandom(0.0, lim_w);
					profits[k]= weights[k]+ frandom(0.0,lim_p);
				}
				break;
			case STRONGLY:
				for (int k=0; k<n; k++)
				{
					weights[k]= frandom(0.0,lim_w);
					profits[k]= weights[k]+lim_p;
				}
				break;
 		}

		switch (restriction)
 		{
			case RESTRICTIVE:
				capacity = 2* lim_w;
				break;
			case AVERAGE:
				capacity = 0.25 * items * lim_w;
				break;
 		}

	}

	/**
	 * Instantiates a new knapsacking.
	 * 
	 * @param filename the filename
	 * 
	 * @throws Exception the exception
	 */
	public Knapsacking(String filename) throws Exception
	{
		load(filename);
 	}

	// get / set methods

	/**
	 * Gets the size.
	 * 
	 * @return the size
	 */
	public int getSize()
	{
 		return n;
	}

	/**
	 * Sets the size.
	 * 
	 * @param its the new size
	 */
	public void setSize (int its)
	{
 		n = its; 
	}

	/**
	 * Gets the weight.
	 * 
	 * @param it the it
	 * 
	 * @return the weight
	 */
	public double getWeight (int it)
	{
 		return weights[it];
	}

	/**
	 * Sets the weight.
	 * 
	 * @param it the it
	 * @param w the w
	 */
	public void setWeight (int it, double w)
	{
 		weights[it] = w;
	}

	/**
	 * Gets the profit.
	 * 
	 * @param it the it
	 * 
	 * @return the profit
	 */
	public double getProfit (int it)
	{
 		return profits[it];
	}

	/**
	 * Sets the profit.
	 * 
	 * @param it the it
	 * @param w the w
	 */
	public void setProfit (int it, double w)
	{
 		profits[it] = w;
	}

	/**
	 * Gets the capacity.
	 * 
	 * @return the capacity
	 */
	public double getCapacity()
	{
 		return capacity;
	}

	/**
	 * Sets the capacity.
	 * 
	 * @param c the new capacity
	 */
	public void setCapacity (double c)
	{
 		capacity = c;
	}

	// IO

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
	 * Write.
	 * 
	 * @param W the w
	 * 
	 * @throws Exception the exception
	 */
	public void write (BufferedWriter W) throws Exception
	{
		W.write(getSize() + "\n");
		W.write(getCapacity() + "\n");

 		for (int i = 0; i < getSize(); i++)
 		{
			W.write(getWeight (i) + " "); 
 			W.write(getProfit (i) + "\n"); 
 		}
		W.flush();
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
 		String str = B.readLine();

		setSize(Integer.parseInt(str));
 		
		str = B.readLine();
		setCapacity(Double.valueOf(str).doubleValue());
 
 		weights = new double[n];
 		profits = new double[n];

		for (int i = 0; i < getSize(); i++)
 		{
			str = B.readLine();
			StringTokenizer st = new StringTokenizer(str);
			setWeight(i, Double.valueOf(st.nextToken()).doubleValue());
			setProfit(i, Double.valueOf(st.nextToken()).doubleValue());
		}
	}


	/**
	 * Prints the solution.
	 * 
	 * @param solution the solution
	 */
	public void printSolution (boolean[] solution)
	{
		System.out.println("Items in solution:");
		for(int i=0; i < solution.length; i++)
			if (solution[i]) System.out.print(i+" ");
		System.out.println("");
		if (this.totalWeight(solution) < this.getCapacity())
			System.out.println("Valid solution");
		else System.out.println("Invalid solution");
		System.out.println("Total weight: " + this.totalWeight(solution));
		System.out.println("Total profit: " + this.totalProfit(solution));
	}
	
// methods to implement the algorithms
// Michalewicz, 4.5

// sorts items by decreasing profits 

	/**
 * Sort items.
 * 
 * @return the int[]
 */
int [] sortItems ()
	{
		int [] s = new int[n];

		for (int i = 0; i < getSize(); i++)
 		{
  			int j = 0 ;
  			while (j<i && getProfit(s[j])>=getProfit(i))
   			j++;
  			int k = i;
  			while (k>j)
  			{
   			s[k] = s[k-1];
   			k--;
  			}
  			s[j] = i;
 		}
 		return s;
	}

// builds a solution to the knapsack using an order of the items
// an item in the knapsack gets true in the correspondent positiom
// otherwise is false

	/**
 * Order knap.
 * 
 * @param ord the ord
 * 
 * @return the boolean[]
 */
boolean[] orderKnap (int[] ord)
	{
		boolean [] sol = new boolean[n];
		double ac= 0.0;

		for(int i= 0; i < getSize(); i++)
		{
			int item = ord[i];
			if (ac + getWeight(item) <= getCapacity() )
  			{
    			ac += getWeight(item);
    			sol[item] = true; 
  			}
  			else
    			sol[item] = false;
 		}
 		return sol;
	}

// similar to previous from an ordinal representation

	/**
 * Ordinal knap.
 * 
 * @param order the order
 * @param rep the rep
 * 
 * @return the boolean[]
 */
public boolean[] ordinalKnap (int[] rep)
{
	boolean[] sol = new boolean[n];
	double ac= 0.0;
	ArrayList<Integer> order = new ArrayList<Integer>();
	
	for (int i=0; i < n; i++) order.add(i, i);
	
	for(int i= 0; i < getSize(); i++)
	{
		int index = rep[i];
		int item = order.get(index);
		order.remove(index);
		if (ac + getWeight(item) <= getCapacity() ) {
			ac += getWeight(item);
			sol[item] = true; 
		}
		else sol[item] = false;
	}
	return sol;
}

// creates a random order and the correspondent knapsacking

	/**
 * Random knap.
 * 
 * @return the boolean[]
 */
public boolean[] randomKnap ()
	{
  		int[] randPerm = give_rand_perm(n);
  		return orderKnap(randPerm);
	}


public static int [] give_rand_perm (int k)
{
		int i, j, l;
		int[] res = new int[k];
		int[] aux = new int[k];

		for(i=0;i<k;i++)
  		aux[i]=i;
		for(j=k-1,i=0;j>=0;j--,i++)
		{
		int r=irandom(j);  
		res[i]=aux[r];
		for(l=r;l<j;l++)
     		aux[l]=aux[l+1];
		}
		return(res);
}

public static int irandom(int b) 
// Random integer within range {0,...,b}    
 { return (int)((b+1)*Math.random());}

// uses a decreasing order of profits as input to the previous method

	/**
 * Greedy knap.
 * 
 * @return the boolean[]
 */
public boolean[] greedyKnap ()
	{
 		return orderKnap(sortItems() );
	}

// computes the total profit of the knapsack
// solution is binary (0 not present 1 present)
	/**
 * Total profit.
 * 
 * @param sol the sol
 * 
 * @return the double
 */
public double totalProfit(int[] sol)
	{
 		double s = 0;

 		for (int i = 0; i< n; i++)
  			s += sol[i]* getProfit(i);

 		return s;
	}

// solution is boolean (false not present true present)
	/**
 * Total profit.
 * 
 * @param sol the sol
 * 
 * @return the double
 */
public double totalProfit(boolean[] sol)
	{
 		double s = 0;

 		for (int i = 0; i< n; i++)
			if (sol[i]) s += getProfit(i);

 		return s;
	}

// computes the total weight of the knapsack
// solution is binary (0 not present 1 present)
	/**
 * Total weight.
 * 
 * @param sol the sol
 * 
 * @return the double
 */
public double totalWeight(int[] sol)
	{
 		double s = 0;

 		for (int i = 0; i< n; i++)
  			s += sol[i]* getWeight(i);

 		return s;
	}

// solution is boolean (false not present true present)
	/**
 * Total weight.
 * 
 * @param sol the sol
 * 
 * @return the double
 */
public double totalWeight(boolean[] sol)
	{
 		double s = 0;

 		for (int i = 0; i< n; i++)
  			if (sol[i]) s += getWeight(i);

 		return s;
	}
	

}
