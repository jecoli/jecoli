package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.dualset;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentationFactory;

/**
 * Created by ptiago on 15-01-2015.
 */
public class DualSetRandomMutationWrapper extends AbstractDualSetMutationOperator {


    @Override
    protected void mutateGenome(DualSetRepresentation childGenome, DualSetRepresentationFactory solutionFactory, IRandomNumberGenerator randomNumberGenerator) {

    }

    @Override
    public IReproductionOperator<DualSetRepresentation, DualSetRepresentationFactory> deepCopy() throws Exception {
        return null;
    }
}
