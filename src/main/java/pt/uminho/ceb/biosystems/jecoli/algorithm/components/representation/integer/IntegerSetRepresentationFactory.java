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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.AbstractSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;




// TODO: Auto-generated Javadoc
/**
 * A factory for creating SetRepresentation objects.
 */
public class IntegerSetRepresentationFactory extends AbstractSetRepresentationFactory<Integer> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The max element. */
	protected int maxElement;

	/**
	 * Instantiates a new sets the representation factory.
	 * 
	 * @param maxElement
	 */
	public IntegerSetRepresentationFactory(int maxElement){
		this(maxElement,1);
	}
	
	/**
	 * Instantiates a new sets the representation factory.
	 * 
	 * @param maxElement the max element
	 * @param maxSetSize the max set size
	 * @param numberOfObjectives the number of objectives
	 */

	public IntegerSetRepresentationFactory(int maxElement, int maxSetSize) {
		super(maxSetSize);
		this.maxElement = maxElement;
	}
	
	public IntegerSetRepresentationFactory(int maxElement, int maxSetSize,int numberObjectives) {
		this(maxElement,1,maxSetSize,1,maxSetSize,numberObjectives);

	}

	/**
	 * Instantiates a new sets the representation factory.
	 * 
	 * @param maxElement the max element
	 * @param minSetSize the min set size
	 * @param maxSetSize the max set size
	 * @param initialMinSize the initial min size
	 * @param initialMaxSize the initial max size
	 * @param numberOfObjectives the number of objectives
	 */
	public IntegerSetRepresentationFactory(int maxElement, int minSetSize, int maxSetSize, int initialMinSize, int initialMaxSize,int numberObjectives) {
		super(minSetSize,maxSetSize,initialMinSize,initialMaxSize,numberObjectives);
		this.maxElement = maxElement;		
	}

	
	/**
	 * Gets the max element.
	 * 
	 * @return the max element
	 */
	public Integer getMaxElement() {
		return maxElement;
	}

	/**
	 * Sets the max element.
	 * 
	 * @param maxElement the new max element
	 */
	public void setMaxElement(int maxElement) {
		this.maxElement = maxElement;
	}

	/**
	 * Gets the min set size.
	 * 
	 * @return the min set size
	 */
	public int getMinSetSize() {
		return minSetSize;
	}

	/**
	 * Sets the min set size.
	 * 
	 * @param minSetSize the new min set size
	 */
	public void setMinSetSize(int minSetSize) {
		this.minSetSize = minSetSize;
	}

	/**
	 * Gets the max set size.
	 * 
	 * @return the max set size
	 */
	public int getMaxSetSize() {
		return maxSetSize;
	}

	/**
	 * Sets the max set size.
	 * 
	 * @param maxSetSize the new max set size
	 */
	public void setMaxSetSize(int maxSetSize) {
		this.maxSetSize = maxSetSize;
	}

	/**
	 * Gets the initial min size.
	 * 
	 * @return the initial min size
	 */
	public int getInitialMinSize() {
		return initialMinSize;
	}

	/**
	 * Sets the initial min size.
	 * 
	 * @param initialMinSize the new initial min size
	 */
	public void setInitialMinSize(int initialMinSize) {
		this.initialMinSize = initialMinSize;
	}

	/**
	 * Gets the initial max size.
	 * 
	 * @return the initial max size
	 */
	public int getInitialMaxSize() {
		return initialMaxSize;
	}

	/**
	 * Sets the initial max size.
	 * 
	 * @param initialMaxSize the new initial max size
	 */
	public void setInitialMaxSize(int initialMaxSize) {
		this.initialMaxSize = initialMaxSize;
	}


	@Override
	public void replaceGenome(ISetRepresentation<Integer> originalGenome, ISetRepresentation<Integer> newGenome) {
		originalGenome.getGenome().clear();
		originalGenome.getGenome().addAll(newGenome.getGenome());
	}

	@Override
	public IntegerSetRepresentationFactory deepCopy(){
		return new IntegerSetRepresentationFactory(maxElement, minSetSize, maxSetSize, initialMinSize, initialMaxSize,numberOfObjectives);
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}


	@Override
	public Integer generateGeneValue(IRandomNumberGenerator randomNumberGenerator) {
		return randomNumberGenerator.nextInt(maxElement);
	}

	
	@Override
	protected Integer copyGeneValue(Integer geneValueToCopy) {
		int newvalue = geneValueToCopy;
		
		return new Integer(newvalue);
	}
	 
}
