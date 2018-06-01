/**
 * Copyright 2009,
 * CCTC - Computer Science and Technology Center
 * IBB-CEB - Institute for Biotechnology and Bioengineering - Centre of
 * Biological Engineering
 * University of Minho
 * 
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Public License for more details.
 * 
 * You should have received a copy of the GNU Public License
 * along with this code. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
 * University of Minho
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset;

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
public class DualSetRepresentation implements IElementsRepresentation<Integer>, IComparableRepresentation<DualSetRepresentation>, Serializable {
	
	private static final long	serialVersionUID	= 1L;
	
	/** The genome. */
	protected TreeSet<Integer>	genomeKO;					// genome of Knockouts
	protected TreeSet<Integer>	genomeKI;					// genome of Knockins
															
	/*
	 * Instantiates a new sets the representation.
	 * 
	 * @param genome the genome
	 */
	public DualSetRepresentation(TreeSet<Integer> genomeKnockouts, TreeSet<Integer> genomeAddReactions) {
		this.genomeKO = genomeKnockouts;
		this.genomeKI = genomeAddReactions;
	}
	
	/**
	 * Gets the number of elements.
	 * 
	 * @return the number of elements
	 */
	public int getNumberOfElements() {
		return genomeKO.size() + genomeKI.size();
	}
	
	public int getNumberOfElements(boolean isKnockout) {
		if (isKnockout) return genomeKO.size();
		return genomeKI.size();
	}
	
	public Integer getElementAt(int index) {
		int numElemKOs = getNumberOfElements(true);
		if (index < numElemKOs)
			return getElementAtIndex(index, true);
		else
			return getElementAtIndex(index - numElemKOs, false);
	}
	
	public Integer getElementAtIndex(int index, boolean isKnockout) {
		Iterator<Integer> it = isKnockout ? genomeKO.iterator() : genomeKI.iterator();
		Integer element = null;
		for (int i = 0; i <= index; i++)
			element = it.next();
		
		return element;
	}
	
	/**
	 * Gets the random element.
	 * 
	 * @return the random element
	 */
	public Integer getRandomElement(IRandomNumberGenerator randomGenerator, boolean isKnockout) {
		int size = isKnockout ? genomeKO.size() : genomeKI.size();
		int index = (int) (randomGenerator.nextDouble() * (size - 1));
		return getElementAtIndex(index, isKnockout);
	}
	
	/**
	 * Adds the element.
	 * 
	 * @param element the element
	 */
	public void addElement(Integer element, boolean isKnockout) {
		if (isKnockout)
			genomeKO.add(element);
		else
			genomeKI.add(element);
	}
	
	/**
	 * Removes the element.
	 * 
	 * @param element the element
	 */
	public void removeElement(Integer element, boolean isKnockout) {
		if (isKnockout)
			genomeKO.remove(element);
		else
			genomeKI.remove(element);
	}
	
	/**
	 * Contains element.
	 * 
	 * @param element the element
	 * @param isKnochout identify the genome part
	 * @return true, if successful
	 */
	public boolean containsElement(Integer element, boolean isKnochout) {
		if (isKnochout) return genomeKO.contains(element);
		return genomeKI.contains(element);
	}
	
	/**
	 * Gets the genome of Knockouts.
	 * 
	 * @return the genome
	 */
	public TreeSet<Integer> getGenomeKnockout() {
		return genomeKO;
	}
	
	/**
	 * Gets the genome of new reactions.
	 * 
	 * @return the genome
	 */
	public TreeSet<Integer> getGenomeAddReactions() {
		return genomeKI;
	}
	
	/*
	 * Sets the genome.
	 * 
	 * @param genome the new genome
	 */
	public void setGenome(TreeSet<Integer> genomeKnockouts, TreeSet<Integer> genomeAddReactions) {
		this.genomeKO = genomeKnockouts;
		this.genomeKI = genomeAddReactions;
	}
	
	public void setGenome(int genomeIndex, TreeSet<Integer> newGenome) {
		if (genomeIndex == 0)
			genomeKO = newGenome;
		else
			genomeKI = newGenome;
	}
	
	@Override
	public String stringRepresentation() {
		String genomeStringRepresentation = "";
		Iterator<Integer> genomeIterator = genomeKO.iterator();
		while (genomeIterator.hasNext()) {
			Integer geneValue = genomeIterator.next();
			genomeStringRepresentation += " " + geneValue;
		}
		genomeIterator = genomeKI.iterator();
		while (genomeIterator.hasNext()) {
			Integer geneValue = genomeIterator.next();
			genomeStringRepresentation += " " + geneValue;
		}
		return genomeStringRepresentation;
	}
	
	@Override
	public IRepresentation deepCopy() {
		TreeSet<Integer> genomeKOCopy = new TreeSet<Integer>();
		TreeSet<Integer> genomeKICopy = new TreeSet<Integer>();
		
		for (Integer gene : genomeKO)
			genomeKOCopy.add(gene);
		
		for (Integer gene : genomeKI)
			genomeKICopy.add(gene);
		
		return new DualSetRepresentation(genomeKOCopy, genomeKICopy);
	}
	
	@Override
	public boolean equals(DualSetRepresentation representation) {
		return genomeKO.equals(representation.getGenomeKnockout()) && genomeKI.equals(representation.getGenomeAddReactions());
	}
	
	public TreeSet<Integer> getIndividualRepresentation(int individualIndex) throws Exception {
		switch (individualIndex) {
			case 0:
				return genomeKO;
			case 1:
				return genomeKI;
			default:
				throw new Exception("Invalid Individual Index");
		}
	}

	@Override
	public void addElement(Integer element) {
		//FIXME - WHAT TO DO WITH THIS....
	}

	@Override
	public void removeElement(Integer element) {
		removeElement(element,true);
		removeElement(element,false);
	}

	@Override
	public boolean containsElement(Integer element) {
		return containsElement(element, true) || containsElement(element, false);
	}

	@Override
	public boolean containsRepresentation(IElementsRepresentation<Integer> representation) {
		for (int i = 0; i < ((DualSetRepresentation)representation).getNumberOfElements(true); i++)
			if (!genomeKO.contains(((DualSetRepresentation)representation).getElementAtIndex(i, true))) return false;
		
		for (int i = 0; i < ((DualSetRepresentation)representation).getNumberOfElements(false); i++)
			if (!genomeKI.contains(((DualSetRepresentation)representation).getElementAtIndex(i, false))) return false;
		
		return true;
	}

	@Override
	public boolean isContainedInRepresentation(IElementsRepresentation<Integer> representation) {
		for (int i = 0; i < this.getNumberOfElements(true); i++)
			if (!((DualSetRepresentation)representation).containsElement(this.getElementAtIndex(i, true), true)) return false;
		
		for (int i = 0; i < this.getNumberOfElements(false); i++)
			if (!((DualSetRepresentation)representation).containsElement(this.getElementAtIndex(i, false), false)) return false;
		
		return true;
	}
	
}
