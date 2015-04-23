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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionParameterException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.InvalidSelectionProcedureException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;


/**
 * The Class TournamentSelection.
 */
//TODO alterar Jecoli para todos os operadores de seleccao terem que aceitar com e sem reps na selecçao
public class TournamentSelection<T extends IRepresentation> implements ISelectionOperator<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3910808335308388027L;

	/** The k. */
	protected int k;
	
	/** The number of solutions per tournment. */
	protected int numberOfSolutionsPerTournment;
    protected boolean selectWithRepetion;

	/**
	 * Instantiates a new tournament selection.
	 * 
	 * @param k the k
	 * @param numberOfSolutionsPerTournment the number of solutions per tournment
	 * 
	 * @throws InvalidSelectionParameterException the invalid selection parameter exception
	 */
	public TournamentSelection(int k,int numberOfSolutionsPerTournment) throws InvalidSelectionParameterException {
		if(k < 1)
			throw new InvalidSelectionParameterException("k < 0");

		if(numberOfSolutionsPerTournment < 1)
			throw new InvalidSelectionParameterException("numberOfSolutionsPerTournment < 1");

		if(k > numberOfSolutionsPerTournment)
			throw new InvalidSelectionParameterException("k > numberOfSolutionsPerTournment");

		
		this.k = k;
		this.numberOfSolutionsPerTournment = numberOfSolutionsPerTournment;
        this.selectWithRepetion = true;
	}

    public TournamentSelection(int k,int numberOfSolutionsPerTournment,boolean selectWithRepetion) throws InvalidSelectionParameterException {
        if(k < 1)
            throw new InvalidSelectionParameterException("k < 0");

        if(numberOfSolutionsPerTournment < 1)
            throw new InvalidSelectionParameterException("numberOfSolutionsPerTournment < 1");

        if(k > numberOfSolutionsPerTournment)
            throw new InvalidSelectionParameterException("k > numberOfSolutionsPerTournment");


        this.k = k;
        this.numberOfSolutionsPerTournment = numberOfSolutionsPerTournment;
        this.selectWithRepetion = selectWithRepetion;
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

		int currentNumberOfSolutions = 0;
        List<ISolution<T>> currentSolutionSet = computeCurrentSolutionSet(solutionSet);

		while(currentNumberOfSolutions < numberOfSolutionsToSelect){
			tournment(solutionSet.getComparator(),solutionList, currentSolutionSet, isMaximization, randomNumberGenerator);
			currentNumberOfSolutions += k;
		}


		return solutionList;
	}

    protected List<ISolution<T>> computeCurrentSolutionSet(ISolutionSet<T> solutionSet) {
        List<ISolution<T>> solutionList = solutionSet.getListOfSolutions();
        List<ISolution<T>> newSolutionList = new ArrayList<>();
        for(ISolution<T> solution:solutionList)
            newSolutionList.add(solution);
        return newSolutionList;
    }


    /**
	 * Tournment.
	 *  @param solutionList the solution list
	 * @param solutionSet the solution set
     * @param isMaximization the is maximization
     * @param randomNumberGenerator
     */
	protected void tournment(Comparator<? super ISolution<T>> comparator,List<ISolution<T>> solutionList, List<ISolution<T>> solutionSet,boolean isMaximization, IRandomNumberGenerator randomNumberGenerator) {
		List<ISolution<T>> randomPooledSolutionList = selectRandomSolutionList(solutionSet,randomNumberGenerator);
		orderSolutionList(comparator,solutionSet,randomPooledSolutionList);
		
		ISolution<T> solution;
		int solutionIndex;
		
		for(int i = 0; i < k;i++){
			if(isMaximization)
				solutionIndex = randomPooledSolutionList.size()-1;
			else
				solutionIndex = 0;

            if(!selectWithRepetion)
                solutionSet.removeAll(randomPooledSolutionList);

            solution = randomPooledSolutionList.remove(solutionIndex);
            solutionList.add(solution.deepCopy());
		}
	}

	protected void orderSolutionList(Comparator<? super ISolution<T>> comparator,List<ISolution<T>> solutionSet, List<ISolution<T>> randomPooledSolutionList) {
		Collections.sort(randomPooledSolutionList, comparator);
	}


	/**
	 * Select random ordered set.
	 * 
	 * @param solutionSet the solution set
	 * @param randomNumberGenerator
	 *
	 * @return the tree set< i solution>
	 */
	protected List<ISolution<T>> selectRandomSolutionList(List<ISolution<T>> solutionSet, IRandomNumberGenerator randomNumberGenerator) {
		List<ISolution<T>>  solutionList = new ArrayList<ISolution<T>>();

	
		while(solutionList.size() < numberOfSolutionsPerTournment){
            int numberOfSolutions = solutionSet.size();
			int randomIndex = randomNumberGenerator.nextInt(numberOfSolutions);
			ISolution<T> solution = solutionSet.remove(randomIndex);
			solutionList.add(solution);
		}
        for(ISolution<T> solution:solutionList)
            solutionSet.add(solution);
		
		return solutionList;
	}


	@Override
	public TournamentSelection<T> deepCopy() {
		try {
			return new TournamentSelection<T>(k, numberOfSolutionsPerTournment);
		} catch (InvalidSelectionParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

}