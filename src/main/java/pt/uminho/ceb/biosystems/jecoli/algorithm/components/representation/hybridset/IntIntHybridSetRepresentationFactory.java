/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;

/**
 * @author pmaia
 *
 * Jan 23, 2013
 */
public class IntIntHybridSetRepresentationFactory extends AbstractHybridSetRepresentationFactory<Integer, Integer>{

	private static final long serialVersionUID = 1L;
	
	/** Maximum integer value */
	protected int maxListValue;
	
	/** Minimum integer value */
	protected int minListValue;
	
	/**
	 * @param minSetSize
	 * @param maxSetSize
	 * @param maxSetValue
	 * @param numberOfObjectives
	 */
	public IntIntHybridSetRepresentationFactory(int minSetSize, int maxSetSize,int maxSetValue, int minIntValue, int maxIntValue, int numberOfObjectives) {
		super(minSetSize, maxSetSize, maxSetValue, numberOfObjectives);
		this.maxListValue = maxIntValue;
		this.minListValue = minIntValue;
	}
	
	@Override
	public Integer generateSetValue(){
		return (int) (randomGenerator.nextDouble()*(maxSetValue-1));
	}
	
	@Override
	public Integer generateListValue(){
		return generateIntegerValue(minListValue,maxListValue);
	}
	
	public Integer generateIntegerValue(int lowerBound,int upperBound){
		int generatedIntegerValue;
		do{
			generatedIntegerValue = lowerBound + (int) (randomGenerator.nextDouble() * ((upperBound-lowerBound) + 1));
		}while(generatedIntegerValue == 0.0);
		
		return generatedIntegerValue; 
	}

	public ISolution<IHybridSetRepresentation<Integer,Integer>> generateSolution(int numberOfValuesToGenerate,IRandomNumberGenerator randomGenerator)
	{
		List<Integer> integerValues = generateIntegerValues(minListValue, maxListValue, numberOfValuesToGenerate);
		TreeSet<Integer> setvalues = generateSetValues(numberOfValuesToGenerate);
				
		IHybridSetRepresentation<Integer,Integer> newSolution = new HybridSetRepresentation<Integer,Integer>(integerValues, setvalues);
		
		return new Solution<IHybridSetRepresentation<Integer,Integer>>(newSolution,numberOfObjectives);
	}
	
	public List<Integer> generateIntegerValues(int lowerBound, int upperBound, int numberOfValuesToGenerate){
		List<Integer> integerList = new ArrayList<Integer>();
		
		for(int i=0; i<numberOfValuesToGenerate ;i++)
			integerList.add(generateIntegerValue(lowerBound, upperBound));
		
		return integerList;
	}

	@Override
	public void replaceGenome(IHybridSetRepresentation<Integer, Integer> originalGenome, IHybridSetRepresentation<Integer, Integer> newGenome) {
		originalGenome.getGenome().clear();
		originalGenome.getGenome().addAll(newGenome.getGenome());
	}

	@Override
	public IHybridSetRepresentationFactory<Integer, Integer> deepCopy() {
		return new IntIntHybridSetRepresentationFactory(minSetSize, maxSetSize, maxSetValue, minListValue, maxListValue, numberOfObjectives);	
	}
	
	/**
	 * @return the maxIntegerValue
	 */
	public Integer getMaxListValue() {
		return maxListValue;
	}

	/**
	 * @return the minIntegerValue
	 */
	public Integer getMinListValue() {
		return minListValue;
	}
	
	@Override
	public void setMinListValue(Integer minListValue){
		this.minListValue = minListValue;
	}

	@Override
	public void setMaxListValue(Integer maxListValue) {
		this.maxListValue = maxListValue;
	}
	
	@Override
	public Integer increaseSetValue(Integer setValue) {
		return setValue+=1;
	}

	@Override
	public Integer decreaseSetValue(Integer setValue) {	
		return setValue-=1;
	}

	@Override
	public Integer increaseListValue(Integer listValue) {
		return listValue+=1;
	}

	@Override
	public Integer decreaseListValue(Integer listValue) {
		return listValue-=1;
	}

	@Override
	public int compareSetValues(Integer value1, Integer value2) {
		return value1.compareTo(value2);
	}

	@Override
	public int compareListValues(Integer value1, Integer value2) {
		return value1.compareTo(value2);
	}
}
