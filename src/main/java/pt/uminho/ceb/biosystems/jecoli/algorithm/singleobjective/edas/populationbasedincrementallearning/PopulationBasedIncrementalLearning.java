package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.edas.populationbasedincrementallearning;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AbstractAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.controller.AlgorithmController;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.SetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.*;

/**
 * Created by ptiago on 05-01-2015.
 */
public class PopulationBasedIncrementalLearning extends AbstractAlgorithm<SetRepresentation<Integer>,PopulationBasedIncrementalLearningConfiguration> {

    public PopulationBasedIncrementalLearning(PopulationBasedIncrementalLearningConfiguration configuration) throws InvalidConfigurationException {
        super(configuration);
    }

    public PopulationBasedIncrementalLearning(PopulationBasedIncrementalLearningConfiguration configuration, AlgorithmController<SetRepresentation<Integer>, PopulationBasedIncrementalLearningConfiguration> algorithmController) throws InvalidConfigurationException {
        super(configuration, algorithmController);
    }

    public PopulationBasedIncrementalLearning(String filePath, boolean resetListenerList) throws Exception {
        super(filePath, resetListenerList);
    }

    protected ISolutionSet<SetRepresentation<Integer>> createNewSolutionSet(double[] geneProbabilityVector,int numberOfSolutions){
        ISolutionSet<SetRepresentation<Integer>> newSolutionSet = new SolutionSet<>();
        for(int i = 0;i < numberOfSolutions;i++){
            TreeSet<Integer> individualRepresentation = new TreeSet<>();
            for(int j = 0; j < geneProbabilityVector.length; j++){
                double randomValue = configuration.getRandomNumberGenerator().nextDouble();
                double geneProbability = geneProbabilityVector[j];
                if(randomValue < geneProbability)
                    individualRepresentation.add(j);
            }
            SetRepresentation<Integer> newIndividualSet = new SetRepresentation<>(individualRepresentation);
            newSolutionSet.add(new Solution<>(newIndividualSet));
        }
        return newSolutionSet;
    }

    @Override
    protected ISolutionSet<SetRepresentation<Integer>> iteration(AlgorithmState<SetRepresentation<Integer>> algorithmState,
                                                                 ISolutionSet<SetRepresentation<Integer>> solutionSet) throws Exception {

        IEvaluationFunction<SetRepresentation<Integer>> evaluationFunction = configuration.getEvaluationFunction();
        boolean isMaximization = evaluationFunction.isMaximization();
        double learningRate = configuration.getLearningRate();
        int numberOfSelectedSolutions = configuration.getNumberOfSolutionsToSelect();

        //GenerateNewSolutionList
        double[] geneProbabilityVector = configuration.getProbabilityVector();
        int populationSize = configuration.getPopulationSize();
        ISolutionSet<SetRepresentation<Integer>> newSolutionSet = createNewSolutionSet(geneProbabilityVector,populationSize);
        //Evaluate
        evaluationFunction.evaluate(newSolutionSet);
        algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(newSolutionSet.getNumberOfSolutions());
        //OrderSolutionList;SelectHigherSolution
        List<ISolution<SetRepresentation<Integer>>> orderedSolutionList = computeOrderedSolutionList(isMaximization,numberOfSelectedSolutions,newSolutionSet);
        //UpdateVector based on higher solutions
        double[] newProbabilityVector = computeNewProbabilityVector(learningRate,geneProbabilityVector,orderedSolutionList);
        configuration.setProbabilityVector(newProbabilityVector);
        return newSolutionSet;
    }

    //Utilizes HebbRule (like in Hebb nets)
    private double[] computeNewProbabilityVector(double learningRate, double[] geneProbabilityVector,
                                                 List<ISolution<SetRepresentation<Integer>>> orderedSolutionList) {
        double[] newGeneProbabilityVector = new double[geneProbabilityVector.length];
        for(int i = 0;i < geneProbabilityVector.length;i++){
            double geneCount = countGenePresence(orderedSolutionList,i);
            newGeneProbabilityVector[i] = (1-learningRate)*geneProbabilityVector[i]+learningRate*(1.0/orderedSolutionList.size())*geneCount;
        }
        return newGeneProbabilityVector;
    }

    private double countGenePresence(List<ISolution<SetRepresentation<Integer>>> orderedSolutionList,int gene) {
        int geneCounter = 0;

        for(ISolution<SetRepresentation<Integer>> solution:orderedSolutionList){
            SetRepresentation<Integer> solutionSetRepresentation = solution.getRepresentation();
            if(solutionSetRepresentation.containsElement(gene))
                geneCounter += 1;
        }

        return geneCounter;
    }

    private List<ISolution<SetRepresentation<Integer>>> computeOrderedSolutionList(boolean isMaximization, int numberOfSelectedSolutions,
                                                                        ISolutionSet<SetRepresentation<Integer>> newSolutionSet) {
        if(isMaximization)
            return newSolutionSet.getHighestValuedSolutions(numberOfSelectedSolutions);
        return newSolutionSet.getLowestValuedSolutions(numberOfSelectedSolutions);
    }

    @Override
    public ISolutionSet<SetRepresentation<Integer>> initialize() throws Exception {
        if(configuration.isDoPopulationInitialization()){
            IRandomNumberGenerator randomGenerator = configuration.getRandomNumberGenerator();
            ISolutionFactory<SetRepresentation<Integer>> solutionFactory = configuration.getSolutionFactory();
            int numberOfSolutions = configuration.getPopulationSize();

            ISolutionSet<SetRepresentation<Integer>> solutionSet = createNewSolutionSet(configuration.getProbabilityVector(),numberOfSolutions);
            IEvaluationFunction<SetRepresentation<Integer>> evaluationFunction = configuration.getEvaluationFunction();
            evaluationFunction.evaluate(solutionSet);
            algorithmState.incrementCurrentIterationNumberOfFunctionEvaluations(solutionSet.getNumberOfSolutions());
            return solutionSet;
        }else{
            ISolutionSet<SetRepresentation<Integer>> solutionSet = configuration.getInitialPopulation();
            IEvaluationFunction<SetRepresentation<Integer>> evaluationFunction = configuration.getEvaluationFunction();
            evaluationFunction.evaluate(solutionSet);
            return solutionSet;
        }
    }

    @Override
    public IAlgorithm<SetRepresentation<Integer>> deepCopy() throws Exception {
        return null;
    }
}
