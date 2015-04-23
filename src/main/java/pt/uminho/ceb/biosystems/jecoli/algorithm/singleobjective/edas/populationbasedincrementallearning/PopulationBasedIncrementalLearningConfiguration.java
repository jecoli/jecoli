package pt.uminho.ceb.biosystems.jecoli.algorithm.singleobjective.edas.populationbasedincrementallearning;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.AbstractConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.InvalidConfigurationException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.SetRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.terminationcriteria.InvalidNumberOfIterationsException;

/**
 * Created by ptiago on 05-01-2015.
 */
public class PopulationBasedIncrementalLearningConfiguration extends AbstractConfiguration<SetRepresentation<Integer>> {
    private ISolutionFactory<SetRepresentation<Integer>> solutionFactory;
    private int numberOfSolutionsToSelect;
    private double learningRate;
    protected double[] probabilityVector;

    @Override
    public void verifyConfiguration() throws InvalidConfigurationException {

    }

    @Override
    public IConfiguration<SetRepresentation<Integer>> deepCopy() throws InvalidNumberOfIterationsException, Exception {
        return null;
    }

    public void setSolutionFactory(ISolutionFactory<SetRepresentation<Integer>> solutionFactory) {
        this.solutionFactory = solutionFactory;
    }

    public ISolutionFactory<SetRepresentation<Integer>> getSolutionFactory() {
        return solutionFactory;
    }

    public int getNumberOfSolutionsToSelect() {
        return numberOfSolutionsToSelect;
    }

    public void setNumberOfSolutionsToSelect(int numberOfSolutionsToSelect) {
        this.numberOfSolutionsToSelect = numberOfSolutionsToSelect;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double[] getProbabilityVector() {
        return probabilityVector;
    }

    public void setProbabilityVector(double[] probabilityVector) {
        this.probabilityVector = probabilityVector;
    }

}
