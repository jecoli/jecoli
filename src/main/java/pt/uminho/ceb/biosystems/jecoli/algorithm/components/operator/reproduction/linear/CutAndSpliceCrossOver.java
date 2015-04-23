package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.LinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;


public class CutAndSpliceCrossOver<G> extends AbstractCrossoverOperator<ILinearRepresentation<G>,ILinearRepresentationFactory<G>> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<ISolution<ILinearRepresentation<G>>> crossOverGenomes(ILinearRepresentation<G> parentGenome,ILinearRepresentation<G> parent1Genome,
			                                                             ILinearRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator) {
		int parentGenomeSize = parentGenome.getNumberOfElements();
		int parent1GenomeSize = parent1Genome.getNumberOfElements();
		List<ISolution<ILinearRepresentation<G>>> solutionList = new ArrayList<ISolution<ILinearRepresentation<G>>>();

		/*if((parentGenomeSize == 0) || (parent1GenomeSize == 0)) {
			solutionList.add(new Solution<ILinearRepresentation<G>>(parentGenome));
			solutionList.add(new Solution<ILinearRepresentation<G>>(parent1Genome));
			return solutionList;
		}*/

		int parentCrossoverPosition = randomNumberGenerator.nextInt(parentGenomeSize);//(int) (randomNumberGenerator.nextDouble()*(parentGenomeSize-1))+1;
		int parent1CrossoverPosition = randomNumberGenerator.nextInt(parent1GenomeSize);//(int) (randomNumberGenerator.nextDouble()*(parent1GenomeSize-1))+1;
		
		
		List<G> childGenome = new ArrayList<G>();	
		List<G> childGenome1 = new ArrayList<G>();

		for(int i=0; i< parentCrossoverPosition+1; i++){
			G parentValue = parentGenome.getElementAt(i);
			childGenome.add(parentValue);
		}
		
		int childEndPosition = parentCrossoverPosition+ (parent1GenomeSize-parent1CrossoverPosition);
		for(int j = parentCrossoverPosition+1; j < childEndPosition;j++){
			int parent1GeneIndex = parent1CrossoverPosition+(j-parentCrossoverPosition);
			G parent1Value = parent1Genome.getElementAt(parent1GeneIndex);
			childGenome.add(parent1Value);
		}
		
		
		for(int i=0; i< parent1CrossoverPosition+1; i++){
			G parent1Value = parent1Genome.getElementAt(i);
			childGenome1.add(parent1Value);
		}
		
		int child1EndPosition = parent1CrossoverPosition +(parentGenomeSize-parentCrossoverPosition);
		for(int j = parent1CrossoverPosition+1; j < child1EndPosition;j++){
			int parentGeneIndex = parentCrossoverPosition+(j-parent1CrossoverPosition);
			G parentValue = parentGenome.getElementAt(parentGeneIndex);
			childGenome1.add(parentValue);
		}
		
		int numObjs = solutionFactory.getNumberOfObjectives();
		
		ISolution<ILinearRepresentation<G>> childSolution = 
				new Solution<ILinearRepresentation<G>>(new LinearRepresentation<G>(childGenome), numObjs);
		ISolution<ILinearRepresentation<G>> child1Solution = 
				new Solution<ILinearRepresentation<G>>(new LinearRepresentation<G>(childGenome1), numObjs);

        solutionList.add(child1Solution);
        solutionList.add(childSolution);
		return solutionList;
	}

	@Override
	public CutAndSpliceCrossOver<G>  deepCopy() {
		return new CutAndSpliceCrossOver<G>();
	}
}
