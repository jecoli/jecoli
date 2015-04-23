package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.dualset;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set.SetRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set.SetUniformCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.dualset.DualSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerSetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.SetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.Solution;

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
