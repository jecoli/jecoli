package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set.SetGrowthMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


public class SetGrowthMutationTest extends AbstractReproductionOperatorTest<ISetRepresentation<Integer>, ISetRepresentationFactory<Integer>> {
	
	public SetGrowthMutationTest()
	{
		this.testName="SetGrowthMutation";
		
		this.expectedSolutions = new ArrayList<ISolution<ISetRepresentation<Integer>>>();
		this.initialPopulation = new ArrayList<ISolution<ISetRepresentation<Integer>>>();
	}

	@Override
	public void setTestValues() {
		this.solutionFactory = new IntegerSetRepresentationFactory(this.size);
		this.operator = new SetGrowthMutation<Integer>(1);
	}

	@Override
	protected void specificOperatorTests() {
		// TODO Auto-generated method stub

	}

}
