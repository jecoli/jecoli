package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.permutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;


public class PermutationUtils {
	
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
	 * Pos_in_arr.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * 
	 * @return the int
	 */
	public static int pos_in_arr (int[] array, int elem)
	{
 		int i=0, res=-1;

 		while (i<array.length && res <0)
 		{
   			if(array[i]== elem) res = i;
   			else i++;
 		} 
		return res;
	}

	/**
	 * Pos_in_arr.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * 
	 * @return the int
	 */
	public static int pos_in_arr (String[] array, String elem)
	{
 		int i=0, res=-1;

 		while (i<array.length && res <0)
 		{
   			if(array[i].equals(elem)) res = i;
   			else i++;
 		} 
		return res;
	}

	
	/**
	 * Pos_in_arr.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * @param st the st
	 * @param end the end
	 * 
	 * @return the int
	 */
	public static int pos_in_arr (int[] array, int elem, int st, int end)
	{
 		int i=st, res=-1;

 		while (i<end && res <0)
 		{
   			if(array[i]== elem) res = i;
   			else i++;
 		} 
		return res;
	}
	
	/**
	 * Next_elem.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * 
	 * @return the int
	 */
	public static int next_elem (int [] array, int elem)
	{
  		int j =0;
		boolean found = false;

  		while (!found && (j<array.length) )
  		{
    		if(array[j] == elem) found = true;
    		j++;
  		}
  		if(found) return array[j%array.length];
  		else return -1;
	}

	
	/**
	 * Prev_elem.
	 * 
	 * @param array the array
	 * @param elem the elem
	 * 
	 * @return the int
	 */
	public static int prev_elem (int [] array, int elem)
	{
  		int j =0;
		boolean found = false;

  		while (!found && (j<array.length) )
  		{
    		if(array[j] == elem) found = true;
    		else j++;
 		}
  		if(found) return array[(j==0)?(array.length-1):(j-1)];
  		else return -1;
	}
	
	
	 /**
	 * Give_k_perm.
	 * 
	 * @param elems the elems
	 * @param k the k
	 * 
	 * @return the int[]
	 */
	protected static int [] give_k_perm (int [] elems, int k,IRandomNumberGenerator randomNumberGenerator)
	{
  		int i, j, l;
  		int [] res = new int[k];
  		int [] aux = new int[k]; 

		for(i=0;i<k;i++) aux[i]=elems[i];

		for(j=k-1,i=0;j>=0;j--,i++)
  		{
    		int r=(int) (randomNumberGenerator.nextDouble()*(j+1)); 
    		res[i]=aux[r];
   			for(l=r;l<j;l++)
        		 aux[l]=aux[l+1];
  		}

 		return(res);
	}


}
