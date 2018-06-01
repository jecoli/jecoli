/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.SetRepresentation;

/**
 * @author pmaia
 *
 * Jan 22, 2013
 */
public class HybridSetRepresentation<G,H> extends SetRepresentation<G> implements IHybridSetRepresentation<G,H>, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected List<H> listValues;

	/**
	 *  Instantiates a new HybridRealSetRepresentation
	 */
	public HybridSetRepresentation()
	{
		this(new ArrayList<H>(),new TreeSet<G>());
	}
	
	/**
	 * Instantiates a new HybridRealSetRepresentation
	 * 
	 * @param representation
	 */
	public HybridSetRepresentation(HybridSetRepresentation<G,H> representation)
	{
		this(representation.getListValues(),representation.getGenome());
	}
	
	/**
	 * Instantiates a new HybridRealSetRepresentation
	 * 
	 * @param listvalues
	 * @param setvalues
	 */
	public HybridSetRepresentation(List<H> listvalues,TreeSet<G> setvalues){
		super(setvalues);
		this.listValues = listvalues;
	}
	
	public void removeElementAt(int index){
		super.removeElementAt(index);
		listValues.remove(getElementAt(index));
	}
	
	public void removeElement(G setValue)
	{
		int position = getElementPosition(setValue);
		
		listValues.remove(position);
		super.removeElement(setValue);
	}
	
	public void removeElements(Set<G> setValues)
	{
		for(G value : setValues)
			removeElement(value);
	}
	
	public void addElement(G setValue, H realValue)
	{
		if(!super.containsElement(setValue))
		{
			super.addElement(setValue);
			int position = getElementPosition(setValue);
			listValues.add(position, realValue);
		}
	}
	
	public void addElement(IHybridSetRepresentation<G,H> representation,int elementIndex){
		G setValue = representation.getElementAt(elementIndex);
		H realValue = representation.getListValue(setValue);
		
		addElement(setValue, realValue);
	}
	
	public void addAllElements(IHybridSetRepresentation<G,H> representation){
		int size = representation.getNumberOfElements();
		
		for(int i=0;i<size;i++)
			addElement(representation, i);
	}
	
	public IHybridSetRepresentation<G,H> getElementAtPosition(int position){
		HybridSetRepresentation<G,H> newRepresentation = new HybridSetRepresentation<G,H>();
		newRepresentation.addElement(this, position);
		return newRepresentation;
	}
	
	public boolean containsListValue(H value){
		return listValues.contains(value);
	}
	
	public H getListValueAt(int index){
		return listValues.get(index);
	}
	
	public List<H> getListValues() {
		return listValues;
	}

	public void setListValues(List<H> listValues) {
		this.listValues = listValues;
	}

	
	public int getElementPosition(G setValue)
	{
		int pos = 0;
		for(G value : genome){
			if(value.equals(setValue))
				return pos;
			pos++;
		}
		return -1;
	}
	
	public H getListValue(G setValue)
	{
		int position = getElementPosition(setValue);
		return getListValueAt(position);
	}
	
	public G getRandomElement(IRandomNumberGenerator randomGenerator)
	{
		int index = (int) (randomGenerator.nextDouble()*(getNumberOfElements() - 1));
//		System.out.println("HybridSetRepresentation: getRandomElement = "+ index+" (total elements = "+getNumberOfElements()+")");
		return getElementAt(index);
	}
	
	@Override
	public String stringRepresentation() {
		String stringrepresentation = "";
		
		int length = getNumberOfElements();
		
		for(int i=0;i<length;i++)
			stringrepresentation+="["+getElementAt(i)+","+getListValueAt(i)+"];";
		
		
		return stringrepresentation;
	}

	@Override
    public IRepresentation deepCopy() {
        TreeSet<G> newGenome = new TreeSet<G>();
        List<H> newList = new ArrayList<H>();
        for(G element:genome)
            newGenome.add(element);
        for(H value:listValues)
        	newList.add(value);
        return new HybridSetRepresentation<G,H>(newList,newGenome);
    }
	
//	public TreeSet<Integer> getSetValuesAsTreeSet()
//	{
//		TreeSet<Integer> list = new TreeSet<Integer>();
//		
//		int size = getSize();
//		for(int i=0;i<size;i++)
//			list.add(getSetValueAtIndex(i));
//			
//		return list;
//	}
	
//	public List<Double> getRealValuesAsList()
//	{
//		List<Double> list = new ArrayList<Double>();
//		
//		int size = getSize();
//		for(int i=0;i<size;i++)
//			list.add(getRealValueAtIndex(i));
//		
//		return list;
//	}

}
