package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.decoder.ISolutionDecoder;

public class ArchiveManagerBestSolutions<E extends Comparable<E>, T extends IElementsRepresentation<E>> extends ArchiveManager<E, T> {

	public static final String STEP_HEADER = "eval";
	public static final String FITNESS_HEADER = "fit";
	private Map<Integer,ISolution<T>> bestMappings;
	private List<Integer> index;
	private boolean maximization = true;
	private ISolution<T> globalBest;
	private double globalBestFit;
	private int numSteps = 0;

	public ArchiveManagerBestSolutions(IAlgorithm<T> alg, InsertionStrategy insertionEventType, InsertionStrategy insertionFilter, ProcessingStrategy processingStrategy,boolean isMaximization){
		super(alg,insertionEventType,insertionFilter,processingStrategy);		
		bestMappings = new HashMap<Integer, ISolution<T>>();
		index = new ArrayList<Integer>();
		this.maximization  = isMaximization;
		globalBestFit = (isMaximization) ? Double.MIN_VALUE : Double.MAX_VALUE;
	}
	

	public void processSolution(ISolution<T> solution){

		switch(insertionFilter){
		
			case ADD_SMART_KEEP_BEST:{
				addSmartKeepBest(solution);
			} break;
			
			default: super.processSolution(solution);
		}
	}
	
	public void addSmartKeepBest(ISolution<T> solution){
		super.addSmart(solution);
		
		double fit = solution.getScalarFitnessValue();
		
		boolean isBetter = (maximization) ? (fit > globalBestFit) : (fit < globalBestFit);
		
		if(isBetter){
//			System.out.println("BETTER: global="+globalBestFit+" | fit="+fit);
			globalBestFit = fit;
			globalBest = solution;
			bestMappings.put(numSteps, globalBest);
			index.add(numSteps);
//			System.out.println("Added solution ["+numSteps+"] with fitness="+globalBestFit);
		}
		
		numSteps++;
	}

	/**
	 * @return the maximization
	 */
	public boolean isMaximization() {
		return maximization;
	}

	/**
	 * @param maximization the maximization to set
	 */
	public void setMaximization(boolean maximization) {
		this.maximization = maximization;
	}

	/**
	 * @return the bestMappings
	 */
	public Map<Integer, ISolution<T>> getBestMappings() {
		return bestMappings;
	}

	/**
	 * @return the globalBest
	 */
	public ISolution<T> getGlobalBest() {
		return globalBest;
	}

	/**
	 * @return the globalBestFit
	 */
	public double getGlobalBestFit() {
		return globalBestFit;
	}

	/**
	 * @return the numSteps
	 */
	public int getNumSteps() {
		return numSteps;
	}
	
	public void writeBestSolutionsToFile(String file, String delimiter, boolean includeHeader, ISolutionDecoder<IRepresentation,Object> decoder) throws Exception{
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		if(includeHeader){	
			bw.append(STEP_HEADER+delimiter+FITNESS_HEADER);
			bw.newLine();
		}
				
		for(int i=0; i<index.size(); i++){
			ISolution<?> sol = bestMappings.get(index.get(i));
//			String s = (sol==null) ? "null" : sol.getScalarFitnessValue().toString();					
//			System.out.println("saving "+i+s);
			Double selectionVal = sol.getScalarFitnessValue();
			String line = index.get(i)+delimiter+selectionVal;
			if(decoder!=null){
				Object decodedSolution = decoder.decode(sol.getRepresentation());
				line+=delimiter+decodedSolution.toString();
			}
			bw.append(line);
			bw.newLine();
		}
		
		bw.flush(); fw.flush();
		bw.close(); fw.close();
	
	}
	
	public void writeBestSolutionsToFile(String file, String delimiter, boolean includeHeader) throws Exception{
		this.writeBestSolutionsToFile(file, delimiter, includeHeader, null);
	
	}
}
