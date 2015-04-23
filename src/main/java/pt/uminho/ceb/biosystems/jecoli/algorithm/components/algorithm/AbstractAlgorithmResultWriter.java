package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public abstract class AbstractAlgorithmResultWriter<T extends IRepresentation,S extends IConfiguration<T>> implements IAlgorithmResultWriter<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected S configuration;
	
	public AbstractAlgorithmResultWriter(S configuration){
		this.configuration = configuration;
	}
}
