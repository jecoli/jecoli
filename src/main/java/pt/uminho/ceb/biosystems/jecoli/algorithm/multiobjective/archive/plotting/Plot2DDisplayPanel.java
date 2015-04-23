/**
 * 
 */
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting;

import java.awt.BorderLayout;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmStateEvent;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStateListener;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IArchivedAlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.components.AMFunctionType;

/**
 * @author pmaia
 *
 */
public class Plot2DDisplayPanel<T extends IRepresentation> extends JPanel implements IAlgorithmStateListener,IPlotter<T>{

	private static final long serialVersionUID = 7867461279942885549L;

	protected static boolean finish = false;
	protected static final int 	EVAL_FUNC_INCREMENT = 100;
	protected XYPlot plot;

	protected IAlgorithm<T> alg;
	protected int last = 0;
	ArrayList<Double> values = new ArrayList<Double>();
	protected boolean multiobjective;

	protected int numObjs = 0;

	protected int mo_objective1 = 0;
	protected int mo_objective2 = 1;
	
	public Plot2DDisplayPanel(){
		super();
		initGUI();
	}	

	public Plot2DDisplayPanel(IAlgorithm<T> alg){
		super();
		this.setAlg(alg);		            
		initGUI();
	}
	
	public void setAlg(IAlgorithm<T> alg){
		this.alg = alg;
		this.multiobjective = (alg.getConfiguration().getEvaluationFunction().getNumberOfObjectives() > 1);
		this.alg.addAlgorithmStateListener(this);
	}
	
	protected JPanel getBottomPanel(){
		JPanel bottom = new JPanel();
		JComboBox box1 = new JComboBox();
		JComboBox box2 = new JComboBox();
		bottom.setLayout(new BorderLayout());
		bottom.add(box1,BorderLayout.WEST);
		bottom.add(box2,BorderLayout.EAST);		

		return bottom;

	}

	protected void initGUI(){

		final JFreeChart chart = ChartFactory.createScatterPlot("Optimization Process", "Obj.fun 1", "Obj.fun 2", null, PlotOrientation.HORIZONTAL, false, false, false);

		
		this.setLayout(new BorderLayout());
		// force aliasing of the rendered content..
		plot = chart.getXYPlot();
		chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ChartPanel panel = new ChartPanel(chart, true);
		panel.setPreferredSize(new java.awt.Dimension(500, 270));
		panel.setSize(new java.awt.Dimension(500, 270));
		panel.setMinimumDrawHeight(10);
		panel.setMaximumDrawHeight(2000);
		panel.setMinimumDrawWidth(20);
		panel.setMaximumDrawWidth(2000);

		this.setPreferredSize(new java.awt.Dimension(500, 270));
		this.setSize(new java.awt.Dimension(500, 270));
		this.add(panel,BorderLayout.CENTER);
		this.add(getBottomPanel(),BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	

	private void updateDataSingle() {

		AlgorithmState<T> state = alg.getAlgorithmState();
		boolean isMax = alg.getConfiguration().getEvaluationFunction().isMaximization();
		if(state!=null){
			ISolutionSet<T> solset = state.getSolutionSet();
			List<ISolution<T>> sollist = null;

			if(isMax)
				sollist = solset.getHighestValuedSolutions(1);
			else
				sollist = solset.getLowestValuedSolutions(1);

			if(sollist.size()>0){
				values.add(sollist.get(0).getScalarFitnessValue());

				double[][] data = new double[2][values.size()];

				for(int i=0; i<values.size(); i++){
					data[0][i] = i;
					data[1][i] = values.get(i);				
				}

				DefaultXYDataset dataset = new DefaultXYDataset();
				dataset.addSeries("data", data);
				plot.setDataset(dataset);
			}
		}
		else throw new NullPointerException("Algorithm state is null");
	}

	private void updateDataMulti(){

		AlgorithmState<T> state = alg.getAlgorithmState();


		if(state!=null){
			boolean archiveNotEmpty = false; 
			ISolutionSet<T> solset = null;
			if(IArchivedAlgorithmState.class.isAssignableFrom(state.getClass())){
				solset = ((IArchivedAlgorithmState<T>) state).getArchive();
				if(solset!=null && solset.getNumberOfSolutions()>0)
					archiveNotEmpty = true;
			}
			if(!archiveNotEmpty){
				solset = new SolutionSet<T>((SolutionSet<T>)state.getSolutionSet());

				int numObjs = solset.getNumberOfObjectives();

				if(numObjs > mo_objective1 && numObjs > mo_objective2){
					List<ISolution<T>> sollist = solset.getListOfSolutions();
					double[][] data = new double[2][sollist.size()];

					for(int i=0; i<sollist.size(); i++){
						data[0][i] = sollist.get(i).getFitnessValue(mo_objective1);
						data[1][i] = sollist.get(i).getFitnessValue(mo_objective2);				
					}

					DefaultXYDataset dataset = new DefaultXYDataset();
					dataset.addSeries("data", data);
					plot.setDataset(dataset);
				}
			}
			else throw new IllegalArgumentException("Selected objectives exceed number of objectives");
		}
		else throw new NullPointerException("Algorithm state is null");
	}

	@Override
	public void processAlgorithmStateEvent(AlgorithmStateEvent event) {
		if(event.getId().equals(AbstractAlgorithm.EVALUATION_FUNCTION_INCREMENT)){
			int current = Integer.parseInt(event.getMessage());
			if(current >= (last + EVAL_FUNC_INCREMENT)){
				last = current;
				if(multiobjective)
					updateDataMulti();
				else
					updateDataSingle();
			}
		}

	}	

	@Override
	public AMFunctionType getFunctionType() {
		return AMFunctionType.PLOTTER;	}

	@Override
	public void plot(double[][] data) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("data", data);
		plot.setDataset(dataset);
	}

	@Override
	public void plot(ISolutionSet<T> solset) {
		if(solset.getNumberOfSolutions()>0){

			if(numObjs <1)
				numObjs = solset.getNumberOfObjectives();

			if(numObjs > mo_objective1 && numObjs > mo_objective2){
				List<ISolution<T>> sollist = solset.getListOfSolutions();
				double[][] data = new double[2][sollist.size()];

				for(int i=0; i<sollist.size(); i++){
					data[0][i] = sollist.get(i).getFitnessValue(mo_objective1);
					data[1][i] = sollist.get(i).getFitnessValue(mo_objective2);
				}

				this.plot(data);

			}else throw new IllegalArgumentException("Selected objectives exceed number of objectives");
		}
	}
}


