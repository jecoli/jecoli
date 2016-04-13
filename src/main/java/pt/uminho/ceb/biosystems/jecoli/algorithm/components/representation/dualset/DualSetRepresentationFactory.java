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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;




// TODO: Auto-generated Javadoc
/**
 * A factory for creating SetRepresentation objects.
 */
public class DualSetRepresentationFactory implements ISolutionFactory<DualSetRepresentation>, Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The max element. */
	protected int maxElementKO;
	protected int maxElementKI;
	
	/** The min set size. */
	protected int minSetSize;
	
	/** The max set size. */
	protected int maxSetSizeKO;
	protected int maxSetSizeKI;
	
	/** The initial min size. */
	protected int initialMinSize;
	
	/** The initial max size. */
	protected int initialMaxSizeKO;
	protected int initialMaxSizeKI;
	
	protected int numberOfObjectives;

	/**
	 * Instantiates a new sets the representation factory.
	 * 
	 * @param maxElement the max element
	 * @param maxSetSize the max set size
	 * @param numberOfObjectives the number of objectives
	 */

	public DualSetRepresentationFactory(int maxElementKnockouts, int maxElementAddReactions, int maxSetSizeKnockouts, int maxSetSizeAddReactions) {
		minSetSize = 1;
		initialMinSize = 1;
		this.maxElementKO = maxElementKnockouts;
		this.maxElementKI = maxElementAddReactions;
		this.maxSetSizeKO = maxSetSizeKnockouts;
		this.maxSetSizeKI = maxSetSizeAddReactions;
		this.initialMaxSizeKO = maxSetSizeKO;
		this.initialMaxSizeKI = maxSetSizeKI;
		
		this.numberOfObjectives = 1;
	}
	
	public DualSetRepresentationFactory(int maxElementKnockouts, int maxElementAddReactions, int maxSetSizeKnockouts, int maxSetSizeAddReactions,int numberObjectives) {
		this(maxElementKnockouts, maxElementAddReactions,maxSetSizeKnockouts,maxSetSizeAddReactions);
		this.numberOfObjectives = numberObjectives;

	}
	
	/**
	 * Instantiates a new sets the representation factory.
	 * 
	 * @param maxElement
	 */
	
	
	public DualSetRepresentationFactory(int maxElementKnockouts, int maxElementAddReactions){
		this(maxElementKnockouts,maxElementAddReactions,maxElementKnockouts,maxElementAddReactions);
	}
	
	
	public ISolution<DualSetRepresentation> generateSolution(IRandomNumberGenerator randomGenerator){		
		int sizeKO, sizeKI;
		if(this.initialMinSize == this.initialMaxSizeKO)
			sizeKO = initialMaxSizeKO;
		else 
			sizeKO = (int) (randomGenerator.nextDouble() *(initialMaxSizeKO-initialMinSize) + initialMinSize);		
		if(this.initialMinSize == this.initialMaxSizeKI)
			sizeKI = initialMaxSizeKI;
		else
			sizeKI = (int) (randomGenerator.nextDouble() *(initialMaxSizeKI-initialMinSize) + initialMinSize);		
		
		return generateSolution(sizeKO, sizeKI, randomGenerator);
	}
	
	private TreeSet<Integer> generateGenome(int size, int maxElement, IRandomNumberGenerator randomGenerator){		
		TreeSet<Integer> genome = new TreeSet<Integer>();		
		for (int i = 0; i < size; i++) {
			Integer element;
			do {
				element = (int) (randomGenerator.nextFloat()*(maxElement-1));
			} while(genome.contains(element));
			genome.add(element);
		}
		
		return genome;
	}
	
	/**
	 * Generate solution.
	 * 
	 * @param setSize the set size
	 * 
	 * @return the i solution
	 */
	public ISolution<DualSetRepresentation> generateSolution(int setSizeKO,int setSizeKI, IRandomNumberGenerator randomGenerator){
		TreeSet<Integer> genomeKO = generateGenome (setSizeKO, maxElementKO, randomGenerator);
		TreeSet<Integer> genomeKI = generateGenome (setSizeKI, maxElementKI, randomGenerator);
		
		DualSetRepresentation representation = new DualSetRepresentation(genomeKO,genomeKI);		
        return new Solution<DualSetRepresentation>(representation,numberOfObjectives);
	}

    public ISolutionSet<DualSetRepresentation> generateSolutionSet(int numberOfSolutions,IRandomNumberGenerator randomGenerator){
        ISolutionSet<DualSetRepresentation> solutionSet = new SolutionSet<DualSetRepresentation>();

        for(int i = 0; i < numberOfSolutions;i++){
            ISolution<DualSetRepresentation> solution = generateSolution(randomGenerator);
            solutionSet.add(solution);
        }
        
        return solutionSet;
    }
    
    @Override
	public ISolutionSet<DualSetRepresentation> copySolutionSet(ISolutionSet<DualSetRepresentation> solutionSetToCopy) {
    	ISolutionSet<DualSetRepresentation> solutionSet = new SolutionSet<DualSetRepresentation>();

        for(int i = 0; i < solutionSetToCopy.getNumberOfSolutions();i++){
            ISolution<DualSetRepresentation> newSol = copySolution(solutionSetToCopy.getSolution(i));
            solutionSet.add(i,newSol);
        }
        
        return solutionSet;
	}


	@Override
	public void replaceGenome(DualSetRepresentation originalGenome, DualSetRepresentation newGenome) {
		originalGenome.genomeKI = newGenome.genomeKI;
		originalGenome.genomeKO = newGenome.genomeKO;
	}

	public ISolution<DualSetRepresentation> copySolution(ISolution<DualSetRepresentation> solutionToCopy) {
    	DualSetRepresentation solutionGenome = (DualSetRepresentation) solutionToCopy.getRepresentation();
    	
    	TreeSet<Integer> genomeToCopyKO = solutionGenome.getGenomeKnockout();
    	TreeSet<Integer> genomeToCopyKI = solutionGenome.getGenomeAddReactions();

    	TreeSet<Integer> newGenomeKO = new TreeSet<Integer>();
    	TreeSet<Integer> newGenomeKI = new TreeSet<Integer>();
    	
    	Iterator<Integer> it = genomeToCopyKO.iterator();
    	
    	while (it.hasNext()){
    		Integer geneValue = it.next();
    		Integer geneCopy = new Integer(geneValue.intValue());
    		newGenomeKO.add(geneCopy);
    	}
    	it = genomeToCopyKI.iterator();
    	while (it.hasNext()){
    		Integer geneValue = it.next();
    		Integer geneCopy = new Integer(geneValue.intValue());
    		newGenomeKI.add(geneCopy);
    	}
		DualSetRepresentation newRepresentation =  new DualSetRepresentation(newGenomeKO, newGenomeKI);
		ISolution<DualSetRepresentation> newSolution = new Solution<DualSetRepresentation>(newRepresentation, solutionToCopy.getNumberOfObjectives());
		return newSolution;
    }
	
	
	
	/**
	 * Gets the max element.
	 * 
	 * @return the max element
	 */
	public int getMaxElementKnockouts() {
		return maxElementKO;
	}
	/**
	 * Gets the max element.
	 * 
	 * @return the max element
	 */
	public int getMaxElementAddReactions() {
		return maxElementKI;
	}
	/**
	 * Sets the max element.
	 * 
	 * @param maxElement the new max element
	 */
	public void setMaxElement(int maxElement, boolean isKnockout) {
		if (isKnockout)
			this.maxElementKO = maxElement;
		else
			this.maxElementKI = maxElement;
	}

	/**
	 * Gets the min set size.
	 * 
	 * @return the min set size
	 */
	public int getMinSetSize() {
		return minSetSize;
	}

//	/**
//	 * Sets the min set size.
//	 * 
//	 * @param minSetSize the new min set size
//	 */
//	public void setMinSetSize(int minSetSize) {
//		this.minSetSize = minSetSize;
//	}

	/**
	 * Gets the max set size.
	 * 
	 * @return the max set size
	 */
	public int getMaxSetSizeKnockouts() {
		return maxSetSizeKO;
	}
	/**
	 * Gets the max set size.
	 * 
	 * @return the max set size
	 */
	public int getMaxSetSizeAddReactions() {
		return maxSetSizeKI;
	}
//	/**
//	 * Sets the max set size.
//	 * 
//	 * @param maxSetSize the new max set size
//	 */
//	public void setMaxSetSize(int maxSetSize, boolean isKnockout) {
//		if(isKnockout)
//			this.maxSetSizeKO = maxSetSize;
//		else
//			this.maxSetSizeKI = maxSetSize;
//	}

	/**
	 * Gets the initial min size.
	 * 
	 * @return the initial min size
	 */
	public int getInitialMinSize() {
		return initialMinSize;
	}

//	/**
//	 * Sets the initial min size.
//	 * 
//	 * @param initialMinSize the new initial min size
//	 */
//	public void setInitialMinSize(int initialMinSize) {
//		this.initialMinSize = initialMinSize;
//	}

	/**
	 * Gets the initial max size.
	 * 
	 * @return the initial max size
	 */
	public int getInitialMaxSizeKnockouts() {
		return initialMaxSizeKO;
	}
	/**
	 * Gets the initial max size.
	 * 
	 * @return the initial max size
	 */
	public int getInitialMaxSizeAddReactions() {
		return initialMaxSizeKI;
	}
	/**
	 * Sets the initial max size.
	 * 
	 * @param initialMaxSize the new initial max size
	 */
	public void setInitialMaxSize(int initialMaxSize, boolean isKnockout) {
		if(isKnockout)
			this.initialMaxSizeKO = initialMaxSize;
		else
			this.initialMaxSizeKI = initialMaxSize;
	}

	
	@Override
	public DualSetRepresentationFactory deepCopy(){
//		return new DualSetRepresentationFactory(maxElementKO, maxElementKI, minSetSize, maxSetSizeKO, maxSetSizeKI, initialMinSize, initialMaxSizeKO, initialMaxSizeKI,numberOfObjectives);
		return new DualSetRepresentationFactory(maxElementKO, maxElementKI, maxSetSizeKO, maxSetSizeKI,numberOfObjectives);
	}

	@Override
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}
	 
}
