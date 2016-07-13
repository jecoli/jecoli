package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.globalPar;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;

public class Evaluation_Task<T extends IRepresentation> implements Runnable{
	private AbstractEvaluationFunction<T> AbsevaluationFunction;
	private AbstractMultiobjectiveEvaluationFunction<T> MOAbsevaluationFunction;
	private ISolutionSet<T> subset;
	private int threadID;
	
	/* Evaluation.java
	public Eval_Thread(AbstractEvaluationFunction<T> AbsevaluationFunction, ISolutionSet<T> subset,int id) {
		this.AbsevaluationFunction=AbsevaluationFunction;
		this.subset=subset;
		//this.threadID=id;
	}
	*/
	
	
	public Evaluation_Task(AbstractMultiobjectiveEvaluationFunction<T> AbsevaluationFunction, ISolutionSet<T> subset) {
		this.MOAbsevaluationFunction=AbsevaluationFunction;
		this.subset=subset;
		//this.threadID=id;
	}
	
	public Evaluation_Task(AbstractEvaluationFunction<T> AbsevaluationFunction, ISolutionSet<T> subset) {
		this.AbsevaluationFunction=AbsevaluationFunction;
		this.subset=subset;
		//this.threadID=id;
	}
	
	
	@Override
	public void run()
	{
		//System.out.println("Number of solutions = "+subset.getNumberOfSolutions());
		for (int i = 0; i < subset.getNumberOfSolutions(); i++) {
			long startTime = System.nanoTime();
            ISolution<T> solution = subset.getSolution(i);
            AbsevaluationFunction.evaluateSingleSolution(solution); 
            long eval_time = System.nanoTime() - startTime;
//            System.out.println("Time of 1 evaluation = "+eval_time%1000);
        }
		
		//antigo:
		//evaluationFunction.evaluate(subset);
	}
	
}
