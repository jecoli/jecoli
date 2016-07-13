package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.globalPar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public class Parallel_Evaluation<T extends IRepresentation> {
	protected int numThreads;
	//protected static int nthreads=Integer.getInteger("nthreads",numThreads).intValue();  // para se definir o número de threads
	protected Thread[] threads;
	protected static AtomicInteger nextSerialNum = new AtomicInteger(0);
	//private int i=0;
	protected static ThreadLocal<Integer> serialNum = new ThreadLocal<Integer>() {
	  protected Integer initialValue()
	  {
	    return nextSerialNum.getAndIncrement();
	  }
	};
	
	public Parallel_Evaluation(int numT){
		numThreads=numT;
		threads = new Thread[numThreads];
	}
	 
	public int getNThreads()
	{
	  return numThreads;
	}

	public int parallel(AbstractEvaluationFunction<T> AbsEvaluationFunction, ISolutionSet<T> solutionSet){
		serialNum.get();
	    final List<ISolutionSet> list=divideSet((SolutionSet) solutionSet,numThreads);
	    //System.out.println("nthreads = "+ numThreads);
	    for(int i=0;i<numThreads;i++)
	    {
	    	Evaluation_Task<T> th = new Evaluation_Task<T>((AbstractEvaluationFunction<T>) AbsEvaluationFunction,list.get(i));
	    	threads[i]=new Thread(th);
	    	threads[i].start();
	    }
	    /*for(int i=0;i<numThreads;i++)
	    {
	      try{
	        threads[i].join();
	      }catch(InterruptedException e){
	        e.printStackTrace();
	      }
	    }*/
		
	    solutionSet.removeAll();
	   
	    ISolutionSet finalSet=new SolutionSet(solutionSet.getNumberOfSolutions());
		for(int i=0;i<list.size();i++)
		{	
			solutionSet.add(list.get(i).getListOfSolutions());
			finalSet.add(list.get(i).getListOfSolutions());
		}
		//i++;
		
		return 1;
	} 
	
	
	
	
	public static List<ISolutionSet> divideSet(SolutionSet solutionSet,int n)
	{
		int i;
		//final Cloner cloner = new Cloner();
		List<ISolutionSet> list=new ArrayList<ISolutionSet>(n);
		List<ISolution> sol=solutionSet.getListOfSolutions();
		for(i=0;i<(n-1);i++)
		{
			list.add(new SolutionSet(subList(sol,(solutionSet.getNumberOfSolutions()/n)*i,(solutionSet.getNumberOfSolutions()/n)*(i+1))));
		}
		list.add(new SolutionSet(subList(sol,(solutionSet.getNumberOfSolutions()/n)*i,solutionSet.getNumberOfSolutions())));

		return list;
	}
	
	public static List<ISolution> subList(List<ISolution> list,int i,int f)
	{
		//final Cloner cloner = new Cloner();
		List<ISolution> result=new ArrayList<ISolution>(f-i);
		for(int j=i;j<f;j++)
		{
			result.add(list.get(j));
		}
		return (List<ISolution>) result;
	}
}

