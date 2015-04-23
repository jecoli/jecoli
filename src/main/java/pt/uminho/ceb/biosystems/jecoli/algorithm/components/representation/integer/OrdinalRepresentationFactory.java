package pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.AbstractLinearRepresentationFactory;

public class OrdinalRepresentationFactory extends AbstractLinearRepresentationFactory<Integer> implements Serializable{

	private static final long serialVersionUID = 1L;

	public OrdinalRepresentationFactory(int solutionSize) {
		super(solutionSize);
	}

	@Override
	protected Integer copyGeneValue(Integer geneValueToCopy) {
		int newGeneValue = geneValueToCopy;
		return newGeneValue;
	}
	
	@Override
	public Integer generateGeneValue(int genePosition, IRandomNumberGenerator randomNumberGenerator){
		
		int maxValue = this.solutionSize;
		double randomValue = randomNumberGenerator.nextDouble();
		int geneValue = (int)(randomValue*(maxValue - genePosition));
		
		return geneValue;
	}
	
	@Override
	public OrdinalRepresentationFactory deepCopy() {
		return new OrdinalRepresentationFactory(solutionSize);
	}
	
	@Override
	public int getMaximumNumberOfGenes() {
		return solutionSize;
	}

	@Override
	public int getMinimumNumberOfGenes() {
		return solutionSize;
	}

}
