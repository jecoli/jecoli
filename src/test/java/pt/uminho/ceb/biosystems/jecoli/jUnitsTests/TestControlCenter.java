package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.jUnitsTests.testsReader.TestsStatistics;



public class TestControlCenter<T extends IRepresentation,F extends ISolutionFactory<T>> {

	protected List<ITest<T,F>> operatorTestList;
	private TestsStatistics stats;
	
	public TestControlCenter()
	{
		operatorTestList = new ArrayList<ITest<T,F>>();
		stats = new TestsStatistics();
	}
	
	public void addTest(ITest<T,F> newTest)
	{
		operatorTestList.add(newTest);
	}
	
	public void addTest(ArrayList<ITest<T,F>> newTests)
	{
		for(ITest<T,F> test : newTests)
			this.operatorTestList.add(test);
	}
	
	@Test
	public void runTest(int index)
	{
		
			operatorTestList.get(index).test();
			this.stats.addSuccessedTest(operatorTestList.get(index));
			this.stats.outputStats();
	}
	
	@Test
	public void runTests()
	{
		for(ITest<T,F> test : operatorTestList)
		{
			test.test();
			this.stats.addSuccessedTest(test);
		}
		this.stats.outputStats();
	}
	
	@Test
	public void runTests(String operatorInditifier)
	{
		for(ITest<T,F> test : operatorTestList)
		{
			test.test();
			this.stats.addSuccessedTest(test);
		}
		this.stats.outputStats();
	}
	
	public void validateTest(int index) throws Exception
	{
		operatorTestList.get(index).validate();
	}
	
	public void validadeAllTests() throws Exception
	{
		for(ITest<T,F> test : operatorTestList)
			test.validate();
	}
}
