package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public interface ILocalOptimizationOperator<T extends IRepresentation> extends IOperator {

	IEvaluationFunction<T> getEvaluationFunction ();
	
	int getNumberFunctionEvaluationPerfomed ();
	
	boolean hasImproved();
}
