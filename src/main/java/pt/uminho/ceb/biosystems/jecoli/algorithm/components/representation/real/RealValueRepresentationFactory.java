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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.AbstractLinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating RealValueRepresentation objects.
 */
public class RealValueRepresentationFactory extends AbstractLinearRepresentationFactory<Double> implements Serializable, ILinearRepresentationFactory<Double> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7148934374382595066L;

	/** The gene lower bound list. */
	protected List<Double> geneLowerBoundList;
	
	/** The gene upper bound list. */
	protected List<Double> geneUpperBoundList;
	
	/**
	 * Instantiates a new real value representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param geneLowerBoundList the gene lower bound list
	 * @param geneUpperBoundList the gene upper bound list
	 */
	public RealValueRepresentationFactory(int solutionSize,List<Double> geneLowerBoundList,List<Double> geneUpperBoundList){
		super(solutionSize);
		this.geneLowerBoundList = geneLowerBoundList;
		this.geneUpperBoundList = geneUpperBoundList;
	}

	/**
	 * Instantiates a new real value representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param geneLowerBoundArray the gene lower bound array
	 * @param geneUpperBoundArray the gene upper bound array
	 */
	public RealValueRepresentationFactory(int solutionSize, double[] geneLowerBoundArray, double[] geneUpperBoundArray)
	{
		super(solutionSize);
		
		this.geneLowerBoundList = new ArrayList<Double>(solutionSize);
		this.geneUpperBoundList = new ArrayList<Double>(solutionSize);
		
		for(int i=0; i < solutionSize; i++)
		{
			geneLowerBoundList.add(i, geneLowerBoundArray[i]);
			geneUpperBoundList.add(i, geneUpperBoundArray[i]);
		}
	}

	
	/**
	 * Instantiates a new real value representation factory.
	 * 
	 * @param solutionSize the solution size
	 * @param geneLowerBound the gene lower bound
	 * @param geneUpperBound the gene upper bound
	 */
	public RealValueRepresentationFactory(int solutionSize, Double geneLowerBound, Double geneUpperBound){
		
		super(solutionSize);
		
		this.geneLowerBoundList = new ArrayList<Double>(solutionSize);
		this.geneUpperBoundList = new ArrayList<Double>(solutionSize);
		
		for(int i=0; i < solutionSize; i++)
		{
			geneLowerBoundList.add(i, geneLowerBound);
			geneUpperBoundList.add(i, geneUpperBound);
		}
		
	}
	
	/**
	 * Instantiates a new real value representation factory.
	 * 
	 * @param solutionSize
	 * @param geneLowerBound
	 * @param geneUpperBound
	 * @param numberOfObjectives
	 */
	public RealValueRepresentationFactory(int solutionSize, Double geneLowerBound, Double geneUpperBound, int numberOfObjectives){
		super(solutionSize,numberOfObjectives);
		
		this.geneLowerBoundList = new ArrayList<Double>(solutionSize);
		this.geneUpperBoundList = new ArrayList<Double>(solutionSize);
		
		for(int i=0; i < solutionSize; i++)
		{
			geneLowerBoundList.add(i, geneLowerBound);
			geneUpperBoundList.add(i, geneUpperBound);
		}
		
	}
	

	@Override
	protected Double copyGeneValue(Double geneValueToCopy) {
		double newValue  = geneValueToCopy;
		return newValue;
	}

	@Override
	public Double generateGeneValue(int genePosition,IRandomNumberGenerator randomGenerator){
		double geneUpperValue = geneUpperBoundList.get(genePosition);
		double geneLowerValue = geneLowerBoundList.get(genePosition);
		double geneValue = randomGenerator.nextDouble()*(geneUpperValue-geneLowerValue)+geneLowerValue;
		return geneValue;
	}

	/**
	 * Gets the gene lower bound list.
	 * 
	 * @return the gene lower bound list
	 */
	public List<Double> getGeneLowerBoundList() {
		return geneLowerBoundList;
	}

	/**
	 * Gets the gene lower bound.
	 * 
	 * @param position the position
	 * 
	 * @return the gene lower bound
	 */
	public Double getGeneLowerBound (int position){
		return geneLowerBoundList.get(position);
	}
	
	/**
	 * Sets the gene lower bound list.
	 * 
	 * @param geneLowerBoundList the new gene lower bound list
	 */
	public void setGeneLowerBoundList(List<Double> geneLowerBoundList) {
		this.geneLowerBoundList = geneLowerBoundList;
	}

	/**
	 * Gets the gene upper bound list.
	 * 
	 * @return the gene upper bound list
	 */
	public List<Double> getGeneUpperBoundList() {
		return geneUpperBoundList;
	}

	/**
	 * Gets the gene upper bound.
	 * 
	 * @param position the position
	 * 
	 * @return the gene upper bound
	 */
	public Double getGeneUpperBound (int position){
		return geneUpperBoundList.get(position);
	}
	
	/**
	 * Sets the gene upper bound list.
	 * 
	 * @param geneUpperBoundList the new gene upper bound list
	 */
	public void setGeneUpperBoundList(List<Double> geneUpperBoundList) {
		this.geneUpperBoundList = geneUpperBoundList;
	}

	@Override
	public RealValueRepresentationFactory deepCopy() {
		List<Double> upperBoundGeneLimitListCopy = new ArrayList<Double>();
		List<Double> lowerBoundGeneLimitListCopy = new ArrayList<Double>();
		
		for(int i = 0; i < solutionSize;i++){
			double geneUpperBound = geneUpperBoundList.get(i);
			double geneLowerBound = geneLowerBoundList.get(i);
			upperBoundGeneLimitListCopy.add(geneUpperBound);
			lowerBoundGeneLimitListCopy.add(geneLowerBound);
		}
		RealValueRepresentationFactory fact = new RealValueRepresentationFactory(solutionSize,lowerBoundGeneLimitListCopy,upperBoundGeneLimitListCopy);
		fact.numberOfObjectives = this.numberOfObjectives;
		
		return fact;
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
