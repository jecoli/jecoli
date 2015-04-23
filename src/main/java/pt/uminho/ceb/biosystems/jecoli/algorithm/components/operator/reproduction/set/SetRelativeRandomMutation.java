package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;

/**
 * Created by ptiago on 27-10-2014.
 */
public class SetRelativeRandomMutation<E> extends AbstractSetMutationOperator<E> {

    /**
     *
     */
    private static final long serialVersionUID = -9052759557582323863L;

    /** The number genes to change. */
    protected double minPercentageOfGenesToModify;
    protected double maxPercentageOfGenesToModify;

    /** The MAXTRIES. */
    private static int MAXTRIES = 20;


    public SetRelativeRandomMutation(){
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = 0.5;
    }

    public SetRelativeRandomMutation(double percentageOfGenesToChange){
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = percentageOfGenesToChange;
    }

    public SetRelativeRandomMutation(double minPercentageOfGenesToModify,double maxPercentageOfGenesToModify) {
        this.minPercentageOfGenesToModify = minPercentageOfGenesToModify;
        this.maxPercentageOfGenesToModify = maxPercentageOfGenesToModify;
    }

    /* (non-Javadoc)
     * @see operators.reproduction.sets.AbstractSetMutationOperator#mutateGenome(core.representation.sets.SetRepresentation)
     */
    @Override
    protected void mutateGenome (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
    {
        int numberOfGenes = childGenome.getNumberOfElements();
        double percentageOfGenesToModify = randomNumberGenerator.nextDouble()*(maxPercentageOfGenesToModify-minPercentageOfGenesToModify)+minPercentageOfGenesToModify;
        int numberGenesToChange = (int)(percentageOfGenesToModify*numberOfGenes);
        int N = randomNumberGenerator.nextInt(numberGenesToChange+1);
        for(int k=0; k < N; k++){
            changeElement(childGenome,solutionFactory,randomNumberGenerator);
        }
    }


    protected void changeElement (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
    {
        int tries = 0;
        if(childGenome.getNumberOfElements() >0)
        {
            E element = childGenome.getRandomElement(randomNumberGenerator);
            childGenome.removeElement(element);
            E newElement;
//			int maxElement = solutionFactory.getMaxElement();
            do {
                tries++;
                newElement = solutionFactory.generateGeneValue(randomNumberGenerator);
            } while(childGenome.containsElement(newElement) && tries < MAXTRIES);
            if (tries < MAXTRIES) childGenome.addElement(newElement);
            else childGenome.addElement(element);
        }
    }

    @Override
    public SetRelativeRandomMutation<E> deepCopy() throws Exception {
        return new SetRelativeRandomMutation<E>(minPercentageOfGenesToModify,maxPercentageOfGenesToModify);
    }
}
