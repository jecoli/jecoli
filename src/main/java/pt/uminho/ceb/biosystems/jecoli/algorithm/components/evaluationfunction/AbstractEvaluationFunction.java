/**
* Copyright 2009,
* CCTC - Computer Science and Technology Center
* IBB-CEB - Institute for Biotechnology and  Bioengineering - Centre of Biological Engineering
* University of Minho
*
* This is free software: you can redistribute it and/or modify
* it under the terms of the GNU Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Public License for more details.
*
* You should have received a copy of the GNU Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
* 
* Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
* University of Minho
*/
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;

/**
 * The Class EvaluationFunction.
 */
public abstract class AbstractEvaluationFunction<T extends IRepresentation> implements IEvaluationFunction<T> {

	private static final long serialVersionUID = 7948503042698103889L;
	
	/** The is maximization. */
    protected boolean isMaximization;
    protected List<IEvaluationFunctionListener<T>> listeners = null;

    /**
     * Instantiates a new evaluation function.
     * 
     * @param isMaximization the is maximization
     */
    public AbstractEvaluationFunction(boolean isMaximization) {
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
        try {
        	Double fitnessValue = null;
        	fitnessValue = evaluate(solution.getRepresentation());
           	solution.setScalarFitnessValue(fitnessValue);
           	
        }catch(Exception e) {
        	e.printStackTrace();
    		if(isMaximization)
             	solution.setScalarFitnessValue(Double.NEGATIVE_INFINITY);
            else
             	solution.setScalarFitnessValue(Double.POSITIVE_INFINITY);
        }
        
        if(listeners!=null && !listeners.isEmpty())
        	notifyEvaluationFunctionListeners(EvaluationFunctionEvent.SINGLE_SOLUTION_EVALUATION_EVENT, "", solution);
    }


	public boolean isMaximization() {
        return isMaximization;
    }
	

	public int getNumberOfObjectives()
	{
		return 1;
	}

    /**
     * Evaluate.
     * 
     * @param solutionRepresentation the solution representation
     * 
     * @return the list< double>
     * 
     * @throws Exception the exception
     */
    public abstract double evaluate(T solutionRepresentation) throws Exception;
    
    public void notifyEvaluationFunctionListeners(String id,String message,ISolution<T> solution){
    	EvaluationFunctionEvent<T> evaluationFunctionEvent = new EvaluationFunctionEvent<T>(this,id,message,solution);
    	
    	for(IEvaluationFunctionListener<T> listenerObject: listeners)
    		listenerObject.processEvaluationFunctionEvent(evaluationFunctionEvent);
    }
    
    public void notifyEvaluationFunctionListeners(String id,String message,ISolutionSet<T> solutionSet){
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
		if(this.listeners == null)
			listeners = new ArrayList<IEvaluationFunctionListener<T>>();
		
		listeners.add(listener);
	}
}