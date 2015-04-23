package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;

public class HybridSetRandomMutation<G,H> extends AbstractHybridSetMutationOperator<G,H>{
	
	
	private static final long serialVersionUID = -5542377489416158365L;

	/** Number of tries */
	private static int MAXTRIES = 20;
	
	/** Number of genes to change */
	protected int numberGenesToChange;
	
	public HybridSetRandomMutation()
	{
		numberGenesToChange = 1;
	}
	
	public HybridSetRandomMutation(int numberOfGenesToChange)
	{
		numberGenesToChange = numberOfGenesToChange;
	}
	
	@Override
	public void mutateGenome(IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int N = computeNumberOfGenesToModify(0,randomNumberGenerator);
				
	 	for(int k=0; k<N; k++)
	 	{
	 		changeElement(childGenome,solutionFactory,randomNumberGenerator);
	 	}
	}

	@Override
	public int computeNumberOfGenesToModify(int childNumberOfGenes,IRandomNumberGenerator randomNumberGenerator) {
		return randomNumberGenerator.nextInt(numberGenesToChange)+1;
	}

	protected void changeElement (IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
	{
		int tries = 0;
		if(childGenome.getNumberOfElements() >0)
		{
			G element = childGenome.getRandomElement(randomNumberGenerator);
			H elementListValue = childGenome.getListValue(element);
			
			childGenome.removeElement(element);
			
			G newElement;
			
			do {
				tries++;
				newElement = solutionFactory.generateSetValue();
			} while(childGenome.containsElement(newElement) && tries < MAXTRIES);
			
			if (tries < MAXTRIES) childGenome.addElement(newElement,solutionFactory.generateListValue());
			else childGenome.addElement(element,elementListValue);
		}
	}

	@Override
	public IReproductionOperator<IHybridSetRepresentation<G,H>, IHybridSetRepresentationFactory<G,H>> deepCopy() throws Exception {
		return new HybridSetRandomMutation<G,H>(numberGenesToChange);
	}

}
