package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation;

/**
 * @author pmaia
 *
 * Jan 11, 2013
 */
public interface IElementsRepresentation<E> extends IRepresentation{

	int getNumberOfElements();
	
	E getElementAt(int index);
	
	void addElement(E element);
	
	void removeElement(E element);
	
	boolean containsElement(E element);
	
	boolean containsRepresentation(IElementsRepresentation<E> representation);
	
	boolean isContainedInRepresentation(IElementsRepresentation<E> representation);
	
}
