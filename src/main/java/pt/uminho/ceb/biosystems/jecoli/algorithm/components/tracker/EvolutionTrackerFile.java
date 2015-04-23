package pt.uminho.ceb.biosystems.jecoli.algorithm.components.tracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.decoder.ISolutionDecoder;

public class EvolutionTrackerFile<T extends IRepresentation> implements IEvolutionTracker<T> {
	
	public static final String				_FIELD_DELIMITER		= ";";
	public static final String				_INTRA_FIELD_DELIMITER	= ",";
	public ISolutionDecoder<T, String>		_decoder				= null;
	
	public String							_file					= null;
	public BufferedWriter					_writer					= null;
	public boolean							_firstWrite				= true;
	private Map<ISolution<T>, TrackInfo<T>>	_trackInfo				= null;
	private Object							_lock					= new Object();
	
	public EvolutionTrackerFile(){
		_trackInfo = new HashMap<ISolution<T>, TrackInfo<T>>(); 
	}
	
	public EvolutionTrackerFile(String file) throws IOException {
		_file = file;
		_trackInfo = new HashMap<ISolution<T>, TrackInfo<T>>();
		_initialize();
	}
	
	public void _initialize() throws IOException {
		if (_file != null) {
            File file = new File(_file);
            file.createNewFile();
			FileWriter fw = new FileWriter(file);
			_writer = new BufferedWriter(fw);
		}else
			throw new IOException("Output file is undefined.");
	}
	
	@Override
	public void terminate() throws IOException {
		_writer.flush();
		_writer.close();
	}
	
	@Override
	public void setFile(String file) throws IOException{
		_file = file;
		_initialize();
	}
	
	@Override
	public synchronized void flush() throws Exception {
		synchronized (_lock) {			
			for (ISolution<T> solution : _trackInfo.keySet()) {
				
				TrackInfo<T> info = _trackInfo.get(solution);
				int iteration = info._iteration;
				String operator = info._operator;
				List<ISolution<T>> parents = info._parents;
				
				if (_firstWrite)
					_firstWrite = false;
				else
					_writer.newLine();
				
				// iteration
				_writer.append(String.valueOf(iteration) + _FIELD_DELIMITER);
				
				// fitness(es)
				int numObjectives = solution.getNumberOfObjectives();
				if (numObjectives == 1) {
					_writer.append(String.valueOf(solution.getScalarFitnessValue()));
				} else {
					for (int i = 0; i < numObjectives; i++) {
						if (i > 0) _writer.append(_INTRA_FIELD_DELIMITER);
						_writer.append(String.valueOf(solution.getFitnessValue(i)));
					}
				}
				_writer.append(_FIELD_DELIMITER);
				
				// jecoliunittest.operators.operator
				_writer.append(operator + _FIELD_DELIMITER);
				
				// solution
				_writer.append(((_decoder != null) ? _decoder.decode(solution.getRepresentation()) : solution.getRepresentation().stringRepresentation()) + _FIELD_DELIMITER);
                //_writer.append(solution.getRepresentation().stringRepresentation()+"("+solution.hashCode()+")"+ _FIELD_DELIMITER);

				// parents
				if (parents != null) {
					for (int i = 0; i < parents.size(); i++) {
						if (i > 0) _writer.append(_INTRA_FIELD_DELIMITER);
						_writer.append(((_decoder != null) ? _decoder.decode(parents.get(i).getRepresentation()) : parents.get(i).getRepresentation().stringRepresentation()));
                        //_writer.append(parents.get(i).getRepresentation().stringRepresentation()+"("+parents.get(i).hashCode()+")");
					}
				}
                _writer.append(_FIELD_DELIMITER);
			}

			_trackInfo.clear();
		}
	}
	
	@Override
	public synchronized void keepSolution(int iteration, String operator, ISolution<T> solution, List<ISolution<T>> parents) {
		synchronized (_lock) {
			TrackInfo<T> ti = new TrackInfo<T>(iteration, operator, parents);
			_trackInfo.put(solution, ti);
		}
	}
	
	@Override
	public void setSolutionDecoder(ISolutionDecoder<T, String> decoder) {
		_decoder = decoder;
	}
}
