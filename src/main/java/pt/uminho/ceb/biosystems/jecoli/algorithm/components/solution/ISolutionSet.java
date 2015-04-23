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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution;

import java.util.Comparator;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Interface ISolutionSet.
 */
public interface ISolutionSet<T extends IRepresentation> {
	
	/**
	 * Adds the.
	 * 
	 * @param solution the solution
	 */
	void add(ISolution<T> solution);
	
	/**
	 * Adds the solution in the specified index.
	 * @param index
	 * @param solution
	 */
	void add(int index, ISolution<T> solution);

    /**
     * Adds the.
     * 
     * @param newSolutions the new solutions
     */
	void add(List<ISolution<T>> newSolutions);
    
    /**
     * Removes the.
     * 
     * @param solution the solution
     */
	void remove(ISolution<T> solution);
    
    /**
     * Removes the.
     * 
     * @param solutions the solutions
     */
	void remove(List<ISolution<T>> solutions);
    
    /**
     * Removes the.
     * 
     * @param solutionIndex the solution index
     */
	void remove(int solutionIndex);
    
    /**
     * Removes the all.
     */
    void removeAll();

    /**
     * Gets the number of solutions.
     * 
     * @return the number of solutions
     */
    int getNumberOfSolutions();

    /**
     * Gets the solution.
     * 
     * @param i the i
     * 
     * @return the solution
     */
    ISolution<T> getSolution(int i);
    
    /**
     * Gets the list of solutions.
     * 
     * @return the list of solutions
     */
    List<ISolution<T>> getListOfSolutions();

    /**
     * Gets the highest valued solutions.
     * 
     * @param elistismValue the elistism value
     * 
     * @return the highest valued solutions
     */
    List<ISolution<T>> getHighestValuedSolutions(int elistismValue);

    /**
     * Gets the lowest valued solutions.
     * 
     * @param elistismValue the elistism value
     * 
     * @return the lowest valued solutions
     */
    List<ISolution<T>> getLowestValuedSolutions(int elistismValue);

    /**
     * Gets the max number of solutions.
     * 
     * @return the max number of solutions
     */
    int getMaxNumberOfSolutions();

    /**
     * Gets the comparator.
     * 
     * @return the comparator
     */
    Comparator<? super ISolution<T>> getComparator();

	/**
	 * Gets the highest valued solutions at.
	 * 
	 * @param solutionPosition the solution position
	 * 
	 * @return the highest valued solutions at
	 */
    ISolution<T> getHighestValuedSolutionsAt(int solutionPosition);

	/**
	 * Gets the lowest valued solutions at.
	 * 
	 * @param solutionPosition the solution position
	 * 
	 * @return the lowest valued solutions at
	 */
    ISolution<T> getLowestValuedSolutionsAt(int solutionPosition);

	/**
	 * Calculate overall fitness.
	 * 
	 * @return the double
	 */
	double calculateOverallFitness();

	/**
	 * Sets the solution.
	 * 
	 * @param solutionIndex the solution index
	 * @param newSolution the new solution
	 */
	void setSolution(Integer solutionIndex, ISolution<T> newSolution);
	
	/**
	 * Union.
	 * 
	 * @param toMerge the to merge
	 * 
	 * @return the i solution set
	 */
	ISolutionSet<T> union(ISolutionSet<T> toMerge);

	/**
	 * Gets the number of objectives.
	 * 
	 * @return the number of objectives
	 */
	int getNumberOfObjectives();

	/**
	 * Sort.
	 */
	void sort();
	
	/**
	 * Sort.
	 */
	void sort(Comparator<? super ISolution<T>> newComparator, boolean replaceComparator, boolean higherToLower);
	
	/**
	 * Contains
	 */
	boolean contains(ISolution<T> solution);
	
	/**
	 * Contains genome only
	 */
	boolean containsGenomeOnly(ISolution<T> solution);
	
	/**
	 * Contains genome and fitnesses
	 */
	boolean containsGenomeAndFitnesses(ISolution<T> solution);
}