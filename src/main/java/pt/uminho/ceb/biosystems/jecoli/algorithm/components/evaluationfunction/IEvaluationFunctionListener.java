package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public interface IEvaluationFunctionListener<T extends IRepresentation> {
	
	void processEvaluationFunctionEvent(EvaluationFunctionEvent<T> event);

}
