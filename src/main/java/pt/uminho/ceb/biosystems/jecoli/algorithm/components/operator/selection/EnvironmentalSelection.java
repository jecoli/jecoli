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

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionProcedureException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.SolutionPureFitnessComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;


// TODO: Auto-generated Javadoc
/**
 * Environmental Selection as described by Zitzler et al, 2001.
 * 
 * NOTE: selection works with selection values instead of scalar fitness values
 */
public class EnvironmentalSelection<T extends IRepresentation> implements ISelectionOperator<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5653097545373812846L;

	@Override
	public 	List<ISolution<T>> selectSolutions(int size, ISolutionSet<T> solutionSet,boolean isMaximization, IRandomNumberGenerator randomNumberGenerator) throws InvalidSelectionProcedureException,	InvalidSelectionParameterException
	{
		
		if(solutionSet.getNumberOfSolutions() < size){
			size = solutionSet.getNumberOfSolutions();
		}
		
		SolutionSet<T> nondominated = new SolutionSet<T>();
		nondominated.setMaxNumberOfSolutions(size);
				
		/** copy nondominated solutions to auxiliary population */		
		int i=0;
		while(i < solutionSet.getNumberOfSolutions()){
			if(solutionSet.getSolution(i).getSelectionValue() < 1.0){
				nondominated.add(solutionSet.getSolution(i));
				solutionSet.remove(i);
			}else{
				i++;
			}
		}		
					
		/** nondominated front is too small - fill it with the previous best dominated individuals */
		if(nondominated.getNumberOfSolutions() < size){
			nondominated.sort(new SolutionPureFitnessComparator<ISolution<T>>(true), true, false); //using selection values instead of scalar fitnesses
			int remain = size-nondominated.getNumberOfSolutions();
			for(int j=0;j< remain;j++)
				nondominated.add(solutionSet.getSolution(j));
			return nondominated.getListOfSolutions();
		}
		
		 /** nondominated front fits exactly into the archive */
		else if(nondominated.getNumberOfSolutions() == size){
			return nondominated.getListOfSolutions();
		}
		/** nondominated front exceeds the size of the archive - apply truncation */
		else{
			return MOUtils.zitzlerTruncation(nondominated.getListOfSolutions(),size);
		}
					
	}

	@Override
	public EnvironmentalSelection<T> deepCopy() {
		return new EnvironmentalSelection<T>();
	}

}
