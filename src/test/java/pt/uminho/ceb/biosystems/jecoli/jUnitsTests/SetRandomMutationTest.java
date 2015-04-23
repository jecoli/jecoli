package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set.SetRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


public class SetRandomMutationTest extends AbstractReproductionOperatorTest<ISetRepresentation<Integer>,ISetRepresentationFactory<Integer>>{
	
	public SetRandomMutationTest()
	{
		this.testName = "SetRandomMutation";
		
		this.expectedSolutions = new ArrayList<ISolution<ISetRepresentation<Integer>>>();
		this.initialPopulation = new ArrayList<ISolution<ISetRepresentation<Integer>>>();
	}
	

	@Override
	public void setTestValues() {
		this.solutionFactory = new IntegerSetRepresentationFactory(this.size);
		this.operator = new SetRandomMutation<Integer>();
	}

	@Override
	protected void specificOperatorTests() {
		// TODO Auto-generated method stub

	}

}
