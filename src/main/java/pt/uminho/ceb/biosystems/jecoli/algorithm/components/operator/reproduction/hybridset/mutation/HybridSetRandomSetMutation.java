package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;

public class HybridSetRandomSetMutation<G,H> extends AbstractHybridSetMutationOperator<G,H>{


	private static final long serialVersionUID = 7410252981741998368L;

	/** Number of genes to change */
	protected int numberGenesToChange;
	
	/** Number of tries */
	private static int MAXTRIES = 20;
	
	
	public HybridSetRandomSetMutation()
	{
		numberGenesToChange = 1;
	}
	
	public HybridSetRandomSetMutation(int numberOfGenesToChange)
	{
		numberGenesToChange = numberOfGenesToChange;
	}
	
	@Override
	public void mutateGenome(IHybridSetRepresentation<G,H> childGenome,IHybridSetRepresentationFactory<G,H> solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int N  =computeNumberOfGenesToModify(0,randomNumberGenerator);
		 
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
		if(childGenome.getNumberOfElements()>0){
			int tries = 0;
			
			G element = childGenome.getRandomElement(randomNumberGenerator);
			H listValue = childGenome.getListValue(element);
			
			childGenome.removeElement(element);
			G newElement;
			
			do {
				tries++;
				newElement = solutionFactory.generateSetValue();
			} while(childGenome.containsElement(newElement) && tries<MAXTRIES);
			
			if (tries < MAXTRIES) 
				childGenome.addElement(newElement,listValue);
			else 
				childGenome.addElement(element,listValue);
		}
	}

	@Override
	public IReproductionOperator<IHybridSetRepresentation<G,H>, IHybridSetRepresentationFactory<G,H>> deepCopy() throws Exception {
		return new HybridSetRandomSetMutation<G,H>(numberGenesToChange);
	}
}
