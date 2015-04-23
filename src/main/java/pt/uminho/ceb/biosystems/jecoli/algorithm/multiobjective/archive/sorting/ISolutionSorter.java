package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.sorting;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;

public interface ISolutionSorter<T extends IRepresentation> {
	
	public void sort(ISolution<T> solution);

}
