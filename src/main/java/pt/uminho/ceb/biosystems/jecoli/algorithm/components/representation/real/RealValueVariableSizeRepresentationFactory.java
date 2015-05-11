package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.AbstractVariableSizeRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;

public class RealValueVariableSizeRepresentationFactory extends AbstractVariableSizeRepresentationFactory<Double>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607347046711205417L;
	
	protected double geneUpperLimit;
	protected double geneLowerLimit;
	
	public RealValueVariableSizeRepresentationFactory(int minSize, int maxSize,double geneLowerLimit,double geneUpperLimit) {
		super(minSize, maxSize);
		this.geneLowerLimit  =  geneLowerLimit;
		this.geneUpperLimit = geneUpperLimit;
	}

	@Override
	public Double generateGeneValue(int genePosition,IRandomNumberGenerator randomNumberGenerator) {
		double geneValue = randomNumberGenerator.nextDouble()*(geneUpperLimit-geneLowerLimit)+geneLowerLimit;
		return geneValue;
	}

	@Override
	public int getMaximumNumberOfGenes() {
		return maxSize;
	}

	@Override
	public int getMinimumNumberOfGenes() {
		return minSize;
	}

	@Override
	public void replaceGenome(ILinearRepresentation<Double> originalGenome, ILinearRepresentation<Double> newGenome) {
		originalGenome.getGenome().clear();
		originalGenome.getGenome().addAll(newGenome.getGenome());
	}

	@Override
	public ILinearRepresentationFactory<Double> deepCopy() {
		return new RealValueVariableSizeRepresentationFactory(minSize, maxSize,geneLowerLimit,geneUpperLimit);
	}

	@Override
	protected Double copyGeneValue(Double geneValueToCopy) {
		double newValue  = geneValueToCopy;
		return newValue;
	}

	@Override
	public int getNumberOfObjectives() {
	return numberOfObjectives;
	}

}
