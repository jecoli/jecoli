package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;

/**
 * Created by ptiago on 27-10-2014.
 */
public class SetRelativeShrinkMutation<E> extends AbstractSetMutationOperator<E> {


    private static final long serialVersionUID = 870642702424643829L;
    /** The number genes to remove. */
    protected double minPercentageOfGenesToModify;
    protected double maxPercentageOfGenesToModify;

    public SetRelativeShrinkMutation() {
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = 0.5;
    }

    public SetRelativeShrinkMutation(double percentageOfGenesToRemove) {
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = percentageOfGenesToRemove;
    }

    public SetRelativeShrinkMutation(double minPercentageOfGenesToModify,double maxPercentageOfGenesToModify) {
        this.minPercentageOfGenesToModify = minPercentageOfGenesToModify;
        this.maxPercentageOfGenesToModify = maxPercentageOfGenesToModify;
    }

    @Override
    protected void mutateGenome (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
    {
        int numberOfGenes = childGenome.getNumberOfElements();
        double percentageOfGenesToModify = randomNumberGenerator.nextDouble()*(maxPercentageOfGenesToModify-minPercentageOfGenesToModify)+minPercentageOfGenesToModify;
        int numberOfGenesToRemove = (int)(percentageOfGenesToModify*numberOfGenes);
        int minSize = solutionFactory.getMinSetSize();

        int N = Math.min(numberOfGenesToRemove,numberOfGenes)+1;

        for(int k=0; k < N; k++)
        {
            if (childGenome.getNumberOfElements() > minSize){
                E element = childGenome.getRandomElement(randomNumberGenerator);
                childGenome.removeElement(element);
            } else return;
        }
    }

    @Override
    public SetRelativeShrinkMutation<E> deepCopy() throws Exception {
        return new SetRelativeShrinkMutation<E>(minPercentageOfGenesToModify,maxPercentageOfGenesToModify);
    }
}
