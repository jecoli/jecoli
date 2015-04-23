package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;


public abstract class AbstractSelectionOperatorTest<T extends IRepresentation,S extends ISolutionFactory<T>> extends AbstractOperatorTest<T,S>{

	protected ISelectionOperator<T> operator;
	
	protected Integer numberOfSolutionsToSelect;
	
	protected Boolean isMaximization;

	@Override
	public void testSolution()
	{
		List<ISolution<T>> actualResultsMAX = new ArrayList<ISolution<T>>();
		try {
			actualResultsMAX = operator.selectSolutions(numberOfSolutionsToSelect,new SolutionSet<T>(initialPopulation), true, this.randomNumberGenerator);
			
			testSolutions(actualResultsMAX, expectedSolutions);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getNumberOfSolutionsToSelect() {
		return numberOfSolutionsToSelect;
	}


	public void setNumberOfSolutionsToSelect(int numberOfSolutionsToSelect) {
		this.numberOfSolutionsToSelect = numberOfSolutionsToSelect;
	}


	public Boolean getIsMaximization() {
		return isMaximization;
	}


	public void setIsMaximization(boolean isMaximization) {
		this.isMaximization = isMaximization;
	}

	@Override
	public void validate() throws Exception {
		if(this.expectedSolutions == null) throw new Exception(this.testName+": Validation error due to a missing set in ExpectedSolutions atribute.");
		if(this.initialPopulation == null) throw new Exception(this.testName+": Validation error due to a missing set in SelectedSolutions atribute.");
		if(this.numberOfSolutionsToSelect == null) throw new Exception(this.testName+": Validation error due to a missing set in numberOfSolutionsToSelect atribute.");
		if(this.isMaximization == null) throw new Exception(this.testName+": Validation error due to a missing set in isMaximization atribute.");
	}
	
	@Override
	public void deepcopyTest() {
		// TODO Auto-generated method stub
		
	}
	
	
}
