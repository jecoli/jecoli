package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import java.util.ArrayList;

//import mpi.MPI;
//import mpi.MPIException;
//import core.SolutionSet;
//TM import core.representation.tree.nodefactory.NonExistingNodeTypeException;
import mpi.MPI;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;



public class IslandModel {
	
	protected IslandModelParams mgp;
	
	// GAs - populations
	protected ArrayList<Island> gas;
		
	// Topology of the GAs/ populations
	protected IMTopology topology; 
	
	//protected MasterState masterState;
	
	protected Migration migration;
		
	protected ArrayList<IMThread> threads;
	
	protected int numberGenerations;
	
	//Island Model (shared memory) constructor
	public IslandModel (IslandModelParams mgp,IMTopology topol,ArrayList<IAlgorithm> algorithms) throws Exception, InvalidConfigurationException//TM , NonExistingNodeTypeException
	{	
		System.out.println("");
		System.out.println("Initializing Island Model..");
		if(mgp.getBoolMPI()) 	System.out.println("Mode: MPI");
		else					System.out.println("Mode: Standard - Java Threads");
		
		System.out.println("Number of Islands = "+mgp.getNumberPopulations());
		System.out.println("Total Number of Genarations = "+mgp.getTotalGenerations());
		System.out.println("Total Number of Individuals = "+mgp.getTotalIndividuals());
		int splitIslandsType = mgp.getSplitIslandsType();
		
		//Set parameters by split island type
		int numIndivsMigration,numGenerations;
		if(splitIslandsType==0){
			numIndivsMigration=mgp.getTotalIndividuals()*mgp.getMigrationRate()/100;
			numGenerations = mgp.getTotalGenerations()/mgp.getNumberPopulations();
			System.out.println("Slipt Island Type = Slip Generations");
			System.out.println("Number of Genarations per Island = "+numGenerations);
			System.out.println("Number of Individuals per Island = "+mgp.getTotalIndividuals());
		}
		else {
			int numIndivs=mgp.getTotalIndividuals()/mgp.getNumberPopulations();
			numIndivsMigration=numIndivs*mgp.getMigrationRate()/100;
			numGenerations = mgp.getTotalGenerations();
			System.out.println("Slipt Island Type = Slip Individuals");
			System.out.println("Number of Genarations per Island = "+numGenerations);
			System.out.println("Number of Individuals per Island = "+mgp.getTotalIndividuals()/mgp.getNumberPopulations());
		}				
		this.numberGenerations = numGenerations;
		this.mgp = mgp;
		
		//Initialize Islands and Migration
		if(mgp.getBoolParallel() == false || mgp.getNumberPopulations()==1){
			if(mgp.getNumberPopulations()==1 || mgp.getBoolMigration()==false)
				this.migration = null;
			else
				this.migration = new Migration(mgp.getMigrationGenerations(),numIndivsMigration,mgp.getSelectMigrationIndividualsType(),mgp.getTradeMigrationIndividualsType(),mgp.getBoolMaximization());
			
			System.out.println("Island Model Sequential Version!");
			threads=null;
			gas = new ArrayList<Island>(mgp.getNumberPopulations());
			for(int i=0; i < mgp.getNumberPopulations(); i++)
			{							
				gas.add(i,new Island(algorithms.get(i)));
				gas.get(i).setID(i);
			}
		}
		else{
			if(mgp.getBoolMPI()){
                if(mgp.getBoolMigration())
                	this.migration = new Migration(mgp.getMigrationGenerations(),numIndivsMigration,mgp.getSelectMigrationIndividualsType(),mgp.getTradeMigrationIndividualsType(),mgp.getNumberPopulations(),mgp.getSyncronnized(), mgp.getBoolMPI() ,mgp.getBoolMaximization());
                else
                	this.migration = null;

				System.out.println("Island Model Parallel Version! Initializing Islands for MPI");
				gas = new ArrayList<Island>(mgp.getNumberPopulations());
				for(int i=0; i < mgp.getNumberPopulations(); i++)
				{						
					Island islandAux = new Island(algorithms.get(i));
					gas.add(i,islandAux);
					gas.get(i).setID(i);
				}	   
				threads=null;
			}
			else{
				if(mgp.getBoolMigration())
					this.migration = new Migration(mgp.getMigrationGenerations(),numIndivsMigration,mgp.getSelectMigrationIndividualsType(),mgp.getTradeMigrationIndividualsType(),mgp.getNumberPopulations(),mgp.getSyncronnized(), mgp.getBoolMPI(),mgp.getBoolMaximization());
				else
					this.migration = null;
				System.out.println("Island Model Parallel Version! Initializing Threads");
				gas = new ArrayList<Island>(mgp.getNumberPopulations());
				threads = new ArrayList<IMThread>(mgp.getNumberPopulations());
				for(int i=0; i < mgp.getNumberPopulations(); i++)
				{						
					Island islandAux = new Island(algorithms.get(i));
					gas.add(i,islandAux);
					gas.get(i).setID(i);
					threads.add(i,new IMThread(i,islandAux,numGenerations,migration));
					
				}	   
			}
			
		    
		}
		
		if(mgp.getBoolMigration()==false){
			this.migration=null;
			System.out.println("Migration OFF");
		}
			
		
		//Initialize Buffers
		if(this.migration!=null){
			//Initialize Topology
			this.topology = topol;
			int [][] edges = this.topology.getGraph().getAllEdges();
			System.out.println("Topology Type = "+topol.getStringType());
			int connectivity_degree = edges.length/mgp.getNumberPopulations();
			System.out.println("Connectivity degree = "+connectivity_degree);
			
			System.out.println("------------------ TOPOLOGY ------------------");
			for(int i=0; i < edges.length; i++)
			{
				System.out.println("Out["+edges[i][0]+"] -> In["+edges[i][1]+"]");
				MigrationBuffer mb = new MigrationBuffer(edges[i][0], edges[i][1],numIndivsMigration);
				mb.setNumIndivs(numIndivsMigration);
				gas.get(edges[i][0]).addOutBuffer(mb);
				gas.get(edges[i][1]).addInBuffer(mb);
			}
			System.out.println("------------------ -- ------------------");
			System.out.println("Migration is done with "+numIndivsMigration+" individuals, "+mgp.getMigrationGenerations()+" in "+mgp.getMigrationGenerations()+" Generations");
			switch(mgp.getSelectMigrationIndividualsType()){
			case 0:
				System.out.println("Migration: individuals selection is done by selecting random individuals");
				break;
			case 1:
				System.out.println("Migration: individuals selection is done by selecting the best individuals");
				break;
			case 2:
				System.out.println("Migration: individuals selection is done by using tournament selection");
				break;
			case 3:
				System.out.println("Migration: individuals selection is done by using ranking selection");
				break;
			default:
				System.out.println("error! invalid Migration Selection!");
				break;
			}
			
			switch(mgp.getTradeMigrationIndividualsType()){
			case 0:
				System.out.println("Migration: trade of individuals is done randomly");
				break;
			case 1:
				System.out.println("Migration: trade of individuals is done by trading the worst individuals");
				break;
			default:
				System.out.println("error! invalid Migration Trade!");
				break;
			}	
		}
	}
	//Island Model (distributed memory) and Hibrid Model constructor
	public IslandModel(IslandModelParams mgp,IMTopology topol) throws Exception, InvalidConfigurationException
	{	
		System.out.println("");
		System.out.println("Initializing Island Model..");
		if(mgp.getBoolMPI()) 	System.out.println("Mode: MPI");
		else					System.out.println("Mode: Standard - Java Threads");
		
		System.out.println("Number of Islands = "+mgp.getNumberPopulations());
		System.out.println("Total Number of Genarations = "+mgp.getTotalGenerations());
		System.out.println("Total Number of Individuals = "+mgp.getTotalIndividuals());
		int splitIslandsType = mgp.getSplitIslandsType();
		
		//Set parameters by split island type
		int numIndivsMigration,numGenerations;
		if(splitIslandsType==0){
			numIndivsMigration=mgp.getTotalIndividuals()*mgp.getMigrationRate()/100;
			numGenerations = mgp.getTotalGenerations()/mgp.getNumberPopulations();
			System.out.println("Slipt Island Type = Slip Generations");
			System.out.println("Number of Genarations per Island = "+numGenerations);
			System.out.println("Number of Individuals per Island = "+mgp.getTotalIndividuals());
		}
		else {
			int numIndivs=mgp.getTotalIndividuals()/mgp.getNumberPopulations();
			numIndivsMigration=numIndivs*mgp.getMigrationRate()/100;
			numGenerations = mgp.getTotalGenerations();
			System.out.println("Slipt Island Type = Slip Individuals");
			System.out.println("Number of Genarations per Island = "+numGenerations);
			System.out.println("Number of Individuals per Island = "+mgp.getTotalIndividuals()/mgp.getNumberPopulations());
		}				
		this.numberGenerations = numGenerations;
		this.mgp = mgp;
		
		//Initialize Islands and Migration
		if(mgp.getBoolMPI()){
            if(mgp.getBoolMigration())
            	this.migration = new Migration(mgp.getMigrationGenerations(),numIndivsMigration,mgp.getSelectMigrationIndividualsType(),mgp.getTradeMigrationIndividualsType(),mgp.getNumberPopulations(),mgp.getSyncronnized(), mgp.getBoolMPI() ,mgp.getBoolMaximization());
            else
            	this.migration = null;

			System.out.println("Island Model Parallel Version! Initializing Islands for MPI");
			gas = null;  
			threads=null;
		}
		
		if(mgp.getBoolMigration()==false){
			this.migration=null;
			System.out.println("Migration OFF");
		}
			
		
		//Initialize Buffers
		if(this.migration!=null){
			//Initialize Topology
			this.topology = topol;
			int [][] edges = this.topology.getGraph().getAllEdges();
			System.out.println("Topology Type = "+topol.getStringType());
			int connectivity_degree = edges.length/mgp.getNumberPopulations();
			System.out.println("Connectivity degree = "+connectivity_degree);
			
			System.out.println("------------------ TOPOLOGY ------------------");
			for(int i=0; i < edges.length; i++)
			{
				System.out.println("Out["+edges[i][0]+"] -> In["+edges[i][1]+"]");
			}
			System.out.println("------------------ -- ------------------");
			System.out.println("Migration is done with "+numIndivsMigration+" individuals, "+mgp.getMigrationGenerations()+" in "+mgp.getMigrationGenerations()+" Generations");
			switch(mgp.getSelectMigrationIndividualsType()){
			case 0:
				System.out.println("Migration: individuals selection is done by selecting random individuals");
				break;
			case 1:
				System.out.println("Migration: individuals selection is done by selecting the best individuals");
				break;
			case 2:
				System.out.println("Migration: individuals selection is done by using tournament selection");
				break;
			case 3:
				System.out.println("Migration: individuals selection is done by using ranking selection");
				break;
			default:
				System.out.println("error! invalid Migration Selection!");
				break;
			}
			
			switch(mgp.getTradeMigrationIndividualsType()){
			case 0:
				System.out.println("Migration: trade of individuals is done randomly");
				break;
			case 1:
				System.out.println("Migration: trade of individuals is done by trading the worst individuals");
				break;
			default:
				System.out.println("error! invalid Migration Trade!");
				break;
			}
		}
	}
	
	
	public IAlgorithmResult returnBest(boolean isMaximization)
	{
		double best;
		int pos=0;
		
		if(isMaximization==true){
			best=Double.MIN_VALUE;
			for(int i=0;i<gas.size();i++)
			{
				if(gas.get(i).getAlgorithmResult().getAlgorithmStatistics().getRunMaxScalarFitnessValue()>best)
				{
					pos=i;
					best=gas.get(i).getAlgorithmResult().getAlgorithmStatistics().getRunMaxScalarFitnessValue();
				}
			}
			System.out.println("MAXofALL = "+gas.get(pos).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getRunMaxScalarFitnessValue());
		}
		else{
			best=Double.MAX_VALUE;
			for(int i=0;i<gas.size();i++)
			{
				if(gas.get(i).getAlgorithmResult().getAlgorithmStatistics().getRunMinScalarFitnessValue()<best)
				{
					pos=i;
					best=gas.get(i).getAlgorithmResult().getAlgorithmStatistics().getRunMinScalarFitnessValue();
				}
			}
			System.out.println("MINofALL = "+gas.get(pos).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getRunMinScalarFitnessValue());
		}
		return gas.get(pos).getAlgorithmResult();
	}
	
	public void run() throws Exception, InvalidConfigurationException 
	{
		System.out.println("Running Standard Island Model..");
		System.out.println("");
		System.out.println("");
		
		//Sequential Island Model
		if(threads==null){
			//Initialize population
			for(int i=0; i < gas.size(); i++)
			{			
				gas.get(i).init();
			}
			Island island;
			//Main cycle
			int currentIteration=1;
			while(currentIteration <= this.numberGenerations+1){
				
				//Possible migration
				if(migration!=null){
					if((currentIteration%mgp.getMigrationGenerations())==0){
						migration.migrate(gas);
					}
				}
				//Apply iteration to all existing islands
				for(int i=0; i < gas.size(); i++)
				{	
					island=gas.get(i);
					island.step();					
				}
				currentIteration++;
			}
		}
		else{ //Parallel Island Model (shared memory)
			//Run Threads
			System.out.println("Running Island Model Threads...");
			for(int i=0;i<this.mgp.getNumberPopulations();i++)
		    {
				threads.get(i).start();
		    }
			for(int i=0;i<threads.size();i++)
		    {
		      try{
		        threads.get(i).join();
		      }catch(InterruptedException e){
		        e.printStackTrace();
		      }
		    }
		}
		System.out.println("---------------------------------------");
		System.out.println("Execution of Island Model is finished");
		System.out.println("");
		System.out.println("");
		System.out.println("Gathering Final Results...");
		if(migration!=null)	System.out.println("NumberOfMigrations = "+migration.getnMig());
		//Set result per Island
		Island island;
		for(int i=0; i < gas.size(); i++){
			island=gas.get(i);
			IAlgorithmResult algorithmResult = island.ga.getAlgorithmState().getAlgorithmResult();
			island.setAlgorithmResult(algorithmResult);
		}
	}
	
//	public void run_mpi_master() throws Exception, InvalidConfigurationException //TM , NonExistingNodeTypeException
//	{
//		System.out.println("Running Island Model with MPI..");
//		System.out.println("");
//		System.out.println("");
//		
////		int myrank = MPI.COMM_WORLD.Rank();
////		int size = MPI.COMM_WORLD.Size() ;
//	 
//		//SEND ISLANDS AND MIGRATION OBJECTS TO PROCESSES
//                
//                Island[] obj=new Island[gas.size()];
//                for(int i=0; i < gas.size(); i++)
//                {
//                  obj[i]=gas.get(i);
//                }
//                for(int i=1; i < gas.size(); i++)
//                {
//                  MPI.COMM_WORLD.Send(obj, i,1, MPI.OBJECT, i, 50) ;
//                }
//                
//                int[] flagMigration =new int[1];
//                if(migration==null){
//                  flagMigration[0] = 0;
//                  //System.out.println("Migration = null, flag = 0, noMigration!");
//                }
//                else
//                  flagMigration[0] = 1;
//
//                //System.out.println("IslModel : flagMigration = "+flagMigration[0]);
//                for(int i=1; i < gas.size(); i++)
//                {
//                  MPI.COMM_WORLD.Send(flagMigration, 0, 1, MPI.INT, i, 20);
//                }
//
//                
//                Migration[] obj2 = new Migration[1];
//                if(flagMigration[0] == 1){
//                	obj2[0]=migration;
//
//		            for(int i=1; i < gas.size(); i++)
//		            {
//		                MPI.COMM_WORLD.Send(obj2, 0,1, MPI.OBJECT, i, 200) ;
//		            }
//                }
//                
//                //RUN MAIN PROCESS
//                System.out.println("Main Process sent all the objects and is starting to run main clycle of its EA!");
//                Island island = gas.get(0);
//                if(island!=null){
//                  //INITIALIZA POP
//                  island.init();
//                  //MAIN CYCLE
//                  ArrayList<Island> islands = new ArrayList<Island>(1);
//                  islands.add(island);
//                  int currentIteration = 1;
//                  while(currentIteration <= numberGenerations+1){//totalIters
//                    if(migration!=null){
//                        if((currentIteration%migration.itStep)==0){
//                            int bool_migration = migration.migrateMPI(islands);
//                            if(bool_migration == 0)
//                                System.out.println("Migration Failed");
//                        }
//                        if(!migration.syncronized && !migration.migCompleted){
//                        	migration.probeNow(islands.get(0));
//                        	System.out.println("Probe NOW_Main!!");
//                        }
//                    }
//                    island.step();
//                    currentIteration++;
//                  }
//                }
//                this.gas.set(0,island); 
//		if(migration!=null)	System.out.println("NumberOfMigrations = "+migration.getnMig());
//                for(int i=1;i<gas.size();i++){
//                    obj=new Island[1];
//                    MPI.COMM_WORLD.Recv(obj, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 100);
//                    this.gas.set(i,obj[0]);
//                    System.out.println("Master Process Received Island "+obj[0].hashCode());
//                }
//		//Result per Island
//		for(int i=0; i < gas.size(); i++){
//			island=gas.get(i);
//			IAlgorithmResult algorithmResult = island.ga.getAlgorithmState().getAlgorithmResult();
//			island.setAlgorithmResult(algorithmResult);
//		}
//	}
	
	public void run_mpi_master(IAlgorithm algorithm) throws Exception, InvalidConfigurationException
	{
		System.out.println("Running Island Model with MPI..");
		System.out.println("");
		System.out.println("");
		
		//Send migration and topology objects to secondary processes if desired
		Island island = new Island(algorithm);
        island.setID(0);
        
        //Verify if migration is desired
        int[] flagMigration =new int[1];
        if(migration==null){
          flagMigration[0] = 0;
          //System.out.println("Migration = null, flag = 0, noMigration!");
        }
        else
          flagMigration[0] = 1;

        
        for(int i=1; i < mgp.getNumberPopulations(); i++)
        {
          MPI.COMM_WORLD.Send(flagMigration, 0, 1, MPI.INT, i, 20);
        }
        
        
        if(migration!=null){
        	Migration[] obj2 = new Migration[1];
            if(flagMigration[0] == 1){
            	obj2[0]=migration;

	            for(int i=1; i < mgp.getNumberPopulations(); i++)
	            {
	                MPI.COMM_WORLD.Send(obj2, 0,1, MPI.OBJECT, i, 200) ;
	            }
            }
            
            IMTopology[] obj=new IMTopology[1];
            obj[0]=this.topology;
            for(int i=1; i < mgp.getNumberPopulations(); i++)
            {
              MPI.COMM_WORLD.Send(obj, 0,1, MPI.OBJECT, i, 50) ;
            }
            
            int [][] edges = topology.getGraph().getAllEdges();
	        int numIndivsMigration = migration.numMigrants;
	        for(int i=0; i < edges.length; i++)
			{
	        	if(0==edges[i][0]){
	        		MigrationBuffer mb = new MigrationBuffer(edges[i][0], edges[i][1],numIndivsMigration);
	    			mb.setNumIndivs(numIndivsMigration);
	    			island.addOutBuffer(mb);
	        	}
			}
	        for(int i=0; i < edges.length; i++)
			{
	        	if(0==edges[i][1]){
	        		MigrationBuffer mb = new MigrationBuffer(edges[i][0], edges[i][1],numIndivsMigration);
	    			mb.setNumIndivs(numIndivsMigration);
	    			island.addInBuffer(mb);
	        	}
			}
        }
        
        System.out.println("Main Process sent all the objects and is starting to run main clycle of its EA!");

        //Run EA
        
        if(island!=null){
        	//Initialize population
        	island.init();
        	//Main cycle
        	ArrayList<Island> islands = new ArrayList<Island>(1);
        	islands.add(island);
        	int currentIteration = 1;
        	while(currentIteration <= numberGenerations+1){
	        	//Possible Migration
				if(migration!=null){
				    if((currentIteration%migration.itStep)==0){
				        int bool_migration = migration.migrateMPI(islands);
				        if(bool_migration == 0)
				            System.out.println("Migration Failed");
				    }
				    if(!migration.syncronized && !migration.migCompleted){
				    	migration.probeNow(islands.get(0));
				    }
				}
				//Apply Iteration
				island.step();
				currentIteration++;
	        }
        	island.getGa().terminate_par();
        }
        
        gas = new ArrayList<Island>(mgp.getNumberPopulations());
        this.gas.add(0,island); 
        
		if(migration!=null)	
			System.out.println("NumberOfMigrations = "+migration.getnMig());
		
		//Receive island objects
		Island[] obj3 = new Island[1];
		for(int i=1;i<mgp.getNumberPopulations();i++){
        	obj3=new Island[1];
            MPI.COMM_WORLD.Recv(obj3, 0, 1, MPI.OBJECT, MPI.ANY_SOURCE, 100);
            this.gas.add(i,obj3[0]);
            System.out.println("Master Process Received Island "+obj3[0].hashCode());
        }
		
		//Set result per island
		for(int i=0; i < gas.size(); i++){
			island=gas.get(i);
			IAlgorithmResult algorithmResult = island.ga.getAlgorithmState().getAlgorithmResult();
			island.setAlgorithmResult(algorithmResult);
		}
	}
	
	public void writeLastResults()
	{
		for(int i=0;i<gas.size();i++)
		{
			//System.out.println("Maximization - "+gas.get(i).getAlgorithmResult().getSolutionContainer().getBestSolutionCellContainer(true).getSolution().getScalarFitnessValue());
			//System.out.println("Minimization - "+gas.get(i).getAlgorithmResult().getSolutionContainer().getBestSolutionCellContainer(false).getSolution().getScalarFitnessValue());
			System.out.println("Island Number "+(i+1)+
					" , ITER - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getNumberOfIterations()+
					" , NFE - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getTotalNumberOfFunctionEvaluations()+
					" , TIME - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getTotalExecutionTime()+
					" , MAX - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getRunMaxScalarFitnessValue()+
					" , MIN - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getRunMinScalarFitnessValue()+
					" , MEAN - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getRunMeanScalarFitnessValue()+
					" , OBS - "+gas.get(i).ga.getAlgorithmState().getAlgorithmResult().getAlgorithmStatistics().getNumberOfObjectives()
					);
		}
	}
}
