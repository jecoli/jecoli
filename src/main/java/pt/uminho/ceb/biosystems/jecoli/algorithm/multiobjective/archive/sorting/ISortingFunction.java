package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.sorting;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.IArchiveManagementFunction;

public interface ISortingFunction<T extends IRepresentation> extends IArchiveManagementFunction {

	public void sort(ISolutionSet<T> original);
}
