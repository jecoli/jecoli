package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.OnePointCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;



public class OnePointCrossoverTest extends AbstractReproductionOperatorTest<ILinearRepresentation<Boolean>, ILinearRepresentationFactory<Boolean>>{
	
	public OnePointCrossoverTest()
	{
		this.testName = "OnePointCrossover";
		
		this.expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		this.initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
	}
	
	@Override
	public void setTestValues() {		
		this.solutionFactory = new BinaryRepresentationFactory(this.size);
		this.operator = new OnePointCrossover<Boolean>();
	}

	@Override
	protected void specificOperatorTests() {
		// TODO Auto-generated method stub
		
	}
	
}
