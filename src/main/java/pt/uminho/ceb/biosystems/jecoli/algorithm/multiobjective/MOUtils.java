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
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.DistanceNodeComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.ObjectivesComparator;



// TODO: Auto-generated Javadoc
/**
 * Utilities for Multi-Objective Optimization.
 * 
 * @author pmaia
 */
public class MOUtils {
	
	/**
	 * Dominates.
	 * 
	 * @param sol1 the sol1
	 * @param sol2 the sol2
	 * 
	 * @return true, if successful
	 */
	public static boolean dominates (double[] sol1, double[] sol2)
	{
		boolean res = true;
		boolean allEqual = true;
		for(int i=0; i < sol1.length && res; i++)
		{
			if (sol2[i] > sol1[i])	res = false;
			else if (sol1[i] > sol2[i]) allEqual = false;
		}
		if (allEqual) res = false;
		return res;
	}
	
	/**
	 * Dominates.
	 * 
	 * @param point1 the point1
	 * @param point2 the point2
	 * @param noObjectives the no objectives
	 * 
	 * @return true, if successful
	 */
	public static boolean dominates(double point1[], double point2[], int noObjectives) {
		int i;
		int betterInAnyObjective;

		betterInAnyObjective = 0;
		for (i = 0; i < noObjectives && point1[i] >= point2[i]; i++)
			if (point1[i] > point2[i])
				betterInAnyObjective = 1;

		return ((i >= noObjectives) && (betterInAnyObjective > 0));
	} 
	
	public static boolean dominates(double[] sol1, double[] sol2, boolean[] senses){
		int i;
		int betterInAnyObjective;
		int noObjectives = senses.length;

		betterInAnyObjective = 0;
		for (i = 0; i < noObjectives && betterOrEqual(sol1[i],sol2[i],senses[i]); i++)
			if (better(sol1[i],sol2[i],senses[i]))
				betterInAnyObjective = 1;

		return ((i >= noObjectives) && (betterInAnyObjective > 0));
	}
	
	public static boolean better(double p1, double p2, boolean maximization){
		return maximization ? (p1 > p2) : (p1 < p2); 
	}
	
	public static boolean betterOrEqual(double p1, double p2, boolean maximization){
		return maximization ? (p1 >= p2) : (p1 <= p2); 
	}
	
	public static double cMeasure(double[][] front1, double[][] front2){
		int nd =0;
		for(int i=0; i< front2.length; i++)
			if(anyDominates(front1,front2[i]))
				nd++;
		
		return ((double)nd / front2.length);
	}
	
	public static boolean anyDominates(double[][] front, double[] sol){
		boolean res = false;
		for(int i=0; i<front.length;i++)
			if(dominates(front[i], sol))
				res = true;
		
		return res;
	} 
	
	
	/**
	 * Computes the dominance characteristic between two solutions.
	 * 
	 * @param solution1 the first solution
	 * @param solution2 the second solution
	 * @param isMaximization - true if it is a maximization problem, false otherwise
	 * ;
	 * 
	 * @return -1 if the first solution dominates, 1 if the second solution dominates or 0 if no one dominates the other
	 */
	public static int individualDominance(ISolution<?> solution1, ISolution<?> solution2,boolean isMaximization){
		if (solution1 == null)
			return 1;
		else if (solution2 == null)
			return -1;

		int dominate1; 		// dominate1 indicates if some objective of solution1
							// dominates the same objective in solution2. dominate2
		int dominate2;		// is the complementary of dominate1.

		dominate1 = 0;
		dominate2 = 0;

		int flag,toret; // stores the result of the comparation

		// dominance Test
		double value1, value2;
		for (int i = 0; i < solution1.getNumberOfObjectives(); i++) {		
			
			value1 = solution1.getFitnessValue(i);
			value2 = solution2.getFitnessValue(i);			
			if (value1 < value2) {
				flag = -1;
			} else if (value1 > value2) {
				flag = 1;
			} else {
				flag = 0;
			}

			if (flag == -1) {
				dominate1 = 1;
			}

			if (flag == 1) {
				dominate2 = 1;
			}
		}

		if (dominate1 == dominate2) {
			toret= 0; // no one dominates the other
		}
		else if (dominate1 == 1) {
			toret = -1; // solution1 dominates
		}
		else {
			toret =1; // solution2 dominates
		}
		
		return (isMaximization ? toret*-1 : toret);
	}
	
	/**
	 * Computes a distance matrix regarding every solution of a given solution set.
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the <code>double[][]</code> containing the distances between solutions of the solution set
	 */
	public static double[][] distanceMatrix(List<? extends ISolution<?>> solutionSet) {
		ISolution<?> solutionI, solutionJ;

		double[][] distance = new double[solutionSet.size()][solutionSet.size()];
		
		//computes distances
		for (int i = 0; i < solutionSet.size(); i++) {
			distance[i][i] = 0.0;
			solutionI = solutionSet.get(i);
			for (int j = i + 1; j < solutionSet.size(); j++) {
				solutionJ = solutionSet.get(j);
				distance[i][j] = distanceBetweenObjectives(solutionI,solutionJ);
				distance[j][i] = distance[i][j];
			} 
		} 

		return distance;
	}
	
	/**
	 * Computes a distance vector regarding for a solution vs all the solutions in a set
	 * 
	 * @param solutionSet the solution set
	 * 
	 * @return the <code>double[]</code> containing the distances between the solutions and the ones in the solution set
	 */
	public static double[] distanceVector(List<? extends ISolution<?>> solutionSet, ISolution<?> sol) {
		ISolution<?> solutionI;

		double[] distance = new double[solutionSet.size()];

		//computes distances
		for (int i = 0; i < solutionSet.size(); i++) {			
			solutionI = solutionSet.get(i);
			distance[i] = distanceBetweenObjectives(sol, solutionI);
		} 

		return distance;
	}	
	
	
	/**
	 * Returns the distance between two solutions in fitness space.
	 * 
	 * @param solutionI the first <code>Solution</code>.
	 * @param solutionJ the second <code>Solution</code>.
	 * 
	 * @return the distance between solutions in fitness space.
	 */
	public static double distanceBetweenObjectives(ISolution<?> solutionI,ISolution<?> solutionJ) {
		double diff;
		double distance = 0.0;
		
		//Euclidean distance
		for (int nObj = 0; nObj < solutionI.getNumberOfObjectives(); nObj++) {
			diff = solutionI.getFitnessValue(nObj) - solutionJ.getFitnessValue(nObj);
			distance += Math.pow(diff, 2.0);
		}

		return Math.sqrt(distance);
	}
	
	/**
	 * Assigns a crowding distance to each solution of a list.
	 * Uses the scheme proposed by Deb et al, 2002 (NSGA-II).
	 * 
	 * @param solutionSet the list of <code>ICrowdedSolution</code>
	 * @param isMaximization the is maximization
	 */
	public static <T extends IRepresentation> void assignCrowdingDistance(List<ISolution<T>> solutionSet,boolean isMaximization){
		int size = solutionSet.size();
		
		if(size == 0)
			return;
		
		if(size == 1){
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY); 
			return;
		}
		
		if(size == 2){
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			solutionSet.get(1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}
	
		// auxiliary solutionSet
		List<ISolution<T>> front = new ArrayList<ISolution<T>>(solutionSet);
	
		
		//assign crowdings for front			
		for(int i = 0;i<size;i++)
			front.get(i).setCrowdingDistance(0.0);
			
		
		double minObj,maxObj,dist;
		
		int numberObjectives = solutionSet.get(0).getNumberOfObjectives();
		
		for(int i=0 ; i<numberObjectives ; i++){
			Collections.sort(front,new ObjectivesComparator<ISolution<T>>(i,isMaximization)); 
			minObj = front.get(0).getFitnessValue(i);
			maxObj = front.get(front.size()-1).getFitnessValue(i);
						
			front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			front.get(front.size()-1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			
			for(int j=1; j< size -1;j++){
				dist = front.get(j+1).getFitnessValue(i)-front.get(j-1).getFitnessValue(i);
				dist /= (maxObj - minObj);
				dist += front.get(j).getCrowdingDistance();
				front.get(j).setCrowdingDistance(dist);
			} 
		}	
	}
	
	
	
	/**
	 * Filter non dominated front assuming all maximization.
	 * 
	 * @param front the front
	 * @param noPoints the no points
	 * @param noObjectives the no objectives
	 * 
	 * @return the int
	 */
	public static int filterNonDominatedFront(double[][] front, int noPoints, int noObjectives){
		int i, j;
		int n;

		n = noPoints;
		i = 0;
		while (i < n) {
			j = i + 1;
			while (j < n) {
				if (dominates(front[i], front[j], noObjectives)) {
					n--;
					swap(front, j, n);
				} else if (dominates(front[j], front[i], noObjectives)) {		
					n--;
					swap(front, i, n);
					i--;
					break;
				} else
					j++;
			}
			i++;
		}
		return n;
	}
	
	/**
	 * Filter non dominated front assuming all maximization.
	 * 
	 * @param front the front
	 * @param noPoints the no points
	 * @param noObjectives the no objectives
	 * 
	 * @return the non-dominated indexes
	 */
	public static int[] filterNonDominatedFrontIndexes(double[][] front, int noPoints, int noObjectives){
		int i, j;
		int n;

		int[] intarray = new int[front.length];
		for (int of = 0; of < front.length; of++)
			intarray[of] = of;

		n = noPoints;
		i = 0;
		while (i < n) {
			j = i + 1;
			while (j < n) {
				if (dominates(front[i], front[j], noObjectives)) {
					n--;
					swap(front, j, n);
					swap(intarray, j,n);
				} else if (dominates(front[j], front[i], noObjectives)) {		
					n--;
					swap(front, i, n);
					swap(intarray, i,n);
					i--;
					break;
				} else
					j++;
			}
			i++;
		}
		
		int[] toret = new int[n];
		for(int k=0; k<n; k++)
			toret[k] = intarray[k];
		return toret;
	} 
	
	/**
	 * Filter non dominated front assuming all maximization.
	 * 
	 * @param front the front
	 * @param noPoints the no points
	 * 
	 * @return the non-dominated indexes
	 */
	public static int[] filterNonDominatedFrontIndexes(double[][] front, int noPoints, boolean[] senses){
		int i, j;
		int n;

		int[] intarray = new int[front.length];
		for (int of = 0; of < front.length; of++)
			intarray[of] = of;

		n = noPoints;
		i = 0;
		while (i < n) {
			j = i + 1;
			while (j < n) {
				if (dominates(front[i], front[j], senses)) {
					n--;
					swap(front, j, n);
					swap(intarray, j,n);
				} else if (dominates(front[j], front[i], senses)) {		
					n--;
					swap(front, i, n);
					swap(intarray, i,n);
					i--;
					break;
				} else
					j++;
			}
			i++;
		}
		
		int[] toret = new int[n];
		for(int k=0; k<n; k++)
			toret[k] = intarray[k];
		return toret;
	} 
	
	public static double[][] filterNonDominatedFront(double[][] front, int noObjectives){
		int offSet = filterNonDominatedFront(front, front.length, noObjectives);
		
		double[][] newFront = new double[offSet][front.length];
		
		for(int i=0;i<offSet; i++)
			newFront[i] = front[i];
		
		return newFront;
	}
	
	/**
	 * Swap.
	 * 
	 * @param front the front
	 * @param i the i
	 * @param j the j
	 */
	public static void swap(double[][] front, int i, int j) {
		double[] temp;

		temp = front[i];
		front[i] = front[j];
		front[j] = temp;
	}
	
	public static void swap(int[] list, int i, int j){
		int temp;
		temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
	
//	 * strength(i) = |{ j | j <- Population and i dominates j}|.
	/**
	 * Computes the Strengths of the individuals in a given population
	 * 
	 * @param solutionSet the population
	 * @param isMaximization the is maximization
	 * 
	 * @return the <code>double[]</code> of strengths
	 */
	public static double[] strength(ISolutionSet<?> solutionSet,boolean isMaximization){
		double[] strength = new double[solutionSet.getNumberOfSolutions()];
		
		for(int i = 0; i<solutionSet.getNumberOfSolutions();i++){
			
				for(int j = 0; j < solutionSet.getNumberOfSolutions(); j++){
					
					if(MOUtils.individualDominance(solutionSet.getSolution(i),solutionSet.getSolution(j),isMaximization) ==-1)
						strength[i] += 1.0; // S(i)
				}
		}
		return strength;
	}
	
	public static double singleStrength(ISolution<?> sol, ISolutionSet<?> solutionSet, boolean isMaximization){
		
		double strength = 0;
		for(int i = 0; i<solutionSet.getNumberOfSolutions();i++){
			if(MOUtils.individualDominance(sol,solutionSet.getSolution(i),isMaximization) ==-1)
				strength += 1.0; // S(i)
		}
		
		return strength;
	}
	
	/**
	 * Computes the raw fitness of the individuals in a given population.
	 * 
	 * @param solutionSet the population
	 * @param strength the <code>double[]</code> the array of strengths;
	 * @param isMaximization the is maximization
	 * 
	 * @return <code>double[]</code> the raw fitness for the individuals of the population
	 */
	public static double[] rawFitness(ISolutionSet<?> solutionSet,double[] strength,boolean isMaximization){
		double[] raw = new double[solutionSet.getNumberOfSolutions()];
		
		for(int i = 0; i< solutionSet.getNumberOfSolutions() ; i++){
			for(int j = 0 ; j < solutionSet.getNumberOfSolutions(); j++){
				if(MOUtils.individualDominance(solutionSet.getSolution(i), solutionSet.getSolution(j),isMaximization)==1)
					raw[i] += strength[j]; // R(i)
			}
		}		
		return raw;
	}
	
	public static double singleRawFitness(ISolution<?> sol, ISolutionSet<?> solutionSet,double[] strength,boolean isMaximization){
		
		double raw = 0;
		
		for(int i = 0; i< solutionSet.getNumberOfSolutions() ; i++){
			if(MOUtils.individualDominance(sol, solutionSet.getSolution(i),isMaximization)==1)
				raw += strength[i]; // R(i)
		}
		
		return raw;
	}
	
	/**
	 * Computes the density.
	 * 
	 * @param solutionSet the population
	 * @param k the k
	 * 
	 * @return <code>double[]</code> the densitys for the individuals of the population
	 */
	public static double[] density(ISolutionSet<?> solutionSet,int k){
		double[][] distances = MOUtils.distanceMatrix(solutionSet.getListOfSolutions());
		double[] density = new double[distances.length];
		
//		k = Math.sqrt(popul.getPopulSize()); NOTE: this should be done this way... since N and non-N are unified at this point

		for(int i=0;i<distances.length;i++){
			Arrays.sort(distances[i]);
			density[i] = 1.0 / (distances[i][k] +2.0); // D(i)
		}
		
		return density;
	}
	
	public static double singleDensity(ISolutionSet<?> solutionSet, ISolution<?> sol, int k){
		double[] distances = MOUtils.distanceVector(solutionSet.getListOfSolutions(),sol);
		Arrays.sort(distances);
		double density = 1.0 / (distances[k] +2.0); // D(i)
		
		return density;
	}

	/**
	 * Computes the final fitness of the individuals of the population.
	 * 
	 * @param solutionSet the population
	 * @param isMaximization the is maximization
	 */
	public static void assignSelectionValue(ISolutionSet<?> solutionSet,boolean isMaximization){
		
		
		double[] strength = MOUtils.strength(solutionSet,isMaximization);
		double[] raw = MOUtils.rawFitness(solutionSet, strength,isMaximization);
		double[] density = MOUtils.density(solutionSet, 1); //NOTE: using by default k=1 may need to change this

		
		for(int i=0;i< solutionSet.getNumberOfSolutions();i++){
//			System.out.println("Indiv <"+i+"> strength : "+strength[i]+"raw :"+(raw[i])+" dens: "+density[i]);
			solutionSet.getSolution(i).setSelectionValue(raw[i]+density[i]); // F(i) = R(i)+D(i) from zitzler et al, 2001
		}			
	}
	
	public static double computeZitzlerSelection4SingleSolution(ISolution<?> sol, ISolutionSet<?> solutionSet, boolean isMaximization){
		
		double[] strength = MOUtils.strength(solutionSet,isMaximization);
		double raw = MOUtils.singleRawFitness(sol,solutionSet,strength,isMaximization);
		double density = MOUtils.singleDensity(solutionSet,sol,1);
		
		double singleSelection = raw + density;
		
		return singleSelection;
	}
	
	
	/**
	 * Performs a truncation method to the nondominated front in order to fit the archive - described by zitzler et al, 2001.
	 * @param <T>
	 * 
	 * @param list - the nondominated front
	 * @param size - the target size of the archive
	 * 
	 * @return the truncated front
	 */
	public static <T extends IRepresentation> List<ISolution<T>> zitzlerTruncation(List<ISolution<T>> list,int size) {
		
		double[][] distances = MOUtils.distanceMatrix(list);
		List<List<DistanceNode>> distanceList = new LinkedList<List<DistanceNode>>();

		for(int pos=0 ; pos <list.size() ; pos++){
			list.get(pos).setLocation(pos);
			List<DistanceNode> distanceNodeList = new ArrayList<DistanceNode>();
			for(int index = 0; index < list.size(); index++){
				if(pos!=index)
					distanceNodeList.add(new DistanceNode(distances[pos][index],index));
			}
			distanceList.add(distanceNodeList);
		}
		
		for(int q = 0; q<distanceList.size();q++)
			Collections.sort(distanceList.get(q),new DistanceNodeComparator());
		
		/** while the nondominated front exceeds the archive size, iterate to find the point that yields the lowest distance to any other point and remove it*/
		while(list.size() > size){
			double minDistance = Double.MAX_VALUE;
			int toRemove = 0;
			int i=0;
			for(List<DistanceNode> dn: distanceList){
				if((dn.size()> 0) && (dn.get(0).getDistance()< minDistance)){
					toRemove = i;
					minDistance = dn.get(0).getDistance();
				} else if((dn.size()> 0) && (dn.get(0).getDistance() == minDistance)){
					int k=0;
					while(
						(dn.get(k).getDistance()==distanceList.get(toRemove).get(k).getDistance()) 
						&& 
						(k < (distanceList.get(i).size() -1))
						) k++;
					
					if(dn.get(k).getDistance() < distanceList.get(toRemove).get(k).getDistance())
						toRemove = i;
					
				}
				
			i++;	
			}
			
			int temp = list.get(toRemove).getLocation();
			list.remove(toRemove);
			distanceList.remove(toRemove);
			
			Iterator<List<DistanceNode>> externIterator = distanceList.iterator();
			while(externIterator.hasNext()){
				Iterator<DistanceNode> internIterator = externIterator.next().iterator();
				while(internIterator.hasNext()){
					if(internIterator.next().getIndex()==temp){
						internIterator.remove();
						continue;
					}
				}
			}
		}		
		
		return list;
	}
	
	  /**
	   * Summatory.
	   * 
	   * @param value the value
	   * 
	   * @return the int
	   */
	  public static int summatory(int value){
		  int summatoryValue = 0;
		  
		for(int i = 0;i <= value;i++)
			summatoryValue += i;
		  
		return summatoryValue;
	  }

}
