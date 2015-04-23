/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;

/**
 * @author pmaia
 *
 * Jan 9, 2013
 */
public interface ISetRepresentationFactory<G> extends ISolutionFactory<ISetRepresentation<G>> {
	
	G generateGeneValue(IRandomNumberGenerator randomNumberGenerator);
	ISolution<ISetRepresentation<G>> generateSolution(int size, IRandomNumberGenerator randomGenerator);
	int getMaxSetSize();
	int getMinSetSize();
	G getMaxElement();

	ISetRepresentationFactory<G> deepCopy();

}
