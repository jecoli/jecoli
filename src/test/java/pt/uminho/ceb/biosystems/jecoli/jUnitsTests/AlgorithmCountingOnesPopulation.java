package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;

public class AlgorithmCountingOnesPopulation extends AbstractEvaluationFunction<ILinearRepresentation<Boolean>>{

	public AlgorithmCountingOnesPopulation(boolean isMaximization) {
		super(isMaximization);
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Boolean>> deepCopy() {
		return new AlgorithmCountingOnesPopulation(isMaximization);
	}

	@Override
	public double evaluate(ILinearRepresentation<Boolean> solutionRepresentation){	
		return count(solutionRepresentation)*1.0;
	}
	
	protected int count(ILinearRepresentation<Boolean> genomeRep){
		
		int counter = 0;
		
		for(int i=0; i < genomeRep.getNumberOfElements(); i++)
			if(genomeRep.getElementAt(i)) counter++;
		
		return counter;
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfObjectives() {
		// TODO Auto-generated method stub
		return 0;
	}

}
