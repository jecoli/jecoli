package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;


public abstract class AbstractReproductionOperatorTest<T extends IRepresentation,S extends ISolutionFactory<T>> extends AbstractOperatorTest<T,S>{ 
	
	protected IReproductionOperator<T,S> operator;
	protected Integer size;
	
	@Test
	@Override
	public void testSolution(){
		List<ISolution<T>> actualResults = new ArrayList<ISolution<T>>();
		try {
			
			actualResults = operator.apply(initialPopulation,solutionFactory,randomNumberGenerator);		
			testSolutions(actualResults, expectedSolutions);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void validate() throws Exception {
		if(this.expectedSolutions == null) throw new Exception(this.testName+": Validation error due to a missing set in ExpectedSolutions atribute.");
		if(this.size == null) throw new Exception(this.testName+": Validation error due to a missing set in Size atribute.");
		if(this.initialPopulation == null) throw new Exception(this.testName+": Validation error due to a missing set in SelectedSolutions atribute.");
	}
	
	@Override
	public void deepcopyTest() throws Exception {
		// TODO Auto-generated method stub
		
	}


	public IReproductionOperator<T, S> getOperator() {
		return operator;
	}


	public void setOperator(IReproductionOperator<T, S> operator) {
		this.operator = operator;
	}

	public Integer getSize() {
		return size;
	}


	public void setSize(Integer size) {
		this.size = size;
	}
	
	
}
