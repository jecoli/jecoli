/**
* Copyright 2009,
* CCTC - Computer Science and Technology Center
* IBB-CEB - Institute for Biotechnology and  Bioengineering - Centre of Biological Engineering
* University of Minho
*
* This is free software: you can redistribute it and/or modify
* it under the terms of the GNU Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Public License for more details.
*
* You should have received a copy of the GNU Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
* 
* Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
* University of Minho
*/
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionProcedureException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;


/**
 * Binary tournament 2 - as defined by Deb et al, 2002.
 * Uses individual dominance and crowding as descriminatory criterion
 */
public class TournamentSelection2<T extends IRepresentation> implements ISelectionOperator<T> {
	
	private static final long serialVersionUID = -6431349493141314736L;

	/** The k. */
	protected int k;
	
	/** The number of solutions per tournment. */
	protected int numberOfSolutionsPerTournment;
	
	protected IRandomNumberGenerator randomNumberGenerator;

	/**
	 * Instantiates a new tournament selection2.
	 * 
	 * @param k the k
	 * @param numberOfSolutionsPerTournment the number of solutions per tournment
	 * 
	 * @throws InvalidSelectionParameterException the invalid selection parameter exception
	 */
	public TournamentSelection2(int k,int numberOfSolutionsPerTournment,IRandomNumberGenerator randomNumberGenerator) throws InvalidSelectionParameterException {
		if(k < 1)
			throw new InvalidSelectionParameterException("k < 0");

		if(numberOfSolutionsPerTournment < 1)
			throw new InvalidSelectionParameterException("numberOfSolutionsPerTournment < 1");

		if(k > numberOfSolutionsPerTournment)
			throw new InvalidSelectionParameterException("k > numberOfSolutionsPerTournment");

		
		this.k = k;
		this.numberOfSolutionsPerTournment = numberOfSolutionsPerTournment;
		this.randomNumberGenerator = randomNumberGenerator;
	}


	/* (non-Javadoc)
	 * @see operators.ISelectionOperator#selectSolutions(int, core.interfaces.ISolutionSet, boolean)
	 */
	@Override
	public List<ISolution<T>> selectSolutions(int numberOfSolutionsToSelect,ISolutionSet<T> solutionSet, boolean isMaximization, IRandomNumberGenerator randomNumberGenerator) throws InvalidSelectionProcedureException, InvalidSelectionParameterException {
		if(numberOfSolutionsToSelect < 0)
			throw new InvalidSelectionParameterException(" numberOfSolutionToSelect < 0");

		if(k > numberOfSolutionsToSelect)
			throw new InvalidSelectionParameterException("k > numberOfSolutionToSelect");

		int modNumberSolutions = numberOfSolutionsToSelect % k;

		if(modNumberSolutions != 0)
			throw new InvalidSelectionParameterException(" numberOfSolutionsToSelect mod k != 0");

		List<ISolution<T>> solutionList = new ArrayList<ISolution<T>>(numberOfSolutionsToSelect);

		tournment(solutionList,solutionSet,isMaximization);

		return solutionList;
	}


	/**
	 * Tournment.
	 * 
	 * @param solutionList the solution list
	 * @param solutionSet the solution set
	 * @param isMaximization the is maximization
	 */
	protected void tournment(List<ISolution<T>> solutionList, ISolutionSet<T> solutionSet,boolean isMaximization) {
		int n = solutionSet.getNumberOfSolutions();
		int better,temp;
		int [] popul= new int[n];
		int [] res = new int[numberOfSolutionsPerTournment];

		for(int i=0; i<n; i++)  popul[i]=i;

		for(int i=0;i<numberOfSolutionsPerTournment;i++) {
			better = popul[(int) (randomNumberGenerator.nextFloat()*(n-1))];
			ISolution<T> indiv1 = solutionSet.getSolution(better);
			for(int j=0;j<k;j++){
				temp = popul[(int) (randomNumberGenerator.nextFloat()*(n-1))];
				ISolution<T> indiv2 = solutionSet.getSolution(temp);
				int flag = MOUtils.individualDominance(indiv1, indiv2,isMaximization);
				if(flag==1){
					better=temp; // temp indiv is better than the current better, so replace it
				} else if(flag==0){
					if(indiv2.getCrowdingDistance() > indiv1.getCrowdingDistance())
						better=temp; // temp indiv is better than the current better, so replace it
				}

			}
			res[i] = better;
			popul[better] = popul[n-1];
			n--;
		}
		
		for(int i=0;i<res.length;i++)
			solutionList.add(solutionSet.getSolution(res[i]));		
	}


	@Override
	public TournamentSelection2<T> deepCopy() throws Exception {
		return new TournamentSelection2<T>(k, numberOfSolutionsPerTournment,randomNumberGenerator.deepCopy());
	}


}
