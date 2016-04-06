package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.dualset;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set.SetUniformCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.SetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;

/**
 * Created by ptiago on 14-01-2015.
 */
public class DualSetUniformCrossoverWrapper extends AbstractDualSetCrossoverOperator {
	
	SetUniformCrossover<Integer>	setUniformCrossover;
	List<Integer>					genomeApplicationList;
									
	public DualSetUniformCrossoverWrapper(List<Integer> genomeApplicationList, List<IntegerSetRepresentationFactory> representationSolutionFactoryVector) {
		this.genomeApplicationList = genomeApplicationList;
		setUniformCrossover = new SetUniformCrossover<>();
		this.representationSolutionFactoryVector = representationSolutionFactoryVector;
	}
	
	@Override
	public void crossoverGenomes(DualSetRepresentation parentGenome1, DualSetRepresentation parentGenome2, DualSetRepresentation childGenome1, DualSetRepresentation childGenome2,DualSetRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator) {
			
		for (Integer individualIndex : genomeApplicationList) {
			List<ISolution<ISetRepresentation<Integer>>> newSolutionList = new ArrayList<>();
			try {
				TreeSet<Integer> parent1SetRepresentation = parentGenome1.getIndividualRepresentation(individualIndex);
				TreeSet<Integer> parent2SetRepresentation = parentGenome2.getIndividualRepresentation(individualIndex);
				newSolutionList.add(new Solution<ISetRepresentation<Integer>>(new SetRepresentation<Integer>(parent1SetRepresentation)));
				newSolutionList.add(new Solution<ISetRepresentation<Integer>>(new SetRepresentation<Integer>(parent2SetRepresentation)));
				IntegerSetRepresentationFactory setSolutionFactory = getSetSolutionFactory(individualIndex);
				List<ISolution<ISetRepresentation<Integer>>> newChildGenomeList = setUniformCrossover.apply(newSolutionList, setSolutionFactory, randomNumberGenerator);
				copyChildSolutions(individualIndex, childGenome1, newChildGenomeList.get(0).getRepresentation().getGenome());
				copyChildSolutions(individualIndex, childGenome2, newChildGenomeList.get(1).getRepresentation().getGenome());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public DualSetUniformCrossover deepCopy() throws Exception {
		return new DualSetUniformCrossover();
	}
}
