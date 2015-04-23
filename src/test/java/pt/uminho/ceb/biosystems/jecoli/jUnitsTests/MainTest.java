package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import org.junit.Test;

import pt.uminho.ceb.biosystems.jecoli.jUnitsTests.testsReader.TestReader;

public class MainTest {

	
	@Test
	public void maintest()
	{

		TestReader tr = new TestReader("./src/jecoli/jUnitsTests/testsFiles/");
		tr.startTests();
		
	}
}
