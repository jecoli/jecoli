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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.LinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;


/**
 * The Class RealValueArithmeticalCrossover.
 */
public class RealValueArithmeticalCrossover extends AbstractCrossoverOperator<ILinearRepresentation<Double>,ILinearRepresentationFactory<Double>>{
	
	
	
	private static final long serialVersionUID = 1504562640830216310L;

protected List<ISolution<ILinearRepresentation<Double>>> crossOverGenomes(ILinearRepresentation<Double> parentGenome,
			ILinearRepresentation<Double> parent1Genome,ILinearRepresentationFactory<Double> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		
		int parentGenomeSize = parentGenome.getNumberOfElements();
		int parent1GenomeSize = parent1Genome.getNumberOfElements();
		
		int minSize = Math.min(parentGenomeSize,parent1GenomeSize);

		double alfa = randomNumberGenerator.nextDouble();
		
		 List<Double> childGenome = new ArrayList<Double>();
		 List<Double> childGenome1 = new ArrayList<Double>();
		
		
		for(int i=0; i< minSize; i++){
			Double parentValue = parentGenome.getElementAt(i);
			Double parent1Value = parent1Genome.getElementAt(i);
			
			childGenome.add(alfa*parentValue+(1.0-alfa)*parent1Value);
			childGenome1.add((1.0-alfa)*parentValue+alfa*parent1Value);
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
public RealValueArithmeticalCrossover deepCopy() {
	return new RealValueArithmeticalCrossover();
}

}

