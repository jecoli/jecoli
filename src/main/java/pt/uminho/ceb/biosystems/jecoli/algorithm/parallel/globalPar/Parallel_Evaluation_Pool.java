package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.globalPar;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public class Parallel_Evaluation_Pool<T extends IRepresentation> implements java.io.Serializable{

	private int nThreads;
	protected static Evaluation_Task[] threads;
    private static ExecutorService pool;
	private static Future<?>[] output;
//	public long eval_time;
	//private static IEvaluationFunction[] evals;
	//boolean cloneEval;
	
	public Parallel_Evaluation_Pool(int numT){
		System.out.println("Parallel_Evaluation_Pool initialization, number of threads="+numT);
		nThreads=numT;
		threads = new Evaluation_Task[numT];
		pool = Executors.newFixedThreadPool(nThreads);
  	  	output = new Future<?>[numT];
//  	  	eval_time=0;
	}
	
	public int getNThreads()
	{
	  return nThreads;
	}
	
	public int parallel(AbstractMultiobjectiveEvaluationFunction<T> AbsEvaluationFunction, ISolutionSet<T> solutionSet){
		int res=0;
		if(nThreads>solutionSet.getNumberOfSolutions())
			System.out.println("nThreads="+nThreads+", NumberOfSolutions="+solutionSet.getNumberOfSolutions());	
			//throw new Error("Number of threads exceeds number of solutions\n\n");

		ISolutionSet[] list=divideSet((SolutionSet) solutionSet,nThreads);
		
		for(int i=0;i<nThreads;i++)
		{
			 Evaluation_Task th = new Evaluation_Task(AbsEvaluationFunction,list[i]);
			 threads[i]=th;
		}
		
		for(int k=0;k<nThreads;k++)
		{	 
			 output[k]=pool.submit(threads[k]);
		}
		
		for(int j=0;j<nThreads;j++)
		{
			try {
				output[j].get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		res=1;
		return res;
	}
	
	
	public int parallel(AbstractEvaluationFunction<T> AbsEvaluationFunction, ISolutionSet<T> solutionSet){
//		long startTime = System.nanoTime();
		int res=0;
		if(nThreads>solutionSet.getNumberOfSolutions())
			System.out.println("nThreads="+nThreads+", NumberOfSolutions="+solutionSet.getNumberOfSolutions());	
			//throw new Error("Number of threads exceeds number of solutions\n\n");
		
		if(threads==null) threads = new Evaluation_Task[nThreads];
        if(pool==null)  pool = Executors.newCachedThreadPool();
        if(output==null)  output = new Future<?>[nThreads];
		//System.out.println("GP Threads Running: Number of solutions to evaluate = "+solutionSet.getNumberOfSolutions());

		ISolutionSet[] list=divideSet((SolutionSet) solutionSet,nThreads);
		
		for(int i=0;i<nThreads;i++)
		{
			Evaluation_Task th = new Evaluation_Task(AbsEvaluationFunction,list[i]);
			threads[i]=th;
		}
//		eval_time += System.nanoTime() - startTime;
		
		for(int k=0;k<nThreads;k++)
		{	 
			output[k]=pool.submit(threads[k]);
		}
		
		for(int j=0;j<nThreads;j++)
		{
			try {
				output[j].get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		res=1;
//		System.out.println("Total Parallel Time (mili): " + eval_time/1000000);
		return res;
	}
	
	private static ISolutionSet[] divideSet(SolutionSet solutionSet,int nThreads)
	{
	    ISolutionSet[] list=new ISolutionSet[nThreads];
	    int size=solutionSet.getNumberOfSolutions();
	    List<ISolution> solutions=solutionSet.getListOfSolutions();
	    int mid=size/nThreads;
	    int start=0;int last;
	    for(int i=1;i<=(nThreads);i++)
	    {
	    	last=start+mid;
	    	if(!((size%nThreads)>=i))
	    		last=last-1;
	    	list[i-1]=subList(solutionSet,start,last);
	    	start = last+1;
	    }
	    return list;
	}
	
		
	private static ISolutionSet subList(SolutionSet solSet,int i,int f)
	{
		ISolutionSet finalSet=new SolutionSet(f-i);
		for(int j=i;j<=f;j++)
		{
			finalSet.add(solSet.getSolution(j));
		}
		return finalSet;
	}
	
	
	public void terminate(){
		System.out.println("PAR out");
		pool.shutdown();//wait for tasks before shutdown
		//pool.shutdownNow();
	}
	
}
