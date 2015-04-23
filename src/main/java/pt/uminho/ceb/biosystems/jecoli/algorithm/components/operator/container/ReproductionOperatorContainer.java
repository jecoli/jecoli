package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.IReproductionOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ReproductionOperatorType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;


public class ReproductionOperatorContainer<T extends IRepresentation, S extends ISolutionFactory<T>> extends AbstractOperatorContainer<IReproductionOperator<T,S>> {
	
	
	private static final long serialVersionUID = 1463940432253156420L;

	public ReproductionOperatorContainer(){
		operatorContainer = new ArrayList<AbstractOperatorContainerCell<IReproductionOperator<T,S>>>();
	}
	
	public  ReproductionOperatorContainer(List<AbstractOperatorContainerCell<IReproductionOperator<T,S>>> operatorContainerList){
		operatorContainer = operatorContainerList;
	}

    public int getNumberOfOperators(){return operatorContainer.size();}
    public IReproductionOperator<T,S> getOperator(int operatorIndex){return operatorContainer.get(operatorIndex).getOperator();}
    //####
	public ReproductionOperatorContainer<T,S> deepCopy() throws Exception {
		List<AbstractOperatorContainerCell<IReproductionOperator<T,S>>> operatorContainerList = new ArrayList<AbstractOperatorContainerCell<IReproductionOperator<T,S>>>();
		
		for(AbstractOperatorContainerCell<IReproductionOperator<T,S>> cell:operatorContainer){
			AbstractOperatorContainerCell<IReproductionOperator<T,S>> cellCopy = cell.deepCopy();
			operatorContainerList.add(cellCopy);
		}
		
		return new ReproductionOperatorContainer<T,S>(operatorContainerList);
	}

	@Override
	public void addOperator(double probability,IReproductionOperator<T,S> operator) throws Exception {
		verifyProbability(probability);
		AbstractOperatorContainerCell<IReproductionOperator<T,S>> cell = new ReproductionOperatorContainerCell<T,S>(probability, operator);
		operatorContainer.add(cell);
	}

	@Override
	public ReproductionOperatorContainer<T,S> getSubSet(List<Integer> indexes) throws Exception {
		
		if(indexes!= null && indexes.size()>0 && operatorContainer!=null && operatorContainer.size()>0){
			
			ReproductionOperatorContainer<T,S> subsetContainer = new ReproductionOperatorContainer<T,S>();
			
			double acumProbability = 0.0;
			double[] newProbabilities = new double[indexes.size()];		
						
			for(int i = 0; i< indexes.size();i++){
				Integer index = indexes.get(i);
				AbstractOperatorContainerCell<IReproductionOperator<T,S>> operatorCell = operatorContainer.get(index);
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
	
	public List<Integer> getIndexesForReproductionType(ReproductionOperatorType type){
		List<Integer> indexes = new ArrayList<Integer>();
		
		for(int i=0; i< operatorContainer.size(); i++){
			AbstractOperatorContainerCell<IReproductionOperator<T,S>> cell = operatorContainer.get(i);
			
			if(cell.getOperator().getReproductionType().equals(type))				
					indexes.add(i);
		}	
		
		if(indexes.size()==0)
				indexes = null;
		
		return indexes;
	}
}
