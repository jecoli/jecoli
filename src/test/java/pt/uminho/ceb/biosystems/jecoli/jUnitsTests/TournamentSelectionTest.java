package pt.uminho.ceb.biosystems.jecoli.jUnitsTests;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;


	
public class TournamentSelectionTest extends AbstractSelectionOperatorTest<ILinearRepresentation<Boolean>,ISolutionFactory<ILinearRepresentation<Boolean>>>{

	protected Integer numberOfSolutionsPerTournment;
	protected Integer k;
	
	public TournamentSelectionTest()
	{
		this.testName = "TournamentSeletion";
		
		this.expectedSolutions = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		this.initialPopulation = new ArrayList<ISolution<ILinearRepresentation<Boolean>>>();
		
	}
	
	public Integer getNumberOfSolutionsPerTournment() {
		return numberOfSolutionsPerTournment;
	}
	


	public void setNumberOfSolutionsPerTournment(
			int numberOfSolutionsPerTournment) {
		this.numberOfSolutionsPerTournment = numberOfSolutionsPerTournment;
	}
	
	public Integer getK() {
		return k;
	}


	public void setK(int k) {
		this.k = k;
	}
	
	@Override
	protected void specificOperatorTests() {
		
		
	}

	@Override
	public void setTestValues() {
		try {
			this.operator = new TournamentSelection<ILinearRepresentation<Boolean>>(k, numberOfSolutionsPerTournment);
		} catch (InvalidSelectionParameterException e) {e.printStackTrace();}
	}

}
