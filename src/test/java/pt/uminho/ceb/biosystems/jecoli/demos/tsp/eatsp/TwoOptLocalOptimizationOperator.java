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
package pt.uminho.ceb.biosystems.jecoli.demos.tsp.eatsp;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.permutation.AbstractPermutationMutationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.demos.tsp.libtsp.Tsp;

/**
 * The Class TwoOptLocalOptimizationOperator: local optimization jecoliunittest.operators.operator for the TSP
 */
public class TwoOptLocalOptimizationOperator extends AbstractPermutationMutationOperator
{
	
	/** The solution factory. */
	PermutationRepresentationFactory solutionFactory;
	
	/** The problem instance. */
	protected Tsp problemInstance;
	
	/** The one pass. */
	boolean onePass = true; // if false do complete 2 opt
	
	/**
	 * Instantiates a new two opt local optimization jecoliunittest.operators.operator.
	 * 
	 * @param solutionFactory the solution factory
	 * @param problemInstance the problem instance
	 */
	public TwoOptLocalOptimizationOperator(PermutationRepresentationFactory solutionFactory, Tsp problemInstance)
	{
		this.problemInstance = problemInstance;
	}

	/**
	 * Instantiates a new two opt local optimization jecoliunittest.operators.operator.
	 * 
	 * @param solutionFactory the solution factory
	 * @param problemInstance the problem instance
	 * @param onePass the one pass
	 */
	public TwoOptLocalOptimizationOperator(PermutationRepresentationFactory solutionFactory, Tsp problemInstance, boolean onePass)
	{
		this.problemInstance = problemInstance;
		this.onePass = onePass;
	}

	protected void mutateGenome(PermutationRepresentation childGenome, IRandomNumberGenerator randomNumberGenerator)
	{
		int[] genomeArray = childGenome.getGenomeAsArray();
		
		if (onePass) {
			double fitness = problemInstance.cost(genomeArray);
			problemInstance.one_pass_2opt(genomeArray, fitness);
		}
		else problemInstance.complete_2opt(genomeArray);
		
		for(int i=0; i < childGenome.getNumberOfElements(); i++)
			childGenome.setElement(i, genomeArray[i]);
	}

	@Override
	public IReproductionOperator<PermutationRepresentation, PermutationRepresentationFactory> deepCopy()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void mutateGenome(PermutationRepresentation childGenome,
			PermutationRepresentationFactory solutionFactory,
			IRandomNumberGenerator randomNumberGenerator) {
		mutateGenome(childGenome, randomNumberGenerator);
		
	}
	
}
