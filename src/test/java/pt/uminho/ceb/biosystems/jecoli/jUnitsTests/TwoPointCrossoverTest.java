package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.TwoPointCrossOver;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;



public class TwoPointCrossoverTest extends AbstractReproductionOperatorTest<ILinearRepresentation<Boolean>, ILinearRepresentationFactory<Boolean>>{
	
	public TwoPointCrossoverTest()
	{
		this.testName = "TwoPointCrossover";
		
		this.expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		this.initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
	}
	
	
	@Override
	public void setTestValues() {
		this.solutionFactory = new BinaryRepresentationFactory(this.size);
		this.operator = new TwoPointCrossOver<Boolean>();
	}
	
	
	@Override
	protected void specificOperatorTests() {
		
	}
}
