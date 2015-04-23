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
package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.differentialevolution;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.LinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;


// TODO: Auto-generated Javadoc
// TODO: add parameter with number of random vectors to add / subtract
// currently implemented DE/rand/1 and DE/rand/2

/**
 * The Class DifferentialEvolution.
 */
public class DifferentialEvolution extends AbstractAlgorithm<ILinearRepresentation<Double>,DifferentialEvolutionConfiguration>{


	private static final long serialVersionUID = -2027599736889292285L;

	/**
	 * Instantiates a new differential evolution.
	 * 
	 * @param configuration the configuration
	 * 
	 * @throws InvalidConfigurationException the invalid configuration exception
	 * @throws InvalidEvaluationFunctionInputDataException 
	 */
	public DifferentialEvolution(DifferentialEvolutionConfiguration configuration) throws Exception {
		super(configuration);
	}

	@Override
	public ISolutionSet<ILinearRepresentation<Double>> initialize() throws Exception {
		if(configuration.isDoPopulationInitialization()){
		IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
		ISolutionFactory<ILinearRepresentation<Double>> solutionFactory = configuration.getSolutionFactory();
		int numberOfSolutions = configuration.getPopulationSize();
		//return solutionFactory.generateSolutionSet(numberOfSolutions);
		// added the rest - MR
		ISolutionSet<ILinearRepresentation<Double>> solutionSet = solutionFactory.generateSolutionSet(numberOfSolutions,randomGenerator);
		IEvaluationFunction<ILinearRepresentation<Double>> evaluationFunction = configuration.getEvaluationFunction();
		evaluationFunction.evaluate(solutionSet);
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(solutionSet.getNumberOfSolutions());
		return solutionSet;
		}else{
			ISolutionSet<ILinearRepresentation<Double>> solutionSet = configuration.getInitialPopulation();
			IEvaluationFunction<ILinearRepresentation<Double>> evaluationFunction = configuration.getEvaluationFunction();
			evaluationFunction.evaluate(solutionSet);
			return solutionSet;
		}
		
	}

	@Override
	public ISolutionSet<ILinearRepresentation<Double>> iteration(AlgorithmState<ILinearRepresentation<Double>> algorithmState, ISolutionSet<ILinearRepresentation<Double>> solutionSet) throws Exception {
		List<ISolution<ILinearRepresentation<Double>>> mutatedSolutionSet = mutation(solutionSet);
		List<ISolution<ILinearRepresentation<Double>>> crossoverSolutionSet = crossover(mutatedSolutionSet,solutionSet);
		ISolutionSet<ILinearRepresentation<Double>> newSolutionSet = selection(crossoverSolutionSet,solutionSet);
		// added following line
		algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(newSolutionSet.getNumberOfSolutions());
		return newSolutionSet;
	}

	/**
	 * Selection.
	 * 
	 * @param crossoverSolutionSet the crossover solution set
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution set
	 */
	protected ISolutionSet<ILinearRepresentation<Double>> selection(List<ISolution<ILinearRepresentation<Double>>> crossoverSolutionSet,ISolutionSet<ILinearRepresentation<Double>> solutionSet) {
		ISolutionSet<ILinearRepresentation<Double>> newSolutionSet = new SolutionSet<ILinearRepresentation<Double>>();
		IEvaluationFunction<ILinearRepresentation<Double>> evaluationFunction = configuration.getEvaluationFunction();
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		
		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<ILinearRepresentation<Double>> originalSolution = solutionSet.getSolution(i);
			ISolution<ILinearRepresentation<Double>> trialSolution = crossoverSolutionSet.get(i);
			evaluationFunction.evaluateSingleSolution(trialSolution);
			double trialSolutionFitness = trialSolution.getScalarFitnessValue();
			double originalSolutionFitnes = originalSolution.getScalarFitnessValue();
			if (configuration.getEvaluationFunction().isMaximization()) // changed
			{
				if(trialSolutionFitness >= originalSolutionFitnes)	
					newSolutionSet.add(trialSolution);
				else
					newSolutionSet.add(originalSolution);
			}
			else
			{
				if(trialSolutionFitness <= originalSolutionFitnes)	
					newSolutionSet.add(trialSolution);
				else
					newSolutionSet.add(originalSolution);
			}
		}
		
		return newSolutionSet;
	}

	/**
	 * Crossover.
	 * 
	 * @param mutatedSolutionSet the mutated solution set
	 * @param solutionSet the solution set
	 * 
	 * @return the list< i solution>
	 */
	protected List<ISolution<ILinearRepresentation<Double>>> crossover(List<ISolution<ILinearRepresentation<Double>>> mutatedSolutionSet,ISolutionSet<ILinearRepresentation<Double>> solutionSet){
		int numberOfSolutions = solutionSet.getNumberOfSolutions();
		double crossoverProbability = configuration.getCrossoverProbability();
		
		for(int i = 0; i < numberOfSolutions;i++){
			ISolution<ILinearRepresentation<Double>> originalSolution = solutionSet.getSolution(i);
			ISolution<ILinearRepresentation<Double>> trialSolution = mutatedSolutionSet.get(i);
			crossoverSolutions(originalSolution,trialSolution,crossoverProbability);
		}
			
		
		return mutatedSolutionSet;
	}

	/**
	 * Crossover solutions.
	 * 
	 * @param originalSolution the original solution
	 * @param trialSolution the trial solution
	 * @param crossoverProbability the crossover probability
	 */
	protected void crossoverSolutions(ISolution<ILinearRepresentation<Double>> originalSolution,ISolution<ILinearRepresentation<Double>> trialSolution,double crossoverProbability){
		ILinearRepresentation<Double> originalRepresentationVector = (ILinearRepresentation<Double>) originalSolution.getRepresentation();
		ILinearRepresentation<Double> trialRepresentationVector = (ILinearRepresentation<Double>) trialSolution.getRepresentation();
		int numberOfPositions = originalRepresentationVector.getNumberOfElements();
        RealValueRepresentationFactory solutionFactory =
                (RealValueRepresentationFactory)(configuration.getSolutionFactory());

        for(int i = 0;i < numberOfPositions;i++)
		{
			double originalValue = originalRepresentationVector.getElementAt(i);
			double randomNumber = configuration.getRandomNumberGenerator().nextDouble();
			if(randomNumber > crossoverProbability){
				trialRepresentationVector.setElement(i,originalValue);
			}
			// test for limits
			if( trialRepresentationVector.getElementAt(i) > solutionFactory.getGeneUpperBound(i) )
				trialRepresentationVector.setElement(i,originalValue);
			if( trialRepresentationVector.getElementAt(i) < solutionFactory.getGeneLowerBound(i) )
				trialRepresentationVector.setElement(i,originalValue);
		}
		
	}

	// calculates list of trial vectors
	/**
	 * Mutation.
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the list< i solution>
	 */
	protected List<ISolution<ILinearRepresentation<Double>>> mutation(ISolutionSet<ILinearRepresentation<Double>> solutionSet){
		int numberOfIndividuals = solutionSet.getNumberOfSolutions();
		List<ISolution<ILinearRepresentation<Double>>> solutionList = new ArrayList<ISolution<ILinearRepresentation<Double>>>(numberOfIndividuals);
		for(int i = 0; i < numberOfIndividuals;i++){
			ISolution<ILinearRepresentation<Double>> baseVector = chooseBaseIndividualVector(solutionSet);
			ISolution<ILinearRepresentation<Double>> randomVector = chooseRandomVector(solutionSet);
			ISolution<ILinearRepresentation<Double>> randomVector1 = chooseRandomVector(solutionSet);
			ISolution<ILinearRepresentation<Double>> trialVector = calculateTrialVector(baseVector,randomVector,randomVector1);
			solutionList.add(trialVector);
		}
		
		return solutionList;
		
	}

	/**
	 * Calculate trial vector.
	 * 
	 * @param baseVector the base vector
	 * @param randomVector the random vector
	 * @param randomVector1 the random vector1
	 * 
	 * @return the i solution
	 */
	protected ISolution<ILinearRepresentation<Double>> calculateTrialVector(ISolution<ILinearRepresentation<Double>> baseVector,ISolution<ILinearRepresentation<Double>> randomVector, ISolution<ILinearRepresentation<Double>> randomVector1) {
		ILinearRepresentation<Double> baseVectorRepresentation = baseVector.getRepresentation();
		ILinearRepresentation<Double> randomVectorRepresentation = randomVector.getRepresentation();
		ILinearRepresentation<Double> randomVectorRepresentation1 =  randomVector1.getRepresentation();
		List<Double> trialVectorGenome = new ArrayList<Double>();

		calculateAuxiliarVectorDifference(trialVectorGenome,randomVectorRepresentation,randomVectorRepresentation1);
		calculateAuxiliarVectorSum(trialVectorGenome,baseVectorRepresentation);
		
		ILinearRepresentation<Double> trialVectorRepresentation = new LinearRepresentation<Double>(trialVectorGenome);
		
		return new Solution<ILinearRepresentation<Double>>(trialVectorRepresentation);
	}

	/**
	 * Calculate auxiliar vector sum.
	 * 
	 * @param trialVectorGenome the trial vector genome
	 * @param baseVectorRepresentation the base vector representation
	 */
	protected void calculateAuxiliarVectorSum(List<Double> trialVectorGenome,	ILinearRepresentation<Double> baseVectorRepresentation) {
		int numberOfParameters = baseVectorRepresentation.getNumberOfElements();
        RealValueRepresentationFactory solutionFactory =
                (RealValueRepresentationFactory)(configuration.getSolutionFactory());

        for(int i = 0; i < numberOfParameters;i++){
			double currentValue = trialVectorGenome.get(i);
			double baseVectorValue = baseVectorRepresentation.getElementAt(i);
			double result = currentValue + baseVectorValue;
            if( result > solutionFactory.getGeneUpperBound(i) )
                trialVectorGenome.set(i,solutionFactory.getGeneUpperBound(i));
            else if( result < solutionFactory.getGeneLowerBound(i) )
                trialVectorGenome.set(i,solutionFactory.getGeneLowerBound(i));
            else
			    trialVectorGenome.set(i,result);
		}
		
	}

	/**
	 * Calculate auxiliar vector difference.
	 * 
	 * @param trialVectorGenome the trial vector genome
	 * @param randomVectorRepresentation the random vector representation
	 * @param randomVectorRepresentation1 the random vector representation1
	 */
	protected void calculateAuxiliarVectorDifference(List<Double> trialVectorGenome,ILinearRepresentation<Double> randomVectorRepresentation,
			ILinearRepresentation<Double> randomVectorRepresentation1) {
		int numberOfParameters = randomVectorRepresentation.getNumberOfElements();
		double scaleFactorF = configuration.getScaleFactor();
		RealValueRepresentationFactory solutionFactory =
				(RealValueRepresentationFactory)(configuration.getSolutionFactory());
		for(int i = 0; i < numberOfParameters;i++){
			double randomVectorValue = randomVectorRepresentation.getElementAt(i);
			double randomVectorValue1 = randomVectorRepresentation1.getElementAt(i);
			double result = scaleFactorF*(randomVectorValue-randomVectorValue1);
            trialVectorGenome.add(result);
		}
		
	}

	/**
	 * Choose random vector.
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution
	 */
	protected ISolution<ILinearRepresentation<Double>> chooseRandomVector(ISolutionSet<ILinearRepresentation<Double>> solutionSet) {
		int solutionIndex = configuration.getRandomNumberGenerator().nextInt(solutionSet.getNumberOfSolutions());
		return solutionSet.getSolution(solutionIndex);
	}

	/**
	 * Choose base individual vector.
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the i solution
	 */
	protected ISolution<ILinearRepresentation<Double>> chooseBaseIndividualVector(ISolutionSet<ILinearRepresentation<Double>> solutionSet) {
		BaseVectorSelectionType baseVectorSelectionType = configuration.getBaseVectorSelectionType();
		
		switch(baseVectorSelectionType){
			case RANDOM:
				return chooseRandomVector(solutionSet);
			case BEST:
				if (configuration.getEvaluationFunction().isMaximization()) // changed
					return solutionSet.getHighestValuedSolutionsAt(0);
				else return solutionSet.getLowestValuedSolutionsAt(0);
			case RANDOM_TO_BEST:
				return null; //TODO
		}
		return null;
	}

	@Override
	public IAlgorithm<ILinearRepresentation<Double>> deepCopy() throws Exception {
		DifferentialEvolutionConfiguration configurationCopy = configuration.deepCopy();
		return new DifferentialEvolution(configurationCopy);
	}
	
	

}
