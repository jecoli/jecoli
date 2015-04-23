package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.IArchiveManagementFunction;

public interface ITrimmingFunction<T extends IRepresentation> extends IArchiveManagementFunction {
	
	ISolutionSet<T> trimm(ISolutionSet<T> original);
	

}
