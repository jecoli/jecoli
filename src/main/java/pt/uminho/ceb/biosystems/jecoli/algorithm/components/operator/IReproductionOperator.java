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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator;

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;



// TODO: Auto-generated Javadoc
/**
 * The Interface IReproductionOperator.
 */
public interface IReproductionOperator<T extends IRepresentation,S extends ISolutionFactory<T>> extends IOperator{

	/**
	 * Apply.
	 * 
	 * @param selectedSolutions the selected solutions
	 * @param randomNumberGenerator TODO
	 * 
	 * @return the list< i solution>
	 * 
	 * @throws InvalidNumberOfInputSolutionsException the invalid number of input solutions exception
	 * @throws InvalidNumberOfOutputSolutionsException the invalid number of output solutions exception
	 */
	List<ISolution<T>> apply(List<ISolution<T>> selectedSolutions,S solutionFactory,IRandomNumberGenerator randomNumberGenerator) throws InvalidNumberOfInputSolutionsException,InvalidNumberOfOutputSolutionsException;
	
	/**
	 * Gets the number of input solutions.
	 * 
	 * @return the number of input solutions
	 */
	int getNumberOfInputSolutions();
	
	/**
	 * Gets the number of output solutions.
	 * 
	 * @return the number of output solutions
	 */
	int getNumberOfOutputSolutions();
//	boolean produceEqualSizeSolution();
//	boolean inputSolutionsHaveSameLength();
	
	ReproductionOperatorType getReproductionType();
	
	IReproductionOperator<T,S> deepCopy() throws Exception;


}