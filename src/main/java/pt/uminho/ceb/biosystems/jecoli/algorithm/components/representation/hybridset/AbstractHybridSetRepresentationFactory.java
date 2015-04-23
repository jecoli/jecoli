package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

public abstract class AbstractHybridSetRepresentationFactory<G,H> implements IHybridSetRepresentationFactory<G,H>, Serializable{


	private static final long serialVersionUID = -2184959231856681867L;

	/** Random generator*/
	protected IRandomNumberGenerator randomGenerator;

	/** Maximum set size */
	protected int maxSetSize;

	/** Minimum set size */
	protected int minSetSize;

	/** Maximum set value */
	protected G maxSetValue;

	/** Number of objectives set representation */
	protected int numberOfObjectives;

	/**
	 * Instantiates a new HybridRealSetRepresentationFactory
	 * 
	 * @param minSetSize
	 * @param maxSetSize
	 * @param maxSetValue
	 * @param minIntegerValue
	 * @param maxIntegerValue
	 * @param numberOfObjectives
	 */
	public AbstractHybridSetRepresentationFactory(int minSetSize, int maxSetSize, G maxSetValue, int numberOfObjectives){
		this.maxSetSize = maxSetSize;
		this.minSetSize = minSetSize;
		this.maxSetValue = maxSetValue;
		this.numberOfObjectives = numberOfObjectives;
		randomGenerator = new DefaultRandomNumberGenerator();
	}


	@Override
	public ISolution<IHybridSetRepresentation<G,H>> copySolution(ISolution<IHybridSetRepresentation<G,H>> solutionToCopy) {

		IHybridSetRepresentation<G,H> solutionGenome = solutionToCopy.getRepresentation();

		TreeSet<G> newGenomeSetValues = new TreeSet<G>();
		List<H> newGenomeListValues = new ArrayList<H>();

		int size = solutionGenome.getNumberOfElements();
		//		System.out.println("AbstracttHybridSetRepresentationFactory: copySolution - solutionGenomeSize= "+size);

		for(int index=0; index<size ;index++)
		{
			newGenomeSetValues.add(solutionGenome.getElementAt(index));
			newGenomeListValues.add(solutionGenome.getListValueAt(index));
		}

		IHybridSetRepresentation<G, H> newGenome = new HybridSetRepresentation<G,H>(newGenomeListValues, newGenomeSetValues);

		ISolution<IHybridSetRepresentation<G,H>> newSolution = new Solution<IHybridSetRepresentation<G,H>>(newGenome,numberOfObjectives);

		return newSolution;
	}

	@Override
	public ISolutionSet<IHybridSetRepresentation<G, H>> copySolutionSet(ISolutionSet<IHybridSetRepresentation<G, H>> solutionSetToCopy) {
		ISolutionSet<IHybridSetRepresentation<G,H>> solutionSet = new SolutionSet<IHybridSetRepresentation<G,H>>();

		for(int i=0;i<solutionSetToCopy.getNumberOfSolutions();i++){
			ISolution<IHybridSetRepresentation<G,H>> newSol = copySolution(solutionSetToCopy.getSolution(i));
			solutionSet.add(i,newSol);
		}

		return solutionSet;
	}

	@Override
	public ISolutionSet<IHybridSetRepresentation<G,H>> generateSolutionSet(int numberOfSolutions, IRandomNumberGenerator randomGenerator) {
		ISolutionSet<IHybridSetRepresentation<G,H>> solutionSet = new SolutionSet<IHybridSetRepresentation<G,H>>();

		for(int i=0;i<numberOfSolutions;i++)
		{
			ISolution<IHybridSetRepresentation<G,H>> solution = generateSolution(randomGenerator);
			solutionSet.add(solution);
		}

		return solutionSet;
	}

	public TreeSet<G> generateSetValues(int numberOfValuesToGenerate){
		TreeSet<G> genome = new TreeSet<G>();

		for (int i = 0; i < numberOfValuesToGenerate; i++) {
			G element;
			do {
				element = generateSetValue();
			} while(genome.contains(element));
			genome.add(element);
		}

		return genome;
	}

	public abstract G generateSetValue();



	public abstract ISolution<IHybridSetRepresentation<G,H>> generateSolution(int numberOfValuesToGenerate,IRandomNumberGenerator randomGenerator);

	@Override
	public ISolution<IHybridSetRepresentation<G,H>> generateSolution(IRandomNumberGenerator randomGenerator) {
		int setRepresentationSize = minSetSize + (int) (randomGenerator.nextDouble() * ((maxSetSize-minSetSize) + 1));
		return generateSolution(setRepresentationSize, randomGenerator);
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}


	/**
	 * @return the randomGenerator
	 */
	public IRandomNumberGenerator getRandomGenerator() {
		return randomGenerator;
	}


	/**
	 * @param randomGenerator the randomGenerator to set
	 */
	public void setRandomGenerator(IRandomNumberGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}


	/**
	 * @return the maxSetSize
	 */
	public int getMaxSetSize() {
		return maxSetSize;
	}


	/**
	 * @param maxSetSize the maxSetSize to set
	 */
	public void setMaxSetSize(int maxSetSize) {
		this.maxSetSize = maxSetSize;
	}


	/**
	 * @return the minSetSize
	 */
	public int getMinSetSize() {
		return minSetSize;
	}


	/**
	 * @param minSetSize the minSetSize to set
	 */
	public void setMinSetSize(int minSetSize) {
		this.minSetSize = minSetSize;
	}


	/**
	 * @return the maxSetValue
	 */
	public G getMaxSetValue() {
		return maxSetValue;
	}

	@Override
	public void setMaxSetValue(G maxSetValue) {
		this.maxSetValue = maxSetValue;
	}

}
