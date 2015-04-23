package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.generic;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.AbstractMutationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;

/**
 * Created by ptiago on 18-11-2014.
 */
public class NewIndividualMutation<G extends IRepresentation,F extends ISolutionFactory<G>> extends AbstractMutationOperator<G,F>  {


	private static final long	serialVersionUID	= -7323315385738141375L;

	@Override
    protected void mutateGenome(G childGenome, F solutionFactory, IRandomNumberGenerator randomNumberGenerator) {
        ISolution<G> solution =  solutionFactory.generateSolution(randomNumberGenerator);
        G representation = solution.getRepresentation();
        solutionFactory.replaceGenome(childGenome,representation);
    }

    @Override
    public IReproductionOperator<G, F> deepCopy() throws Exception {
        return new NewIndividualMutation<>();
    }
}
