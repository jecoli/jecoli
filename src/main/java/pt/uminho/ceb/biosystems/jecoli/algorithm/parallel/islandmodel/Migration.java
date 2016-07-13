package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import mpi.MPI;
import mpi.MPIException;
import mpi.Status;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionProcedureException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.RankingSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection2;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public class Migration implements java.io.Serializable{
	public int itStep;							// Number of iterations in each step without migrations
	public int numMigrants;						// Number of migrants in each migration step
	public boolean syncronized;
	public boolean isMaximization;
	public boolean migCompleted=true;
	public int index;
	
	private int selectMigrationIndividualsType;	// 0 = random; 1=best; 2=tornement; 3=ranking.
	private CyclicBarrier barrier=null;
	private boolean mpi;
	private int nMig=0; 						// Counts the times that migration occurs
	private int tradeMigrationIndividualsType;	// 0 = random; 1 = worst;
	private boolean parallel;
	
	//Sequential migration
	public Migration(int iterationStep, int migrants, int sMIT,  int tMIT, boolean isMax){
		this.itStep=iterationStep;
		this.numMigrants=migrants;
		this.selectMigrationIndividualsType=sMIT;
		this.tradeMigrationIndividualsType=tMIT;
		this.parallel=false;
		this.isMaximization = isMax;
		this.barrier=null;
	}
	
	//Parallel migration
	public Migration(int iterationStep, int migrants, int sMIT, int tMIT,int pops,boolean syncronized, boolean BoolMpi,boolean isMax){
		this.itStep=iterationStep;
		this.numMigrants=migrants;
		this.syncronized=syncronized;
		this.selectMigrationIndividualsType=sMIT;
		this.tradeMigrationIndividualsType=tMIT;
		this.mpi=BoolMpi;
		this.parallel=true;
		this.isMaximization=isMax;
		if(isMaximization)
			System.out.println("The Problem is Maximization!");
		else
			System.out.println("The Problem is Minimization!");
		
		
		
		if(syncronized==true){
			System.out.println("Synchronized = true");
			if(mpi==false)
			{
				barrier=new CyclicBarrier(pops);
				System.out.println("Synchronized = true");
				System.out.println("Set CyclicBarrier..");
			}
			else{
				barrier=null;
			}
		}
		else{
			System.out.println("Synchronized = false");
			barrier=null;
		}
	}
		
	public void incMig()
	{
		nMig++;
	}
	
	public int getnMig()
	{
		return nMig;
	}
	
	//Select migrant solutions
	public List<ISolution> getIndivs(IAlgorithm ga,AlgorithmState state) throws InvalidSelectionProcedureException, InvalidSelectionParameterException, Exception 
	{
		List<ISolution> inds=null;
		String s = ga.getConfiguration().getClass().getSimpleName(); 			// Get the type of the algorithm
		
//		if(s.equals("CellularGeneticAlgorithmConfiguration"))
//			inds=putFromBufferCellular((CellularGeneticAlgorithm) ga,numMigrants,state);
//		else
		
		switch(this.selectMigrationIndividualsType){
			case 0:
				inds = selectIndividuals(state.getSolutionSet(),numMigrants);
				break;
			case 1:
				inds = selectBestIndividuals(state.getSolutionSet(),numMigrants);
				break;
			case 2:
				int k=1,TorIndv=2;//;
				inds = selectTournamentIndividuals(state.getSolutionSet(),numMigrants,k,TorIndv);
				break;
			case 3:
				inds = selectRankingIndividuals(state.getSolutionSet(),numMigrants);
				break;
			default:
				System.out.println("ERROR!!!");
				break;
		}
		
		return inds;
	}
	
	public List<ISolution> selectIndividuals(ISolutionSet solutionSet,int numMigrants)
	{
		List<ISolution> indivs=new ArrayList<ISolution>(numMigrants);				// Create the set of individual to migrate
		int max=solutionSet.getNumberOfSolutions();									// Gets the size of the population
		Random r=new Random();
		for(int i=0;i<numMigrants;i++)												// Run until the number of individuals to migrate is met
		{
			int position = (int) r.nextInt(max);
			indivs.add(solutionSet.getSolution((int) position));		// Choose a random individual of the population and adds it to the Set of indivuals to migrate
		}
		return	indivs;
	}
	
	public List<ISolution> selectBestIndividuals(ISolutionSet solutionSet,int numMigrants)
	{	
		return	solutionSet.getLowestValuedSolutions(numMigrants);
	}
	
	public List<ISolution> selectTournamentIndividuals(ISolutionSet solutionSet,int numMigrants,int k, int TorIndv) throws InvalidSelectionProcedureException, Exception{
		List<ISolution> indivs=new ArrayList<ISolution>(numMigrants);				// Create the set of individual to migrate
		IRandomNumberGenerator randomGenerator = new DefaultRandomNumberGenerator();
		ISelectionOperator selOp = new TournamentSelection2(1, numMigrants, randomGenerator);
		indivs = selOp.selectSolutions(numMigrants, solutionSet, isMaximization, randomGenerator);
		return indivs;
	}
	
	public List<ISolution> selectRankingIndividuals(ISolutionSet solutionSet,int numMigrants) throws InvalidSelectionProcedureException, InvalidSelectionParameterException, Exception{
		List<ISolution> indivs=new ArrayList<ISolution>(numMigrants);	// Create the set of individual to migrate
		IRandomNumberGenerator randomGenerator = new DefaultRandomNumberGenerator();
		ISelectionOperator selOp = new RankingSelection();
		indivs = selOp.selectSolutions(numMigrants, solutionSet, isMaximization, randomGenerator);
		return indivs;
	}

	//Apply migration (shared memory)
	public boolean migrate(ArrayList<Island> islands)
	{
		boolean res=true;
		//Filling buffers
		for(int i=0;i<islands.size();i++){
			Island island = islands.get(i);
			IAlgorithm algorithm = island.getGa();
			AlgorithmState state = island.getGa().getAlgorithmState();
			ISolutionSet solutions = state.getSolutionSet();
			if((state.getCurrentIteration()%itStep)==0 && (!algorithm.getClass().getSimpleName().equals("SimulatedAnnealing")) && (!algorithm.getClass().getSimpleName().equals("RandomSearch")))
			{
				try 
				{
					putInBuffer(island,state);
				}
				catch(Exception e){e.printStackTrace();}
			}
		}
		
		if(this.parallel && this.syncronized){
			try{
				barrier.await();
			}
			catch(Exception e){e.printStackTrace();}
		}
		
		//Trade solutions
		for(int i=0;i<islands.size();i++){
			Island island = islands.get(i);
			IAlgorithm algorithm = island.getGa();
			AlgorithmState state = island.getGa().getAlgorithmState();
			ISolutionSet solutions = state.getSolutionSet();
			try 
			{
				if(!this.syncronized)
				  res=getFromBuffer(island,state,solutions);
				else
					getFromBuffer(island,state,solutions);
			}
			catch(Exception e){e.printStackTrace();}
		}
		incMig();
		
		return res;
	}

	//Select migrant solutions and put them in buffers
	public void putInBuffer(Island island, AlgorithmState state) throws Exception
	{
		
		IAlgorithm ga = island.getGa();
		
		ArrayList<MigrationBuffer> mBuffers = island.getOutBuffers();
		int size = mBuffers.size();
		
		//Select migrant solutions
		List<ISolution> inds=getIndivs(ga,state);
		
		//For each buffer set the migrant solutions
		for(int k=0; k < size; k++)
		{
			MigrationBuffer mbo = (MigrationBuffer) mBuffers.get(k);
			
			List<ISolution> cloneList = new ArrayList<ISolution>(inds.size());
			for(int i=0; i < inds.size(); i++ ){
				ISolution solution = inds.get(i).deepCopy();
				cloneList.add(solution);
			}
			ISolutionSet IndivsToMigrate = new SolutionSet();
			IndivsToMigrate.add(cloneList);
			mbo.setSolutionSet(IndivsToMigrate);
		}
		
	}
	
	//Get migrants from buffer and apply trade
	public boolean getFromBuffer(Island island,AlgorithmState state,ISolutionSet solutionSet)
	{
		boolean res=true;
		Random r=new Random();
		int max = solutionSet.getNumberOfSolutions(); // Numero maximo de soluções no solutionSet
		ArrayList<MigrationBuffer> mBuffers = island.getInBuffers();
		int size = mBuffers.size();
		try {

			ISolutionSet migrants=null;
			//For each buffer, get the migrant solutions and trade
			for(int k=0; k < size; k++)
			{
				MigrationBuffer mbi = (MigrationBuffer) mBuffers.get(k);
				migrants = mbi.getIndFromBuffer();
				
				if(!this.syncronized && migrants==null){
					res=false;
				}
					
				//APPLY TRADE
				if(state!=null && migrants!=null)
				{
					ArrayList<Integer> position = new ArrayList<Integer>(migrants.getListOfSolutions().size());

					if(tradeMigrationIndividualsType==0){
						//Choose randomly the positions to trade
						for(int f=0; f < migrants.getListOfSolutions().size()+1; f++){
							int aux = r.nextInt(max);
							while(position.contains(aux))
								aux = r.nextInt(max);
							position.add(aux);
						}
					}
					else{
						//Choose the worst positions to trade
						List<ISolution> aux;
						if(isMaximization)
							aux = solutionSet.getLowestValuedSolutions(migrants.getNumberOfSolutions());
						else
							aux = solutionSet.getHighestValuedSolutions(migrants.getNumberOfSolutions());

						for(int i=0; i < migrants.getListOfSolutions().size(); i++){
							int index = solutionSet.getListOfSolutions().indexOf(aux.get(i));
							position.add(index);
						}
					}
					for(int i=0;i<migrants.getNumberOfSolutions();i++)
					{
						solutionSet.setSolution(position.get(i), migrants.getSolution(i)); //Insere o individuo numa posicao aleatoria da população
					}
				}
			}
		
		} catch (Exception e) {	e.printStackTrace(); }
		return res;
	}
	
	//Get migrants from buffer and apply trade
	public boolean testAndTrade(Island island)
	{
		boolean res=true;
		AlgorithmState state = island.getGa().getAlgorithmState();
        ISolutionSet solutionSet = state.getSolutionSet();
		Random r=new Random();
		int max = solutionSet.getNumberOfSolutions(); // Numero maximo de soluções no solutionSet
		ArrayList<MigrationBuffer> mBuffers = island.getInBuffers();
		int size = mBuffers.size();
		try {

			ISolutionSet migrants=null;
			//For each buffer, get the migrant solutions and trade
			for(int k=0; k < size && res; k++)
			{
				MigrationBuffer mbi = (MigrationBuffer) mBuffers.get(k);
				if(mbi.migrateLater){
					migrants = mbi.getIndFromBuffer();

					if(migrants==null){
						res=false;
					}
					
					//APPLY TRADE
					if(state!=null && migrants!=null)
					{
						ArrayList<Integer> position = new ArrayList<Integer>(migrants.getListOfSolutions().size());

						if(tradeMigrationIndividualsType==0){
							//Choose randomly the positions to trade
							for(int f=0; f < migrants.getListOfSolutions().size()+1; f++){
								int aux = r.nextInt(max);
								while(position.contains(aux))
									aux = r.nextInt(max);
								position.add(aux);
							}
						}
						else{
							//Choose the worst positions to trade
							List<ISolution> aux;
							if(isMaximization)
								aux = solutionSet.getLowestValuedSolutions(migrants.getNumberOfSolutions());
							else
								aux = solutionSet.getHighestValuedSolutions(migrants.getNumberOfSolutions());

							for(int i=0; i < migrants.getListOfSolutions().size(); i++){
								int index = solutionSet.getListOfSolutions().indexOf(aux.get(i));
								position.add(index);
							}
						}
						for(int i=0;i<migrants.getNumberOfSolutions();i++)
						{
							solutionSet.setSolution(position.get(i), migrants.getSolution(i)); //Insere o individuo numa posicao aleatoria da população
						}
					}
					else System.out.println("State = NULL : No migration was made!");
				}
				
			}
		
		} catch (Exception e) {	e.printStackTrace(); }
		return res;
	}
	
	//Apply MPI migration (distributed memory)
	public int migrateMPI(ArrayList<Island> islands){
        int res=1;
        try{
          int myrank = MPI.COMM_WORLD.Rank();
          for(int i=0;i<islands.size() && res==1; i++){
        	  Island island = islands.get(i);
              IAlgorithm algorithm = island.getGa();
              AlgorithmState state = island.getGa().getAlgorithmState();
              ISolutionSet solutions = state.getSolutionSet();
              if((state.getCurrentIteration()%itStep)==0 && (!algorithm.getClass().getSimpleName().equals("SimulatedAnnealing")) && (!algorithm.getClass().getSimpleName().equals("RandomSearch"))){
            	  try{ 
            		  sendSolutionsMPI(island,state);
            		  res = receiveSolutionsMPI(island,state,solutions);
            	  }catch(Exception e){e.printStackTrace();}
              }
              else res=0;
          }
          if(res==0){
              System.out.println("FALHOU A MIGRAÇÃO, RES=0");
              System.exit(1);
          }
          
          if(myrank==0)
            System.out.println("MPI migration has completed!");

        } catch (MPIException e) { 
            e.printStackTrace();  
        } 
    
        incMig();
        return res;
    }
	
	//Select migrants solutions and send them to the neighbors
	public void sendSolutionsMPI(Island island, AlgorithmState state) throws Exception
    {
    	IAlgorithm ga = island.getGa();
    	
    	//Select migrant solutions
    	List<ISolution> inds=getIndivs(ga,state);
            
    	ArrayList<MigrationBuffer> mBuffers = island.getOutBuffers();
    	int size = mBuffers.size();
            
    	int tag=200;
    	try 
    	{ 
    		int myrank = MPI.COMM_WORLD.Rank();
    		//Send migrant solutions through MPI messages
    		for(int k=0; k < size; k++)
    		{
    			MigrationBuffer mbo = (MigrationBuffer) mBuffers.get(k);
    			List<ISolution> cloneList = new ArrayList<ISolution>(inds.size());
    			for(int i=0; i < inds.size(); i++ ){
    				ISolution solution = inds.get(i).deepCopy();
    				cloneList.add(solution);
    			}
    			ISolutionSet IndivsToMigrate = new SolutionSet();
    			IndivsToMigrate.add(cloneList);
    			//mbo.setSolutionSet(IndivsToMigrate);
    			ISolutionSet[] obj = new ISolutionSet[1];
    			obj[0] = IndivsToMigrate;
    			MPI.COMM_WORLD.Isend(obj, 0,obj.length, MPI.OBJECT,mbo.getDest(), tag) ;
    		}
    	}catch (MPIException e) { e.printStackTrace(); }
    }
	
	// Receive migrants solutions and apply trade
	public int receiveSolutionsMPI(Island island,AlgorithmState state,ISolutionSet solutionSet)
	{
		int res=1;
		try{
			Random r=new Random();
			int max = solutionSet.getNumberOfSolutions(); // Numero maximo de soluções no solutionSet
			ArrayList<MigrationBuffer> mBuffers = island.getInBuffers();
			int size = mBuffers.size();
			//try {
			int myrank = MPI.COMM_WORLD.Rank();
			ISolutionSet migrants=null;
			//Receive solutions through MPI messages
			if(this.syncronized==false){
				if(!migCompleted){
					//this.probeFinal(island);
					this.probeNow(island);
					System.out.println("Probe Final!!");
				}
				migCompleted=true;
				index=0;
			}
			else{
				migCompleted=true;
			}
			
			for(int k=0; k < size && migCompleted; k++)
			{
				MigrationBuffer mbi = (MigrationBuffer) mBuffers.get(k);
                
				if(this.syncronized==true){
					//Receive migrant solutions
					ISolutionSet[] obj=new ISolutionSet[1];//ISolutionSet[numMigrants];
					MPI.COMM_WORLD.Recv(obj, 0, obj.length, MPI.OBJECT,mbi.getSource(), 200) ;
					migrants = obj[0];

					//Apply trade
					if(state!=null && migrants!=null)
					{
						ArrayList<Integer> position = new ArrayList<Integer>(migrants.getListOfSolutions().size());
						if(tradeMigrationIndividualsType==0){
							//Choose randomly the positions to trade
							for(int f=0; f < migrants.getListOfSolutions().size()+1; f++){
								int aux = r.nextInt(max);
								while(position.contains(aux))
									aux = r.nextInt(max);
								position.add(aux);
							}
						}
						else{
							//Choose the worst positions to trade
							List<ISolution> aux;
							if(isMaximization)
								aux = solutionSet.getLowestValuedSolutions(migrants.getNumberOfSolutions());
							else
								aux = solutionSet.getHighestValuedSolutions(migrants.getNumberOfSolutions());
							for(int i=0; i < migrants.getListOfSolutions().size(); i++){
								int index = solutionSet.getListOfSolutions().indexOf(aux.get(i));
								position.add(index);
							}
						}
							for(int i=0;i<migrants.getNumberOfSolutions();i++)
							{
								solutionSet.setSolution(position.get(i), migrants.getSolution(i)); //Insere o individuo numa posicao aleatoria da população
							}
					}
					else System.out.println("State = NULL : No migration was made!");
				}
				else{
					
					Status bool = MPI.COMM_WORLD.Iprobe(mbi.getSource(), 200); 
					migrants=null;
					
					//Receive migrant solutions
					if(bool != null && migCompleted){
						if(!bool.Test_cancelled()){
							ISolutionSet[] obj=new ISolutionSet[1];
							MPI.COMM_WORLD.Recv(obj, 0, obj.length, MPI.OBJECT,mbi.getSource(), 200) ;	
							migrants=obj[0];
						}
					}
					else{
						migCompleted=false;
						index=k;
					}
					
					//Apply trade
					if(state!=null && migrants!=null && migrants.getListOfSolutions().size()!=0)
					{
						ArrayList<Integer> position = new ArrayList<Integer>(migrants.getListOfSolutions().size());
						if(tradeMigrationIndividualsType==0){
							//Choose randomly the positions to trade
							for(int f=0; f < migrants.getListOfSolutions().size()+1; f++){
								int aux = r.nextInt(max);
								while(position.contains(aux))
									aux = r.nextInt(max);
								position.add(aux);
							}
						}
						else{
							//Choose the worst positions to trade
							List<ISolution> aux;
							if(isMaximization)
								aux = solutionSet.getLowestValuedSolutions(migrants.getNumberOfSolutions());
							else
								aux = solutionSet.getHighestValuedSolutions(migrants.getNumberOfSolutions());
							for(int i=0; i < migrants.getListOfSolutions().size(); i++){
								int index = solutionSet.getListOfSolutions().indexOf(aux.get(i));
								position.add(index);
							}
						}
							for(int i=0;i<migrants.getNumberOfSolutions();i++)
							{
								solutionSet.setSolution(position.get(i), migrants.getSolution(i)); //Insere o individuo numa posicao aleatoria da população
							}
					}
					//else System.out.println("State = null or migrants = null or migrants.size = 0 : No migration was made!");
				}
				
			}
		
		} catch (Exception e) {	e.printStackTrace(); }

		return res;
	}
	
	public void probeNow(Island island){
		ArrayList<MigrationBuffer> mBuffers = island.getInBuffers();
		int size = mBuffers.size();
		migCompleted=true;
		for(int k=index; k < size && migCompleted; k++)
		{
			MigrationBuffer mbi = (MigrationBuffer) mBuffers.get(k);
			Status bool;
			try {
				bool = MPI.COMM_WORLD.Iprobe(mbi.getSource(), 200);
			 
				ISolutionSet migrants=null;
				//Receive migrant solutions
				if(bool != null && migCompleted){
					if(!bool.Test_cancelled()){
						ISolutionSet[] obj=new ISolutionSet[1];
						MPI.COMM_WORLD.Recv(obj, 0, obj.length, MPI.OBJECT,mbi.getSource(), 200) ;	
		                //System.out.println("Thread received migrants, rank = "+myrank);
						migrants=obj[0];
					}
				}
				else{
					migCompleted=false;
					index=k;
				}
				
				AlgorithmState state = island.getGa().getAlgorithmState();
	            ISolutionSet solutionSet = state.getSolutionSet();
				//Apply trade
				if(state!=null && migrants!=null && migrants.getListOfSolutions().size()!=0)
				{
					Random r=new Random();
					int max = solutionSet.getNumberOfSolutions(); // Numero maximo de soluções no solutionSet
					ArrayList<Integer> position = new ArrayList<Integer>(migrants.getListOfSolutions().size());
					if(tradeMigrationIndividualsType==0){
						//Choose randomly the positions to trade
						for(int f=0; f < migrants.getListOfSolutions().size()+1; f++){
							int aux = r.nextInt(max);
							while(position.contains(aux))
								aux = r.nextInt(max);
							position.add(aux);
						}
					}
					else{
						//Choose the worst positions to trade
						List<ISolution> aux;
						if(isMaximization)
							aux = solutionSet.getLowestValuedSolutions(migrants.getNumberOfSolutions());
						else
							aux = solutionSet.getHighestValuedSolutions(migrants.getNumberOfSolutions());
						for(int i=0; i < migrants.getListOfSolutions().size(); i++){
							int index = solutionSet.getListOfSolutions().indexOf(aux.get(i));
							position.add(index);
						}
					}
						for(int i=0;i<migrants.getNumberOfSolutions();i++)
						{
							solutionSet.setSolution(position.get(i), migrants.getSolution(i)); //Insere o individuo numa posicao aleatoria da população
						}
				}
				//else System.out.println("State = null or migrants = null or migrants.size = 0 : No migration was made!");
			} catch (MPIException e) {
				e.printStackTrace();
			}
		}
	}
	
}
