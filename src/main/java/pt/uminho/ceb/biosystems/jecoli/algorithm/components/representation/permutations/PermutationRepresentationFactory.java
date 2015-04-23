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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;


/**
 * A factory for creating PermutationRepresentation objects.
 */
public class PermutationRepresentationFactory implements ISolutionFactory<PermutationRepresentation>,Serializable {


	private static final long serialVersionUID = 2072793712057470588L;
	/** The solution size. */
	protected int solutionSize; // also defines max value of the permutation
	protected int numberOfObjectives;




	/**
	 * Instantiates a new permutation representation factory.
	 * 
	 * @param solutionSize the solution size
	 */
	public PermutationRepresentationFactory(int solutionSize,int numberObjectives){
		this.solutionSize = solutionSize;
		this.numberOfObjectives = numberObjectives;
	}

	/**
	 * Instantiates a new permutation representation factory.
	 * 
	 * @param solutionSize the solution size
	 */
	public PermutationRepresentationFactory(int solutionSize){
		this.solutionSize = solutionSize;
		this.numberOfObjectives = 1;
	}



	public ISolution<PermutationRepresentation> generateSolution(IRandomNumberGenerator randomGenerator){
		return generateSolution(solutionSize,randomGenerator);
	}

	/**
	 * Generate solution.
	 * 
	 * @param size the size
	 * 
	 * @return the i solution
	 */
	public ISolution<PermutationRepresentation> generateSolution(int size,IRandomNumberGenerator randomGenerator){

		List<Integer> genome = new ArrayList<Integer>(solutionSize);

		int [] aux = new int[size];
		int k,j,i,l;
		for(k=0;k<size;k++) aux[k]=k;

		for(j=size,i=0;j>0;j--,i++)
		{
			int r = (int) (randomGenerator.nextDouble()*(j-1));
			genome.add(i, aux[r]);
			for(l=r;l<j-1;l++) aux[l]=aux[l+1];
		}

		PermutationRepresentation representation = new PermutationRepresentation(genome);
		return new Solution<PermutationRepresentation>(representation,numberOfObjectives);
	}

	@Override
	public void replaceGenome(PermutationRepresentation originalGenome, PermutationRepresentation newGenome) {
		originalGenome.genome = newGenome.genome;
	}

	/* (non-Javadoc)
         * @see core.interfaces.ISolutionFactory#copySolution(core.interfaces.ISolution)
         */
	public ISolution<PermutationRepresentation> copySolution(ISolution<PermutationRepresentation> solutionToCopy) {
		PermutationRepresentation solutionGenome = (PermutationRepresentation) solutionToCopy.getRepresentation();
		int solutionSize = solutionGenome.getNumberOfElements();

		List<Integer> newGenome = new ArrayList<Integer>(solutionSize);

		for (int i = 0; i < solutionSize; i++) {
			Integer geneValue = solutionGenome.getElement(i);
			Integer geneCopy = new Integer(geneValue.intValue());
			newGenome.add(geneCopy);
		}

		PermutationRepresentation newRepresentation =  new PermutationRepresentation(newGenome);
		ISolution<PermutationRepresentation> newSolution = new Solution<PermutationRepresentation>(newRepresentation);
		return newSolution;

	}
	
	@Override
	public ISolutionSet<PermutationRepresentation> copySolutionSet(ISolutionSet<PermutationRepresentation> solutionSetToCopy) {
		ISolutionSet<PermutationRepresentation> solutionSet = new SolutionSet<PermutationRepresentation>();

		for(int i = 0; i < solutionSetToCopy.getNumberOfSolutions();i++){
			ISolution<PermutationRepresentation> newSol = copySolution(solutionSetToCopy.getSolution(i));
			solutionSet.add(i,newSol);
		}

		return solutionSet;
	}

	public ISolutionSet<PermutationRepresentation> generateSolutionSet(int numberOfSolutions,IRandomNumberGenerator randomGenerator){
		ISolutionSet<PermutationRepresentation> solutionSet = new SolutionSet<PermutationRepresentation>();

		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<PermutationRepresentation> solution = generateSolution(randomGenerator);
			solutionSet.add(solution);
		}

		return solutionSet;
	}

	@Override
	public PermutationRepresentationFactory deepCopy(){
		return new PermutationRepresentationFactory(solutionSize);
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}

}


