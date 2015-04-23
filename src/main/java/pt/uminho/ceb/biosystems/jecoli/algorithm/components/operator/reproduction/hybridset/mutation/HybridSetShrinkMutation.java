package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;

public class HybridSetShrinkMutation<G,H> extends AbstractHybridSetMutationOperator<G,H>{

	
	private static final long serialVersionUID = -6331533494232077137L;
	
	/** Number of genes to remove */
	protected int numberGenesToRemove;
	
	public HybridSetShrinkMutation()
	{
		numberGenesToRemove = 1;
	}
	
	public HybridSetShrinkMutation(int numberOfGenesToRemove)
	{
		numberGenesToRemove = numberOfGenesToRemove;
	}

	@Override
	public void mutateGenome(IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int minSize = solutionFactory.getMinSetSize();
		int N = computeNumberOfGenesToModify(0,randomNumberGenerator);

	 	for(int k=0; k<N; k++)
 		{
 			if (childGenome.getNumberOfElements() > minSize)
 			{
 				G element = childGenome.getRandomElement(randomNumberGenerator);
 				childGenome.removeElement(element);
 			}
 		}
	}

	@Override
	public int computeNumberOfGenesToModify(int childNumberOfGenes,IRandomNumberGenerator randomNumberGenerator) {
		return randomNumberGenerator.nextInt(numberGenesToRemove)+1;
	}

	@Override
	public IReproductionOperator<IHybridSetRepresentation<G,H>, IHybridSetRepresentationFactory<G,H>> deepCopy() throws Exception {
		return new HybridSetShrinkMutation<G,H>(numberGenesToRemove);
	}
}
