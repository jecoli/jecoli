package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;

public interface IArchivedAlgorithmState<T extends IRepresentation> {
	
	ISolutionSet<T> getArchive();
	
	void updateArchiveState(ISolutionSet<T> newArchive);

}
