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
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.AbstractLinearRepresentationFactory;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating IntegerRepresentation objects.
 */
public class IntegerRepresentationFactory extends AbstractLinearRepresentationFactory<Integer> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** The upper bound gene limit list. */
	protected List<Integer> upperBoundGeneLimitList;
	
	/** The lower bound gene limit list. */
	protected List<Integer> lowerBoundGeneLimitList;
	
	/**
	 * Instantiates a new integer representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param upperBoundGeneLimitList the upper bound gene limit list
	 * @param lowerBoundGeneLimitList the lower bound gene limit list
	 */
	public IntegerRepresentationFactory(int solutionSize,List<Integer> upperBoundGeneLimitList,List<Integer> lowerBoundGeneLimitList) {
		super(solutionSize);
		this.lowerBoundGeneLimitList = lowerBoundGeneLimitList;
		this.upperBoundGeneLimitList = upperBoundGeneLimitList;
	}
	
	/**
	 * Instantiates a new integer representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param upperBound the upper bound
	 * @param lowerBound the lower bound
	 */
	public IntegerRepresentationFactory(int solutionSize, Integer upperBound, Integer lowerBound) {
		super(solutionSize);
		this.lowerBoundGeneLimitList = new ArrayList<Integer>(solutionSize);
		this.upperBoundGeneLimitList = new ArrayList<Integer>(solutionSize);
		for(int i=0; i < solutionSize; i++)
		{
			lowerBoundGeneLimitList.add(i, lowerBound);
			upperBoundGeneLimitList.add(i, upperBound);
		}
	}
	
	/**
	 * Instantiates a new integer representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param upperBound the upper bound
	 */
	public IntegerRepresentationFactory(int solutionSize, Integer upperBound) {
		super(solutionSize);
		this.lowerBoundGeneLimitList = new ArrayList<Integer>(solutionSize);
		this.upperBoundGeneLimitList = new ArrayList<Integer>(solutionSize);
		for(int i=0; i < solutionSize; i++)
		{
			lowerBoundGeneLimitList.add(i, 0);
			upperBoundGeneLimitList.add(i, upperBound);
		}
	}
	
	public IntegerRepresentationFactory(int solutionSize, Integer upperBound, Integer lowerBound, int numObjectives) {
		super(solutionSize, numObjectives);
		this.lowerBoundGeneLimitList = new ArrayList<Integer>(solutionSize);
		this.upperBoundGeneLimitList = new ArrayList<Integer>(solutionSize);
		for(int i=0; i < solutionSize; i++)
		{
			lowerBoundGeneLimitList.add(i, lowerBound);
			upperBoundGeneLimitList.add(i, upperBound);
		}
	}
	
	@Override
	protected Integer copyGeneValue(Integer geneValueToCopy) {
		int newGeneValue = geneValueToCopy;
		return newGeneValue;
	}

	/* (non-Javadoc)
	 * @see core.representation.ILinearRepresentationFactory#generateGeneValue(int)
	 */
	@Override
	public Integer generateGeneValue(int genePosition,IRandomNumberGenerator randomNumberGenerator){
		if(genePosition < upperBoundGeneLimitList.size()){
			int geneUpperValue = upperBoundGeneLimitList.get(genePosition);
			int geneLowerValue = lowerBoundGeneLimitList.get(genePosition);
			int geneValue = (int) (randomNumberGenerator.nextDouble()*(geneUpperValue-geneLowerValue)+geneLowerValue);
			return geneValue;
		}else{
			int geneUpperValue = upperBoundGeneLimitList.get(0);
			int geneLowerValue = lowerBoundGeneLimitList.get(0);
			int geneValue = (int) (randomNumberGenerator.nextDouble()*(geneUpperValue-geneLowerValue)+geneLowerValue);
			return geneValue;
		}
	}

	/**
	 * Gets the upper bound gene limit list.
	 * 
	 * @return the upper bound gene limit list
	 */
	public List<Integer> getUpperBoundGeneLimitList() {
		return upperBoundGeneLimitList;
	}

	/**
	 * Sets the upper bound gene limit list.
	 * 
	 * @param upperBoundGeneLimitList the new upper bound gene limit list
	 */
	public void setUpperBoundGeneLimitList(List<Integer> upperBoundGeneLimitList) {
		this.upperBoundGeneLimitList = upperBoundGeneLimitList;
	}

	/**
	 * Gets the lower bound gene limit list.
	 * 
	 * @return the lower bound gene limit list
	 */
	public List<Integer> getLowerBoundGeneLimitList() {
		return lowerBoundGeneLimitList;
	}

	/**
	 * Sets the lower bound gene limit list.
	 * 
	 * @param lowerBoundGeneLimitList the new lower bound gene limit list
	 */
	public void setLowerBoundGeneLimitList(List<Integer> lowerBoundGeneLimitList) {
		this.lowerBoundGeneLimitList = lowerBoundGeneLimitList;
	}

	@Override
	public IntegerRepresentationFactory deepCopy() {
		List<Integer> upperBoundGeneLimitListCopy = new ArrayList<Integer>();
		List<Integer> lowerBoundGeneLimitListCopy = new ArrayList<Integer>();
		
		for(int i = 0; i < solutionSize;i++){
			int geneUpperBound = upperBoundGeneLimitList.get(i);
			int geneLowerBound = lowerBoundGeneLimitList.get(i);
			upperBoundGeneLimitListCopy.add(geneUpperBound);
			lowerBoundGeneLimitListCopy.add(geneLowerBound);
		}
		
		return new IntegerRepresentationFactory(solutionSize,upperBoundGeneLimitListCopy,lowerBoundGeneLimitListCopy);
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
