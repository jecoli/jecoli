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


import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;



// TODO: Auto-generated Javadoc
/**
 * The Class RankingPreProcessing.
 */
public class RankingPreProcessing<T extends IRepresentation> implements IPreProcessing<T>{

	private static final double SOLUTION_FITNESS_MAX_VALUE = 1E300;

	/* (non-Javadoc)
	 * @see operators.selection.preprocessing.IPreProcessing#preProcessFitness(boolean, core.interfaces.ISolutionSet)
	 */
	@Override
	public List<ScaledFitnessData<T>> preProcessFitness(boolean isMaximization, ISolutionSet<T> solutionSet) throws Exception {
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		List<ScaledFitnessData<T>> scaledFitnessDataVector = new ArrayList<ScaledFitnessData<T>>();
		double totalFitnessValue = calculateTotalFitnessValue(solutionSet);
		
		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<T> solution = solutionSet.getSolution(i);
			double solutionFitness = solution.getScalarFitnessValue();
			
			if(solutionFitness > SOLUTION_FITNESS_MAX_VALUE)
				solutionFitness = SOLUTION_FITNESS_MAX_VALUE;
			
			if(solutionFitness < -1*SOLUTION_FITNESS_MAX_VALUE)
				solutionFitness = SOLUTION_FITNESS_MAX_VALUE*-1;
			
			double scaledFitness = solutionFitness/totalFitnessValue;
			ScaledFitnessData<T> scaledFitnessData = new ScaledFitnessData<T>(solution,scaledFitness);
			scaledFitnessDataVector.add(scaledFitnessData);
		}
		
		return scaledFitnessDataVector;
	}



	protected double calculateTotalFitnessValue(ISolutionSet<T> solutionSet) {
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		double totalFitnessValue = 0;
		
		for(int i = 0; i < numberOfSolutions;i++){ 
			ISolution<T> solution = solutionSet.getSolution(i);
			Double solutionScalarFitnessValue = solution.getScalarFitnessValue();
			double processedScalarFitnessValue = processRawFitnessValue(solutionScalarFitnessValue);
			totalFitnessValue += processedScalarFitnessValue;
		}
		
		return totalFitnessValue;
	}

	protected double processRawFitnessValue(Double solutionScalarFitnessValue) {
		
		if(Double.MAX_VALUE == solutionScalarFitnessValue)
			return SOLUTION_FITNESS_MAX_VALUE;
		
		if(Double.MAX_VALUE*(-1) == solutionScalarFitnessValue)
			return SOLUTION_FITNESS_MAX_VALUE*-1;
		
		return solutionScalarFitnessValue;
	}
	
	
	

}
