package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

public class IslandModelParams implements java.io.Serializable{
	
	protected int numberPopulations; 					// Number of islands

	protected int migrationGenerations; 				// Number of iterations the populations run autonomously without migration 
	// for now, assuming this is fixed for every island
	
	protected int migrationRate; 						// Percentage of number of individuals in each migration step - 0 to 100 %
	// for now, assuming this is fixed for every buffer
	
	protected boolean syncronnized; 					// Sets the migration of individuals, synchronized or not
	
	protected int totalGenerations; 					// Total number of generations
	
	protected int totalIndividuals; 					// Total number o individuals
	
	protected int selectMigrationIndividualsType = 1;	// 0 = random; 1=best; 2=tournament; 3=ranking.
	//best by default
	
	protected int tradeMigrationIndividualsType = 1; 	// 0 = random; 1 = worst;
	//worst by default
	
	protected int splitIslandsType = 1;					// 0 = split generations; 1 = split individuals
	//split individuals by default
	
	protected boolean parallel=true;					// IM parallel or sequential
	//parallel by default
		
	protected boolean migration=true;					// IM with or without migration
	//true by default
	
	protected boolean isMaximization=true;				// If the problem is maximization or not
	//true by default
			
	protected boolean mpi=false; 						// Use mpi migration
	//false bye default
	
	
	
	public IslandModelParams (int np,int migrationGenerations,int migrationRate,boolean syncronnized,int totalGenerations, int totalIndividuals, boolean p, int sMIT,int tMIT, int sIT, boolean migration, boolean isMax,boolean mpi)
	{
		this.numberPopulations = np;
		this.migrationGenerations=migrationGenerations;
		this.migrationRate=migrationRate;
		this.syncronnized=syncronnized;
		this.totalGenerations=totalGenerations;
		this.totalIndividuals = totalIndividuals;
		this.selectMigrationIndividualsType=sMIT;
		this.tradeMigrationIndividualsType=tMIT;
		this.splitIslandsType=sIT;
		this.parallel=p;
		if(migrationGenerations==0 || migrationRate==0)
			this.migration=false;
		else
			this.migration=migration;
		this.isMaximization = isMax;
		this.mpi=mpi;
	}
	
	public int getNumberPopulations() {
		return numberPopulations;
	}
	
	public int getMigrationGenerations() {
		return migrationGenerations;
	}
	
	public int getMigrationRate() {
		return migrationRate;
	}
	
	public boolean getSyncronnized(){
		return syncronnized;
	}
	
	public int getTotalGenerations() {
		return totalGenerations;
	}
	
	public int getTotalIndividuals() {
		return totalIndividuals;
	}
	
	public int getSelectMigrationIndividualsType() {
		return selectMigrationIndividualsType;
	}
	
	public int getTradeMigrationIndividualsType() {
		return tradeMigrationIndividualsType;
	}
	
	public int getSplitIslandsType(){
		return splitIslandsType;
	}
	
	public boolean getBoolParallel() {
		return parallel;
	}
	
	public boolean getBoolMigration() {
		return migration;
	}
	
	public boolean getBoolMaximization() {
		return isMaximization;
	}
	
	public boolean getBoolMPI() {
		return mpi;
	}
}
