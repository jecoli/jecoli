package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public abstract class AbstractVariableSizeRepresentationFactory <G> implements ILinearRepresentationFactory<G> ,Serializable {
	private static final long serialVersionUID = 1L;

	/** The solution size. */
	protected int minSize;
	protected int maxSize;
	protected int numberOfObjectives;

	/**
	 * Instantiates a new abstract linear representation factory.
	 * 
	 * @param maxSetSize the solution size
	 */
	public AbstractVariableSizeRepresentationFactory(int minSize,int maxSize){
		this.minSize = minSize;
		this.maxSize = maxSize;
		numberOfObjectives = 1;
	}

	public AbstractVariableSizeRepresentationFactory(int minSize,int maxSize,int numberOfObjectives){
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.numberOfObjectives = numberOfObjectives;
	}

	@Override
	public ISolution<ILinearRepresentation<G>> generateSolution(IRandomNumberGenerator randomGenerator) {
		int solutionSize = (int) (randomGenerator.nextDouble()*(maxSize-minSize)+minSize);
		return generateSolution(solutionSize,randomGenerator);
	}

	@Override
	public ISolution<ILinearRepresentation<G>> copySolution(ISolution<ILinearRepresentation<G>> solutionToCopy) {
		ILinearRepresentation<G> solutionToCopyRepresentation = (ILinearRepresentation<G>) solutionToCopy.getRepresentation();
		int solutionToCopySize = solutionToCopyRepresentation.getNumberOfElements();
		List<G> newGenome = new ArrayList<G>(solutionToCopySize);

		for (int i = 0; i < solutionToCopySize; i++) {
			G geneValueToCopy = solutionToCopyRepresentation.getElementAt(i);
			G geneCopy = copyGeneValue(geneValueToCopy);
			newGenome.add(geneCopy);
		}

		ILinearRepresentation<G> newRepresentation =  createRepresentation(newGenome);		
		ISolution<ILinearRepresentation<G>> newSolution = new Solution<ILinearRepresentation<G>>(newRepresentation);
		return newSolution;
	}

	@Override
	public ISolutionSet<ILinearRepresentation<G>> copySolutionSet(ISolutionSet<ILinearRepresentation<G>> solutionSetToCopy) {
		ISolutionSet<ILinearRepresentation<G>> solutionSet = new SolutionSet<ILinearRepresentation<G>>();

		for(int i = 0; i < solutionSetToCopy.getNumberOfSolutions();i++){
			ISolution<ILinearRepresentation<G>> newSol = copySolution(solutionSetToCopy.getSolution(i));
			solutionSet.add(i,newSol);
		}

		return solutionSet;
	}


	@Override
	public ISolutionSet<ILinearRepresentation<G>> generateSolutionSet(int numberOfSolutions,IRandomNumberGenerator randomGenerator){
		ISolutionSet<ILinearRepresentation<G>> solutionSet = new SolutionSet<ILinearRepresentation<G>>();

		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<ILinearRepresentation<G>> solution = generateSolution(randomGenerator);
			solutionSet.add(solution);
		}

		return solutionSet;
	}

	@Override
	public ISolution<ILinearRepresentation<G>> generateSolution(int solutionSize,IRandomNumberGenerator randomGenerator){
		List<G> genome = new ArrayList<G>(solutionSize);

		for(int i = 0; i < solutionSize;i++){
			G geneValue = generateGeneValue(i,randomGenerator);
			genome.add(geneValue);
		}

		ILinearRepresentation<G> representation = createRepresentation(genome);
		return new Solution<ILinearRepresentation<G>>(representation);
	}


	/**
	 * Creates a new AbstractLinearRepresentation object.
	 * 
	 * @param genome the genome
	 * 
	 * @return the i linear representation< g>
	 */
	protected ILinearRepresentation<G>  createRepresentation(List<G> genome){
		return new LinearRepresentation<G>(genome);
	}

	/**
	 * Copy gene value.
	 * 
	 * @param geneValueToCopy the gene value to copy
	 * 
	 * @return the g
	 */
	protected abstract G copyGeneValue(G geneValueToCopy);


}
