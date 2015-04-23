package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.IntegerAddMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;



public class IntegerAddMutationTest extends AbstractReproductionOperatorTest<ILinearRepresentation<Integer>, IntegerRepresentationFactory>{

	protected Integer upperBound;
	protected Integer lowerBound;
	
	public IntegerAddMutationTest()
	{
		this.testName = "IntegerAddMutation";
		
		this.expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Integer>>>();
		this.initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Integer>>>();
		
	}

	

	@Override
	protected void specificOperatorTests() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTestValues() {
		this.solutionFactory = new IntegerRepresentationFactory(this.size, this.upperBound,this.lowerBound);
		this.operator = new IntegerAddMutation(size);
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

	public Integer getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Integer lowerBound) {
		this.lowerBound = lowerBound;
	}

}
