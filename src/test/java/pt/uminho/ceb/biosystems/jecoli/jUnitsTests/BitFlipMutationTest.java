package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.BitFlipMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;



public class BitFlipMutationTest extends AbstractReproductionOperatorTest<ILinearRepresentation<Boolean>, BinaryRepresentationFactory>{
	
	public BitFlipMutationTest()
	{
		this.testName = "BitFlipMutation";
		
		expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
	}
	
	@Override
	public void setTestValues()
	{
		this.solutionFactory = new BinaryRepresentationFactory(size);
		this.operator = new BitFlipMutation(size);	
	}

	@Override
	protected void specificOperatorTests() {
		// TODO Auto-generated method stub
		
	}
	
}
