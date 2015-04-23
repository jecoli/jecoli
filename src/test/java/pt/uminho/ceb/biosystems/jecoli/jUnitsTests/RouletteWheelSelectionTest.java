package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.RouletteWheelSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;

public class RouletteWheelSelectionTest extends AbstractSelectionOperatorTest<ILinearRepresentation<Boolean>, ILinearRepresentationFactory<Boolean>>{

	
	public RouletteWheelSelectionTest()
	{
		this.testName = "RouletteWheelSelection";
	
		this.expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		this.initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
	}


	@Override
	protected void specificOperatorTests() {
		
		
	}
		@Override
	public void setTestValues() {
		this.operator = new RouletteWheelSelection<ILinearRepresentation<Boolean>>();		
	}

}
