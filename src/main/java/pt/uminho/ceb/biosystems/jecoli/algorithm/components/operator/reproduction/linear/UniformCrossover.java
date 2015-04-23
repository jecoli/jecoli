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


// TODO: Auto-generated Javadoc
/**
 * The Class UniformCrossover.
 */
public class UniformCrossover<G> extends AbstractCrossoverOperator<ILinearRepresentation<G>,ILinearRepresentationFactory<G>>  {
	

	private static final long serialVersionUID = 7844061815734829820L;


	protected List<ISolution<ILinearRepresentation<G>>> crossOverGenomes(ILinearRepresentation<G> parentGenome,ILinearRepresentation<G> parent1Genome,ILinearRepresentationFactory<G> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{	

		int parentGenomeSize = parentGenome.getNumberOfElements();
		int parent1GenomeSize = parent1Genome.getNumberOfElements();
		int finalSize = Math.min(parentGenomeSize,parent1GenomeSize); 
		
		List<G> childGenome = new ArrayList<G>();	
		List<G> childGenome1 = new ArrayList<G>();

		
		for(int i = 0; i < finalSize;i++){
			double randomNumber = randomNumberGenerator.nextDouble();
			
			if(randomNumber < 0.5){
				childGenome.add(parent1Genome.getElementAt(i));
				childGenome1.add(parentGenome.getElementAt(i));
			}else{
				childGenome.add(parentGenome.getElementAt(i));
				childGenome1.add(parent1Genome.getElementAt(i));
			}	
		}
		
		if(parentGenomeSize > finalSize)
			for(int i = finalSize; i < parentGenomeSize;i++)
				childGenome.add(parentGenome.getElementAt(i));
		else if(parent1GenomeSize > finalSize)
			for(int i = finalSize; i < parent1GenomeSize;i++)
				childGenome1.add(parent1Genome.getElementAt(i));
		
		int numObjectives = solutionFactory.getNumberOfObjectives();
		
		ISolution<ILinearRepresentation<G>> childSolution = 
				new Solution<ILinearRepresentation<G>>(new LinearRepresentation<G>(childGenome),numObjectives);		
		ISolution<ILinearRepresentation<G>> child1Solution = 
				new Solution<ILinearRepresentation<G>>(new LinearRepresentation<G>(childGenome1),numObjectives);
		
		
		List<ISolution<ILinearRepresentation<G>>> solutionList = new ArrayList<ISolution<ILinearRepresentation<G>>>();
        solutionList.add(childSolution);
		solutionList.add(child1Solution);
		return solutionList;
		
	}


	@Override
	public UniformCrossover<G> deepCopy() {
		return new UniformCrossover<G>();
	}

}
