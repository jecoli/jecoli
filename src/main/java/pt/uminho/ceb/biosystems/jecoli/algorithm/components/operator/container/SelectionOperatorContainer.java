package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ISelectionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;


public class SelectionOperatorContainer<T extends IRepresentation> extends AbstractOperatorContainer<ISelectionOperator<T>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8623483269667947629L;


	public SelectionOperatorContainer(){
		operatorContainer = new ArrayList<AbstractOperatorContainerCell<ISelectionOperator<T>>>();
	}
	
	public SelectionOperatorContainer(List<AbstractOperatorContainerCell<ISelectionOperator<T>>> operatorContainerList) {
		operatorContainer = operatorContainerList; 
	}
	
	public void addOperator(double probability, ISelectionOperator<T> operator) throws Exception{
		verifyProbability(probability);
		AbstractOperatorContainerCell<ISelectionOperator<T>> cell = new SelectionOperatorContainerCell<T>(probability, operator);
		operatorContainer.add(cell);
	}

	@Override
	public IOperatorContainer<ISelectionOperator<T>> deepCopy() throws Exception {
		List<AbstractOperatorContainerCell<ISelectionOperator<T>>> operatorContainerList = new ArrayList<AbstractOperatorContainerCell<ISelectionOperator<T>>>();
		
		for(AbstractOperatorContainerCell<ISelectionOperator<T>> cell:operatorContainer){
			AbstractOperatorContainerCell<ISelectionOperator<T>> cellCopy = cell.deepCopy();
			operatorContainerList.add(cellCopy);
		}
		
		return new SelectionOperatorContainer<T>(operatorContainerList);
	}

	
	@Override
	public SelectionOperatorContainer<T> getSubSet(List<Integer> indexes) throws Exception {
		
		if(indexes!= null && indexes.size()>0 && operatorContainer!=null && operatorContainer.size()>0){
			
			SelectionOperatorContainer<T> subsetContainer = new SelectionOperatorContainer<T>();
			
			double acumProbability = 0.0;
			double[] newProbabilities = new double[indexes.size()];		
						
			for(int i = 0; i< indexes.size();i++){
				Integer index = indexes.get(i);
				AbstractOperatorContainerCell<ISelectionOperator<T>> operatorCell = operatorContainer.get(index);
				double prob = operatorCell.getProbability();
				acumProbability += prob;
				newProbabilities[i] = prob;
			}			
			
			for(int i=0; i< indexes.size();i++)
				newProbabilities[i] = newProbabilities[i] / acumProbability;
			
			for(int i = 0; i< indexes.size();i++){
				Integer index = indexes.get(i);
				subsetContainer.addOperator(newProbabilities[i],operatorContainer.get(index).getOperator());
			}
			
			if(subsetContainer.isValid())
				return subsetContainer;
			else
				throw new OperatorContainerException("Probabilities for the new container are not valid");
		}
		else return null;
	}

	

}
