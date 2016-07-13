package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import java.util.ArrayList;
import java.util.Random;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;

public class Island implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	protected IAlgorithm ga;
	
	protected ArrayList<MigrationBuffer> inBuffers;
	
	protected ArrayList<MigrationBuffer> outBuffers;
		
	protected IAlgorithmResult algorithmResult;
	
	protected ISolutionSet currentSolutionSet;
		
	protected int code;
	
	protected int id_topology;
	
	public Island()
	{
		this.ga = null;
		inBuffers = null;
		outBuffers = null;
		Random r=new Random();
		code = r.nextInt(1000);
		currentSolutionSet=null;
	}
	
	public Island(IAlgorithm ga)
	{
		this.ga = ga;
		inBuffers = new ArrayList<MigrationBuffer>();
		outBuffers = new ArrayList<MigrationBuffer>();
		Random r=new Random();
		code = r.nextInt(1000);
		currentSolutionSet=null;
	}
	
	@Override
	public int hashCode()
	{
		return code;
	}
	
	public int getID(){
		return id_topology;
	}
	
	public void setID(int id){
		this.id_topology=id;
	}
	
	public ArrayList<MigrationBuffer> getOutBuffers()
	{
		return outBuffers;
	}
	
	public ArrayList<MigrationBuffer> getInBuffers()
	{
		return inBuffers;
	}
	
	public void addInBuffer(MigrationBuffer mb)
	{
		inBuffers.add(mb);
	}
	
	public void addOutBuffer(MigrationBuffer mb)
	{
		outBuffers.add(mb);
	}
	
	public void step() throws Exception, InvalidConfigurationException//, NonExistingNodeTypeException
	{ 
		currentSolutionSet = ga.iteration(ga.getAlgorithmState(), currentSolutionSet);
		
		AlgorithmState as = ga.getAlgorithmState();
		as.updateState(currentSolutionSet);
		
	}
	
	public void init() throws Exception, InvalidConfigurationException//, NonExistingNodeTypeException
	{ 
		System.out.println("init hashCode() = "+hashCode());
		
		ga.getConfiguration().verifyConfiguration();
		
		//runInitialization();
		AlgorithmState as = ga.getAlgorithmState();
		as.initializeState();
		ISolutionSet solutionSet = ga.initialize();
		as.setSolutionSet(solutionSet);
		as.updateState(solutionSet);
		
		currentSolutionSet = solutionSet;
	}
	

	public IAlgorithm getGa() {
		return ga;
	}

	public void setGa(IAlgorithm ga) {
		this.ga = ga;
	}
	
	public IAlgorithmResult getAlgorithmResult()
	{
		return algorithmResult;
	}
	
	public void setAlgorithmResult(IAlgorithmResult algorithmResult)
	{
		this.algorithmResult=algorithmResult;
	}
}
