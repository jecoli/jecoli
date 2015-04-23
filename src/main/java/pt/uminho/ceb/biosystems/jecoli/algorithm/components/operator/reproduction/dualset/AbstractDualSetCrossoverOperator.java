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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.dualset;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractSetCrossoverOperator.
 */
public abstract class AbstractDualSetCrossoverOperator implements IReproductionOperator<DualSetRepresentation,DualSetRepresentationFactory>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2408110583368409500L;

	/** The Constant NUMBER_OF_INPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_INPUT_SOLUTIONS = 2;
	
	/** The Constant NUMBER_OF_OUTPUT_SOLUTIONS. */
	protected static final int NUMBER_OF_OUTPUT_SOLUTIONS = 2;

	protected List<IntegerSetRepresentationFactory> representationSolutionFactoryVector;



	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#apply(java.util.List)
	 */
	public List<ISolution<DualSetRepresentation>> apply(List<ISolution<DualSetRepresentation>> selectedSolutions,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomGenerator)	throws InvalidNumberOfInputSolutionsException,
			InvalidNumberOfOutputSolutionsException {
		
		if(selectedSolutions.size() != NUMBER_OF_INPUT_SOLUTIONS)
			throw new InvalidNumberOfInputSolutionsException();
		
		List<ISolution<DualSetRepresentation>> solutionList = crossover(selectedSolutions,solutionFactory,randomGenerator);
		
		if(solutionList.size() != NUMBER_OF_OUTPUT_SOLUTIONS)
			throw new InvalidNumberOfOutputSolutionsException();
		
		return solutionList;
	}
	
	/**
	 * Crossover.
	 * 
	 * @param selectedSolutions the selected solutions
	 * 
	 * @return the list< i solution>
	 */
	public List<ISolution<DualSetRepresentation>> crossover(List<ISolution<DualSetRepresentation>> selectedSolutions,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomGenerator)
	{
		List<ISolution<DualSetRepresentation>> resultList = new ArrayList<ISolution<DualSetRepresentation>>(NUMBER_OF_OUTPUT_SOLUTIONS);
	
		ISolution<DualSetRepresentation> parentSolution = selectedSolutions.get(0);
		ISolution<DualSetRepresentation> parentSolution1 = selectedSolutions.get(1);

		DualSetRepresentation parentGenome1 = parentSolution.getRepresentation();
		DualSetRepresentation parentGenome2 = parentSolution1.getRepresentation();

		ISolution<DualSetRepresentation> child1 = solutionFactory.generateSolution(0,0,randomGenerator);
		ISolution<DualSetRepresentation> child2 = solutionFactory.generateSolution(0,0,randomGenerator);
	
		DualSetRepresentation childGenome1 = (DualSetRepresentation) child1.getRepresentation();
		DualSetRepresentation childGenome2 = (DualSetRepresentation) child2.getRepresentation();

		crossoverGenomes(parentGenome1, parentGenome2, childGenome1, childGenome2,solutionFactory,randomGenerator);
		
		resultList.add(child1);
		resultList.add(child2);
	
		return resultList;
	}
	
	/**
	 * Crossover genomes.
	 * 
	 * @param parentGenome1 the parent genome1
	 * @param parentGenome2 the parent genome2
	 * @param childGenome1 the child genome1
	 * @param childGenome2 the child genome2
	 * @param randomNumberGenerator TODO
	 */
	public abstract void crossoverGenomes (DualSetRepresentation parentGenome1, DualSetRepresentation parentGenome2,
			DualSetRepresentation childGenome1, DualSetRepresentation childGenome2,DualSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator);
	
	
	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#getNumberOfInputSolutions()
	 */
	public int getNumberOfInputSolutions(){
		return NUMBER_OF_INPUT_SOLUTIONS;
	}

	/* (non-Javadoc)
	 * @see operators.IReproductionOperator#getNumberOfOutputSolutions()
	 */
	public int getNumberOfOutputSolutions(){
		return NUMBER_OF_OUTPUT_SOLUTIONS;
	}

	/**
	 * Input solutions have same length.
	 * 
	 * @return true, if successful
	 */
	public boolean inputSolutionsHaveSameLength(){
		return false;
	}

	/**
	 * Produce equal size solution.
	 * 
	 * @return true, if successful
	 */
	public boolean produceEqualSizeSolution(){
		return false;
	}
	
	@Override
	public ReproductionOperatorType getReproductionType() {
		return ReproductionOperatorType.CROSSOVER;
	}

	public void copyChildSolutions(int genomeIndex,DualSetRepresentation childGenome,TreeSet<Integer> newChildGenome) {
		childGenome.setGenome(genomeIndex,newChildGenome);
	}


	public IntegerSetRepresentationFactory getSetSolutionFactory(Integer individualIndex) {
		return representationSolutionFactoryVector.get(individualIndex);
	}

}
