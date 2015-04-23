package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation.relative;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.hybridset.mutation.HybridSetRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;

/**
 * Created by ptiago on 22-01-2015.
 */
public class HybridSetRelativeRandomMutation<G,H> extends HybridSetRandomMutation<G,H> {
    protected final double minPercentageOfGenesToModify;
    protected final double maxPercentageOfGenesToModify;

    public HybridSetRelativeRandomMutation(){
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = 0.5;
    }

    public HybridSetRelativeRandomMutation(double percentageOfGenesToChange){
        this.minPercentageOfGenesToModify = 0;
        this.maxPercentageOfGenesToModify = percentageOfGenesToChange;
    }

    public HybridSetRelativeRandomMutation(double minPercentageOfGenesToModify,double maxPercentageOfGenesToModify) {
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
