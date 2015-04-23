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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionProcedureException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.preprocessing.IPreProcessing;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.preprocessing.RankingPreProcessing;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.preprocessing.ScaledFitnessData;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;



// TODO: Auto-generated Javadoc
/**
 * The Class RouletteWheelSelection.
 */
public class RouletteWheelSelection<T extends IRepresentation> implements ISelectionOperator<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7930450291053634592L;
	/** The preprocesing. */
	protected IPreProcessing<T> preprocesing;

	/**
	 * Instantiates a new roulette wheel selection.
	 * 
	 * @param preprocesing the preprocesing
	 */
	public RouletteWheelSelection(IPreProcessing<T> preprocesing) {
		this.preprocesing = preprocesing;
	}
	
	/**
	 * Instantiates a new roulette wheel selection.
	 */
	public RouletteWheelSelection() {
		preprocesing = new RankingPreProcessing<T>();
	}
	
	/* (non-Javadoc)
	 * @see operators.ISelectionOperator#selectSolutions(int, core.interfaces.ISolutionSet, boolean)
	 */
	public 	List<ISolution<T>> selectSolutions(int numberOfSolutionsToSelect, ISolutionSet<T> solutionSet,boolean isMaximization, IRandomNumberGenerator randomNumberGenerator) throws Exception{
		if(numberOfSolutionsToSelect < 0)
			throw new InvalidSelectionParameterException(" numberOfSolutionToSelect < 0");
		
		
		return selectSolutionSetWithRepetition(numberOfSolutionsToSelect,solutionSet,isMaximization,randomNumberGenerator);
	}

	/**
	 * Select solution set with repetition.
	 * 
	 * @param numberOfSolutionsToSelect the number of solutions to select
	 * @param solutionSet the solution set
	 * @param isMaximization the is maximization
	 * @param randomNumberGenerator 
	 * 
	 * @return the list< i solution>
	 * 
	 * @throws Exception the exception
	 */
	protected List<ISolution<T>> selectSolutionSetWithRepetition(int numberOfSolutionsToSelect, ISolutionSet<T> solutionSet,boolean isMaximization, IRandomNumberGenerator randomNumberGenerator) throws Exception{
		List<ISolution<T>> solutionList = new ArrayList<ISolution<T>>(numberOfSolutionsToSelect);
		
		List<ScaledFitnessData<T>> scaledFitnessDataVector = preprocesing.preProcessFitness(isMaximization,solutionSet);
		
		for(int i = 0; i < numberOfSolutionsToSelect;i++){
			ISolution<T> solution = selectSolution(solutionSet,isMaximization,scaledFitnessDataVector,randomNumberGenerator);
			solutionList.add(solution);
		}
		
		return solutionList;
	}



	/**
	 * Calculate total scaled fitness value.
	 * 
	 * @param scaledFitnessDataVector the scaled fitness data vector
	 * 
	 * @return the double
	 */
	protected double calculateTotalScaledFitnessValue(List<ScaledFitnessData<T>> scaledFitnessDataVector){
		double totalFitnessValue = 0;
		
		for(ScaledFitnessData<T> scaledFitnessData:scaledFitnessDataVector)
			totalFitnessValue += scaledFitnessData.getScaledFitnessValue();
		
		return totalFitnessValue;
	}

	/**
	 * Select solution.
	 * 
	 * @param solutionSet the solution set
	 * @param totalFitnessValue the total fitness value
	 * @param isMaximization the is maximization
	 * @param scaledFitnessDataVector the scaled fitness data vector
	 * @param randomNumberGenerator 
	 * 
	 * @return the i solution
	 * 
	 * @throws InvalidSelectionProcedureException the invalid selection procedure exception
	 */
	protected ISolution<T> selectSolution(ISolutionSet<T> solutionSet, boolean isMaximization, List<ScaledFitnessData<T>> scaledFitnessDataVector, IRandomNumberGenerator randomNumberGenerator) throws InvalidSelectionProcedureException {
		double overallScaledFitnessValue = calculateTotalScaledFitnessValue(scaledFitnessDataVector);
		double randomFitnessValue = randomNumberGenerator.nextDouble()*overallScaledFitnessValue;
		double currentFitnessValue = 0;
		
		
		for(int i = 0; i < scaledFitnessDataVector.size();i++){
			ScaledFitnessData<T> scaledFitnessData = scaledFitnessDataVector.get(i);
			ISolution<T> solution = scaledFitnessData.getSolution();
			
			if(isMaximization)
				currentFitnessValue += scaledFitnessData.getScaledFitnessValue();
			else
				currentFitnessValue += 1-scaledFitnessData.getScaledFitnessValue();
		
			
			if((currentFitnessValue >= randomFitnessValue)) 
				return solution;
			
			
		}
		
		throw new InvalidSelectionProcedureException();
	}

	@Override
	public RouletteWheelSelection<T> deepCopy() {
		return new RouletteWheelSelection<T>(preprocesing);
	}

	

}
