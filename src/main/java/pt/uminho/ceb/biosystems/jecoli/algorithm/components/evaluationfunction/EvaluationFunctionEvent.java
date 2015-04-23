package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import java.util.EventObject;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;

public class EvaluationFunctionEvent<T extends IRepresentation> extends EventObject {

	public static final String SINGLE_SOLUTION_EVALUATION_EVENT = "singleSolutionEvaluated";
	public static final String SOLUTIONSET_EVALUATION_EVENT = "solutionSetEvaluated";
	
	private static final long serialVersionUID = -5003895925688369727L;
	private String id;
	private String message;
	private ISolution<T> solution = null;
	private ISolutionSet<T> solutionSet = null;

	public EvaluationFunctionEvent(Object source, String id, String message){
		super(source);
		this.id = id;
		this.message = message;
	}
	
	public EvaluationFunctionEvent(Object source,String id, String message, ISolution<T> solution) {
		this(source,id,message);
		this.solution = solution;
	}
	
	public EvaluationFunctionEvent(Object source,String id, String message, ISolutionSet<T> solutionSet) {
		this(source,id,message);
		this.solutionSet = solutionSet;
	}


	public ISolution<T> getSolution() {
		return solution;
	}


	public String getMessage() {
		return message;
	}


	public String getId() {
		return id;
	}

	public ISolutionSet<T> getSolutionSet() {
		return solutionSet;
	}


}
