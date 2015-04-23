package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public class SelectionOperatorContainerCell <T extends IRepresentation> extends AbstractOperatorContainerCell<ISelectionOperator<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionOperatorContainerCell(double probability,ISelectionOperator<T> operator) throws Exception {
		super(probability, operator);
	}

	@Override
	public SelectionOperatorContainerCell<T> deepCopy()	throws Exception {
		ISelectionOperator<T> operatorCopy = operator.deepCopy();
		return new SelectionOperatorContainerCell<T>(probability, operatorCopy);
	}

}
