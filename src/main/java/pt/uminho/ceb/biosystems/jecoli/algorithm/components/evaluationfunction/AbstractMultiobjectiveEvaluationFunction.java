package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.aggregation.IAggregationFunction;

public abstract class AbstractMultiobjectiveEvaluationFunction<T extends IRepresentation> implements IEvaluationFunction<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 608090232509317733L; 
	
	 /** The is maximization. */
    protected boolean isMaximization;
    
    protected List<IEvaluationFunctionListener<T>> listeners = null; 
    
    protected IAggregationFunction fitnessAggregation = null;
    
    protected boolean performFitnessAggregation = false;

    /**
     * Instantiates a new evaluation function.
     * 
     * @param isMaximization the is maximization
     */
    public AbstractMultiobjectiveEvaluationFunction(boolean isMaximization) {
        this.isMaximization = isMaximization;
    }
    

    @Override
    public void evaluate(ISolutionSet<T> solutionSet) {
        for (int i = 0; i < solutionSet.getNumberOfSolutions(); i++) {
            ISolution<T> solution = solutionSet.getSolution(i);
            evaluateSingleSolution(solution);                        	
        }
        if(listeners!=null && !listeners.isEmpty())
        	notifyEvaluationFunctionListeners(EvaluationFunctionEvent.SOLUTIONSET_EVALUATION_EVENT, String.valueOf(solutionSet.getNumberOfSolutions()), solutionSet);
    }

    @Override
    public void evaluateSingleSolution(ISolution<T> solution) {
        Double[] fitnessValues = null;
        
        try {
        	fitnessValues = evaluateMO(solution.getRepresentation());
        	
        	boolean fitsOK = true;
        	
        	for(int i=0; i<fitnessValues.length; i++){
        		if(fitnessValues[i]==null){
        			if(isMaximization)
                     	fitnessValues[i]=Double.MIN_VALUE;
                    else
                    	fitnessValues[i]=Double.MAX_VALUE;
        			
        			fitsOK = false;
        		}
        			
        	}

           	solution.setFitnessValues(fitnessValues);
           	if(fitnessValues.length==1)
           		solution.setScalarFitnessValue(fitnessValues[0]);
           	
           	if(performFitnessAggregation){
           		double rawfitness;
           		if(fitsOK)
           			rawfitness = fitnessAggregation.aggregate(fitnessValues);
           		else
           			rawfitness = (isMaximization) ? -Double.MAX_VALUE : Double.MAX_VALUE; //NOTE: pmaia changed this, was using Double.MIN_VALUE instead of -Double.MAX_VALUE
                     	               
           		solution.setScalarFitnessValue(rawfitness);
           	}
            
        } catch (Exception e) {
        	 e.printStackTrace();
        		if(isMaximization)
                 	solution.setScalarFitnessValue(Double.MIN_VALUE);
                else
                 	solution.setScalarFitnessValue(Double.MAX_VALUE);
        } 
        
        if(listeners!=null && !listeners.isEmpty()){
        	notifyEvaluationFunctionListeners(EvaluationFunctionEvent.SINGLE_SOLUTION_EVALUATION_EVENT, "", solution);
//        	System.out.println("notify: scalar="+solution.getScalarFitnessValue()+" / selection="+solution.getSelectionValue()+" / fits="+Arrays.toString(solution.getFitnessValuesArray()));
        }
    }


	public boolean isMaximization() {
        return isMaximization;
    }
	
	public abstract int getNumberOfObjectives();

    /**
     * Evaluate.
     * 
     * @param solutionRepresentation the solution representation
     * 
     * @return the list 
     * 
     * @throws Exception the exception
     */
    public abstract Double[] evaluateMO(T solutionRepresentation) throws Exception;
    
    synchronized  public void notifyEvaluationFunctionListeners(String id,String message,ISolution<T> solution){
    	
    	EvaluationFunctionEvent<T> evaluationFunctionEvent = new EvaluationFunctionEvent<T>(this,id,message,solution);
    	
    	for(IEvaluationFunctionListener<T> listenerObject: listeners)
    		listenerObject.processEvaluationFunctionEvent(evaluationFunctionEvent);
    }
    
    synchronized public void notifyEvaluationFunctionListeners(String id,String message,ISolutionSet<T> solutionSet){
    	
    	EvaluationFunctionEvent<T> evaluationFunctionEvent = new EvaluationFunctionEvent<T>(this,id,message,solutionSet);
    	
    	for(IEvaluationFunctionListener<T> listenerObject: listeners)
    		listenerObject.processEvaluationFunctionEvent(evaluationFunctionEvent);
    }


	public List<IEvaluationFunctionListener<T>> getEvaluationFunctionListeners() {
		return listeners;
	}


	public void setListeners(List<IEvaluationFunctionListener<T>> listeners) {
		this.listeners = listeners;
	}
	
	public void addEvaluationFunctionListener(IEvaluationFunctionListener<T> listener){
		if(listeners==null)
			listeners = new ArrayList<IEvaluationFunctionListener<T>>();
		
		listeners.add(listener);
	}


	public IAggregationFunction getFitnessAggregation() {
		return fitnessAggregation;
	}


	public void setFitnessAggregation(IAggregationFunction fitnessAggregation) {
		this.fitnessAggregation = fitnessAggregation;
		if(fitnessAggregation!=null)
			performFitnessAggregation = true;
	}
    
    

}
