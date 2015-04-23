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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;



/**
 * The Class RecombinationParameters.
 */
public class RecombinationParameters implements IDeepCopy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Substitution rate (number of individuals replaced from one generation to the next. */
	protected int numberOfSurvivors;

	/** Number of descendents created in each generation. */
	protected int offspringSize;

	/** Elitism value - number of individuals automatically selected to pass on to the next generation. */
	protected int elitismValue;

	/** allows multiple offspring from same parent. */
	protected boolean multipleOffspring; //flag that if set(true) allows for multiple offspring from one parent, each generation


	/**
	 * Instantiates a new recombination parameters.
	 * 
	 * @param numberOfSurvivors the number of survivors
	 * @param offSpringSize the off spring size
	 * @param elitismValue the elitism value
	 * @param multipleOffspring the multiple offspring
	 */
	public RecombinationParameters(int numberOfSurvivors,int offSpringSize,int elitismValue,boolean multipleOffspring){
		this.numberOfSurvivors = numberOfSurvivors;
		this.offspringSize = offSpringSize;
		this.elitismValue = elitismValue;
		this.multipleOffspring = multipleOffspring;
	}

	/**
	 * Instantiates a new recombination parameters.
	 * 
	 * @param populationSize the population size
	 */
	public RecombinationParameters(int populationSize){
		setDefaultParameters(populationSize);
	}


	/**
	 * Sets the default parameters.
	 * 
	 * @param populationSize the new default parameters
	 */
	public void setDefaultParameters (int populationSize)
	{
		this.elitismValue = (int) (0.1*populationSize);
		this.multipleOffspring = true;
		this.numberOfSurvivors = (int)(populationSize/2)-1;
		this.offspringSize = populationSize - this.elitismValue - this.numberOfSurvivors; 
	}
	
	/**
	 * Gets the number of survivors.
	 * 
	 * @return the substitutionRate
	 */
	public int getNumberOfSurvivors() {
		return numberOfSurvivors;
	}

	/**
	 * Sets the substitution rate.
	 * 
	 * @param substitutionRate the substitutionRate to set
	 */
	public void setSubstitutionRate(int substitutionRate) {
		this.numberOfSurvivors = substitutionRate;
	}

	/**
	 * Gets the offspring size.
	 * 
	 * @return the offspringSize
	 */
	public int getOffspringSize() {
		return offspringSize;
	}

	/**
	 * Sets the offspring size.
	 * 
	 * @param offspringSize the offspringSize to set
	 */
	public void setOffspringSize(int offspringSize) {
		this.offspringSize = offspringSize;
	}

	/**
	 * Gets the elitism value.
	 * 
	 * @return the elitismValue
	 */
	public int getElitismValue() {
		return elitismValue;
	}

	/**
	 * Sets the elitism value.
	 * 
	 * @param elitismValue the elitismValue to set
	 */
	public void setElitismValue(int elitismValue) {
		this.elitismValue = elitismValue;
	}

	/**
	 * Checks if is multiple offspring.
	 * 
	 * @return the multipleOffspring
	 */
	public boolean isMultipleOffspring() {
		return multipleOffspring;
	}

	/**
	 * Sets the multiple offspring.
	 * 
	 * @param multipleOffspring the multipleOffspring to set
	 */
	public void setMultipleOffspring(boolean multipleOffspring) {
		this.multipleOffspring = multipleOffspring;
	}

	@Override
	public RecombinationParameters deepCopy(){
		return new RecombinationParameters(numberOfSurvivors,offspringSize,elitismValue,multipleOffspring);
	}

}