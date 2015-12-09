package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.DistanceNodeComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.DistanceNode;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.MOUtils;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.AMFunctionType;

public class ZitzlerTruncation<T extends IRepresentation> implements ITrimmingFunction<T>{
	
	private int size;
	private IEvaluationFunction<T> evaluationFunction;

	public ZitzlerTruncation(int size, IEvaluationFunction<T> evaluationFunction){
		this.size = size;
		this.evaluationFunction = evaluationFunction;
	}

	@Override
	public ISolutionSet<T> trimm(ISolutionSet<T> original) {

		if(original.getNumberOfSolutions()> size){

			//NOTE: REVISIT THIS IN THE FUTURE!!!
			//		evaluationFunction.evaluate(original);		
			MOUtils.assignSelectionValue(original, true);

			SolutionSet<T> nondominated = new SolutionSet<T>();
			nondominated.setMaxNumberOfSolutions(size);

			/** copy nondominated solutions to auxiliary population */		
			int z=0;
			while(z < original.getNumberOfSolutions()){
				if(original.getSolution(z).getSelectionValue() < 1.0){
					nondominated.add(original.getSolution(z));
					original.remove(z);
				}else{
					z++;
				}
			}		

			List<ISolution<T>> list = nondominated.getListOfSolutions();

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

			ISolutionSet<T> toret = new SolutionSet<T>(list);

			return toret;
		}
		else return original;
	}
	
	@Override
	public AMFunctionType getFunctionType() {
		return AMFunctionType.TRIMMER;
	}

}
