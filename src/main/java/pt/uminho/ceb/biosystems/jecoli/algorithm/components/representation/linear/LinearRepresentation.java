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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IComparableRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

// TODO: Auto-generated Javadoc
/**
 * The Class LinearRepresentation.
 */
public class LinearRepresentation<G> implements	ILinearRepresentation<G>, IComparableRepresentation<ILinearRepresentation<G>>, Serializable {
	
	private static final long serialVersionUID = 1L;

	/** The genome. */
	protected List<G> genome;

	/**
	 * Instantiates a new linear representation.
	 * 
	 * @param genome the genome
	 */
	public LinearRepresentation(List<G> genome){
		this.genome = genome;
	}

	/**
	 * Instantiates a new linear representation.
	 */
	public LinearRepresentation() {
		genome = new ArrayList<G>();
	}

	
	@Override
	public G getElementAt(int index){
		return genome.get(index);
	}

	
	@Override
	public int getNumberOfElements(){
		return genome.size();
	}

	
	@Override
	public void setElement(int index,G element){
		genome.set(index, element);
	}

	
	public void removeElementAt (int index){
		genome.remove(index);
	}
	
	
	public void addElementAt (int index, G element){
		genome.add(index, element);
	}
	
	public void removeElement(G element){
		genome.remove(element);
	}


	@Override
	public String stringRepresentation() {
		String genomeStringRepresentation = "[";
		int numberOfGenes = genome.size();
		
		for(int i = 0; i< numberOfGenes;i++){
			G geneValue = genome.get(i);
			genomeStringRepresentation += " "+geneValue.toString();
		}
		
		genomeStringRepresentation+="]";
		
		return genomeStringRepresentation;
	}

    @Override
    public IRepresentation deepCopy() {
        List<G> newGenome = new ArrayList<>();
        for(int i = 0;i <genome.size();i++)
            newGenome.add(i,genome.get(i));
        return new LinearRepresentation<>(newGenome);
    }

//	@Override
//	public boolean equals(IRepresentation rep) {
//		if(this.getClass().isAssignableFrom(rep.getClass())){ // incompatible representations
//			List<G> repGenome = ((LinearRepresentation<G>) rep).getGenome();
//			
//			for(int i=0; i<repGenome.size();i++){ //follows order, order is important in linear representations
//				if(!(genome.get(i).equals(repGenome.get(i))))
//						return false;
//			}
//			return true;
//			
//		}
//		return false; //default behaviour
//	}

	public List<G> getGenome() {
		return genome;
	}

	@Override
	public boolean equals(ILinearRepresentation<G> rep) {
		
		// number of elements
		if(this.getNumberOfElements()!= rep.getNumberOfElements())
			return false;
		
		// order of the elements must be the same
		for(int i=0; i<this.getNumberOfElements(); i++){ 
			if(!this.getElementAt(i).equals(rep.getElementAt(i)))
				return false;
		}
		
		return true;
	}

	
	@Override
	public void addElement(G element) {
		this.genome.add(element);
	}

	
	@Override
	public boolean containsElement(G element) {
		return genome.contains(element);
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
