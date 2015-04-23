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

package pt.uminho.ceb.biosystems.jecoli.demos.numericalopt;

// TODO: Auto-generated Javadoc
/**
 * Defines a set of benchmark functions for numerical optimization.
 */

class Functions{

	/** The Constant LINEAR_QUADRATIC. */
	public static final int LINEAR_QUADRATIC = 1; // Michalewicz p.109
	
	/** The Constant HARVEST. */
	public static final int HARVEST = 2; // idem
	
	/** The Constant PUSH_CART. */
	public static final int PUSH_CART = 3;	// Mic. - p.110
	
	/** The Constant SPHERE. */
	public static final int SPHERE = 4;	// Yao & Liu - paper FES (1997)- f1
	
	/** The Constant YAO_F2. */
	public static final int YAO_F2 = 5;	// Yao & Liu - paper FES (1997)- f2 -unimodal set; schewfel 2.22
	
	/** The Constant YAO_F3. */
	public static final int YAO_F3 = 6;	// Yao & Liu - paper FES (1997)- f3 -unimodal set; schewfel 1.2
	
	/** The Constant MAX_ABS. */
	public static final int MAX_ABS = 7;	// Yao & Liu - paper FES (1997) - f4
	
	/** The Constant ROSENBROCK. */
	public static final int ROSENBROCK = 8;	// Yao & Liu - paper FES (1997) - f5
	
	/** The Constant YAO_F8. */
	public static final int YAO_F8 = 11;	// Yao & Liu - paper FES (1997) - f8 - multimodal set
	
	/** The Constant RASTRIGIN. */
	public static final int RASTRIGIN = 12;	// Yao & Liu - paper FES (1997) - f9 - multimodal set
	
	/** The Constant ACKLEY. */
	public static final int ACKLEY = 13;	// Yao & Liu - paper FES (1997) - f10 - multimodal set
	
	/** The Constant GRIEWANK. */
	public static final int GRIEWANK = 14;	// Yao & Liu - paper FES (1997) - f11 - multimodal set
	
	/** The Constant YAO_F18. */
	public static final int YAO_F18 = 18;	// Yao & Liu - paper FES (1997) - f18 - multimodal set

/**
 * Returns the value a function given:.
 * 
 * @param f function - one of the above constants
 * @param x array of inputs
 * @param nvar number of variables
 * @param pars array of parameters
 * 
 * @return the double
 */
	static double function (int f, double[] x, int nvar, double[] pars)
	{
		double r=0.0;
		switch (f)
		{
			case LINEAR_QUADRATIC:
				r = linquad(x, nvar, pars);
				break;
			case HARVEST:
				r = harvest(x, nvar, pars);
				break;
			case PUSH_CART:
				r = pushcart(x, nvar);
				break;
			case SPHERE:
				r = sphere(x, nvar);
				break;
			case YAO_F2:
				r = yaof2(x, nvar);
				break;
			case YAO_F3:
				r = yaof3(x, nvar);
				break;
			case MAX_ABS:
				r = maxabs(x, nvar);
				break;
			case ROSENBROCK:
				r = rosenbrock(x, nvar);
				break;
			case YAO_F8:
				r = yaof8(x, nvar);
				break;
			case RASTRIGIN:
				r = rastrigin(x, nvar);
				break;
			case ACKLEY:
				r = ackley(x, nvar);
				break;
			case GRIEWANK:
				r = griewank(x, nvar);
				break;
			case YAO_F18:
				r = yaof18(x);
				break;
		}
		return r;
	}

	/**
	 * Linquad.
	 * 
	 * @param u the u
	 * @param N the n
	 * @param pars the pars
	 * 
	 * @return the double
	 */
	static double linquad(double[] u, int N, double[] pars) // Michalewski - p. 109
	// pars[0]=x0; pars[1]=s; pars[2]=r; pars[3]=q; pars[4]=a; pars[5]=b
	{
		double [] x = new double[N+1];
		double sumsq = 0.0;

		x[0] = pars[0];

		for (int i=0; i<N; i++)
		{
			x[i+1] = pars[4]*x[i]+ pars[5]*u[i]; 
			sumsq += (pars[1]*x[i]*x[i]+ pars[2]*u[i]*u[i]);	
		}
		return pars[3]*x[N]*x[N] + sumsq;
	}

	/**
	 * Harvest.
	 * 
	 * @param u the u
	 * @param s the s
	 * @param pars the pars
	 * 
	 * @return the double
	 */
	static double harvest(double[] u, int s, double [] pars) // Michalewski - p. 110
	// pars[0]=x0 pars[1]=a
	{
		// lets assume that last u is calculated to keep constraint
		// real N for the problem is s+1 
		double [] x = new double[s+1];
		double sum = 0.0;
		x[0] = pars[0];
		for(int i=0; i<s; i++)
		{
			x[i+1] = pars[1]*x[i] - u[i];
			//System.out.println("x i+1 = " + x[i+1]);
			sum += Math.sqrt(u[i]);
		}
		double uk = pars[1]* x[s] - x[0];
		//System.out.println("uk = " + uk);
		if(uk <= 0.0) return 0.0;
		else return ( sum + Math.sqrt(uk) )*(-1.0);
	}

	/**
	 * Pushcart.
	 * 
	 * @param u the u
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double pushcart(double [] u, int N) // Michalewski - p.110
	{
		double [] x1 = new double[N+1];
		double [] x2 = new double[N+1];

		x1[0] = 0.0;
		x2[0] = 0.0;
		double sum = 0.0;
		for(int i=0; i<N; i++)
		{
			x1[i+1] = x2[i];
			x2[i+1] = 2*x2[i] - x1[i] + u[i]/(double)(N*N);
			sum += u[i]*u[i];
		}
		
		return (-1.0)* (x1[N] - ( 1.0/(double)(2*N)* sum)) ;
	}

	/**
	 * Sphere.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double sphere(double[] x, int N)
	{
		double sum = 0.0;
		for(int i=0; i<N; i++) sum += x[i]*x[i];
		return sum; 
	}

	/**
	 * Yaof2.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double yaof2(double[] x, int N)
	{
		double sum = 0.0;
		double p = 1.0;
		for(int i=0; i<N; i++) sum += Math.abs(x[i]);
		for(int i=0; i<N; i++) p *= Math.abs(x[i]);
		return sum + p; 
	}

	/**
	 * Yaof3.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double yaof3(double[] x, int N)
	{
		double sum = 0.0;
		for(int i=0; i<N; i++) 
		{
			double isum = 0.0;
			for(int j=0; j<=i; j++) isum += x[j];
			sum += (isum*isum);
		}
		return sum; 
	}

	/**
	 * Maxabs.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double maxabs(double[] x, int N)
	{
		double max = Math.abs(x[0]);
		for(int i=1; i<N; i++) 
			if( Math.abs(x[i]) > max ) max = Math.abs(x[i]);
		return max; 
	}

	/**
	 * Rosenbrock.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double rosenbrock(double[] x, int N)
	{
		double sum = 0.0;
		for(int i=0; i<N-1; i++)
		{
			sum += 100* Math.pow( (x[i+1]-x[i]*x[i]), 2.0 );
			sum += ( (x[i] - 1.0)* (x[i] - 1.0) );
		} 
		return sum; 
	}

	/**
	 * Yaof8.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double yaof8(double[] x, int N)
	{
		double sum = 0.0;
		for(int i=0; i<N; i++) 
			sum += ( -x[i]*Math.sin( Math.sqrt(Math.abs(x[i])) ) );
		return sum; 
	}

	/**
	 * Rastrigin.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double rastrigin(double[] x, int N)
	{
		double sum = 0.0;
		for(int i=0; i<N; i++) 
			sum += ( (x[i]*x[i]) - 10.0*Math.cos(2*Math.PI*x[i]) + 10.0 ) ;
		return sum; 
	}

	/**
	 * Ackley.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double ackley(double[] x, int N)
	{
		double sumsq = 0.0, sumcos = 0.0;
		double r;
		for(int i=0; i<N; i++) sumsq += (x[i]*x[i]);
		for(int i=0; i<N; i++) sumcos += ( Math.cos(2*Math.PI*x[i]) );
		r = -20.0*Math.exp(-0.2*Math.sqrt( (1/(double)N)*sumsq ) );
		r -= Math.exp( (1/(double)N)* sumcos);
		r += 20 + Math.E;
		return r; 
	}

	/**
	 * Griewank.
	 * 
	 * @param x the x
	 * @param N the n
	 * 
	 * @return the double
	 */
	static double griewank(double[] x, int N)
	{
		double sumsq = 0.0, pcos = 1.0;
		double r;
		for(int i=0; i<N; i++) sumsq += (x[i]*x[i]);
		for(int i=0; i<N; i++) pcos *= ( Math.cos( x[i]/Math.sqrt(i+1) ) );
		r = 1.0/4000.0 * sumsq - pcos + 1.0;
		return r;
	}

	/**
	 * Yaof18.
	 * 
	 * @param x the x
	 * 
	 * @return the double
	 */
	static double yaof18(double[] x) // N = 2
	{
		double p1; 
		double p2; 
		p1 = 1.0 + (x[0]+x[1]+1.0)*(x[0]+x[1]+1.0)*(19.0-14*x[0]+3*x[0]*x[0]-14*x[1]+6*x[0]*x[1]+3*x[1]*x[1]);
		p2 = 30.0 + (2*x[0]-3*x[1])*(2*x[0]-3*x[1])*(18.0-32*x[0]+12*x[0]*x[0]+48*x[1]-36*x[0]*x[1]+27*x[1]*x[1]);
		return p1*p2;
	}
}


