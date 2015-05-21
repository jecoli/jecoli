package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components;

public enum ProcessingStrategy {
	
	NO_PROCESSING,
	
	//state
	PROCESS_ARCHIVE_ON_ANY_STATE_EVENT,
	PROCESS_ARCHIVE_ON_ITERATION_INCREMENT,
	
	//evaluation
	PROCESS_ARCHIVE_ON_ANY_EVALUATION_FUNCTION_EVENT,
	PROCESS_ARCHIVE_ON_SINGLE_EVALUATION_FUNCTION_EVENT,
	PROCESS_ARCHIVE_ON_SOLUTIONSET_EVALUATION_FUNCTION_EVENT

}