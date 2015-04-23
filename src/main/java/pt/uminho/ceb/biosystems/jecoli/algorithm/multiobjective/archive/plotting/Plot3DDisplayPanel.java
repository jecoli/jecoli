package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.plotting;
///**
// * 
// */
//package jecoli.algorithm.multiobjective.archive.plotting;
//
//import java.awt.BorderLayout;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//
//import org.optflux.jecoli3.algorithm.components.algorithm.AbstractAlgorithm;
//import org.optflux.jecoli3.algorithm.components.algorithm.AlgorithmState;
//import org.optflux.jecoli3.algorithm.components.algorithm.AlgorithmStateEvent;
//import org.optflux.jecoli3.algorithm.components.algorithm.IAlgorithm;
//import org.optflux.jecoli3.algorithm.components.algorithm.IAlgorithmStateListener;
//import org.optflux.jecoli3.algorithm.components.algorithm.IArchivedAlgorithmState;
//import org.optflux.jecoli3.algorithm.components.representation.IRepresentation;
//import org.optflux.jecoli3.algorithm.components.solution.ISolution;
//import org.optflux.jecoli3.algorithm.components.solution.ISolutionSet;
//import org.optflux.jecoli3.algorithm.components.solution.SolutionSet;
//import org.optflux.jecoli3.algorithm.multiobjective.archive.components.AMFunctionType;
//
//import org.jzy3d.chart.Chart;
//import org.jzy3d.colors.Color;
//import org.jzy3d.maths.Coord3d;
//import org.jzy3d.plot3d.primitives.Scatter;
//import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;
//
///**
// * @author pmaia
// *
// */
//public class Plot3DDisplayPanel <T extends IRepresentation> extends JPanel implements IAlgorithmStateListener,IPlotter<T>{
//
//	private static final long serialVersionUID = 7867461279942885549L;
//
//	protected static boolean finish = false;
//	protected static final int 	EVAL_FUNC_INCREMENT = 100;;
//	protected Scatter scatter;
//
//	protected IAlgorithm<T> alg;
//	protected int last = 0;
//	ArrayList<Double> values = new ArrayList<Double>();
//	protected boolean multiobjective = false;
//
//	protected int numObjs = 0;
//
//	protected int mo_objective1 = 0;
//	protected int mo_objective2 = 1;
//	protected int mo_objective3 = 2;
//
//	public Plot3DDisplayPanel(){
//		super();
//		initGUI();
//	}
//
//
//	public Plot3DDisplayPanel(IAlgorithm<T> alg){
//		super();
//		this.setAlg(alg);		            
//		initGUI();
//	}
//
//	public void setAlg(IAlgorithm<T> alg){
//		this.alg = alg;
//		this.numObjs = alg.getConfiguration().getEvaluationFunction().getNumberOfObjectives();
//		this.multiobjective = numObjs > 1;
//		this.alg.addAlgorithmStateListener(this);
//	}
//
//	protected void initGUI(){
//
//		Chart chart = new Chart();
//		chart.getAxeLayout().setMainColor(Color.WHITE);
//		chart.getView().setBackgroundColor(Color.BLACK);
//
//		scatter = new Scatter();
//		chart.addDrawable(scatter);
//		chart.getScene().add(scatter);
//
//		if(numObjs<3)
//			chart.setViewMode(ViewPositionMode.TOP);
//
//		this.setPreferredSize(new java.awt.Dimension(500, 270));
//		this.setSize(new java.awt.Dimension(500, 270));
//		this.add((JComponent)chart.getCanvas(),BorderLayout.CENTER);
//
//		this.setVisible(true);
//
//	}
//
//
//	private void updateDataSingle() {
//
//		AlgorithmState<T> state = alg.getAlgorithmState();
//		boolean isMax = alg.getConfiguration().getEvaluationFunction().isMaximization();
//		if(state!=null){
//			ISolutionSet<T> solset = state.getSolutionSet();
//			List<ISolution<T>> sollist = null;
//
//			if(isMax)
//				sollist = solset.getHighestValuedSolutions(1);
//			else
//				sollist = solset.getLowestValuedSolutions(1);
//
//			values.add(sollist.get(0).getScalarFitnessValue());
//
//			Coord3d[] points = new Coord3d[values.size()];
//
//			for(int i=0; i<values.size(); i++){
//				Coord3d coord = new Coord3d(i,values.get(i),0);
//				points[i] = coord;
//			}
//
//			scatter.setData(points);
//
//		}
//		else throw new NullPointerException("Algorithm state is null");
//	}
//
//	private void updateDataMulti(){
//
//		AlgorithmState<T> state = alg.getAlgorithmState();
//
//
//		if(state!=null){
//			boolean archiveNotEmpty = false; 
//			ISolutionSet<T> solset = null;
//			if(IArchivedAlgorithmState.class.isAssignableFrom(state.getClass())){
//				solset = ((IArchivedAlgorithmState) state).getArchive();
//				if(solset!=null && solset.getNumberOfSolutions()>0)
//					archiveNotEmpty = true;
//			}
//			if(!archiveNotEmpty)
//				solset = new SolutionSet<T>((SolutionSet<T>)state.getSolutionSet());
//
//			List<ISolution<T>> sollist = solset.getListOfSolutions();
//			Coord3d[] points = new Coord3d[sollist.size()];
//
//			for(int i=0; i<sollist.size(); i++){
//				Coord3d coord = null;
//				if(numObjs < mo_objective1 && numObjs < mo_objective2){
//					if(numObjs<=2)
//						coord = new Coord3d(sollist.get(i).getFitnessValue(mo_objective1),sollist.get(i).getFitnessValue(mo_objective2),0);
//					else if(numObjs < mo_objective3)
//						coord = new Coord3d(sollist.get(i).getFitnessValue(mo_objective1),sollist.get(i).getFitnessValue(mo_objective2),sollist.get(i).getFitnessValue(mo_objective3));
//					else
//						coord = new Coord3d(0,0,0);
//				}
//				points[i] = coord;
//			}
//			scatter.setData(points);
//		}
//		else throw new NullPointerException("Algorithm state is null");
//	}
//
//	@Override
//	public void processAlgorithmStateEvent(AlgorithmStateEvent event) {
//		if(event.getId().equals(AbstractAlgorithm.EVALUATION_FUNCTION_INCREMENT)){
//			int current = Integer.parseInt(event.getMessage());
//			if(current >= (last + EVAL_FUNC_INCREMENT)){
//				last = current;
//				if(multiobjective)
//					updateDataMulti();
//				else
//					updateDataSingle();
//			}
//		}
//
//	}	
//
//	@Override
//	public AMFunctionType getFunctionType() {
//		return AMFunctionType.PLOTTER;	}
//
//	@Override
//	public void plot(double[][] data) {
////		Coord3d[] points = new Coord3d[data.length];
////		for(double[] data_i: data){
////			
////		}
////			
////		dataset.addSeries("data", data);
////		plot.setDataset(dataset);
//	}
//
//	@Override
//	public void plot(ISolutionSet<T> solset) {
//		if(solset.getNumberOfSolutions()>0){
//
//			if(numObjs <1)
//				numObjs = solset.getNumberOfObjectives();
//
//			if(numObjs > mo_objective1 && numObjs > mo_objective2){
//				List<ISolution<T>> sollist = solset.getListOfSolutions();
//				double[][] data = new double[2][sollist.size()];
//
//				for(int i=0; i<sollist.size(); i++){
//					data[0][i] = sollist.get(i).getFitnessValue(mo_objective1);
//					data[1][i] = sollist.get(i).getFitnessValue(mo_objective2);
//				}
//
//				this.plot(data);
//
//			}else throw new IllegalArgumentException("Selected objectives exceed number of objectives");
//		}
//	}
//}
//
//
