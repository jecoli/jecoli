package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.AMFunctionType;

public class NullTrimming<T extends IRepresentation> implements ITrimmingFunction<T> {

	@Override
	public ISolutionSet<T> trimm(ISolutionSet<T> original) {
		return original;
	}

	@Override
	public AMFunctionType getFunctionType() {
		return AMFunctionType.TRIMMER;
	}

}
