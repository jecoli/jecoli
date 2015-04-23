package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.comparator;

import java.util.Comparator;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public class RepresentationComparator<T extends IRepresentation> implements Comparator<T> {

	@Override
	public int compare(T arg0, T arg1) {
		
		String sol0 = arg0.stringRepresentation();
		
		String sol1 = arg1.stringRepresentation();
		
		return sol0.compareTo(sol1);			
	}

}
