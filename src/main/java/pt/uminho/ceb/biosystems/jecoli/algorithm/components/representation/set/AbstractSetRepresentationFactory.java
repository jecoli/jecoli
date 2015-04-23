/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set;

import java.io.Serializable;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

/**
 * @author pmaia
 *
 * Jan 9, 2013
 */
public abstract class AbstractSetRepresentationFactory<G> implements ISetRepresentationFactory<G>,Serializable{

	private static final long serialVersionUID = 1L;

	/** The min set size. */
	protected int minSetSize = 1;

	/** The max set size. */
	protected int maxSetSize = Integer.MAX_VALUE;

	/** The initial min size. */
	protected int initialMinSize = 1;

	/** The initial max size. */
	protected int initialMaxSize = Integer.MAX_VALUE;
	protected int numberOfObjectives;


	public AbstractSetRepresentationFactory(int maxSetSize) {
		this.maxSetSize = maxSetSize;
		this.initialMaxSize = maxSetSize;
		this.numberOfObjectives = 1;
	}

	public AbstractSetRepresentationFactory(int maxSetSize,int numberObjectives) {
		this(1,maxSetSize,1,maxSetSize,numberObjectives);

	}

	/**
	 * Instantiates a new sets the representation factory.
	 * 
	 * @param maxElement the max element
	 * @param minSetSize the min set size
	 * @param maxSetSize the max set size
	 * @param initialMinSize the initial min size
	 * @param initialMaxSize the initial max size
	 * @param numberOfObjectives the number of objectives
	 */
	public AbstractSetRepresentationFactory(int minSetSize, int maxSetSize, int initialMinSize, int initialMaxSize,int numberObjectives) {
		this.minSetSize = minSetSize;
		this.maxSetSize = maxSetSize;
		this.initialMinSize = initialMinSize;
		this.initialMaxSize = initialMaxSize;
		this.numberOfObjectives = numberObjectives;
	}

	@Override
	public ISolution<ISetRepresentation<G>> copySolution(ISolution<ISetRepresentation<G>> solutionToCopy) {
		ISetRepresentation<G> solutionToCopyRepresentation = (ISetRepresentation<G>) solutionToCopy.getRepresentation();
		int solutionToCopySize = solutionToCopyRepresentation.getNumberOfElements();

		TreeSet<G> newGenome = new TreeSet<G>();

		for (int i = 0; i < solutionToCopySize; i++) {
			G geneValueToCopy = solutionToCopyRepresentation.getElementAt(i);
			G geneCopy = copyGeneValue(geneValueToCopy);
			newGenome.add(geneCopy);
		}

		ISetRepresentation<G> newRepresentation =  createRepresentation(newGenome);
		int numberObjectives = solutionToCopy.getNumberOfObjectives();
		ISolution<ISetRepresentation<G>> newSolution = new Solution<ISetRepresentation<G>>(newRepresentation,numberObjectives);
		return newSolution;
	}
	
	@Override
	public ISolutionSet<ISetRepresentation<G>> copySolutionSet(ISolutionSet<ISetRepresentation<G>> solutionSetToCopy) {		
		ISolutionSet<ISetRepresentation<G>> solutionSet = new SolutionSet<ISetRepresentation<G>>();

		for(int i = 0; i < solutionSetToCopy.getNumberOfSolutions();i++){
			ISolution<ISetRepresentation<G>> newSol = copySolution(solutionSetToCopy.getSolution(i));
			solutionSet.add(i,newSol);
		}

		return solutionSet;
	}

	@Override
	public ISolutionSet<ISetRepresentation<G>> generateSolutionSet(int numberOfSolutions, IRandomNumberGenerator randomGenerator) {
		ISolutionSet<ISetRepresentation<G>> solutionSet = new SolutionSet<ISetRepresentation<G>>();

		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<ISetRepresentation<G>> solution = generateSolution(randomGenerator);
			solutionSet.add(solution);
		}

		return solutionSet;
	}


	/**
	 * Creates a new AbstractLinearRepresentation object.
	 * 
	 * @param genome the genome
	 * 
	 * @return the i linear representation< g>
	 */
	protected ISetRepresentation<G>  createRepresentation(TreeSet<G> genome){
		return new SetRepresentation<G>(genome);
	}

	public ISolution<ISetRepresentation<G>> generateSolution(IRandomNumberGenerator randomGenerator)
	{
		if(this.initialMinSize == this.initialMaxSize)
		{
			return generateSolution(initialMaxSize,randomGenerator);
		}
		else 
		{
			int setSize = (int) (randomGenerator.nextDouble() *(initialMaxSize-initialMinSize) + initialMinSize);
			return generateSolution(setSize,randomGenerator);
		}
	}



	@Override
	public ISolution<ISetRepresentation<G>> generateSolution(int setSize, IRandomNumberGenerator randomGenerator) {
		TreeSet<G> genome = new TreeSet<G>();

		for(int i = 0; i < setSize; i++) {
			G element;
			do {
				element = generateGeneValue(randomGenerator);
			} while(genome.contains(element));
			genome.add(element);	            
		}

		ISetRepresentation<G> representation = createRepresentation(genome);
		return new Solution<ISetRepresentation<G>>(representation,numberOfObjectives);
	}


	/**
	 * Copy gene value.
	 * 
	 * @param geneValueToCopy the gene value to copy
	 * 
	 * @return the g
	 */
	protected abstract G copyGeneValue(G geneValueToCopy);


	@Override
	public int getNumberOfObjectives() {		
		return numberOfObjectives;
	}
}
