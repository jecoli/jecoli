package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;

public class HybridSetRandomIntegerMutation<G,H> extends AbstractHybridSetMutationOperator<G,H>{

	
	private static final long serialVersionUID = -4102519863675711234L;

	/** Number of genes to change */
	protected int numberGenesToChange;
	
	public HybridSetRandomIntegerMutation()
	{
		numberGenesToChange = 1;
	}
	
	public HybridSetRandomIntegerMutation(int numberOfGenesToChange)
	{
		numberGenesToChange = numberOfGenesToChange;
	}
	
	@Override
	public void mutateGenome(IHybridSetRepresentation<G,H> childGenome, IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		if(childGenome.getNumberOfElements()>0){
			int N = computeNumberOfGenesToModify(0,randomNumberGenerator);
			 
		 	for(int k=0; k<N; k++)
		 	{
		 		changeElement(childGenome,solutionFactory,randomNumberGenerator);
		 	}
		}
	}

	@Override
	public int computeNumberOfGenesToModify(int childNumberOfGenes,IRandomNumberGenerator randomNumberGenerator) {
		return randomNumberGenerator.nextInt(numberGenesToChange)+1;
	}

	protected void changeElement (IHybridSetRepresentation<G,H> childGenome, IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		G element = childGenome.getRandomElement(randomNumberGenerator);
		
		childGenome.removeElement(element);
		
		childGenome.addElement(element,solutionFactory.generateListValue());
	}

	@Override
	public IReproductionOperator<IHybridSetRepresentation<G,H>, IHybridSetRepresentationFactory<G,H>> deepCopy() throws Exception {
		return new HybridSetRandomIntegerMutation<G,H>(numberGenesToChange);
	}
}
