package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.controller;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public interface IAlgorithmController<T extends IRepresentation, C extends IConfiguration<T>> {
	
	C processInitialAlgorithmState(AlgorithmState<T> algorithmState, C configuration);
	
	C processAlgorithmState(AlgorithmState<T> algorithmState, C configuration);
	
}