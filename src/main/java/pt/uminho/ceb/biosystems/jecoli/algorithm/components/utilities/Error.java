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

package pt.uminho.ceb.biosystems.jecoli.algorithm.components.utilities;


// TODO: Auto-generated Javadoc
/**
 * This is a simple class that deals with error metrics.
 * The error metric is allways defined by the error type. Some error types
 * require the use of a hit tolerance.
 * 
 * @author Paulo Cortez
 * Last updated 9, October, 2001.
 */

public class Error implements java.io.Serializable
{
 // error types
    /** Median Absolute Error. */
    public static final int MAD=0;
    
    /** Sum Square Error. */
    public static final int SSE=1; 
    
    /** Mean Square Error. */ 
    public static final int MSE=2; 
    
    /** Root Mean Square Error. */ 
    public static final int RMSE=3; 
	
	/** Number of correct classifications (assumes binary classes or m-class representation *. */
    public static final int NCLASSES = 4;
	
	/** sum of absolute deviations. */
    public static final int SAD = 5;

    /** The Error type. */
    protected int e;  
    
    /** The hit tolerance. */
    protected double a;

    /**
     * Default Constructor.
     */
    public Error()
	{ e=RMSE;}
    
    /**
     * Constructor using error type.
     * 
     * @param ErrorType one of the following: MAD, SSE, MSE or RMSE
     */
    public Error(int ErrorType)
	{ e=ErrorType;}
    
    /**
     * Constructor using error type and tolerance k.
     * 
     * @param ErrorType one of the following: MAD, SSE, MSE or RMSE
     * @param k a hit tolerance, used by some error types.
     */
    public Error(int ErrorType,double k)
	{ e=ErrorType; a=k; }

    /**
     * Returns the error type.
     * 
     * @return the error type
     */
    public int type()
	{ return e;}
    
    /**
     * Returns the hit tolerance.
     * 
     * @return the hit tolerance
     */
    public double k()
	{ return a;}

    /**
     * True if Error has a parameter.
     * 
     * @return true if the error metric uses a hit tolerance
     */
    public boolean parameter()
	{ 
          boolean res=false;
 	  switch(e)
		{
		 case MAD: res=false; break;
		 case SAD: res=false; break;
		 case SSE: res=false; break;
		 case MSE: res=false; break;
		 case RMSE: res=false; break;
		 case NCLASSES: res=false; break;
		}
	  return res;
        }

    /**
     * Convert error type to String.
     * 
     * @return the string that corresponds to the Error metric
     */
     public String toString()
	{ 
          String res="";
 	  switch(e)
		{
		 case MAD: res="MAD"; break;
		 case SAD: res="SAD"; break;
		 case SSE: res="SSE"; break;
		 case MSE: res="MSE"; break;
		 case RMSE: res="RMSE"; break;
		 case NCLASSES: res="NCLASSES"; break;
		}
	  return res;
	} 
    
    /**
     * Convert error type to String.
     * 
     * @return the string that corresponds to the error metric
     */
     public String nametype()
	{ 
          String res="";
 	  switch(e)
		{
		 case MAD: res="MAD"; break;
		 case SAD: res="SAD"; break;
		 case SSE: res="SSE"; break;
		 case MSE: res="MSE"; break;
		 case RMSE: res="RMSE"; break;
		 case NCLASSES: res="NCLASSES"; break;
		}
	  return res;
	} 
     
     /**
      * Set the error values.
      * 
      * @param ErrorType one of the following: MAD, SSE, MSE or RMSE
      * @param k a hit tolerance, used by some error types
      */
     public void set(int ErrorType, double k)
	{ e=ErrorType; a=k;}

     /**
      * True if a is better than b, according to the error type.
      * 
      * @param a first error value
      * @param b second error value
      * 
      * @return true, if best
      */
     public boolean best(double a, double b)
        {
          if(e<NCLASSES) return a<b;
		  else return a>b;
        }

     /**
      * Get the worst possible value, according to the error type.
      * 
      * @return the worst possible value
      */
     public double worst()
        {
          double res=0.0;
          switch (e)
          { 
           case MAD:
           case SAD:
           case SSE:
           case MSE:
           case RMSE: 
                 res=Double.MAX_VALUE;
                 break;
		   case NCLASSES: res=0; break;
          }
          return res;
        }

     /**
      * Compute the total accumulate error value.
      * 
      * @param target the target array
      * @param output the output array
      * @param sum the accumulate sum
      * 
      * @return the total accumulate error value
      */
     public double total(double []target, double[]output,double sum)
	{
          double res=sum;
          switch (e)
	   {
   	    case SAD: res=res+MatUtils.sad(target,output);
		      break;
   	    case MAD: res=res+MatUtils.sad(target,output)/
                              ((double)output.length);
		      break;
            case SSE: res=res+MatUtils.sse(target,output);
		       break;
            case MSE:
            case RMSE: res=res+MatUtils.sse(target,output)/
                               ((double)output.length);
		       break;
			case NCLASSES: res=res+MatUtils.nclasses(target,output); 
			// only works for binary or m-class representations
           }
           return res;
        }

     /**
      * Compute the final error value.
      * 
      * @param p1 the accumulated error value, has computed by total
      * @param p2 depends on the error type. if MAD, SSE, MSE or RMSE then is equal to N, the number of samples
      * 
      * @return the final error value
      */
        public double e(double p1, double p2)
        {
          double res=0.0;
          switch (e)
	   {
   	    case MAD: res=p1/p2;
		      break;
	    case SAD:
            case SSE: res=p1;
		      break;
            case MSE: res=p1/p2;
                      break;
            case RMSE: res=Math.sqrt(p1/p2);
                       break;
            case NCLASSES: res=p1/p2; break;
           }
           return res;
        }

}
