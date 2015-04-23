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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing;


/**
 * The Interface IAnnealingSchedule.
 */
public interface IAnnealingSchedule{
	
	/**
	 * Checks if is equilibrium state.
	 * 
	 * @param numberOfAcceptedMoves the number of accepted moves
	 * @param numberOfRejectedMoves the number of rejected moves
	 * 
	 * @return true, if is equilibrium state
	 */
	boolean isEquilibriumState(int numberOfAcceptedMoves,int numberOfRejectedMoves);
	
	/**
	 * Calculate new temperature.
	 * 
	 * @return the new temperature
	 */
	double calculateNewTemperature();
	
	/**
	 * Reset.
	 */
	void reset();
	
	/**
	 * Caculate accept solution probability.
	 * 
	 * @param currentSolutionFitnessValue the current solution fitness value
	 * @param trialSolutionFitnessValue the trial solution fitness value
	 * 
	 * @return the double
	 */
	double caculateAcceptSolutionProbability(double currentSolutionFitnessValue, double trialSolutionFitnessValue);
	
	/**
	 * Gets the fitness function data.
	 * 
	 * @param scalarFitnessValue the scalar fitness value
	 * 
	 * @return the fitness function data
	 */
	void getFitnessFunctionData(Double scalarFitnessValue);
}
