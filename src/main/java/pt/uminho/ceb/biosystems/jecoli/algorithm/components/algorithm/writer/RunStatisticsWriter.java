package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer;

import java.io.BufferedWriter;
import java.io.IOException;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.IAlgorithmIterationStatisticCell;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.ObjectiveStatisticCell;

public class RunStatisticsWriter<T extends IRepresentation> extends AbstractSingleFileAlgorithmResultWriter<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2011428414014797047L;

	public RunStatisticsWriter(String baseDirectory){
		super(baseDirectory,"runStatistics");
	}
	
	public RunStatisticsWriter(String baseDirectory,String filename){
		super(baseDirectory,filename);
	}

	@Override
	protected void writeFile(BufferedWriter bufferedWriter,IAlgorithmResult<T> algorithmResult) throws IOException {
		IAlgorithmStatistics<T> algorithmStatistics = algorithmResult.getAlgorithmStatistics();
		bufferedWriter.write("NumberOfObjectives: " + algorithmStatistics.getNumberOfObjectives()+"\n");
		bufferedWriter.write("MaxScalarFitnessValue: "+algorithmStatistics.getRunMaxScalarFitnessValue()+"\n");
		bufferedWriter.write("MinScalarFitnessValue: "+algorithmStatistics.getRunMinScalarFitnessValue()+"\n");
		bufferedWriter.write("MinScalarFitnessValue: "+algorithmStatistics.getRunMeanScalarFitnessValue()+"\n");
		
		
		for(int i = 0; i < algorithmStatistics.getNumberOfObjectives();i++){
			bufferedWriter.write("MaxObjective"+i+"FitnessValue: "+algorithmStatistics.getRunObjectiveMaxFitnessValue(i)+"\n");
			bufferedWriter.write("MinObjective"+i+"FitnessValue: "+algorithmStatistics.getRunObjectiveMinFitnessValue(i)+"\n");
			bufferedWriter.write("MeanObjective"+i+"FitnessValue: "+algorithmStatistics.getRunObjectiveMeanFitnessValue(i)+"\n");
		}
		
		writeIterationStatistics(bufferedWriter,algorithmStatistics);
	}

	protected void writeIterationStatistics(BufferedWriter bufferedWriter,IAlgorithmStatistics<T> algorithmStatistics) throws IOException {
		bufferedWriter.write("\n");
		String separator = "\t";
		String titleString = "IterationNumber"+separator+"MAX"+separator+"MIN"+separator+"MEAN"+separator+"StdDev"+separator+"FunctionEvaluations"+separator+"AccumulatedFunctionEvaluations"
		 +separator+"IterationTime"+separator+"AccumulatedIterationTime\n";
		bufferedWriter.write(titleString);
		double accumaletedTime = 0;
        int totalNumberOfFunctionEvaluations = 0;
		for(int i = 0; i <algorithmStatistics.getNumberOfIterations();i++){
			IAlgorithmIterationStatisticCell<T> iterationCell = algorithmStatistics.getAlgorithmIterationStatisticCell(i);
			ObjectiveStatisticCell scalarFitnessCell = iterationCell.getScalarFitnessCell();
			bufferedWriter.write(iterationCell.getIterationNumber()+separator);
			bufferedWriter.write(scalarFitnessCell.getMaxValue()+separator);
			bufferedWriter.write(scalarFitnessCell.getMinValue()+separator);
			bufferedWriter.write(scalarFitnessCell.getMean()+separator);
			bufferedWriter.write(scalarFitnessCell.getStdDev()+separator);
            totalNumberOfFunctionEvaluations += scalarFitnessCell.getStdDev()+iterationCell.getNumberOfFunctionEvaluations();
            bufferedWriter.write(totalNumberOfFunctionEvaluations+separator);
			double iterationTime = iterationCell.getExecutionTime();
			bufferedWriter.write(iterationTime+separator);
			accumaletedTime += iterationTime;
			bufferedWriter.write(accumaletedTime+"\n");
		}
	}

	@Override
	public IAlgorithmResultWriter<T> deepCopy() {
		return new RunStatisticsWriter<T>(new String(baseDirectory),new String(filename));
	}

}
