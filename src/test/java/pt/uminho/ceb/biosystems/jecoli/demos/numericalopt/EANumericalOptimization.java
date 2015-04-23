/**
* Copyright 2009,
* CCTC - Computer Science and Technology Center
* IBB-CEB - Institute for Biotechnology and  Bioengineering - Centre of Biological Engineering
* University of Minho
*
* This is free software: you can redistribute it and/or modify
* it under the terms of the GNU Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Public License for more details.
*
* You should have received a copy of the GNU Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
* 
* Created inside the SysBio Research Group <http://sysbio.di.uminho.pt/>
* University of Minho
*/
package pt.uminho.ceb.biosystems.jecoli.demos.numericalopt;

import java.util.ArrayList;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithmStatistics;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.writer.IAlgorithmResultWriter;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.container.ReproductionOperatorContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.GaussianPerturbationMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.LinearGenomeRandomMutation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.RealValueArithmeticalCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear.UniformCrossover;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.selection.TournamentSelection;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.DefaultRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.real.RealValueRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.ITerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.IterationTerminationCriteria;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.differentialevolution.BaseVectorSelectionType;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.differentialevolution.DifferentialEvolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.differentialevolution.DifferentialEvolutionConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.EvolutionaryConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.evolutionary.RecombinationParameters;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.AnnealingSchedule;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.IAnnealingSchedule;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.SimulatedAnnealing;
import pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.simulatedannealing.SimulatedAnnealingConfiguration;


// TODO: Auto-generated Javadoc
/**
 * The Class EANumericalOptimization.
 */
public class EANumericalOptimization {

	/** The number variables. */
	protected int numberVariables = 1; // number of variables
	
	/** The parameters. */
	protected double [] parameters = null; // function parameters
	
	/** The upper limit. */
	protected double upperLimit = 1.0;
	
	/** The lower limit. */
	protected double lowerLimit = 0.0;
	
	/** The function type. */
	protected int functionType = 0;
	
	
	/** The algorithm. */
	protected IAlgorithm<ILinearRepresentation<Double>> algorithm;
	
	/** The results. */
	protected IAlgorithmResult<ILinearRepresentation<Double>> results;
	
	/** The statistics. */
	protected IAlgorithmStatistics<ILinearRepresentation<Double>> statistics;

	


	/**
	 * Instantiates a new eA numerical optimization.
	 * 
	 * @param functionType the function type
	 * @param numberVariables the number variables
	 * @param parameters the parameters
	 * @param lowerLimit the lower limit
	 * @param upperLimit the upper limit
	 */
	public EANumericalOptimization(int functionType, int numberVariables, double[] parameters,
			double lowerLimit, double upperLimit) 
	{
		this.functionType = functionType;
		this.numberVariables = numberVariables;
		this.parameters = parameters;
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;	
	}



	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
	
		int nv = 1; // number of variables
		double [] pars = null; // function parameters
		double ul = 1.0;
		double ll = 0.0;
		int f = 0;

		// get function and parameters from command line 
		if(args[0].equals("linquad"))
		{
			f = Functions.LINEAR_QUADRATIC;
			nv = 45; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			pars = new double[6];
			pars[0]=100;
			if(args.length > 2) pars[0] = Double.parseDouble(args[2]); 
			pars[1]=1;
			if(args.length > 3) pars[1] = Double.parseDouble(args[3]); 
			pars[2]=1;
			if(args.length > 4) pars[2] = Double.parseDouble(args[4]); 
			pars[3]=1;
			if(args.length > 5) pars[3] = Double.parseDouble(args[5]); 
			pars[4]=1;
			if(args.length > 6) pars[4] = Double.parseDouble(args[6]); 
			pars[5]=1;
			if(args.length > 7) pars[5] = Double.parseDouble(args[7]); 
			ul = 200.0;
			if(args.length > 8) ul = Double.parseDouble(args[8]); 
			ll = -200.0;
			if(args.length > 9) ll = Double.parseDouble(args[9]); 
		}
		else if(args[0].equals("harvest"))
		{
			f = Functions.HARVEST;
			nv = 44; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			pars = new double[2];
			pars[0]=100;
			if(args.length > 2) pars[0] = Double.parseDouble(args[2]); 
			pars[1]=1.1;
			if(args.length > 3) pars[1] = Double.parseDouble(args[3]); 
			ul = 1000.0; 
			if(args.length > 4) ul = Double.parseDouble(args[4]); 
			ll = 0.0;
			if(args.length > 5) ll = Double.parseDouble(args[5]); 
		}
		else if(args[0].equals("pushcart"))
		{
			f = Functions.PUSH_CART;
			nv = 45; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 100.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = 0.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f1")) // sum of squares
		{
			f = Functions.SPHERE;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 100.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -100.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f2")) // f2 
		{
			f = Functions.YAO_F2;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 10.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -10.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f3")) // f2 
		{
			f = Functions.YAO_F3;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 100.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -100.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f4")) // max abs 
		{
			f = Functions.MAX_ABS;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 100.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -100.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f5")) 
		{
			f = Functions.ROSENBROCK;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 30.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -30.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f8")) 
		{
			f = Functions.YAO_F8;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 500.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -500.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f9")) 
		{
			f = Functions.RASTRIGIN;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 5.12; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -5.12;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f10")) 
		{
			f = Functions.ACKLEY;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 32.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -32.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f11")) 
		{
			f = Functions.GRIEWANK;
			nv = 30; // number of variables
			if(args.length > 1) nv = Integer.parseInt(args[1]); 
			ul = 600.0; 
			if(args.length > 2) ul = Double.parseDouble(args[2]); 
			ll = -600.0;
			if(args.length > 3) ll = Double.parseDouble(args[3]); 
		}
		else if(args[0].equals("f18")) 
		{
			f = Functions.YAO_F18;
			nv = 2;
			ul = 2.0; 
			ll = -2.0;
		}
		
		try {
			
			EANumericalOptimization ea = new EANumericalOptimization(f, nv, pars, ll, ul);
		
			//ea.configureDifferentialEvolution(20, 20000);
			ea.configureEvolutionaryAlgorithm(250, 15000);		
			//ea.configureSimulatedAnnealing(100000);
			
			ea.run();
			//}

			//MatUtils.printa(ea.getBestSolution());
			
		} catch (InvalidNumberOfIterationsException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//testNumericalOpt(f,nv,pars,ll,ul);
	}

	

	
	/**
	 * Configure solution factory.
	 * 
	 * @return the i solution factory
	 */
	public RealValueRepresentationFactory configureSolutionFactory ()
	{	
		return new RealValueRepresentationFactory(this.numberVariables,this.lowerLimit,this.upperLimit);
	}
	
	/**
	 * Configure evaluation function.
	 * 
	 * @return the i evaluation function< i linear representation< double>>
	 */
	public IEvaluationFunction<ILinearRepresentation<Double>> configureEvaluationFunction()
	{
		return new NumericalOptimizationEvaluation(this.functionType, this.numberVariables, this.parameters);
	}
	
	
	/**
	 * Creates the reproduction operators.
	 * 
	 * @param factory the factory
	 * @param useCrossover the use crossover
	 * 
	 * @return the i jecoliunittest.operators.operator container< i reproduction jecoliunittest.operators.operator>
	 * 
	 * @throws Exception the exception
	 */
	public ReproductionOperatorContainer createReproductionOperators(RealValueRepresentationFactory factory, boolean useCrossover) 
	throws Exception
	{
		double probEachOperator = 0.0;
		if (useCrossover) probEachOperator = 0.25;
		else probEachOperator = 0.5;
		
		ReproductionOperatorContainer operatorContainer = new ReproductionOperatorContainer();
		operatorContainer.addOperator(probEachOperator, new GaussianPerturbationMutation(0.05));
		operatorContainer.addOperator(probEachOperator,	new LinearGenomeRandomMutation<Double>(0.05));
		if (useCrossover)
		{
			operatorContainer.addOperator(probEachOperator, new UniformCrossover<Double>());
			operatorContainer.addOperator(probEachOperator, new RealValueArithmeticalCrossover());
		}
			
		
		return operatorContainer;
	}
	
	/**
	 * Configure evolutionary algorithm.
	 * 
	 * @param populationSize the population size
	 * @param numberGenerations the number generations
	 * 
	 * @throws Exception the exception
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	public void configureEvolutionaryAlgorithm(int populationSize, int numberGenerations) 
	throws Exception, InvalidConfigurationException
	{
		EvolutionaryConfiguration<ILinearRepresentation<Double>,RealValueRepresentationFactory> configuration = new EvolutionaryConfiguration<ILinearRepresentation<Double>,RealValueRepresentationFactory>();
		
		configuration.setEvaluationFunction( configureEvaluationFunction() );
		
		RealValueRepresentationFactory solutionFactory = configureSolutionFactory(); 
		configuration.setSolutionFactory( solutionFactory );
		
		configuration.setPopulationSize(populationSize);
		
		configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
		configuration.setProblemBaseDirectory("nullDirectory");
		configuration.setAlgorithmStateFile("nullFile");
		configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
		configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Double>>>());
		configuration.setStatisticsConfiguration(new StatisticsConfiguration());
		configuration.getStatisticsConfiguration().setVerbose(true);
		
		ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
		configuration.setTerminationCriteria(terminationCriteria);
	
		RecombinationParameters recombinationParameters = new RecombinationParameters(populationSize);
		configuration.setRecombinationParameters(recombinationParameters);
		
		configuration.setSelectionOperators(new TournamentSelection<ILinearRepresentation<Double>>(1,2));
		
		ReproductionOperatorContainer operatorContainer = 	createReproductionOperators(solutionFactory,true);
		configuration.setReproductionOperatorContainer(operatorContainer);
		
		algorithm = new EvolutionaryAlgorithm<ILinearRepresentation<Double>,RealValueRepresentationFactory>(configuration);
	}
	
	
	/**
	 * Configure differential evolution.
	 * 
	 * @param populationSize the population size
	 * @param numberGenerations the number generations
	 * 
	 * @throws InvalidNumberOfIterationsException the invalid number of iterations exception
	 * @throws InvalidConfiguration		configuration.setPopulationSize(1000);
Exception the invalid configuration exception
	 */
	public void configureDifferentialEvolution (int populationSize, int numberGenerations) 
	throws InvalidNumberOfIterationsException, InvalidConfigurationException
	{
		DifferentialEvolutionConfiguration configuration = new DifferentialEvolutionConfiguration();
		configuration.setEvaluationFunction( configureEvaluationFunction() );
		
		ISolutionFactory<ILinearRepresentation<Double>> solutionFactory = configureSolutionFactory(); 
		configuration.setSolutionFactory( solutionFactory );
		
		configuration.setPopulationSize(populationSize);
		configuration.setDoPopulationInitialization(true);
		
		configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
		configuration.setProblemBaseDirectory("nullDirectory");
		configuration.setAlgorithmStateFile("nullFile");
		configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
		configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Double>>>());
		configuration.setStatisticsConfiguration(new StatisticsConfiguration());
		configuration.getStatisticsConfiguration().setVerbose(true);
		
		ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
		configuration.setTerminationCriteria(terminationCriteria);
		
		BaseVectorSelectionType baseVecSelType = BaseVectorSelectionType.RANDOM;
		configuration.setBaseVectorSelectionType(baseVecSelType);
		
		configuration.setScaleFactorF(0.6);
		
		configuration.setCrossoverProbability(0.5);
		
		try {
			algorithm  = new DifferentialEvolution(configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Configure simulated annealing.
	 * 
	 * @param numberGenerations the number generations
	 * 
	 * @throws Exception the exception
	 * @throws InvalidConfigurationException the invalid configuration exception
	 */
	public void configureSimulatedAnnealing(int numberGenerations) 	throws Exception, InvalidConfigurationException
	{
		SimulatedAnnealingConfiguration<ILinearRepresentation<Double>,RealValueRepresentationFactory> configuration = new SimulatedAnnealingConfiguration<ILinearRepresentation<Double>,RealValueRepresentationFactory>();
		configuration.setEvaluationFunction( configureEvaluationFunction());
		RealValueRepresentationFactory solutionFactory = configureSolutionFactory();
		configuration.setSolutionFactory(solutionFactory); 
		 
		IAnnealingSchedule annealingSchedule = new  AnnealingSchedule(100,0.9999,5.0);
		configuration.setAnnealingSchedule(annealingSchedule);
		
		configuration.setRandomNumberGenerator(new DefaultRandomNumberGenerator());
		configuration.setProblemBaseDirectory("nullDirectory");
		configuration.setAlgorithmStateFile("nullFile");
		configuration.setSaveAlgorithmStateDirectoryPath("nullDirectory");
		configuration.setAlgorithmResultWriterList(new ArrayList<IAlgorithmResultWriter<ILinearRepresentation<Double>>>());
		configuration.setStatisticsConfiguration(new StatisticsConfiguration());
		
		ITerminationCriteria terminationCriteria = new IterationTerminationCriteria(numberGenerations);
		configuration.setTerminationCriteria(terminationCriteria);
		
		ReproductionOperatorContainer operatorContainer = createReproductionOperators((RealValueRepresentationFactory)solutionFactory, false);
		configuration.setMutationOperatorContainer(operatorContainer);
		
		algorithm = new SimulatedAnnealing<ILinearRepresentation<Double>,RealValueRepresentationFactory>(configuration);
	}
	
	/**
	 * Run.
	 * 
	 * @throws Exception the exception
	 */
	public void run() throws Exception
	{
		results =  algorithm.run();
	}


	
	/**
	 * Gets the best solution.
	 * 
	 * @return the best solution
	 */
	public double[] getBestSolution()
	{
		ISolution<ILinearRepresentation<Double>> bestSolution = results.getSolutionContainer().getBestSolutionCellContainer(false).getSolution();
		
		ILinearRepresentation<Double> genome = bestSolution.getRepresentation();
		
		double[] res = new double[genome.getNumberOfElements()];
		
		for(int i=0; i < genome.getNumberOfElements(); i++)
			res[i] = genome.getElementAt(i);
		return res;
	}
	

}
	
