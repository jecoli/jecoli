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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionProcedureException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class RankingSelection.
 */
public class RankingSelection<T extends IRepresentation> implements ISelectionOperator<T>{

	private static final long serialVersionUID = 877108963785480212L;

	@Override
	public 	List<ISolution<T>> selectSolutions(int numberOfSolutionsToSelect, ISolutionSet<T> solutionSet,boolean isMaximization, IRandomNumberGenerator randomNumberGenerator) throws InvalidSelectionProcedureException,	InvalidSelectionParameterException{
		
		if(numberOfSolutionsToSelect < 1)
			throw new InvalidSelectionParameterException(" numberOfSolutionToSelect < 1");
		
		List<ISolution<T>> resultList = new ArrayList<ISolution<T>>();
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		int summatoryNumberOfSolutions = MOUtils.summatory(numberOfSolutions);
		ISolution<T> solution;

		for(int i = 0; i < numberOfSolutionsToSelect;i++){
			int solutionPosition = calculateSolutionPosition(isMaximization,summatoryNumberOfSolutions,numberOfSolutions,randomNumberGenerator);  
			
			if(isMaximization)
				solution = solutionSet.getHighestValuedSolutionsAt(solutionPosition);
			else
				solution = solutionSet.getLowestValuedSolutionsAt(solutionPosition);
			
			resultList.add(solution);
		}
		
		return resultList;
	}

	/**
	 * Calculate solution position.
	 * 
	 * @param isMaximization the is maximization
	 * @param summatoryNumberOfSolutions the summatory number of solutions
	 * @param numberOfSolutions the number of solutions
	 * @param randomNumberGenerator 
	 * 
	 * @return the int
	 */
	protected int calculateSolutionPosition(boolean isMaximization,int summatoryNumberOfSolutions, int numberOfSolutions, IRandomNumberGenerator randomNumberGenerator){
		int randomNumber = randomNumberGenerator.nextInt(summatoryNumberOfSolutions+1);
		int solutionIndex = calculateSolutionIndex(randomNumber,numberOfSolutions); 
		return (numberOfSolutions-1)-solutionIndex;
	}

	/**
	 * Calculate solution index.
	 * 
	 * @param randomNumber the random number
	 * @param numberOfSolutions the number of solutions
	 * 
	 * @return the int
	 */
	protected int calculateSolutionIndex(int randomNumber,int numberOfSolutions){
		int currentIndex = 0;
		
		for(int i = 0; i < numberOfSolutions ;i++){
            currentIndex += i;
			if(currentIndex >= randomNumber)
				return i;
		}
			
		return numberOfSolutions-1;
	}

	@Override
	public RankingSelection<T> deepCopy() {
		return new RankingSelection<T>();
	}

}
