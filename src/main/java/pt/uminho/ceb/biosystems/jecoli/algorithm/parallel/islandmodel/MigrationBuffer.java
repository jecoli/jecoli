package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public class MigrationBuffer implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	protected ISolutionSet solutionSet; // set of individuals
	
	protected int source; // source island
	
	protected int dest;	// destination island
	
	protected int numIndivs; // number of indivs it takes as input/ ouptut in each step 
	// Migration of 4 individuals by default
	
	protected boolean migrateLater;
		
	public MigrationBuffer(int s, int d) throws Exception
	{
		this.solutionSet = null;
		this.source = s;
		this.dest = d;
		this.migrateLater=false;
	}
	
	public MigrationBuffer(int s, int d, int numind) throws Exception
	{
		this(s, d);
		this.numIndivs = numind;
	}

	public boolean solutionSetIsEmpty()
	{
		boolean res;
		if(solutionSet.getNumberOfSolutions()==0 || solutionSet==null)
			res=true;
		else 
			res=false; 
		
		return res;
	}
	
	public ISolutionSet getSolutionSet() {
		return solutionSet;
	}

	public void setSolutionSet(ISolutionSet pop) {
		this.solutionSet= pop;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public int getNumIndivs() {
		return numIndivs;
	}

	public void setNumIndivs(int numIndivs) {
		this.numIndivs = numIndivs;
	}
	
	
	// Removes solutions from the buffer and returns them in the SolutionSet
	public ISolutionSet getIndFromBuffer() throws Exception
	{
		synchronized(this){
			ISolutionSet res = null;
			
			if(solutionSet!=null){
				int indsToRemove = 0; // number of individuals to remove
				if (this.solutionSet.getNumberOfSolutions() < this.numIndivs)
					indsToRemove = this.solutionSet.getNumberOfSolutions();
				else indsToRemove = this.numIndivs;
			
				res = new SolutionSet(indsToRemove);
				for(int i=0; i < indsToRemove; i++)
				{
					res.add(solutionSet.getSolution(i)); // retrieves the first indivs
				}
				
				solutionSet=null;
				migrateLater=false;
			}
			else	migrateLater=true;
			
			return res;
		}
	}

	//Assincrono antigo
//	public ISolutionSet getIndFromBuffer() throws Exception
//	{
//		synchronized(solutionSet){
//			ISolutionSet res = new SolutionSet();
//	
//	
//			int indsToRemove = 0; // number of individuals to remove
//			if (this.solutionSet.getNumberOfSolutions() <= 0) // no individuals
//				return res;
//			else
//				if (this.solutionSet.getNumberOfSolutions() < this.numIndivs)
//					indsToRemove = this.solutionSet.getNumberOfSolutions();
//				else indsToRemove = this.numIndivs;
//		
//			res = new SolutionSet(indsToRemove);
//			for(int i=0; i < indsToRemove; i++)
//			{
//				res.add(solutionSet.getSolution(i)); // retrieves the first indivs
//			}
//			
//			return res;
//		}
//	}
	
	public void addIndiv (ISolution solution)
	{
		solutionSet.add(solution);
	}
	
	public void addSetIndivs(List<ISolution> list)
	{
		solutionSet.add(list);
	}
}
