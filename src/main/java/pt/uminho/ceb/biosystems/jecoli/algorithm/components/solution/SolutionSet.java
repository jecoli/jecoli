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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.SolutionPureFitnessComparator;



// TODO: Auto-generated Javadoc
/**
 * The Class SolutionSet.
 */
public class SolutionSet<T extends IRepresentation> implements ISolutionSet<T>, Serializable{
	
	private static final long serialVersionUID = 1L;

	/** The solution list. */
	protected List<ISolution<T>> solutionList;
	
	protected boolean isOrdered;
	
	protected double overallFitnessValue;
	
	/** The max number of solutions. */
	protected int maxNumberOfSolutions;
	
	/** The comparator. */
	protected  Comparator<? super ISolution<T>> comparator;

	/**
	 * Instantiates a new solution set.
	 */
	public SolutionSet() {
		solutionList = new ArrayList<ISolution<T>>();
		this.comparator = new SolutionPureFitnessComparator<ISolution<T>>(false);
		overallFitnessValue = 0;
	}

	/**
	 * Instantiates a new solution set.
	 * 
	 * @param comparator the comparator
	 */
	public SolutionSet(Comparator<? super ISolution<T>> comparator) {
		solutionList = new ArrayList<ISolution<T>>();
		this.comparator = comparator;
		overallFitnessValue = 0;
	}

	/**
	 * Instantiates a new solution set.
	 * 
	 * @param numberOfSolutions the number of solutions
	 */
	public SolutionSet(int numberOfSolutions) {
		solutionList = new ArrayList<ISolution<T>>(numberOfSolutions);
		this.comparator = new SolutionPureFitnessComparator<ISolution<T>>(false);
		overallFitnessValue = 0;
	}

	//NOTE: this method does not perform a deep copy of the object
	/**
	 * Instantiates a new solution set.
	 * 
	 * @param toCopy the to copy
	 */
	public SolutionSet(SolutionSet<T> toCopy) {
		solutionList = new ArrayList<ISolution<T>>();        
		add(toCopy.solutionList);//TODO ver isto
		maxNumberOfSolutions = toCopy.maxNumberOfSolutions;
		comparator = toCopy.getComparator();
		overallFitnessValue = 0;
	}

	/**
	 * Instantiates a new solution set.
	 * 
	 * @param solutionList the solution list
	 */
	public SolutionSet(List<ISolution<T>> solutionList){
		this.solutionList = solutionList;
		this.comparator = new SolutionPureFitnessComparator<ISolution<T>>(false);
		overallFitnessValue = 0;
	}

	public SolutionSet(List<ISolution<T>> solutionListCopy,Comparator<? super ISolution<T>> comparator,int maxNumberOfSolutions){
		solutionList = solutionListCopy;
		this.comparator = comparator;
		this.maxNumberOfSolutions = maxNumberOfSolutions;
		overallFitnessValue = 0;
	}


	/**
	 * Sets the comparator.
	 * 
	 * @param comparator the new comparator
	 */
	public void setComparator(Comparator<? super ISolution<T>> comparator) {
		this.comparator = comparator;
	}

	@Override
	public void add(ISolution<T> solution) {
		if(solution!=null){
			solutionList.add(solution);
			overallFitnessValue += solution.getScalarFitnessValue();
			isOrdered = false;
		}
	}
	
	@Override
	public void add(int index, ISolution<T> solution) {
		if(solution!=null){
			solutionList.add(index,solution);
			overallFitnessValue += solution.getScalarFitnessValue();
			isOrdered = false;
		}		
	}


	@Override
	public void add(List<ISolution<T>> newSolutions) {
		for (ISolution<T> solution : newSolutions){
			if(solution!=null){
				solutionList.add(solution);
				overallFitnessValue += solution.getScalarFitnessValue();
			}
		}
		isOrdered = false;
	}

	@Override
	public void remove(ISolution<T> solution){
		solutionList.remove(solution);
		overallFitnessValue -= solution.getScalarFitnessValue();
	}

	@Override
	public void remove(List<ISolution<T>> solutions){
		for(ISolution<T> s: solutions){
			remove(s);
			overallFitnessValue -= s.getScalarFitnessValue();
		}
	}        

	@Override
	public void remove(int solutionIndex){
		solutionList.remove(solutionIndex);
		isOrdered = false;
	}

	@Override
	public void removeAll() {
		solutionList.clear();
		isOrdered = false;
	}

	@Override
	public int getNumberOfSolutions() {
		return solutionList.size();
	}

	@Override
	public ISolution<T> getSolution(int i) {
		return solutionList.get(i);
	}

	@Override
	public List<ISolution<T>> getListOfSolutions(){
		return solutionList;
	}

	@Override
	public List<ISolution<T>> getHighestValuedSolutions(int numberOfSolutions) {
		int totalNumberOfSolutions = solutionList.size();
		
		if(numberOfSolutions > totalNumberOfSolutions)
			numberOfSolutions = totalNumberOfSolutions;
		
		List<ISolution<T>> resultList = new ArrayList<ISolution<T>>();
		sort();
		int currentPosition = solutionList.size();
		for (int i = 0; i < numberOfSolutions;i++){
			currentPosition--;
			ISolution<T> solution = solutionList.get(currentPosition);
			resultList.add(solution);
		}
		
		return resultList;
	}


	@Override
	public int getMaxNumberOfSolutions() {
		return maxNumberOfSolutions;
	}

	@Override
	public Comparator<? super ISolution<T>> getComparator() {
		return comparator;
	}

	public void setMaxNumberOfSolutions(int maxNumberOfSolutions) {
		this.maxNumberOfSolutions = maxNumberOfSolutions;
	}

	@Override
	public List<ISolution<T>> getLowestValuedSolutions(int numberOfSolutions) {
		List<ISolution<T>> resultList = new ArrayList<ISolution<T>>();
		sort();
		for (int i = 0; i < numberOfSolutions; i++) {
			ISolution<T> solution = solutionList.get(i);
			resultList.add(solution);
		}
		return resultList;
	}

	/**
	 * Merges this instance of <code>SolutionSet</code> with another and returns the result.
	 * 
	 * @param popul2merge the <code>SolutionSet</code> to merge
	 * 
	 * @return the resulting merged solution
	 */
	 public ISolutionSet<T> union(ISolutionSet<T> popul2merge) {

		 int newSize = this.getNumberOfSolutions() + popul2merge.getNumberOfSolutions();

		 if (newSize > getMaxNumberOfSolutions())
			 newSize = getMaxNumberOfSolutions();

		 SolutionSet<T> mergedSet = new SolutionSet<T>(this);
		 for(int i=0;i<popul2merge.getNumberOfSolutions();i++)
			 mergedSet.add(popul2merge.getSolution(i));

		 return mergedSet;
	 }

 	@Override
	 public ISolution<T> getHighestValuedSolutionsAt(int solutionPosition) {
		 sort();
		 int maximumIndex = solutionList.size()-1;
		 int newIndex = maximumIndex-solutionPosition;
		 return solutionList.get(newIndex);
	 }

 	@Override
	 public ISolution<T> getLowestValuedSolutionsAt(int solutionPosition) {
		 sort();
		 return solutionList.get(solutionPosition);
	 }

	 /**
 	 * Verify ordered solution set status.
 	 */
 	public void sort(){
		 if(isOrdered)
			 return;		 
		 Collections.sort(solutionList, comparator);
	 }


 	@Override
	 public double calculateOverallFitness() {
		 return overallFitnessValue;//TODO retirar o overallFitnessValue Error Prone muito
	 }


 	@Override
	 public void setSolution(Integer index,ISolution<T> element){
		 solutionList.set(index,element);
		 isOrdered = false;
		 overallFitnessValue -= solutionList.get(index).getScalarFitnessValue();
		 overallFitnessValue += element.getScalarFitnessValue();
	 }


 	@Override
	 public int getNumberOfObjectives() {
// 		if(!solutionList.isEmpty()){
 			ISolution<T> solution = solutionList.get(0);
 			return solution.getNumberOfObjectives();
// 		}
// 		return 0;
	 }

	@Override
	public void sort(Comparator<? super ISolution<T>> newComparator, boolean replaceComparator, boolean higherToLower) {
		if(replaceComparator)
			comparator = newComparator;
		
		if(higherToLower)
			comparator = Collections.reverseOrder(comparator);
		
		Collections.sort(solutionList,comparator);						
	}	

	@Override
	public boolean contains(ISolution<T> solution) {
		for(ISolution<T> sol: getListOfSolutions())
			if(sol.equals(solution))
				return true;
		
		return false;
	}

	@Override
	public boolean containsGenomeOnly(ISolution<T> solution) {
		for(ISolution<T> sol: getListOfSolutions())
			if(sol.equalsRepresentation(solution))
				return true;
		
		return false;
	}

	@Override
	public boolean containsGenomeAndFitnesses(ISolution<T> solution) {
		for(ISolution<T> sol: getListOfSolutions())
			if(sol.equalsRepresentationAndFitness(solution))
				return true;
		
		return false;
	}



}
