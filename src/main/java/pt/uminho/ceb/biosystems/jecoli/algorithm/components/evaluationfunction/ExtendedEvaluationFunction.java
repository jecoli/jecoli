package pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public class ExtendedEvaluationFunction<S extends IRepresentation,E> extends AbstractEvaluationFunction<S> implements IExtendedEvaluationFunction<S, E>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7716969850564959703L;
	
	protected IRepresentationDecoder<S,E> decoder;
	protected IFitnessFunctionEvaluator<E> fitnessFunctionEvaluator;
	
	public ExtendedEvaluationFunction(boolean isMaximization,IRepresentationDecoder<S,E> decoder,IFitnessFunctionEvaluator<E> fitnessFunctionEvaluator) {
		super(isMaximization);
		this.decoder = decoder;
		this.fitnessFunctionEvaluator = fitnessFunctionEvaluator;
	}

	@Override
	public void verifyInputData()throws InvalidEvaluationFunctionInputDataException {
	}

	@Override
	public IEvaluationFunction<S> deepCopy() throws Exception {
		return null;
	}

	@Override
	public double evaluate(S solutionRepresentation) throws Exception {
		E data = decoder.decodeSolution(solutionRepresentation);
		return fitnessFunctionEvaluator.evaluate(data);
	}
	
	@Override
	public IRepresentationDecoder<S,E> getDecoder(){
		return decoder;
	}
	
	@Override
	public IFitnessFunctionEvaluator<E> getFitnessFunctionEvaluator(){
		return fitnessFunctionEvaluator;
	}

}
