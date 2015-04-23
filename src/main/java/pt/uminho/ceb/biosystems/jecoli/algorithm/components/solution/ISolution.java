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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;


/**
 * The Interface ISolution.
 */
public interface ISolution<T extends IRepresentation> {
	
	/**
	 * Get the selection value
	 * 
	 * @return the selection value
	 */
	Double getSelectionValue();
	
	/**
	 * Sets the selection value
	 * 
	 * @param the selection value
	 */	
	void setSelectionValue(Double selectionValue);
	   
    /**
     * Gets the scalar fitness value.
     * 
     * @return the scalar fitness value
     */
    Double getScalarFitnessValue();
    
    /**
     * Sets the fitness value.
     * 
     * @param fitnessValue the new fitness value
     */
    void setScalarFitnessValue(Double fitnessValue);
    
    /**
     * Gets the representation.
     * 
     * @return the representation
     */
    T getRepresentation();
    
    /**
     * Gets the fitness value.
     * 
     * @param objectiveIndex the objective index
     * 
     * @return the fitness value
     */
    Double getFitnessValue(int objectiveIndex);
    
    /**
     * Gets the array of fitnesses.
     * 
     * @return the fitness values array
     */
    Double[] getFitnessValuesArray();
    
   
    /**
     * Gets the number of objectives.
     * 
     * @return the number of objectives
     */
    int getNumberOfObjectives();
    
    /**
     * Gets the location.
     * 
     * @return the location
     */
    int getLocation();
    
    /**
     * Sets the location.
     * 
     * @param location the new location
     */
    void setLocation(int location);
    
    /**
     * Gets the rank.
     * 
     * @return the rank
     */
    int getRank();
    
    /**
     * Sets the rank.
     * 
     * @param rank the new rank
     */
    void setRank(int rank);
    
    /**
     * Gets the crowding distance.
     * 
     * @return the crowding distance
     */
    double getCrowdingDistance();
    
    /**
     * Sets the crowding distance.
     * 
     * @param crowdingDistance the new crowding distance
     */
    void setCrowdingDistance(double crowdingDistance);

    /**
     * Sets the fitness values
     * 
     * @param fitnessValues
     */
	void setFitnessValues(Double... fitnessValues);
    
	/**
	 * @return true if this solution has multiple objectives, false otherwise
	 */
	boolean isMultiobjective();
	
	/**
	 * Implements an equals function for all the instance variables in the solution
	 * 
	 * @param solution the solution to be compared with the current one
	 * @return true if the solutions are equal, false otherwise
	 */
	boolean equals(ISolution<T> solution);
	
	/**
	 * Implements an equals function using only the representation of the solutions
	 * @param solution the solution to be compared with the current one
	 * 
	 * @return true if the solutions are equal, false otherwise
	 */
	boolean equalsRepresentation(ISolution<T> solution);
	
	/**
	 * Implements an equals function using only the representation and the fitnesses of the solutions
	 * @param solution the solution to be compared with the current one
	 * 
	 * @return true if the solutions are equal, false otherwise
	 */
	boolean equalsRepresentationAndFitness(ISolution<T> solution);
    ISolution<T> deepCopy();
}