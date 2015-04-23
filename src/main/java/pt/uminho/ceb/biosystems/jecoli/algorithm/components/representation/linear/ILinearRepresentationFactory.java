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

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating ILinearRepresentation objects.
 */
public interface ILinearRepresentationFactory<G> extends ISolutionFactory<ILinearRepresentation<G>>{
    	
	G generateGeneValue(int genePosition,IRandomNumberGenerator randomNumberGenerator);
	ISolution<ILinearRepresentation<G>> generateSolution(int size, IRandomNumberGenerator randomGenerator);
	int getMaximumNumberOfGenes();
	int getMinimumNumberOfGenes();

	ILinearRepresentationFactory<G> deepCopy();
}
