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

// TODO: Auto-generated Javadoc
/**
 * The Class AnnealingSchedule.
 */
public class AnnealingSchedule implements IAnnealingSchedule{
	
	/** The number of function evaluations. */
	protected int numberOfFunctionEvaluations;
	
	/** The alpha. */
	protected double alpha;
	
	/** The initial temperature. */
	protected double initialTemperature;
	
	/** The current temperature. */
	protected double currentTemperature;
	
	/** The minimum temperature. */
	protected double minimumTemperature;

	private boolean debug = false;
	
	/**
	 * Instantiates a new annealing schedule.
	 * 
	 * @param numberOfFunctionEvaluations the number of function evaluations
	 * @param alpha the alpha
	 * @param initialTemperature the initial temperature
	 */
	public AnnealingSchedule(int numberOfFunctionEvaluations,double alpha,	double initialTemperature){
		this.numberOfFunctionEvaluations = numberOfFunctionEvaluations;
		this.alpha = alpha;
		this.initialTemperature = initialTemperature;
		this.currentTemperature = initialTemperature;
	}
	
	/**
	 * Instantiates a new annealing schedule.
	 * 
	 * @param allowedInitialDelta the allowed initial delta
	 * @param allowedFinalDelta the allowed final delta
	 * @param numberOfTrialsPerTemperature the number of trials per temperature
	 * @param totalNumberOfFunctionEvaluations the total number of function evaluations
	 */
	public AnnealingSchedule(double allowedInitialDelta, double allowedFinalDelta, int numberOfTrialsPerTemperature,int totalNumberOfFunctionEvaluations) {
		initialTemperature = -allowedInitialDelta / Math.log(0.5);
		minimumTemperature = -allowedFinalDelta / Math.log(0.5);
		this.numberOfFunctionEvaluations = numberOfTrialsPerTemperature;
		
		double temperatureModifications =  Math.ceil(totalNumberOfFunctionEvaluations / numberOfTrialsPerTemperature);
		alpha = Math.exp(Math.log(minimumTemperature / initialTemperature) / temperatureModifications);
		currentTemperature = initialTemperature;
	}

	public AnnealingSchedule(double alpha,double allowedInitialDelta, double allowedFinalDelta, int numberOfTrialsPerTemperature,int totalNumberOfFunctionEvaluations) {
		initialTemperature = -allowedInitialDelta / Math.log(0.5);
		minimumTemperature = -allowedFinalDelta / Math.log(0.5);
		this.numberOfFunctionEvaluations = numberOfTrialsPerTemperature;

		//double temperatureModifications =  Math.ceil(totalNumberOfFunctionEvaluations / numberOfTrialsPerTemperature);
		this.alpha = alpha;// Math.exp(Math.log(minimumTemperature / initialTemperature) / temperatureModifications);
		currentTemperature = initialTemperature;
	}
	
	

	/* (non-Javadoc)
	 * @see algorithm.simulatedannealing.IAnnealingSchedule#caculateAcceptSolutionProbability(double, double)
	 */
	@Override
	public double caculateAcceptSolutionProbability(double currentSolutionFitnessValue, double trialSolutionFitnessValue){
		double deltaEnergy= trialSolutionFitnessValue - currentSolutionFitnessValue;
		double value = deltaEnergy/currentTemperature;
		return Math.exp(value);
	}

	@Override
	public double calculateNewTemperature(){
		double oldT = currentTemperature;
		currentTemperature = alpha*currentTemperature;
		if(debug ) System.out.println("Ctemp:"+currentTemperature);
		return currentTemperature;
	}

	@Override
	public void getFitnessFunctionData(Double scalarFitnessValue) {
		
	}

	@Override
	public boolean isEquilibriumState(int numberOfAcceptedMoves,int numberOfRejectedMoves) {
		return (numberOfAcceptedMoves + numberOfRejectedMoves) >= numberOfFunctionEvaluations;
	}

	@Override
	public void reset() {
		currentTemperature = initialTemperature;
	}

}
