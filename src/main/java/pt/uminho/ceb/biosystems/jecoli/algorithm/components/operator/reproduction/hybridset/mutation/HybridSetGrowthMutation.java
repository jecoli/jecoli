package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;

public class HybridSetGrowthMutation<G,H> extends AbstractHybridSetMutationOperator<G,H>{
	
	private static final long serialVersionUID = 1841479786610557527L;

	/** Number of tries */
	private static int MAXTRIES = 20;
	
	/** Number of genes to add */ 
	protected int numberGenesToAdd;
	
	public HybridSetGrowthMutation()
	{
		numberGenesToAdd = 1;
	}
	
	public HybridSetGrowthMutation(int numberOfGenesToAdd)
	{
		numberGenesToAdd = numberOfGenesToAdd;
	}
	
	@Override
	public void mutateGenome(IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator) {
		int N = computeNumberOfGenesToModify(0,randomNumberGenerator);
		
		for(int i=0;i<N;i++)
			addNewElement(childGenome,solutionFactory,randomNumberGenerator);
	}

	@Override
	public int computeNumberOfGenesToModify(int childNumberOfGenes,IRandomNumberGenerator randomNumberGenerator) {
		return randomNumberGenerator.nextInt(numberGenesToAdd)+1;
	}

	protected void addNewElement(IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomGenerator)
	{
		int maxSize = solutionFactory.getMaxSetSize();
		G newElementSetValue;
		
		if(childGenome.getNumberOfElements()<maxSize)
		{
			int tries = 0;
			do{
				newElementSetValue = solutionFactory.generateSetValue();
				tries++;
			} while(childGenome.containsElement(newElementSetValue) && tries<MAXTRIES);
			if(tries<MAXTRIES) childGenome.addElement(newElementSetValue, solutionFactory.generateListValue());
		}
	}
	
	@Override
	public IReproductionOperator<IHybridSetRepresentation<G,H>, IHybridSetRepresentationFactory<G,H>> deepCopy() throws Exception {
		return new HybridSetGrowthMutation<G,H>(numberGenesToAdd);
	}
}
