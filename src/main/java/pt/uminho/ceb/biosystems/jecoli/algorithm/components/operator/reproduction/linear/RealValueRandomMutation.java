package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;

public class RealValueRandomMutation extends AbstractMutationOperator<ILinearRepresentation<Double>,RealValueRepresentationFactory> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5878113421564651703L;

	/** The max number genes. */
	int maxNumberGenes = 1; // maximum number of genes to mutate; actual number generated between 1 and this parameter

	/** The position probability. */
	double positionProbability = 0.0; // probability to apply mutation at each position; use if larger then 0 (if zero do not use)

	
	/**
	 * Instantiates a new gaussian perturbation mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param maxNumberGenes the max number genes
	 */
	public RealValueRandomMutation(int maxNumberGenes){
		this.maxNumberGenes = maxNumberGenes;
	}
	
	/**
	 * Instantiates a new gaussian perturbation mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param positionProbability the position probability
	 */
	public RealValueRandomMutation(double positionProbability) {
		this.positionProbability = positionProbability;
	}

		
	public RealValueRandomMutation(double positionProbability, int maxNumberGenes) {
		this.positionProbability = positionProbability;
		this.maxNumberGenes = maxNumberGenes;
	}

	@Override
	protected void mutateGenome(ILinearRepresentation<Double> childGenome,RealValueRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int numberOfGenes = childGenome.getNumberOfElements();
		
		// check if positional probability will be sued
		if (this.positionProbability > 0.0)
		{
			for(int i=0; i < childGenome.getNumberOfElements(); i++)
			{
				double v = randomNumberGenerator.nextDouble();
				if (v < this.positionProbability) mutatePosition(childGenome, i,solutionFactory,randomNumberGenerator);
			}			
		}
		else
		{
			int numberGenesToMutate = 1;
			if (this.maxNumberGenes > 1) 
				numberGenesToMutate = (int) (randomNumberGenerator.nextDouble()*(this.maxNumberGenes)+1);
		
			for(int i=0; i < numberGenesToMutate; i++)
			{
				int selectedGeneIndex = randomNumberGenerator.nextInt(numberOfGenes);
				mutatePosition(childGenome, selectedGeneIndex,solutionFactory,randomNumberGenerator);
			}
		}
		
	}
	
	/**
	 * Mutate position.
	 * 
	 * @param childGenome the child genome
	 * @param position the position
	 * @param randomNumberGenerator 
	 */
	protected void mutatePosition(ILinearRepresentation<Double> childGenome, int position,RealValueRepresentationFactory solutionFactory ,IRandomNumberGenerator randomNumberGenerator)
	{
		double pert, ul, ll;
		
		ul = solutionFactory.getGeneUpperBound(position);
		ll = solutionFactory.getGeneLowerBound(position);
		
		pert = ll + (ul-ll)*randomNumberGenerator.nextDouble();

		childGenome.setElement(position, pert);
	}

	@Override
	public RealValueRandomMutation deepCopy() {
		return new RealValueRandomMutation(positionProbability,maxNumberGenes);
	}
	
}
