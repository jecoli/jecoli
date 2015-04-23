package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.local;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.ILocalOptimizationOperator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.IOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;

public class LocalOptConfiguration<T extends IRepresentation> extends AbstractConfiguration<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811586526281070668L;

	/** The solution factory. */
	protected ISolutionFactory<T> solutionFactory;

	/** The jecoliunittest.operators.operator container. */
	protected IOperatorContainer<ILocalOptimizationOperator> localOperatorContainer;
	

	/**
	 * Gets the solution factory.
	 * 
	 * @return the solution factory
	 */
	public ISolutionFactory<T> getSolutionFactory() {
		return solutionFactory;
	}

	/**
	 * Sets the solution factory.
	 * 
	 * @param solutionFactory the new solution factory
	 */
	public void setSolutionFactory(ISolutionFactory<T> solutionFactory){
		this.solutionFactory = solutionFactory;
	}

	public void verifyConfiguration() throws InvalidConfigurationException{
		
		if(terminationCriteria == null)
			throw new InvalidConfigurationException("Termination Criteria is null") ;

		if(evaluationFunction == null)
			throw new InvalidConfigurationException("Evaluation Function is null") ;

		if(solutionFactory == null)
			throw new InvalidConfigurationException("Solution Factory is null") ;

	}

	@Override
	public void setStatisticsConfiguration(StatisticsConfiguration statisticsConfiguration) {
		this.statisticsConfiguration = statisticsConfiguration;
	}

	/**
	 * Sets the mutation jecoliunittest.operators.operator container.
	 * 
	 * @param mutationOperatorContainer the new mutation jecoliunittest.operators.operator container
	 */
	public void setLocalOperatorContainer(IOperatorContainer<ILocalOptimizationOperator> localOperatorContainer) {
		this.localOperatorContainer = localOperatorContainer;
	}

	@Override
	public LocalOptConfiguration<T> deepCopy() throws Exception{
		LocalOptConfiguration<T> configurationCopy = new LocalOptConfiguration<T>();
		configurationCopy.setUID(new String(UID));
		
		configurationCopy.setProblemBaseDirectory(new String(problemBaseDirectory));
		configurationCopy.setAlgorithmStateFile(new String(saveAlgorithmStateFile));
		configurationCopy.setSaveAlgorithmStateIterationInterval(new Integer(saveAlgorithmStateIterationInterval));
		configurationCopy.setSaveAlgorithmStateDirectoryPath(new String(saveAlgorithmStateDirectoryPath));
		
		List<IAlgorithmResultWriter<T>> algorithmResultWriterListCopy = new ArrayList<IAlgorithmResultWriter<T>>();
		for(IAlgorithmResultWriter<T> algorithmResultWriter:algorithmResultWriterList)
			algorithmResultWriterListCopy.add(algorithmResultWriter.deepCopy());
		
		configurationCopy.setAlgorithmResultWriterList(algorithmResultWriterListCopy);
		return configurationCopy;
	}


}
