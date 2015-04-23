package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;

public class ReproductionOperatorContainerCell<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractOperatorContainerCell<IReproductionOperator<T,S>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -225850204933329954L;

	public ReproductionOperatorContainerCell(double probability,IReproductionOperator<T,S> operator) throws Exception {
		super(probability, operator);
	}

	@Override
	public ReproductionOperatorContainerCell<T,S> deepCopy() throws Exception {
		IReproductionOperator<T,S> operatorCopy = operator.deepCopy();
		return new ReproductionOperatorContainerCell<T,S>(probability, operatorCopy);
	}

}
