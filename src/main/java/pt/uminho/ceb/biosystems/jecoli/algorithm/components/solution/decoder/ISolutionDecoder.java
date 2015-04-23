package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.decoder;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public interface ISolutionDecoder<T extends IRepresentation,E> extends IDeepCopy {
	E decode(T representation) throws Exception;
}
