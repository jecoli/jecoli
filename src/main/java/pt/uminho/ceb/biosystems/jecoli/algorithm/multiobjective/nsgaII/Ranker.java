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

package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.nsgaII;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;


// TODO: Auto-generated Javadoc
/**
 * A population ranking class. Scheme proposed by Deb et al, 2002 (NSGA-II).
 * This is the non-dominated sorting part of the NSGA-II.
 * (Credits to the JMetal team)
 * 
 * @author pmaia
 */
public class Ranker<T extends IRepresentation> {
	
/** the population to rank. */
	private ISolutionSet<T> population;

/** true if this is a maximization problem, false otherwise. */
	private boolean isMaximization;

/** an array containing all the fronts. */	
	private ISolutionSet<T>[] rankings;
	
	
/**
 * default constructor.
 * 
 * @param pop the pop
 * @param isMaximization the is maximization
 */	
	public Ranker(ISolutionSet<T> pop,boolean isMaximization){
		this.population = pop;		
		this.isMaximization= isMaximization;
		this.execute();
	}

	/**
	 * Gets the population.
	 * 
	 * @return the population
	 */
	public ISolutionSet<T> getPopulation() {
		return population;
	}

	/**
	 * Sets the population.
	 * 
	 * @param population the population to set
	 */
	public void setPopulation(ISolutionSet<T> population) {
		this.population = population;
	}
	
	
	/**
	 * Non-dominated sorting.
	 */
	private void execute(){

		int[] dominateMe = new int[this.population.getNumberOfSolutions()];
		 
		
		List<Integer>[] iDominate= new List[this.population.getNumberOfSolutions()];		
		List<Integer>[] front = new List[this.population.getNumberOfSolutions()+1];
		
		for(int i=0;i<front.length;i++)
			front[i] = new LinkedList<Integer>();
		
		int dominance;
		
		for(int x = 0;x<this.population.getNumberOfSolutions();x++){
					
			iDominate[x] = new ArrayList<Integer>();
			dominateMe[x] = 0;
			
			for(int y=0;y<this.population.getNumberOfSolutions();y++){
			
				dominance = MOUtils.individualDominance(this.population.getSolution(x),this.population.getSolution(y), this.isMaximization);
			
				
				if(dominance==-1){				
						iDominate[x].add(new Integer(y));
				} else if(dominance==1){
						dominateMe[x]++;			
					}
				
			}
			
			if(dominateMe[x]==0){
				
				front[0].add(new Integer(x));
				this.population.getSolution(x).setRank(0); // assign x to the first front
			}
		}
			
		//the rest of the fronts
			int i = 0;
			Iterator<Integer> it1,it2;
			while(front[i].size()!=0){
								
				i++;
				it1 = front[i-1].iterator();
				while(it1.hasNext()){
					it2 = iDominate[it1.next().intValue()].iterator();
					while(it2.hasNext()){
				
						int index = it2.next().intValue();
						dominateMe[index]--;
						if(dominateMe[index]==0){
							front[i].add(new Integer(index));
							this.population.getSolution(index).setRank(i);
						}
					}
				}
			}
			
			rankings = new ISolutionSet[i];
			for(int j = 0; j<i;j++){
								 
				rankings[j] = new SolutionSet<T>();
								
				it1 = front[j].iterator();
				while(it1.hasNext()){				
					//copy individuals to subfront
					ISolution<T> ind = this.population.getSolution(it1.next().intValue());
					rankings[j].add(ind);
				}
				
			}

	}
	
	/**
	 * Gets the sub front.
	 * 
	 * @param rank the rank
	 * 
	 * @return the sub front
	 */
	public ISolutionSet<T> getSubFront(int rank){
		return this.rankings[rank];
	}
	
	/**
	 * Number of fronts.
	 * 
	 * @return the int
	 */
	public int numberOfFronts(){
		return this.rankings.length;
	}

	/**
	 * Checks if is maximization.
	 * 
	 * @return the maximization
	 */
	public boolean isMaximization() {
		return isMaximization;
	}

	/**
	 * Sets the maximization.
	 * 
	 * @param maximization the maximization to set
	 */
	public void setMaximization(boolean maximization) {
		this.isMaximization = maximization;
	}

	/**
	 * Gets the rankings.
	 * 
	 * @return the rankings
	 */
	public ISolutionSet<T>[] getRankings() {
		return rankings;
	}

	/**
	 * Sets the rankings.
	 * 
	 * @param rankings the rankings to set
	 */
	public void setRankings(ISolutionSet<T>[] rankings) {
		this.rankings = rankings;
	}
}
