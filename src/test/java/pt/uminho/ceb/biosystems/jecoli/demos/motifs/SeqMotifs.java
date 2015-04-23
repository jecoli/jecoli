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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
// class to handle the search of motifs in biological sequences (e.g. DNA)
/**
 * The Class SeqMotifs.
 */
public class SeqMotifs {

	// set of symbols used
	/** The symbols. */
	String symbols;
	
	// sequences
	/** The sequences. */
	List<String> sequences;
	
	// size of motif
	/** The motif size. */
	int motifSize; 
	
	// constructor from filename (file with the set of sequences), the set of symbols and motif's size
	/**
	 * Instantiates a new seq motifs.
	 * 
	 * @param filename the filename
	 * @param symb the symb
	 * @param ms the ms
	 * 
	 * @throws Exception the exception
	 */
	public SeqMotifs(String filename, String symb, int ms) throws Exception
	{
		this.symbols = symb;
		this.motifSize = ms;
		this.sequences = new ArrayList<String>();
		readSequences(filename);
	}
	
	public SeqMotifs(SeqMotifs sequenceMotifsOriginal){
		symbols = new String(sequenceMotifsOriginal.symbols);
		motifSize = sequenceMotifsOriginal.motifSize;
		sequences = copySequences(sequenceMotifsOriginal.sequences);
	}
	
	private List<String> copySequences(List<String> originalSequences){
		List<String> sequenceList = new ArrayList<String>();
		for(String sequence:originalSequences)
			sequenceList.add(new String(sequence));
		
		return sequenceList;
	}

	// returns the number of sequences
	/**
	 * Gets the num sequences.
	 * 
	 * @return the num sequences
	 */
	public int getNumSequences ()
	{
		return this.sequences.size();
	}
	
	// assumes all sequences have the same size
	/**
	 * Gets the seq size.
	 * 
	 * @return the seq size
	 */
	public int getSeqSize()
	{
		return this.sequences.get(0).length();
	}
	
	/**
	 * Gets the motif size.
	 * 
	 * @return the motif size
	 */
	public int getMotifSize()
	{
		return this.motifSize;
	}
	
	// reads sequences from file
	/**
	 * Read sequences.
	 * 
	 * @param fname the fname
	 * 
	 * @throws Exception the exception
	 */
	private void readSequences(String fname) throws Exception
	{
		FileReader r = new FileReader(fname);
		BufferedReader b = new BufferedReader(r);
		
		String str;
		while((str = b.readLine())!= null)
		{
			sequences.add(str);
		}
		b.close();
		r.close();
	}
	
	// returns the matrix of occurrences given a solution - array with starting points in each sequence 
	/**
	 * Occur matrix.
	 * 
	 * @param sol the sol
	 * 
	 * @return the int[][]
	 */
	public int[][] occurMatrix (int[] sol)
	{
		int[][] res = new int[symbols.length()][motifSize];

		for(int i=0; i<motifSize; i++)
		{
			for (int k=0; k<symbols.length(); k++) 
			{
				res[k][i] = 0;
				//for (int j=0; j< sequences.size(); j++)
		  		for (int j=0; j< sol.length; j++)
				{
					String seq = sequences.get(j);
					if (seq.charAt(sol[j]+i) == symbols.charAt(k))
						res[k][i]++;
				}
			}
		  }
		
		return res;	  	
	}
		
	// returns the score of a solution
	/**
	 * Score.
	 * 
	 * @param sol the sol
	 * 
	 * @return the int
	 */
	public int score (int[] sol)
	{
		int score = 0;	
		int[][] mat = this.occurMatrix(sol);
		
		for(int j=0; j<mat[0].length; j++)
		{
			int maxcol = mat[0][j];
			for(int i=1; i<mat.length; i++)
				if(mat[i][j]>maxcol) maxcol = mat[i][j];
			score += maxcol;
		}
		return score;
	}

	
	// returns the consensus motif given by a solution 
	/**
	 * Consensus.
	 * 
	 * @param sol the sol
	 * 
	 * @return the string
	 */
	public String consensus (int[] sol)
	{
		String res = "";
		
		int[][] mat = this.occurMatrix(sol);
		
		for(int j=0; j<mat[0].length; j++)
		{
			int maxcol = mat[0][j];
			int maxcoli = 0;
			for(int i=1; i<mat.length; i++)
				if(mat[i][j]>maxcol) { 
					maxcol = mat[i][j];
					maxcoli = i;
				}
			res += symbols.charAt(maxcoli);		
		}
		
		return res;
	}
	
	// prints the occurrence matrix
	/**
	 * Prints the matrix.
	 * 
	 * @param mat the mat
	 */
	public void printMatrix (int[][] mat)
	{
		for (int i=0; i<mat.length; i++)
		{
			System.out.print(symbols.charAt(i)+" ");
			for(int j=0; j<mat[i].length; j++)
			{
				System.out.print(mat[i][j]+ " ");
			}
			System.out.println("");
		}
	}

	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main (String[] args)
	{
		try {
			String symb = "actg";
			String filename = "exemploMotifs.txt";
			SeqMotifs sm = new SeqMotifs(filename, symb, 8);
			
			int [] sol = {25,20,2,55,59};
			
			int[][] mat = sm.occurMatrix(sol);
			
			sm.printMatrix(mat);
			
			System.out.println("score: " + sm.score(sol));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public SeqMotifs deepCopy() {
		return new SeqMotifs(this);
	}
}
