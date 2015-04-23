package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.IArchiveManagementFunction;

public interface IPlotter<T extends IRepresentation> extends IArchiveManagementFunction {
	
	void plot(double[][] data);
	
	void plot(ISolutionSet<T> solutionSet);

}
