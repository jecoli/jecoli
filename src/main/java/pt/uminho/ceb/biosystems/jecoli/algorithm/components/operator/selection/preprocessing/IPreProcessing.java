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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.preprocessing;

import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;


// TODO: Auto-generated Javadoc
/**
 * The Interface IPreProcessing.
 */
public interface IPreProcessing<T extends IRepresentation> {
	
	/**
	 * Pre process fitness.
	 * 
	 * @param isMaximization the is maximization
	 * @param solutionSet the solution set
	 * 
	 * @return the ordered list
	 * 
	 * @throws Exception the exception
	 */
	List<ScaledFitnessData<T>> preProcessFitness(boolean isMaximization,ISolutionSet<T> solutionSet) throws Exception;
}
