package pt.uminho.ceb.biosystems.jecoli.algorithm.components.tracker;

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;

public class TrackInfo<T extends IRepresentation> {
	
	int							_iteration	= 0;
	String						_operator;
	public List<ISolution<T>>	_parents;
	
	public TrackInfo(int iteration, String operator, List<ISolution<T>> parents) {
		_iteration = iteration;
		_operator = operator;
		_parents = parents;
	}
	
}
