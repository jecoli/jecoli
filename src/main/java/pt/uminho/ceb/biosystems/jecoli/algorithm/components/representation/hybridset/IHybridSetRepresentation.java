/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation;

/**
 * @author pmaia
 *
 * Jan 23, 2013
 */
public interface IHybridSetRepresentation<G,H> extends IElementsRepresentation<G>{

	void removeElementAt(int index);
	
	G getRandomElement(IRandomNumberGenerator randomNumberGenerator);
	
	TreeSet<G> getGenome();
	
	H getListValueAt(int index);
	
	 void removeElements(Set<G> setValues);
	 
	 void addElement(IHybridSetRepresentation<G,H> representation,int elementIndex);
	 
	 void addElement(G setValue, H listValue);
	 
	 void addAllElements(IHybridSetRepresentation<G,H> representation);
	 
	 IHybridSetRepresentation<G,H> getElementAtPosition(int position);
	 
	 H getListValue(G setValue);
	 
	 List<H> getListValues();
}
