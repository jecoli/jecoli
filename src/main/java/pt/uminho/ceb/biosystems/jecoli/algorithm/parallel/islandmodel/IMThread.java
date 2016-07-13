package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import java.util.ArrayList;

public class IMThread extends Thread{
	
	private Island island;
	private int ID;
	private Migration migration;
	private int totalIters;
	private boolean bool_migration=true;
	
	public IMThread(int id,Island i,int tI,Migration m)
	{
		this.ID=id;
		this.island=i;
		this.totalIters=tI;
		this.migration=m;
	}
	
	public Migration getMigration()
	{
		return this.migration;
	}
	
	public int getThreadID()
	{
		return this.ID;
	}
	
	public Island getIsland()
	{
		return this.island;
	}
	
	@Override
	public  void run()
	{    	
		try
		{
			System.out.println("THREAD");
			try 
			{ 
				//Initialize population
				island.init();
				
				
				//Main cycle
				ArrayList<Island> islands = new ArrayList<Island>(1);
				islands.add(this.island);
				int currentIteration=1;
				
				while(currentIteration <= totalIters+1){
					
//					//Possible migration
					if(migration!=null){
						if((currentIteration%migration.itStep)==0){
								if(this.ID==0)	System.out.println("Migration!");
								bool_migration = migration.migrate(islands);
						}
						else if(!migration.syncronized && !bool_migration){
							bool_migration=migration.testAndTrade(islands.get(0));
							//System.out.println("testAndTrade!!");
						}
					}
					//Apply iteration(step)
					island.step();
					currentIteration++;
					
//					Footprint footprint;
//					Long memory = MemoryMeasurer.measureBytes(island);
//					footprint = ObjectGraphMeasurer.measure(island);
//					System.out.println("AAAAAAAAAAA ObjectGraphMeasurer FermProcess = "+footprint);
//					System.out.println("BBBBBBBBBBB MemoryMeasurer FermProcess = "+memory/1024+"KB");
				}
				
			}
        	catch (Exception e){ e.printStackTrace(); }
	    }
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        System.exit(1);
		}
	}

}
