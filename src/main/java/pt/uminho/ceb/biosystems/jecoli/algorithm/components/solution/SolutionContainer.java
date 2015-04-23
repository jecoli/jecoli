/**
* Copyright 2009,46
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
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.SolutionCellScalarFitnessComparator;


// TODO: Auto-generated Javadoc
/**
 * The Class SolutionContainer.
 */
public class SolutionContainer<T extends IRepresentation> implements ISolutionContainer<T>, Serializable {
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 5719941743147917952L;

	/** The solution container. */
    protected TreeSet<SolutionCellContainer<T>> solutionContainer;
    
    /** The data time step. */
    protected int dataTimeStep;
    
    /** The number of solutions to keep. */
    protected int numberOfSolutionsToKeep;

    /**
     * Instantiates a new solution container.
     */
    public SolutionContainer() {
        dataTimeStep = 1;
        numberOfSolutionsToKeep = 1;
        Comparator<SolutionCellContainer<T>> comparator = new SolutionCellScalarFitnessComparator<SolutionCellContainer<T>>();
        solutionContainer = new TreeSet<SolutionCellContainer<T>>(comparator);
    }
      
    /**
     * Instantiates a new solution container.
     * 
     * @param numberOfSolutionsToKeep the number of solutions to keep
     */
    public SolutionContainer(int numberOfSolutionsToKeep){
    	this.numberOfSolutionsToKeep = numberOfSolutionsToKeep;
    	Comparator<SolutionCellContainer<T>> comparator = new SolutionCellScalarFitnessComparator<SolutionCellContainer<T>>();
        solutionContainer = new TreeSet<SolutionCellContainer<T>>(comparator);
        dataTimeStep = 1;
    }
    
    /**
     * Instantiates a new solution container.
     * 
     * @param numberOfSolutionsToKeep the number of solutions to keep
     * @param comparator the comparator
     */
    public SolutionContainer(int numberOfSolutionsToKeep,Comparator<? super SolutionCellContainer<T>> comparator){
    	this.numberOfSolutionsToKeep = numberOfSolutionsToKeep;
    	solutionContainer = new TreeSet<SolutionCellContainer<T>>(comparator);
    	dataTimeStep = 1;
    }
     
    /* (non-Javadoc)
     * @see core.interfaces.ISolutionContainer#addSpecificSolutions(core.interfaces.ISolutionSet, int, boolean)
     */
    @Override
    public void addSpecificSolutions(ISolutionSet<T> solutionSet, int currentIteration,boolean isMaximization){
        int modCurrentIteration =  0;
        
        if(currentIteration != 0)
        	modCurrentIteration = currentIteration % dataTimeStep; 

        if (modCurrentIteration == 0)
			try {
				if(solutionSet.getNumberOfObjectives()==1)
					addElitistSolutions(solutionSet,currentIteration,isMaximization);
				else //TODO:Ver Esta parte relativa ao MultiObjectivo
					addAllSolutions(solutionSet,currentIteration,isMaximization);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
    
    }
    
    protected void addAllSolutions(ISolutionSet<T> solutionSet,int currentIteration,boolean isMaximization) throws Exception{
		int totalNumberOfSolutionsToKeep = numberOfSolutionsToKeep;
		
		if(totalNumberOfSolutionsToKeep > solutionSet.getMaxNumberOfSolutions())
			totalNumberOfSolutionsToKeep = solutionSet.getNumberOfSolutions();
			
		solutionContainer.clear();
		for(int i = 0; i < totalNumberOfSolutionsToKeep;i++){
			ISolution<T> solution = solutionSet.getSolution(i);						
			solutionContainer.add(new SolutionCellContainer<T>(currentIteration,solution));
		}
		
	}
    

	/**
	 * Adds the elitist solutions.
	 * 
	 * @param solutionSet the solution set
	 * @param currentIteration the current iteration
	 * @param isMaximization the is maximization
	 * 
	 * @throws Exception the exception
	 */
	protected void addElitistSolutions(ISolutionSet<T> solutionSet,int currentIteration,boolean isMaximization) throws Exception{
		solutionSet.sort();
		int currentPosition = -1;
		
		if(isMaximization)
			currentPosition = solutionSet.getNumberOfSolutions();
		
		int numberOfSolutionsToVisit = numberOfSolutionsToKeep;
		
		if(numberOfSolutionsToKeep > solutionSet.getNumberOfSolutions())
			numberOfSolutionsToVisit = solutionSet.getNumberOfSolutions();
		
		for(int i=0;i<numberOfSolutionsToVisit;i++){
			if(isMaximization)
				currentPosition--;
			else
				currentPosition++;
			
			ISolution<T> solution = solutionSet.getSolution(currentPosition);
			
			if(solutionContainer.size() < numberOfSolutionsToKeep)
				solutionContainer.add(new SolutionCellContainer<T>(currentIteration,solution));
			else{
				SolutionCellContainer<T> solutionCellContainer = new SolutionCellContainer<T>(currentIteration,solution);
				SolutionCellContainer<T> worstSolution = getWorstSolution(solutionCellContainer,isMaximization);
				
				if((worstSolution != null) && verifySolutionInsertion(solutionCellContainer,worstSolution,isMaximization)){
					solutionContainer.remove(worstSolution);
					solutionContainer.add(new SolutionCellContainer<T>(currentIteration,solution));
				}else
					break;		
			}
		} 
		
	}
	
	protected boolean verifySolutionInsertion(SolutionCellContainer<T> solutionCellContainer,SolutionCellContainer<T> worstSolution, boolean isMaximization) {
		Comparator<? super SolutionCellContainer<T>> comparator = solutionContainer.comparator();
		
		if(((comparator.compare(solutionCellContainer,worstSolution) > 0)) && isMaximization)
			return true;

        return ((comparator.compare(solutionCellContainer, worstSolution) < 0)) && !isMaximization;

    }

	protected SolutionCellContainer<T> getWorstSolution(SolutionCellContainer<T> solutionCellContainer,boolean isMaximization){
		if(isMaximization)
			return solutionContainer.lower(solutionCellContainer);
		
		return solutionContainer.higher(solutionCellContainer);
	}

	/**
	 * Gets the elitist solution.
	 * 
	 * @param currentElitistSolutionIndex the current elitist solution index
	 * @param solutionSet the solution set
	 * @param isMaximization the is maximization
	 * 
	 * @return the elitist solution
	 */
	protected ISolution<T> getElitistSolution(int currentElitistSolutionIndex,ISolutionSet<T> solutionSet,boolean isMaximization){
		
		if(isMaximization)
			return solutionSet.getHighestValuedSolutionsAt(currentElitistSolutionIndex);
			
		return solutionSet.getLowestValuedSolutionsAt(currentElitistSolutionIndex);
	}

	
	/* (non-Javadoc)
	 * @see core.interfaces.ISolutionContainer#getNumberOfSolutions()
	 */
	public int getNumberOfSolutions(){
		return solutionContainer.size();
	}
	
	public SolutionCellContainer<T> getBestSolutionCellContainer(boolean isMaximization){
		if(isMaximization)
			return solutionContainer.last();
		else 
			return solutionContainer.first();
	}

	@Override
	public int getNumberOfSolutionsToKeep() {
		return numberOfSolutionsToKeep;
	}

	@Override
	public Iterator<SolutionCellContainer<T>> iterator() {
		return solutionContainer.iterator();
	}


	
	
	
    
    
    

}