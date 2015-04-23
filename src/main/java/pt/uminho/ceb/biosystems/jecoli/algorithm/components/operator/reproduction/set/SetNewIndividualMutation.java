package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.set;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;

/**
 * Created by ptiago on 10-10-2014.
 */
public class SetNewIndividualMutation<E> extends AbstractSetMutationOperator<E> {


    public SetNewIndividualMutation(){}

    /* (non-Javadoc)
     * @see operators.reproduction.sets.AbstractSetMutationOperator#mutateGenome(core.representation.sets.SetRepresentation)
     */
    @Override
    protected void mutateGenome (ISetRepresentation<E> childGenome,ISetRepresentationFactory<E> solutionFactory,IRandomNumberGenerator randomNumberGenerator)
    {
        ISolution<ISetRepresentation<E>> solution =  solutionFactory.generateSolution(randomNumberGenerator);
        ISetRepresentation<E> representation = solution.getRepresentation();
        childGenome.getGenome().clear();
        childGenome.getGenome().addAll(representation.getGenome());
    }


       @Override
    public SetNewIndividualMutation<E> deepCopy() throws Exception {
        return new SetNewIndividualMutation<E>();
    }
}
