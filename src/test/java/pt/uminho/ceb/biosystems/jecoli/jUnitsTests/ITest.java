package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.List;

import org.junit.Test;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;



public interface ITest<T extends IRepresentation,F extends ISolutionFactory<T>> { 
	
	@Test
	void test();
	void validate() throws Exception;
	void deepcopyTest() throws Exception;
	
	void setInitialPopulation(List<ISolution<T>> initialPopulation);
	void setExpectedSolutions(List<ISolution<T>> expectedSolution);
	void setTestValues();
	void setTestFileName(String testFileName);
	void setRandomNumberGenerator(IRandomNumberGenerator randomNumberGenerator);
	void setTestName(String testeName);
	
	IRandomNumberGenerator getRandomNumberGenerator();
	String getName();
	String getTestFileName();
	String getTestName();
}
