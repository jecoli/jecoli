/**
 * Copyright 2009,
 * CCTC - Computer Science and Technology Center
 * IBB-CEB - Institute for Biotechnology and  Bioengineering - Centre of Biological Engineering
 * University of Minho
 *
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Public License for more details.
 *
 * You should have received a copy of the GNU Public License
 * along with this code.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
 * University of Minho
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;


/**
 * A factory for creating AbstractLinearRepresentation objects.
 */
public abstract class AbstractLinearRepresentationFactory<G> implements ILinearRepresentationFactory<G> ,Serializable {

	private static final long serialVersionUID = 1L;

	/** The solution size. */
	protected int solutionSize;
	protected int numberOfObjectives;


	/**
	 * Instantiates a new abstract linear representation factory.
	 * 
	 * @param solutionSize the solution size
	 */
	public AbstractLinearRepresentationFactory(int solutionSize){
		this.solutionSize = solutionSize;
		this.numberOfObjectives = 1;
	}

	/**
	 * Instantiates a new abstract linear representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param numberOfObjectives the number of objectives
	 */
	public AbstractLinearRepresentationFactory(int solutionSize, int numberOfObjectives){
		this(solutionSize);
		this.numberOfObjectives = numberOfObjectives;
	}

	@Override
	public void replaceGenome(ILinearRepresentation<G> originalGenome, ILinearRepresentation<G> newGenome) {
		originalGenome.getGenome().clear();
		originalGenome.getGenome().addAll(newGenome.getGenome());
	}

	@Override
	public ISolution<ILinearRepresentation<G>> generateSolution(IRandomNumberGenerator randomGenerator) {
		return generateSolution(solutionSize,randomGenerator);
	}

	@Override
	public ISolution<ILinearRepresentation<G>> copySolution(ISolution<ILinearRepresentation<G>> solutionToCopy) {
		ILinearRepresentation<G> solutionToCopyRepresentation = (ILinearRepresentation<G>) solutionToCopy.getRepresentation();
		int solutionToCopySize = solutionToCopyRepresentation
				.getNumberOfElements();
		List<G> newGenome = new ArrayList<G>(solutionToCopySize);

		for (int i = 0; i < solutionToCopySize; i++) {
			G geneValueToCopy = solutionToCopyRepresentation.getElementAt(i);
			G geneCopy = copyGeneValue(geneValueToCopy);
			newGenome.add(geneCopy);
		}

		ILinearRepresentation<G> newRepresentation =  createRepresentation(newGenome);
		int numberObjectives = solutionToCopy.getNumberOfObjectives();
		ISolution<ILinearRepresentation<G>> newSolution = new Solution<ILinearRepresentation<G>>(newRepresentation,numberObjectives);
		return newSolution;
	}

	@Override
	public ISolutionSet<ILinearRepresentation<G>> copySolutionSet(ISolutionSet<ILinearRepresentation<G>> solutionSetToCopy){
		ISolutionSet<ILinearRepresentation<G>> solutionSet = new SolutionSet<ILinearRepresentation<G>>();
		for(int i=0 ; i<solutionSetToCopy.getNumberOfSolutions(); i++){
			ISolution<ILinearRepresentation<G>> newSol = copySolution(solutionSetToCopy.getSolution(i));
			solutionSet.add(i, newSol);
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


	/* (non-Javadoc)
	 * @see core.representation.ILinearRepresentationFactory#generateSolution(int)
	 */
	@Override
	public ISolution<ILinearRepresentation<G>> generateSolution(int size,IRandomNumberGenerator randomGenerator){
		List<G> genome = new ArrayList<G>(solutionSize);

		for(int i = 0; i < size;i++){
			G geneValue = generateGeneValue(i,randomGenerator);
			genome.add(geneValue);
		}

		ILinearRepresentation<G> representation = createRepresentation(genome);
		return new Solution<ILinearRepresentation<G>>(representation,numberOfObjectives);
	}

	/**
	 * Creates a new AbstractLinearRepresentation object.
	 * 
	 * @param genome
	 * @return a new <code>LinearRepresentation</code> instance
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


	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}


}
