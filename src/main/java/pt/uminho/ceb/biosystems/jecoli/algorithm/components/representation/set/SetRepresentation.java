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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IComparableRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;


// TODO: Auto-generated Javadoc
/**
 * The Class SetRepresentation.
 */
public class SetRepresentation<G> implements ISetRepresentation<G>,IComparableRepresentation<ISetRepresentation<G>>, Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The genome. */
	protected TreeSet<G> genome;
	
	/**
	 * Instantiates a new sets the representation.
	 * 
	 * @param genome the genome
	 */
	public SetRepresentation (TreeSet<G> genome)
	{
		this.genome = genome;
	}
	
    /**
     * Gets the number of elements.
     * 
     * @return the number of elements
     */
    public int getNumberOfElements() {
        return genome.size();
    }

    /**
     * Gets the element at index.
     * 
     * @param index the index
     * 
     * @return the element at index
     */
    public G getElementAt(int index) {
    	Iterator<G> it = genome.iterator();
    	G element = null;

    	for (int i = 0; i <= index; i++)
    		element = it.next();

    	return element;
    }

    /**
     * Gets the random element.
     * 
     * @return the random element
     */
    public G getRandomElement(IRandomNumberGenerator randomGenerator) {
    	int index = randomGenerator.nextInt(genome.size());
    	return getElementAt(index);
    }
    
    
    /**
     * Adds the element.
     * 
     * @param element the element
     */
    public void addElement(G element) {
        genome.add(element);
    }
    
    
    /**
     * Removes the element.
     * 
     * @param element the element
     */
    public void removeElement(G element)
    {
    	genome.remove(element);
    }
    
    /**
     * Contains element.
     * 
     * @param element the element
     * 
     * @return true, if successful
     */
    public boolean containsElement(G element)
    {
    	return genome.contains(element);
    }

	/**
	 * Gets the genome.
	 * 
	 * @return the genome
	 */
	public TreeSet<G> getGenome() {
		return genome;
	}

	/**
	 * Sets the genome.
	 * 
	 * @param genome the new genome
	 */
	public void setGenome(TreeSet<G> genome) {
		this.genome = genome;
	}

	@Override
	public String stringRepresentation() {
		String genomeStringRepresentation = "";
		Iterator<G> genomeIterator = genome.iterator();
		
		while(genomeIterator.hasNext()){
			G geneValue = genomeIterator.next();
			genomeStringRepresentation += " "+geneValue;
		}
		
		return genomeStringRepresentation;
	}

    @Override
    public IRepresentation deepCopy() {
        TreeSet<G> newGenome = new TreeSet<G>();
        for(G element:genome)
            newGenome.add(element);
        return new SetRepresentation<G>(newGenome);
    }


    public boolean equals(ISetRepresentation<G> rep) {
		return genome.equals(rep.getGenome());
	}

	@Override
	public void removeElementAt(int index) {
		Iterator<G> iterator = genome.iterator();
		G item = null;
		int i=0;
		do{
			item = iterator.next();
			i++;
		}while(iterator.hasNext() && i<=index);
		
		if(i==index)
			genome.remove(item);
			
	}

	/**
	 * @see pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation#containsRepresentation(pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation)
	 */
	@Override
	public boolean containsRepresentation(IElementsRepresentation<G> representation) {
		for(int i=0; i<representation.getNumberOfElements(); i++)
			if(!genome.contains(representation.getElementAt(i)))
				return false;
		
		return true;
	}

	/**
	 * @see pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation#isContainedInRepresentation(pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation)
	 */
	@Override
	public boolean isContainedInRepresentation(IElementsRepresentation<G> representation) {
		for(int i=0; i<this.getNumberOfElements(); i++)
			if(!representation.containsElement(this.getElementAt(i)))
				return false;
		
		return true;
	}	

}
