package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfInputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidNumberOfOutputSolutionsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;


public abstract class AbstractOperatorTest<T extends IRepresentation,S extends ISolutionFactory<T>> extends AbstractTestClass<T,S>{
	
	protected S solutionFactory;
	
	protected abstract void specificOperatorTests();
	protected abstract void testSolution();
	
	
	@Test
	public void testSolutions(List<ISolution<T>> actualResults,List<ISolution<T>> expectedSolutions) 
						throws InvalidNumberOfInputSolutionsException, InvalidNumberOfOutputSolutionsException {
		assertTrue("Acutal results size is bigger than expected solutions size.",actualResults.size() <= expectedSolutions.size());

		for(ISolution<T> solution:actualResults)
			assertTrue(this.testName+": Actual results are different from expected.",verifyExpectedSolutionList(solution, expectedSolutions));

	}

	protected boolean verifyExpectedSolutionList(ISolution<T> solution, List<ISolution<T>> expectedSolutionList) {
		T solutionRepresentation = solution.getRepresentation();
		
		for(ISolution<T> expectedSolution:expectedSolutionList){
			T expectedSolutionRepresentation = expectedSolution.getRepresentation();
			if(expectedSolutionRepresentation.stringRepresentation().equals(solutionRepresentation.stringRepresentation()))
				return true;
		}

		return false;
	}

		
	@Override
	@Test
	public void test()
	{
		testSolution();
		specificOperatorTests();
	}

	
	public S getSolutionFactory() {
		return solutionFactory;
	}

	public void setSolutionFactory(S solutionFactory) {
		this.solutionFactory = solutionFactory;
	}

}
