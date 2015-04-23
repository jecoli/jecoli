package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.CutAndSpliceCrossOver;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.binary.BinaryRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;


public class CutAndSpliceCrossoverTest extends AbstractReproductionOperatorTest<ILinearRepresentation<Boolean>, ILinearRepresentationFactory<Boolean>>{
	
	public CutAndSpliceCrossoverTest()
	{
		this.testName = "CutAndSpliceCrossover";
		
		this.expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		this.initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
	}
	

	@Override
	public void setTestValues() {
		this.solutionFactory = new BinaryRepresentationFactory(this.size);
		this.operator = new CutAndSpliceCrossOver<Boolean>();
	}
	
	@Override
	public void specificOperatorTests() {
	
		
	}
	
}
