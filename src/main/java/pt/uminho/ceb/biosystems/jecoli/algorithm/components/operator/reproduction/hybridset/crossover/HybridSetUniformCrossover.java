package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.crossover;

import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.HybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.hybridset.IHybridSetRepresentationFactory;


public class HybridSetUniformCrossover<G,H> extends AbstractHybridSetCrossoverOperator<G,H>{


	private static final long serialVersionUID = -7785155812759195170L;

	@Override
	public void crossoverGenomes(
			IHybridSetRepresentation<G,H> parentGenome1,
			IHybridSetRepresentation<G,H> parentGenome2,
			IHybridSetRepresentation<G,H> childGenome1,
			IHybridSetRepresentation<G,H> childGenome2,
			IHybridSetRepresentationFactory<G,H> solutionFactory,
			IRandomNumberGenerator randomNumberGenerator) {
		
		int maxRepresentationSize = solutionFactory.getMaxSetSize();
		int minRepresentationSize = solutionFactory.getMinSetSize();
		
		// Shared elements
		TreeSet<G> sharedElements = new TreeSet<G>(parentGenome1.getGenome());
		sharedElements.retainAll(parentGenome2.getGenome());
		
		for(G element : sharedElements)
		{	
			double prob = 0.5;
		
			if (randomNumberGenerator.nextDouble() <= prob)
			{
				childGenome1.addElement(element, parentGenome1.getListValue(element));
				childGenome2.addElement(element, parentGenome2.getListValue(element));
			}
			else
			{
				childGenome1.addElement(element, parentGenome2.getListValue(element));
				childGenome2.addElement(element, parentGenome1.getListValue(element));
			}
			
			
		}
		
		// Not shared elements
		IHybridSetRepresentation<G,H> otherElements = new HybridSetRepresentation<G,H>();
		otherElements.addAllElements(parentGenome1);
		otherElements.addAllElements(parentGenome2);
		otherElements.removeElements(sharedElements);
		
		int size = otherElements.getNumberOfElements();
		for(int i=0;i<size;i++)
		{
			double prob = 0.5;
			
			if(childGenome1.getNumberOfElements() >= maxRepresentationSize || childGenome2.getNumberOfElements() < minRepresentationSize ) 
				prob = 0;
			else if(childGenome2.getNumberOfElements() >= maxRepresentationSize || childGenome1.getNumberOfElements() < minRepresentationSize) 
				prob = 1;
		
			IHybridSetRepresentation<G,H> element = otherElements.getElementAtPosition(i);
			if (randomNumberGenerator.nextDouble() <= prob)
			{
			
				if (!childGenome1.containsElement(element.getElementAt(0))) 
					childGenome1.addElement(element,0);
			}
			else
			{
				
				if (!childGenome2.containsElement(element.getElementAt(0))) 
					childGenome2.addElement(element,0);
			}
		}
	}
	
	@Override
	public IReproductionOperator<IHybridSetRepresentation<G,H>, IHybridSetRepresentationFactory<G,H>> deepCopy()throws Exception {
		return new HybridSetUniformCrossover<G,H>();
	}
}
