package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.LinearGenomeRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;



public class LinearGenomeRandomMutationTest extends AbstractReproductionOperatorTest<ILinearRepresentation<Integer>, ILinearRepresentationFactory<Integer>>{

	protected Integer upperBound;
	protected Integer lowerBound;
	

	public LinearGenomeRandomMutationTest()
	{
		this.testName = "LinearGenomeRandomMutation";
		
		expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Integer>>>();
		initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Integer>>>();
	}
	

	@Override
	protected void specificOperatorTests() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setTestValues() {
		this.solutionFactory = new IntegerRepresentationFactory(this.size, this.upperBound,this.lowerBound);
		this.operator = new LinearGenomeRandomMutation<Integer>(1);
    }

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	
}
