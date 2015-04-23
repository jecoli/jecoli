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
package pt.uminho.ceb.biosystems.jecoli.demos.motifs;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcuraMotifs.
 */
public class ProcuraMotifs {


	/** The sm. */
	SeqMotifs sm;
	
	/**
	 * Instantiates a new procura motifs.
	 * 
	 * @param sm the sm
	 */
	public ProcuraMotifs(SeqMotifs sm)
	{
		this.sm = sm;
	}
	
	
	/**
	 * Next solution.
	 * 
	 * @param s the s
	 * 
	 * @return the int[]
	 */
	public int[] nextSolution (int[] s)
	{
		int [] nextS = new int[s.length];
		
		int pos = s.length - 1;
		
		while(pos >=0 && s[pos]== sm.getSeqSize()-sm.getMotifSize() )
			pos--;
		if (pos < 0) nextS = null;
		else
		{
			for(int i=0; i < pos; i++) nextS[i] = s[i];
			nextS[pos] = s[pos]+1;
			for(int i = pos+1; i < s.length; i++) nextS[i] = 0;
		}
		return nextS;
	}
	
	/**
	 * Next vert.
	 * 
	 * @param s the s
	 * 
	 * @return the int[]
	 */
	public int[] nextVert (int[] s)
	{
		int [] res = null;
		
		 if (s.length < sm.getNumSequences()) 
		  {
		    res = new int[s.length+1];
		    for(int i=0; i < s.length; i++) res[i] = s[i];
		    res[s.length] = 0;
		  }
		  else // folha 
		  { 
		   int pos = s.length -1;
		   
		   while (pos >=0 && s[pos] == sm.getSeqSize()-sm.getMotifSize()) 
			   pos--;
		  
		   if (pos < 0) res = null; // ultima solucao
		   else
		   {
		      res = new int[pos+1];
		      for (int i=0; i <pos; i++) res[i] = s[i];
		      res[pos] = s[pos]+1;
		   }
		  }
		return res;
	}
	
	
	/**
	 * Bypass.
	 * 
	 * @param s the s
	 * 
	 * @return the int[]
	 */
	public int[] bypass (int[] s)
	{
		int[] res = null;
		int pos = s.length -1;
		
		while (pos >=0 && s[pos] == sm.getSeqSize()-sm.getMotifSize()) 
			   pos--;

		if (pos < 0) res = null; // ultima solucao
		else
		{
			res = new int[pos+1];
			for (int i=0; i <pos; i++) res[i] = s[i];
			res[pos] = s[pos]+1;
		}
		return res;
	}
	
	/**
	 * Exhaustive search.
	 * 
	 * @return the int[]
	 */
	public int [] exhaustiveSearch ()
	{
		int melhorScore = -1;
		int [] res = null;
		int [] s = new int[sm.getNumSequences()];
		for(int i=0; i < s.length; i++) s[i] = 0;
		while (s!= null)
		{
			int sc = sm.score(s);
			if (sc > melhorScore)
			{
				melhorScore = sc;
				res = s;
			}
			s = this.nextSolution(s);
		}
		return res;
	}
	
	// BRANCH & BOUND 
	
	/**
	 * Branch and bound.
	 * 
	 * @return the int[]
	 */
	public int [] branchAndBound ()
	{
		int melhorScore = -1;
		int [] melhorMotif = null;
		int size = sm.getNumSequences();
		int[]  s = new int[sm.getNumSequences()];
		
		for (int i= 0; i < size; i++) s[i] = 0;

		while (s != null) {
		    if (s.length < size)
		    {
		      int optimScore = sm.score(s) + (size-s.length)*sm.getMotifSize();
		      if (optimScore < melhorScore) s = bypass(s);
		      else s = nextVert(s);
		    }
		    else
		    {
		    	int sc = sm.score(s);
		    	if (sc > melhorScore) {
		    		melhorScore = sc;
		    		melhorMotif  = s;
		    	}
		    	s = nextVert (s);
		    }
		  }
		  return melhorMotif;
	}
	
	// GREEDY
	
	/**
	 * Greedy consensus.
	 * 
	 * @return the int[]
	 */
	public int[] greedyConsensus ()
	{
		int[] res = new int[sm.getNumSequences()]; 
	
		// first 2 seqs
		int maxScore = -1;
		int [] partial = new int[2];
		for(int i=0; i <= sm.getSeqSize()-sm.getMotifSize(); i++)
			for(int j=0; j <= sm.getSeqSize()-sm.getMotifSize(); j++)
			{
				partial[0] = i;
				partial[1] = j;
				int sc = sm.score(partial);
				if(sc > maxScore) {
					maxScore = sc;
					res[0] = i; 
					res[1] = j;
				}
			}
		// the rest
		for(int k=2; k < sm.getNumSequences(); k++)
		{
			partial = new int[k+1];
			for(int j=0; j < k; j++) partial[j] = res[j];
			maxScore = -1;
			for(int i=0; i <= sm.getSeqSize()-sm.getMotifSize(); i++)
			{
				partial[k] = i;
				int sc = sm.score(partial);
				if(sc > maxScore) {
					maxScore = sc;
					res[k] = i; 
				}
			}
		}
		return res;
	}
	
	// STOCHASTIC
	
	 // random
	/**
	 * Random search.
	 * 
	 * @param numt the numt
	 * 
	 * @return the int[]
	 */
	public int[] randomSearch (int numt)
	{
		int bestScore = -1;
		int [] res = null;
		int [] s = new int[sm.getNumSequences()];
		
		for (int i=0; i < numt; i++)
		{
			for(int k=0; k < s.length; k++)
				s[k] = (int)(Math.random()*(sm.getSeqSize()-sm.getMotifSize()+1));
			int sc = sm.score(s);
			if (sc > bestScore)
			{
				bestScore = sc;
				res = s;
			}
		}
		
		return res;
	}
	
	
	 // evolutionary alg - very simple version ... 
	/**
 	 * Mutation sol.
 	 * 
 	 * @param s the s
 	 * 
 	 * @return the int[]
 	 */
 	public int[] mutationSol (int[] s)
	{
		int [] ns = new int[s.length];
		
		int pos = (int)(Math.random()*s.length);
		
		for(int i=0; i < s.length; i++)
			if (i==pos) ns[i] = (int)(Math.random()*(sm.getSeqSize()-sm.getMotifSize()+1));
			else ns[i] = s[i];
		
		return ns;
	}
	
	/**
	 * Evol alg motifs.
	 * 
	 * @param numg the numg
	 * 
	 * @return the int[]
	 */
	public int[] evolAlgMotifs (int numg)
	{
		int bestScore = -1;
		int [] res = null;
		int [] s = new int[sm.getNumSequences()];
		for(int k=0; k < s.length; k++)
			s[k] = (int)(Math.random()*(sm.getSeqSize()-sm.getMotifSize()+1));
		
		for (int i=0; i < numg; i++)
		{
			int sc = sm.score(s);
			if (sc > bestScore)
			{
				bestScore = sc;
				res = s;
			}
			s = mutationSol(s);
		}	
		return res;
	}
	
	/**
	 * Prints the sol.
	 * 
	 * @param sol the sol
	 */
	public void printSol (int [] sol)
	{
		for(int i=0; i < sol.length; i++)
			System.out.print(sol[i]+" ");
		System.out.println("");
	}
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			
			String symb = "actg";
			String filename = "exemploMotifs.txt";
			SeqMotifs sm = new SeqMotifs(filename, symb, 8);
			ProcuraMotifs pm = new ProcuraMotifs(sm);
			//int[] s = {60,60,60,60,60};
			
			//int[] sol = pm.exhaustiveSearch();
			//int[] sol = pm.randomSearch(1000);
			//int[] sol = pm.evolAlgMotifs(10000);
			int[] sol = pm.branchAndBound();
			//int [] sol = pm.greedyConsensus();
			pm.printSol(sol);
			System.out.println("Score: "+ sm.score(sol));
			String cons = sm.consensus(sol);
			System.out.println("Melhor sol:" + cons);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
