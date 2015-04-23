package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;

/**
 * Created by ptiago on 27-10-2014.
 */
public class SetRelativeGrowMutation<E> extends AbstractSetMutationOperator<E> {

    /**
     *
     */
    private static final long serialVersionUID = -2501862357841781220L;

    /** The MAXTRIES. */
    private static int MAXTRIES = 20;

    /** The number genes to add. */
    protected double minPercentageOfGenesToModify;
    protected double maxPercentageOfGenesToModify;


    public SetRelativeGrowMutation() {
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = 0.5;
    }

    public SetRelativeGrowMutation(double percentageOfGenesToAdd) {
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = percentageOfGenesToAdd;
    }

    public SetRelativeGrowMutation(double minPercentageOfGenesToModify,double maxPercentageOfGenesToModify) {
        this.minPercentageOfGenesToModify = minPercentageOfGenesToModify;
        this.maxPercentageOfGenesToModify = maxPercentageOfGenesToModify;
    }

    @Override
    protected void mutateGenome(ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
    {

        int numberOfGenes = childGenome.getNumberOfElements();
        double percentageOfGenesToModify = randomNumberGenerator.nextDouble()*(maxPercentageOfGenesToModify-minPercentageOfGenesToModify)+minPercentageOfGenesToModify;
        int numberGenesToAdd = (int)(percentageOfGenesToModify*numberOfGenes);
        if(numberGenesToAdd == 0)
            numberGenesToAdd = 1;
        int N = randomNumberGenerator.nextInt(numberGenesToAdd)+1;

        int maxSetSize = solutionFactory.getMaxSetSize();
        for(int k=0; k < N; k++){
            if(childGenome.getNumberOfElements() < maxSetSize)
                addNewElement(childGenome,solutionFactory,randomNumberGenerator);
            else return;
        }
    }

    /**
     * Adds the new element.
     *
     * @param childGenome the child genome
     * @param randomNumberGenerator
     */
    protected void addNewElement(ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator){
        E newElement;
        int tries = 0;
        do {
            tries++;
            newElement = solutionFactory.generateGeneValue(randomNumberGenerator);
        }while(childGenome.containsElement(newElement) && tries < MAXTRIES);

        if (tries < MAXTRIES)
            childGenome.addElement(newElement);

    }

    @Override
    public SetRelativeGrowMutation<E> deepCopy() throws Exception {
        return new SetRelativeGrowMutation<E>(minPercentageOfGenesToModify,maxPercentageOfGenesToModify);
    }
}
