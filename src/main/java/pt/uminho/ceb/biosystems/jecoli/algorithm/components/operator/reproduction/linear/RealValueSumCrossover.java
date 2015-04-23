package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.LinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;


public class RealValueSumCrossover extends AbstractCrossoverOperator<ILinearRepresentation<Double>,RealValueRepresentationFactory> {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2404875179952037791L;

	protected List<ISolution<ILinearRepresentation<Double>>> crossOverGenomes(ILinearRepresentation<Double> parentGenome,
			ILinearRepresentation<Double> parent1Genome,RealValueRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		
		int parentGenomeSize = parentGenome.getNumberOfElements();
		int parent1GenomeSize = parent1Genome.getNumberOfElements();
		
		int minSize = Math.min(parentGenomeSize,parent1GenomeSize);
		 List<Double> childGenome = new ArrayList<Double>();
		 List<Double> childGenome1 = new ArrayList<Double>();
		 
		for(int i=0; i< minSize; i++){
			Double parentValue = parentGenome.getElementAt(i);
			Double parent1Value = parent1Genome.getElementAt(i);
		
			double sumValue = parentValue + parent1Value;
			double difValue = parentValue - parent1Value;
			
			double ul = solutionFactory.getGeneUpperBound(i);
			double ll = solutionFactory.getGeneLowerBound(i);
			
			if (sumValue < ll) sumValue = ll;
			if (sumValue > ul) sumValue = ul;
			
			if (difValue < ll) difValue = ll;
			if (difValue > ul) difValue = ul;
			
			childGenome.add(sumValue);
			childGenome1.add(difValue);
		}

		if (parentGenomeSize > minSize)
			for(int i = minSize; i<parentGenomeSize; i++)
				childGenome.add(parentGenome.getElementAt(i));
		
		if (parent1GenomeSize > minSize)
			for(int i = minSize; i<parent1GenomeSize; i++)
				childGenome1.add(parent1Genome.getElementAt(i));
		
		int numObjs = solutionFactory.getNumberOfObjectives();
		
		ISolution<ILinearRepresentation<Double>> childSolution = 
				new Solution<ILinearRepresentation<Double>>(new LinearRepresentation<Double>(childGenome), numObjs);
		ISolution<ILinearRepresentation<Double>> child1Solution = 
				new Solution<ILinearRepresentation<Double>>(new LinearRepresentation<Double>(childGenome1), numObjs);
		List<ISolution<ILinearRepresentation<Double>>> solutionList = new ArrayList<ISolution<ILinearRepresentation<Double>>>();
		solutionList.add(child1Solution); solutionList.add(childSolution);
		return solutionList;
		
	}

	@Override
	public RealValueSumCrossover deepCopy() {
		return new RealValueSumCrossover();
	}
}
