package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import java.util.ArrayList;

import mpi.MPI;
import mpi.MPIException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;

public class MPISecondaryProcess implements java.io.Serializable{
	
	int myrank;
	
	int numGen;
	
	IAlgorithm algorithm;
	
	public MPISecondaryProcess(int rank, int nGen,IAlgorithm alg) {
		this.myrank = rank;
		this.numGen = nGen;
		this.algorithm=alg;
	}
	
	public int run_mpi_secondary(){
		int res=1;
		try{
			//Receive topology and migration objects before running the EA
	        Island island = new Island(algorithm);
	        island.setID(myrank);
	        
	        //Verify if migration is desired
	        int[] flagMigration= new int[1];
	        MPI.COMM_WORLD.Recv(flagMigration, 0, 1, MPI.INT, 0, 20);
	        
	        //If migration is desired, receive migration and topology objects
	        Migration[] obj2 = new Migration[1];
	        if(flagMigration[0] == 1){
	        	MPI.COMM_WORLD.Recv(obj2, 0, 1, MPI.OBJECT, 0, 200);
	        	if(obj2 != null)
	        		System.out.println("FermOptIM::Migration Object Received, numMigrants = "+obj2[0].numMigrants);  
	          
	          	IMTopology[] obj = new IMTopology[1];
		        MPI.COMM_WORLD.Recv(obj, 0, 1, MPI.OBJECT, 0, 50) ;
		        if(obj != null) 
		            System.out.println("FermOptIM::Topology Object Received");
		        
		        //Set some information about the pretended topology
		        IMTopology topology = obj[0];
		        int [][] edges = topology.getGraph().getAllEdges();
		        
		        int numIndivsMigration = obj2[0].numMigrants;
		        
		        for(int i=0; i < edges.length; i++)
				{
		        	if(myrank==edges[i][0]){
		        		MigrationBuffer mb = new MigrationBuffer(edges[i][0], edges[i][1],numIndivsMigration);
		    			mb.setNumIndivs(numIndivsMigration);
		    			island.addOutBuffer(mb);
		        	}
				}
		        
		        for(int i=0; i < edges.length; i++)
				{
		        	if(myrank==edges[i][1]){
		        		MigrationBuffer mb = new MigrationBuffer(edges[i][0], edges[i][1],numIndivsMigration);
		    			mb.setNumIndivs(numIndivsMigration);
		    			island.addInBuffer(mb);
		        	}
				}
	        }
	        else{
	          obj2[0]=null;
	        }
	        
	        //Run EA
	        
	        String processor_name = MPI.Get_processor_name();
	        System.out.println("My rank is "+myrank+" and my processor name is "+processor_name);
	        Migration migration = obj2[0];
	        
	        if(island!=null){
	        	//Initialize population
				island.init();
				//Main cycle
				ArrayList<Island> islands = new ArrayList<Island>(1);
				islands.add(island);
				int currentIteration=1;
				while(currentIteration <= this.numGen+1 ){
			        //Possible migration
	                if(migration!=null){
	                        if((currentIteration%migration.itStep) == 0 ){
	                            int bool_migration = migration.migrateMPI(islands);
	                            if(bool_migration == 0)
	                                  System.out.println("Migration Failed");
	                        }
	                        if(!migration.syncronized && !migration.migCompleted){
	                        	migration.probeNow(islands.get(0));
	                        	System.out.println("Probe NOW!!");
	                        }
	                }
	                //Apply iteration
					island.step();
					currentIteration++;
				}
				island.getGa().terminate_par();
				Island[] obj3 = new Island[1];
	            obj3[0] = island;
	            MPI.COMM_WORLD.Send(obj3, 0, 1, MPI.OBJECT, 0, 100);
	        }
	        else{
	        	System.out.println("Ilha = null");
	        	res=0;
	        }
		} catch (MPIException e) { 
			res=0;
			e.printStackTrace();	
		} catch (NumberFormatException e) {
			res=0;
			e.printStackTrace();
		} catch (Exception e) {
			res=0;
			e.printStackTrace();
		}
		
		return res;
	}
	
	public void setAlg(IAlgorithm alg){
		this.algorithm=alg;
	}
}
