package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components;

import java.util.ArrayList;
import java.util.List;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmStateEvent;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStateListener;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.EvaluationFunctionEvent;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunctionListener;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IElementsRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.comparator.SolutionPureFitnessComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.comparator.RepresentationComparator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting.IPlotter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.sorting.ISolutionSorter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.sorting.ISortingFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.sorting.SolutionRepresentationSorter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.trimming.ITrimmingFunction;

public class ArchiveManager<E extends Comparable<E>, T extends IElementsRepresentation<E>> implements IAlgorithmStateListener, IEvaluationFunctionListener<T> {
	
	protected int							MAXIMUM_ARCHIVE_SIZE	= 200;
	protected double						FITNESS_THRESHOLD		= 0.000002;
	
	protected InsertionStrategy				insertionEventType;
	protected InsertionStrategy				insertionFilter;
	protected ProcessingStrategy			processingStrategy;
	
	protected IAlgorithm<T>					algorithm;
	protected int							maxArchiveSize;
	private ISolutionSet<T>					archive;
	
	protected List<ITrimmingFunction<T>>	trimmers;
	
	protected IPlotter<T>					plotter					= null;
	
	protected ISortingFunction<T>			sorter;
	
	protected ISolutionSorter<T>			solutionSorter;
	
	public ArchiveManager(
		IAlgorithm<T> alg) {
		this(alg, InsertionStrategy.ADD_ON_SINGLE_EVALUATION_FUNCTION_EVENT, InsertionStrategy.ADD_ALL, ProcessingStrategy.NO_PROCESSING);
	}
	
	public ArchiveManager(
		IAlgorithm<T> alg,
		InsertionStrategy insertionEventType,
		InsertionStrategy insertionFilter,
		ProcessingStrategy processingStrategy) {
		this.archive = new SolutionSet<T>(new SolutionPureFitnessComparator<ISolution<T>>(false));
		this.algorithm = alg;
		alg.addAlgorithmStateListener(this);
		alg.getConfiguration().getEvaluationFunction().addEvaluationFunctionListener(this);
		this.insertionEventType = insertionEventType;
		this.insertionFilter = insertionFilter;
		this.processingStrategy = processingStrategy;
	}
	
	@Override
	public synchronized void processAlgorithmStateEvent(AlgorithmStateEvent event) {
		
		String id = event.getId();
		
		switch (processingStrategy) {
		
			case NO_PROCESSING:
				break;
			
			case PROCESS_ARCHIVE_ON_ANY_STATE_EVENT: {
				processArchive();
			}
				break;
			
			case PROCESS_ARCHIVE_ON_ITERATION_INCREMENT: {
				if (id.equals(AbstractAlgorithm.ITERATION_INCREMENT_EVENT))
					processArchive();
			}
				break;
			
			default:
				break;
		}
		
	}
	
	@Override
	public synchronized void processEvaluationFunctionEvent(EvaluationFunctionEvent<T> event) {
		String id = event.getId();
		
		switch (insertionEventType) {
		
			case ADD_ON_SINGLE_EVALUATION_FUNCTION_EVENT: {
				if (id.equals(EvaluationFunctionEvent.SINGLE_SOLUTION_EVALUATION_EVENT)) {
					ISolution<T> solution = event.getSolution();
					processSolution(solution);
				}
			}
				break;
			
			case ADD_ON_SOLUTIONSET_EVALUATION_FUNCTION_EVENT: {
				if (id.equals(EvaluationFunctionEvent.SOLUTIONSET_EVALUATION_EVENT)) {
					ISolutionSet<T> solutionSet = event.getSolutionSet();
					processSolutionSet(solutionSet);
				}
			}
			
			default:
				break;
		}
		
		switch (processingStrategy) {
		
			case NO_PROCESSING:
				break;
			
			case PROCESS_ARCHIVE_ON_ANY_EVALUATION_FUNCTION_EVENT: {
				processArchive();
			}
				break;
			
			case PROCESS_ARCHIVE_ON_SINGLE_EVALUATION_FUNCTION_EVENT: {
				if (id.equals(EvaluationFunctionEvent.SINGLE_SOLUTION_EVALUATION_EVENT))
					processArchive();
			}
				break;
			
			case PROCESS_ARCHIVE_ON_SOLUTIONSET_EVALUATION_FUNCTION_EVENT: {
				if (id.equals(EvaluationFunctionEvent.SOLUTIONSET_EVALUATION_EVENT))
					processArchive();
			}
				break;
			
			default:
				break;
		
		}
		
	}
	
	public synchronized void processSolution(ISolution<T> solution) {
		
		switch (insertionFilter) {
			case ADD_ALL: {
				addSolution(solution);
			}
				break;
			
			case ADD_ALL_ORDERED: {
				// TODO: addOrdered(solution);
			}
				break;
			
			case ADD_NO_REPEAT: {
				addNoRepeat(solution);
			}
				break;
			
			case ADD_NO_REPEAT_ORDERED: {
				// TODO: addNoRepeatOrdered(solution);
			}
				break;
			
			case ADD_SMART: {
				addSmart(solution);
			}
				break;
			
			default:
				break;
		}
	}
	
	public synchronized void processSolutionSet(ISolutionSet<T> solutionSet) {
		for (ISolution<T> solution : solutionSet.getListOfSolutions())
			processSolution(solution);
	}
	
	public synchronized void processArchive() {
		if (trimmers != null)
			for (ITrimmingFunction<T> t : trimmers)
				archive = t.trimm(archive);
		
		if (getPlotter() != null) {
			getPlotter().plot(archive);
		}
		
	}
	
	public synchronized void sort() {
		if (sorter != null)
			sorter.sort(archive);
	}
	
	public synchronized void sortSolution(ISolution<T> solution) {
		if (solutionSorter == null)
			solutionSorter = new SolutionRepresentationSorter<T>();
		solutionSorter.sort(solution);
	}
	
	/**
	 * Assumes solution is sorted and solutionset is ordered
	 * 
	 * @param solution
	 * @return
	 */
	public synchronized int findSolution(ISolution<T> solution) {
		int c = -1;
		int i = 0;
		
		RepresentationComparator<T> comp = new RepresentationComparator<T>();
		
		while (c < 0 && i < archive.getNumberOfSolutions()) {
			ISolution<T> sol = archive.getSolution(i);
			c = comp.compare(solution.getRepresentation(), sol.getRepresentation());
			
			if (c == 0)
				return i;
			else if (c < 0)
				i++;
			else
				return -1;
		}
		
		return -1;
	}
	
	public synchronized void printArchive() {
		for (ISolution<T> sol : archive.getListOfSolutions())
			System.out.println(sol.getRepresentation().stringRepresentation());
	}
	
	public synchronized void addSolution(ISolution<T> solution) {
		if (archive == null)
			archive = new SolutionSet<T>();
		
		archive.add(solution);
	}
	
	public synchronized void addSolution(int index, ISolution<T> solution) {
		if (archive == null)
			archive = new SolutionSet<T>();
		
		archive.add(index, solution);
	}
	
	public synchronized void addSmart(ISolution<T> solution) {
		if (archive == null)
			archive = new SolutionSet<T>();
		
		if (!checkIfSolutionExists(solution))
			archive.add(solution);
	}
	
	/**
	 * Adds a solution respecting the order of its representation
	 * 
	 * @param solution
	 *            the solution to be added
	 * 
	 * @return the index where the solution was inserted into the archive
	 */
	public synchronized int addOrdered(ISolution<T> solution) {
		if (archive == null)
			archive = new SolutionSet<T>();
		
		sortSolution(solution);
		
		if (findSolution(solution) >= 0) {
			return -1;
		}
		
		if (archive.getNumberOfSolutions() == 0) {
			archive.getListOfSolutions().add(0, solution);
			return 0;
		} else {
			for (int i = 0; i < archive.getNumberOfSolutions(); i++) {
				ISolution<T> sol = archive.getSolution(i);
				int sizeLocal = sol.getRepresentation().getNumberOfElements();
				int minSize = Math.min(sizeLocal, solution.getRepresentation().getNumberOfElements());
				for (int j = 0; j < minSize; j++) {
					E kLocal = sol.getRepresentation().getElementAt(j);
					E kCandidate = solution.getRepresentation().getElementAt(j);
					int comp = kLocal.compareTo(kCandidate);
					if (comp == 0) {
						if (j == minSize - 1 && sizeLocal > minSize) {
							addSolution(i, solution);
							return i;
						}
						continue;
					} else if (comp < 0)
						break;
					else {
						addSolution(i, solution);
						return i;
					}
				}
			}
			archive.add(solution);
			
			return archive.getNumberOfSolutions();
		}
	}
	
	public synchronized boolean checkIfSolutionExists(ISolution<T> solution) {
		
		if (findSolution(solution) >= 0) {
			return true;
		}
		
		Double[] fitsSol = solution.getFitnessValuesArray();
		
		List<ISolution<T>> ss = findSubsetSolutions(solution);
		for (int k = 0; k < ss.size(); k++) {
			
			ISolution<T> sol = ss.get(k);
			Double[] fitsSub = sol.getFitnessValuesArray();
			boolean allBetter = true;
			for (int j = 0; j < fitsSol.length; j++)
				if (fitsSol[j] - fitsSub[j] > FITNESS_THRESHOLD)
					allBetter = false;
			if (allBetter) {
				return true;
			}
		}
		
		return false;
	}
	
	public synchronized List<ISolution<T>> findSubsetSolutions(ISolution<T> targetSolution) {
		List<ISolution<T>> list = new ArrayList<ISolution<T>>();
		
		for (int i = 0; i < archive.getNumberOfSolutions(); i++) {
			ISolution<T> sol = archive.getSolution(i);
			T solRep = sol.getRepresentation();
			T targetRep = targetSolution.getRepresentation();
			
			if (targetRep.containsRepresentation(solRep))
				list.add(sol);
		}
		
		return list;
	}
	
	public synchronized List<ISolution<T>> findSupersetSolutions(ISolution<T> targetSolution) {
		List<ISolution<T>> list = new ArrayList<ISolution<T>>();
		
		for (int i = 0; i < archive.getNumberOfSolutions(); i++) {
			ISolution<T> sol = archive.getSolution(i);
			T solRep = sol.getRepresentation();
			T targetRep = targetSolution.getRepresentation();
			
			if (targetRep.isContainedInRepresentation(solRep))
				list.add(sol);
		}
		
		return list;
	}
	
	public synchronized void addNoRepeat(ISolution<T> solution) {
		if (archive == null)
			archive = new SolutionSet<T>();
		
		if (!archive.containsGenomeOnly(solution))
			archive.add(solution);
		
		// System.out.println("=== SOLUTION ===");
		// ArrayList<ISolution<T>> solist = new ArrayList<ISolution<T>>();
		// solist.add(solution);
		// print(solist,"\t");
		// // printArchive();
		// System.out.println("\t SUBSET:");
		// print(this.findSubsetSolutions(solution),"\t\t");
		// System.out.println("\t SUPERSET:");
		// print(this.findSupersetSolutions(solution),"\t\t");
		//
		// System.out.println("=== END ===\n");
	}
	
	public synchronized void print(List<ISolution<T>> sols, String t) {
		for (ISolution<T> sol : sols)
			System.out.println(t + sol.getRepresentation().stringRepresentation());
	}
	
	public List<ITrimmingFunction<T>> getTrimmers() {
		return trimmers;
	}
	
	public synchronized void addTrimmingFunction(ITrimmingFunction<T> trimmingFunction) throws IllegalArgumentException {
		if (trimmers == null)
			trimmers = new ArrayList<ITrimmingFunction<T>>();
		
		if (!trimmers.contains(trimmingFunction))
			trimmers.add(trimmingFunction);
		else
			throw new IllegalArgumentException("Trimming function [" + trimmingFunction.getClass().getCanonicalName() + "] already exists");
		
	}
	
	public synchronized IPlotter<T> getPlotter() {
		return plotter;
	}
	
	public synchronized void setPlotter(IPlotter<T> plotter) {
		this.plotter = plotter;
	}
	
	public synchronized ISolutionSet<T> getArchive() {
		return archive;
	}
	
	/**
	 * @return the fITNESS_THRESHOLD
	 */
	public double getFITNESS_THRESHOLD() {
		return FITNESS_THRESHOLD;
	}
	
	/**
	 * @param fitnessThreshold
	 *            the FITNESS_THRESHOLD to set
	 */
	public void setFitnessThreshold(double fitnessThreshold) {
		FITNESS_THRESHOLD = fitnessThreshold;
	}
	
	/**
	 * @return the MAXIMUM_ARCHIVE_SIZE
	 */
	public int getMaximumArchiveSize() {
		return MAXIMUM_ARCHIVE_SIZE;
	}
	
	/**
	 * @param maximumArchiveSize
	 *            the mAXIMUM_ARCHIVE_SIZE to set
	 */
	public void setMaximumArchiveSize(int maximumArchiveSize) {
		MAXIMUM_ARCHIVE_SIZE = maximumArchiveSize;
	}
	
}
