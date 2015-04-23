/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;

/**
 * @author pmaia
 *
 * Jan 23, 2013
 */
public class IntRealHybridSetRepresentationFactory extends AbstractHybridSetRepresentationFactory<Integer, Double>{

	private static final long serialVersionUID = 1L;
	
	/** Maximum real value */
	protected double maxListValue;
	
	/** Minimum real value */
	protected double minListValue;
	
	/** Number of steps between minRealValue and maxRealValue */
	protected int numberOfSteps;
	
	/** Continuous or Discrete representation */
	protected boolean isContinuous;
	
	protected static final double MINREALVALUE = 0.0;
	
	protected static final double MAXREALVALUE = 2.0;


	/**
	 * Instantiates a new IntRealHybridSetRepresentationFactory
	 * 
	 * @param maxSetValue
	 * @param minSetSize
	 * @param maxSetSize
	 * @param minRealValue
	 * @param maxRealValue
	 * @param numberOfObjectives
	 */
	public IntRealHybridSetRepresentationFactory(int maxSetValue,int minSetSize,int maxSetSize,double minRealValue,double maxRealValue,int numberOfObjectives)
	{	
		this(maxSetValue,minSetSize,maxSetSize,minRealValue,maxRealValue,numberOfObjectives,0,true);
	}
	
	/**
	 * Instantiates a new HybridRealSetRepresentationFactory
	 * 
	 * @param maxSetValue
	 * @param minSetSize
	 * @param maxSetSize
	 * @param minRealValue
	 * @param maxRealValue
	 * @param numberOfObjectives
	 * @param numberOfSteps
	 */
	public IntRealHybridSetRepresentationFactory(int maxSetValue,int minSetSize,int maxSetSize,double minRealValue,double maxRealValue,int numberOfObjectives,int numberOfSteps){	
		this(maxSetValue,minSetSize,maxSetSize,minRealValue,maxRealValue,numberOfObjectives,numberOfSteps,false);
	}
	
	/**
	 * Instantiates a new HybridRealSetRepresentationFactory
	 * 
	 * @param maxSetValue
	 * @param minSetSize
	 * @param maxSetSize
	 * @param minRealValue
	 * @param maxRealValue
	 * @param numberOfObjectives
	 * @param numberOfSteps
	 * @param isContinuous
	 */
	private IntRealHybridSetRepresentationFactory(int maxSetValue,int minSetSize,int maxSetSize,double minRealValue,double maxRealValue,int numberOfObjectives,int numberOfSteps,boolean isContinuous){
		super(minSetSize,maxSetSize,maxSetValue,numberOfObjectives);						
		this.minListValue = minRealValue;
		this.maxListValue = maxRealValue;
		this.randomGenerator = new DefaultRandomNumberGenerator();		
		this.numberOfSteps = numberOfSteps;
		this.isContinuous = isContinuous;
	}

	@Override
	public void replaceGenome(IHybridSetRepresentation<Integer, Double> originalGenome, IHybridSetRepresentation<Integer, Double> newGenome) {
		originalGenome.getGenome().clear();
		originalGenome.getGenome().addAll(newGenome.getGenome());
	}

	@Override
	public IHybridSetRepresentationFactory<Integer, Double> deepCopy() {
		return new IntRealHybridSetRepresentationFactory(minSetSize, maxSetSize, maxSetValue, maxListValue, minListValue, numberOfObjectives);
	}

	
	@Override
	public Integer generateSetValue() {
		return (int) (randomGenerator.nextDouble()*(maxSetValue-1));		
	}

	@Override
	public ISolution<IHybridSetRepresentation<Integer, Double>> generateSolution(int numberOfValuesToGenerate, IRandomNumberGenerator randomGenerator) {
		List<Double> realvalues = generateRealvalue(minListValue, maxListValue, numberOfValuesToGenerate);
		TreeSet<Integer> setvalues = generateSetValues(numberOfValuesToGenerate);
				
		IHybridSetRepresentation<Integer,Double> newSolution = new HybridSetRepresentation<Integer,Double>(realvalues, setvalues);
		
		return new Solution<IHybridSetRepresentation<Integer,Double>>(newSolution,numberOfObjectives);
	}
	
	@Override
	public Double generateListValue(){
		if(isContinuous)
			return generateContinuousRealValue(minListValue,maxListValue);
		else
			return generateDiscreteRealValue(minListValue,maxListValue);
	}
	
	private double generateDiscreteRealValue(double lowerBound,double upperBound){
		double stepSize = upperBound / numberOfSteps;
		int chosenStep = randomGenerator.nextInt((numberOfSteps+1));
		return lowerBound+chosenStep*stepSize;
	}
	
	private double generateContinuousRealValue(double lowerBound,double upperBound){
		double generatedvalue = randomGenerator.nextDouble()*(upperBound-lowerBound)+lowerBound; 
		return generatedvalue;
	}
	
	
	public List<Double> generateRealvalue(double lowerBound,double upperBound,int numberOfValuesToGenerate)
	{
		List<Double> generatedValues = new ArrayList<Double>();
		for(int i=0;i<numberOfValuesToGenerate;i++){
			if(isContinuous)
				generatedValues.add(generateContinuousRealValue(lowerBound, upperBound));
			else
				generatedValues.add(generateDiscreteRealValue(lowerBound, upperBound));
		}
		return generatedValues;
	}
	
	public double generateRealValue(double lowerBound,double upperBound){
		if(isContinuous)
			return generateContinuousRealValue(lowerBound, upperBound);
		else
			return generateDiscreteRealValue(lowerBound, upperBound);
	}
	

	public double getMaxRealValue() {
		return maxListValue;
	}


	public void setMaxRealValue(double maxRealValue) {
		this.maxListValue = maxRealValue;
	}


	public double getMinRealValue() {
		return minListValue;
	}


	public void setMinRealValue(double minRealValue) {
		this.minListValue = minRealValue;
	}


	public void setNumberOfObjectives(int numberOfObjectives) {
		this.numberOfObjectives = numberOfObjectives;
	}

	public int getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	public boolean isContinuous() {
		return isContinuous;
	}

	public void setContinuous(boolean isContinuous) {
		this.isContinuous = isContinuous;
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
	public Double increaseListValue(Double listValue) {
		return listValue+=1.0;
	}

	@Override
	public Double decreaseListValue(Double listValue) {
		return listValue-=1.0;
	}


	@Override
	public Double getMaxListValue() {
		return maxListValue;
	}


	@Override
	public void setMaxListValue(Double maxListValue) {
		this.maxListValue = maxListValue;
	}


	@Override
	public Double getMinListValue() {
		return minListValue;
	}
	
	@Override
	public void setMinListValue(Double minListValue){
		this.minListValue = minListValue;
	}

	@Override
	public int compareSetValues(Integer value1, Integer value2) {
		return value1.compareTo(value2);
	}

	@Override
	public int compareListValues(Double value1, Double value2) {
		return value1.compareTo(value2);
	}

}
