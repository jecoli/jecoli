package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation.relative;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation.HybridSetRandomIntegerMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;

/**
 * Created by ptiago on 22-01-2015.
 */
public class HybridSetRelativeRandomIntegerMutation<G,H> extends HybridSetRandomIntegerMutation<G,H> {
    protected final double minPercentageOfGenesToModify;
    protected final double maxPercentageOfGenesToModify;

    public HybridSetRelativeRandomIntegerMutation(){
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = 0.5;
    }

    public HybridSetRelativeRandomIntegerMutation(double percentageOfGenesToChange){
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = percentageOfGenesToChange;
    }

    public HybridSetRelativeRandomIntegerMutation(double minPercentageOfGenesToModify,double maxPercentageOfGenesToModify) {
        this.minPercentageOfGenesToModify = minPercentageOfGenesToModify;
        this.maxPercentageOfGenesToModify = maxPercentageOfGenesToModify;
    }

    @Override
    public int computeNumberOfGenesToModify(int childNumberOfGenes,IRandomNumberGenerator randomNumberGenerator) {
        double percentageOfGenesToModify = randomNumberGenerator.nextDouble()*(maxPercentageOfGenesToModify-minPercentageOfGenesToModify)+minPercentageOfGenesToModify;
        int numberGenesToChange = (int)(percentageOfGenesToModify*childNumberOfGenes);
        return numberGenesToChange;
    }
}
