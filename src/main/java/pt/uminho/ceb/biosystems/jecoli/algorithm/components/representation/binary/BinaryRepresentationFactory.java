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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.AbstractLinearRepresentationFactory;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating BinaryRepresentation objects.
 */
public class BinaryRepresentationFactory extends AbstractLinearRepresentationFactory<Boolean> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new binary representation factory.
	 * 
	 * @param solutionSize the solution size
	 */
	public BinaryRepresentationFactory(int solutionSize) {
		this(solutionSize,1);
	}
	
	/**
	 * Instantiates a new binary representation factory.
	 * 
	 * @param solutionSize the solution size
	 */
	public BinaryRepresentationFactory(int solutionSize,int numberObjectives) {
		super(solutionSize,numberObjectives);
	}
	

	@Override
	protected Boolean copyGeneValue(Boolean geneValueToCopy) {
		boolean newGeneValue = geneValueToCopy;
		return newGeneValue;
	}


	@Override
	public Boolean generateGeneValue(int genePosition,IRandomNumberGenerator randomGenerator) {
		double randomValue = randomGenerator.nextDouble();

        return randomValue < 0.5;

    }



	@Override
	public BinaryRepresentationFactory deepCopy(){
		return new BinaryRepresentationFactory(solutionSize);
	}

	@Override
	public int getMaximumNumberOfGenes() {
		return solutionSize;
	}

	@Override
	public int getMinimumNumberOfGenes() {
		return solutionSize;
	}

	
}
