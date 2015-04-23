package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

public abstract class AbstractSingleFileAlgorithmResultWriter<T extends IRepresentation> implements IAlgorithmResultWriter<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1033352308169761669L;
	protected String baseDirectory;
	protected String filename;
	
	public AbstractSingleFileAlgorithmResultWriter(String baseDirectory,String filename) {
		this.baseDirectory = baseDirectory;
		this.filename = filename;
	}

	@Override
	public void writeResult(IAlgorithmResult<T> algorithmResult) throws IOException {
		writeResult(algorithmResult,"");
	}

	@Override
	public void writeResult(IAlgorithmResult<T> algorithmResult,String directoryUID) throws IOException {
		File baseDirectoryDescriptor = new File(baseDirectory+directoryUID);
		baseDirectoryDescriptor.mkdirs(); // cria dir se nao existir
		String filePath = baseDirectoryDescriptor.getAbsolutePath()+"/"+filename;
		File resultFile = new File(filePath);
		FileWriter fileWriter = new FileWriter(resultFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		writeFile(bufferedWriter,algorithmResult);
		bufferedWriter.close();
	}
	
	protected abstract void writeFile(BufferedWriter bufferedWriter,IAlgorithmResult<T> algorithmResult) throws IOException;
	
}
