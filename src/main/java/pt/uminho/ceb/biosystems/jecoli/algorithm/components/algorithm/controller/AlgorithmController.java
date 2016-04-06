package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.controller;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

/**
 * Created by ptiago on 16-10-2014.
 */
public class AlgorithmController<T extends IRepresentation, C extends IConfiguration<T>> implements IAlgorithmController<T, C> {
    /* (non-Javadoc)
	 * @see pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.controller.IAlgorithmController#processInitialAlgorithmState(pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState, C)
	 */
    @Override
	public C processInitialAlgorithmState(AlgorithmState<T> algorithmState,C configuration){
        return configuration;
    }

    /* (non-Javadoc)
	 * @see pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.controller.IAlgorithmController#processAlgorithmState(pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState, C)
	 */
    @Override
	public C processAlgorithmState(AlgorithmState<T> algorithmState,C configuration){
        return configuration;
    }
}
