package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ILocalOptimizationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;

public class BestAddElementSetLocalOperator extends AbstractSetLocalOperator<Integer,ISetRepresentation<Integer>,IntegerSetRepresentationFactory> {

	private static final long serialVersionUID = 1L;

	int numberFunctionEvaluations = 0;
	
	boolean hasImproved = false;
	
	
	public BestAddElementSetLocalOperator(IEvaluationFunction<ISetRepresentation<Integer>> evalFunction) {
		super(evalFunction);
	}

	@Override
	protected ISolution<ISetRepresentation<Integer>> localopt(ISolution<ISetRepresentation<Integer>> originalSolution,IntegerSetRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) {
		
		this.hasImproved = false;
		this.numberFunctionEvaluations = 0;
		
		ISolution<ISetRepresentation<Integer>> bestSolution = solutionFactory.copySolution(originalSolution);
		ISetRepresentation<Integer> originalGenome = originalSolution.getRepresentation();
		Integer maxElement = solutionFactory.getMaxElement();
		double originalFitness = originalSolution.getScalarFitnessValue();
		double bestFitness = originalFitness;
		
	
		for(int i=0; i < maxElement; i++)
		{
			if (!originalGenome.containsElement(i))
			{
				ISolution<ISetRepresentation<Integer>> newSolution = solutionFactory.copySolution(originalSolution);
				ISetRepresentation<Integer> newGenome = newSolution.getRepresentation();
				newGenome.addElement(i);
				this.evalFunction.evaluateSingleSolution(newSolution);
				this.numberFunctionEvaluations++;
				double newFitness = newSolution.getScalarFitnessValue();
				if (newFitness > bestFitness) {
					this.hasImproved = true;
					bestSolution = newSolution;
					bestFitness = newFitness;
				}
			}
		}
		
		return bestSolution;
	}

	@Override
	public int getNumberFunctionEvaluationPerfomed() {
		return this.numberFunctionEvaluations;
	}

	@Override
	public boolean hasImproved() {
		return this.hasImproved;
	}

	@Override
	public ILocalOptimizationOperator<ISetRepresentation<Integer>> deepCopy() throws Exception {
		return new BestAddElementSetLocalOperator(evalFunction.deepCopy());
	}


	
	
}
