package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.AMFunctionType;

public class RepeatedTrimming<T extends IRepresentation> implements ITrimmingFunction<T>{

	@Override
	public ISolutionSet<T> trimm(ISolutionSet<T> original) {
		
		ISolutionSet<T> trimmedSet = new SolutionSet<T>();
		
		for(ISolution<T> solution : original.getListOfSolutions()){
			if(!trimmedSet.containsGenomeOnly(solution))
				trimmedSet.add(solution);
		}
		
		return trimmedSet;
	}
	
	@Override
	public AMFunctionType getFunctionType() {
		return AMFunctionType.TRIMMER;
	}

}
