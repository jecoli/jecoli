package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import java.io.Serializable;

public interface IFitnessFunctionEvaluator<T> extends Serializable {
	double evaluate(T data) throws Exception;
}
