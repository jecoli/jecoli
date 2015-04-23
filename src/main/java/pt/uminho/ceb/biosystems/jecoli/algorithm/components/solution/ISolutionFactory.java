/**
 * Copyright 2009,
 * CCTC - Computer Science and Technology Center
 * IBB-CEB - Institute for Biotechnology and Bioengineering - Centre of
 * Biological Engineering
 * University of Minho
 * 
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License
 * along with this code. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
 * University of Minho
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

/**
 * A factory for creating ISolution objects.
 */
public interface ISolutionFactory<T extends IRepresentation> extends IDeepCopy {
	
	/**
	 * Replaces the original genome by the new one
	 * 
	 * @param originalGenome
	 * @param newGenome
	 */
	void replaceGenome(T originalGenome, T newGenome);
	
	/**
	 * Copy solution.
	 * 
	 * @param solutionToCopy the solution to copy
	 * 
	 * @return the solution
	 */
	ISolution<T> copySolution(ISolution<T> solutionToCopy);
	
	/**
	 * Copy solution set.
	 * 
	 * @param solutionSetToCopy the solution set to copy
	 * 
	 * @return the solution set
	 */
	ISolutionSet<T> copySolutionSet(ISolutionSet<T> solutionSetToCopy);
	
	/**
	 * Generate solution set.
	 * 
	 * @param numberOfSolutions the number of solutions
	 * 
	 * @return the solution set
	 */
	ISolutionSet<T> generateSolutionSet(int numberOfSolutions, IRandomNumberGenerator randomGenerator);
	
	/**
	 * Generate a solution
	 * 
	 * @param randomGenerator
	 * @return the solution
	 */
	ISolution<T> generateSolution(IRandomNumberGenerator randomGenerator);
	
	/**
	 * DeepCopy mandatory method signature
	 * 
	 */
	ISolutionFactory<T> deepCopy();
	
	/**
	 * Gets the number of objectives.
	 * 
	 * @return the number of objectives
	 */
	int getNumberOfObjectives();
	
}