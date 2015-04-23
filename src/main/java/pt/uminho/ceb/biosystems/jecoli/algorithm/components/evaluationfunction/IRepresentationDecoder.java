package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import java.io.Serializable;

public interface IRepresentationDecoder<S,E> extends Serializable{
	E decodeSolution(S solutionRepresentation) throws Exception;
}
