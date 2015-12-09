package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting;

import java.awt.BorderLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;

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

public class Plot2DGUI<T extends IRepresentation> extends ApplicationFrame implements IAlgorithmStateListener, IPlotter<T> {
	
	private static final long	serialVersionUID	= -795408230268615951L;
	protected static final long	REFRESH_MILLIS		= 1000;
	protected static final int	EVAL_FUNC_INCREMENT	= 100;
	protected static boolean	finish				= false;
	protected XYPlot			plot;
	
	protected IAlgorithm<T>				alg;
	protected HashMap<Integer, Double>	dataSingle;
	protected int						last	= 0;
	ArrayList<Double>					values	= new ArrayList<Double>();
	protected boolean					multiobjective;
	
	protected int numObjs = 0;
	
	protected int	mo_objective1	= 0;
	protected int	mo_objective2	= 1;
	
	protected boolean observeArchive = false;
	
	public Plot2DGUI() {
		super("Real-time plot");
		initGUI();
	}
	
	public Plot2DGUI(IAlgorithm<T> alg) {
		super("Real-Time plot");
		this.alg = alg;
		this.multiobjective = (alg.getConfiguration().getEvaluationFunction().getNumberOfObjectives() > 1);
		this.alg.addAlgorithmStateListener(this);
		
		initGUI();
	}
	
	protected JPanel getBottomPanel() {
		JPanel bottom = new JPanel();
		JComboBox box1 = new JComboBox();
		JComboBox box2 = new JComboBox();
		bottom.setLayout(new BorderLayout());
		bottom.add(box1, BorderLayout.WEST);
		bottom.add(box2, BorderLayout.EAST);
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				finish();
			}
		});
		
		bottom.add(closeButton, BorderLayout.SOUTH);
		
		return bottom;
		
	}
	
	protected void initGUI() {
		final JFreeChart chart = ChartFactory.createScatterPlot("Real-time plot", "Y", "X", null, PlotOrientation.HORIZONTAL, false, false, false);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// force aliasing of the rendered content..
		plot = chart.getXYPlot();
		chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final ChartPanel panel = new ChartPanel(chart, true);
		//	        panel.setLayout(new BorderLayout());
		
		//	        panel.add(getBottomPanel(),BorderLayout.SOUTH);
		panel.setPreferredSize(new java.awt.Dimension(500, 270));
		panel.setSize(new java.awt.Dimension(500, 270));
		panel.setMinimumDrawHeight(10);
		panel.setMaximumDrawHeight(2000);
		panel.setMinimumDrawWidth(20);
		panel.setMaximumDrawWidth(2000);
		
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.add(getBottomPanel(), BorderLayout.SOUTH);
		setContentPane(mainPanel);
		pack();
		setVisible(true);
	}
	
	public void finish() {
		finish = true;
		this.dispose();
		System.exit(0);
	}
	
	private void updateDataSingle() {
		
		System.out.println("PRINT SINGLE DATA! ");
		AlgorithmState<T> state = alg.getAlgorithmState();
		if (state != null) {
			ISolutionSet<T> solset = state.getSolutionSet();
			List<ISolution<T>> sollist = null;
			
			sollist = solset.getHighestValuedSolutions(1);
			
			values.add(sollist.get(0).getScalarFitnessValue());
			
			double[][] data = new double[2][values.size()];
			
			for (int i = 0; i < values.size(); i++) {
				data[0][i] = i;
				data[1][i] = values.get(i);
			}
			
			DefaultXYDataset dataset = new DefaultXYDataset();
			dataset.addSeries("data", data);
			plot.setDataset(dataset);
			//			System.out.println(">>>>>>>>>>>>>>>> UPDATED <<<<<<<<<<<<<<<<<<");
		} else
			throw new NullPointerException("Algorithm state is null");
	}
	
	private void updateDataMulti() {
		
		AlgorithmState<T> state = alg.getAlgorithmState();
		
		if (state != null) {
			boolean archiveNotEmpty = false;
			ISolutionSet<T> solset = null;
			if (IArchivedAlgorithmState.class.isAssignableFrom(state.getClass())) {
				solset = ((IArchivedAlgorithmState<T>) state).getArchive();
				if (solset != null && solset.getNumberOfSolutions() > 0)
					archiveNotEmpty = true;
			}
			if (!archiveNotEmpty)
				solset = new SolutionSet<T>((SolutionSet<T>) state.getSolutionSet());
				
			int numObjs = solset.getNumberOfObjectives();
			//			solset.sort(new ObjectivesComparator(mo_objective2,alg.getConfiguration().getEvaluationFunction().isMaximization()), true);
			
			if (numObjs > mo_objective1 && numObjs > mo_objective2) {
				List<ISolution<T>> sollist = solset.getListOfSolutions();
				double[][] data = new double[2][sollist.size()];
				
				for (int i = 0; i < sollist.size(); i++) {
					data[0][i] = sollist.get(i).getFitnessValue(mo_objective1);
					data[1][i] = sollist.get(i).getFitnessValue(mo_objective2);
				}
				
				DefaultXYDataset dataset = new DefaultXYDataset();
				dataset.addSeries("data", data);
				plot.setDataset(dataset);
				
			} else
				throw new IllegalArgumentException("Selected objectives exceed number of objectives");
		} else
			throw new NullPointerException("Algorithm state is null");
	}
	
	public void run() {
		Thread dummy = new Thread() {
			public void run() {
				while (!finish) {
					try {
						sleep(REFRESH_MILLIS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		dummy.setPriority(Thread.MAX_PRIORITY); // more priority than the operation process
		dummy.start();
	}
	
	@Override
	public void processAlgorithmStateEvent(AlgorithmStateEvent event) {
		if (event.getId().equals(AbstractAlgorithm.EVALUATION_FUNCTION_INCREMENT)) {
			int current = Integer.parseInt(event.getMessage());
			if (current >= (last + EVAL_FUNC_INCREMENT)) {
				last = current;
				if (multiobjective)
					updateDataMulti();
				else
					updateDataSingle();
			}
		}
		
	}
	
	public boolean isObserveArchive() {
		return observeArchive;
	}
	
	public void setObserveArchive(boolean observeArchive) {
		this.observeArchive = observeArchive;
	}
	
	@Override
	public AMFunctionType getFunctionType() {
		return AMFunctionType.PLOTTER;
	}
	
	@Override
	public void plot(double[][] data) {
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries("data", data);
		plot.setDataset(dataset);
	}
	
	@Override
	public void plot(ISolutionSet<T> solset) {
		
		if (solset.getNumberOfSolutions() > 0) {
		
//			System.out.println(">>>>>>>>>> NUM. SOLUTIONS = "+solset.getNumberOfSolutions());
			
			if (numObjs < 1)
				numObjs = solset.getNumberOfObjectives();
				
//			System.out.println(">>>>>>>>>> NUM. OBJECTIVES = "+numObjs);
			
			if (numObjs > mo_objective1 && numObjs > mo_objective2) {
				List<ISolution<T>> sollist = solset.getListOfSolutions();
				double[][] data = new double[2][sollist.size()];
				
				for (int i = 0; i < sollist.size(); i++) {
					data[0][i] = sollist.get(i).getFitnessValue(mo_objective1);
					data[1][i] = sollist.get(i).getFitnessValue(mo_objective2);
					
					//					System.out.println("\t\t: FIT 0 = "+ sollist.get(i).getFitnessValue(mo_objective1));
					//					System.out.println("\t\t: FIT 1 = "+ sollist.get(i).getFitnessValue(mo_objective2));
					
				}
				
				this.plot(data);
				
			} else
				throw new IllegalArgumentException("Selected objectives exceed number of objectives");
		}
	}
	
}
