package pt.uminho.ceb.biosystems.jecoli.algorithm.components.niching;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;

/**
 * Created by ptiago on 05-01-2015.
 */
public class NichingConfiguration<R extends IRepresentation> {
    protected double alpha;
    protected double beta;
    protected int maximumNumberOfIndividualsPerNiche;
    protected double individualRadius;
    protected double clearingRadius;
    protected INichingDistance<R> nichingDistanceProcessor;
    protected double defaultFitnessValue;

    public NichingConfiguration() {
        defaultFitnessValue = 0;
        individualRadius = 1;
        clearingRadius = 1000;
        maximumNumberOfIndividualsPerNiche = 1000;
        alpha = 1;
        beta = 1;
    }

    public double getDefaultFitnessValue() {
        return defaultFitnessValue;
    }

    public void setDefaultFitnessValue(double defaultFitnessValue) {
        this.defaultFitnessValue = defaultFitnessValue;
    }

    public double getClearingRadius() {
        return clearingRadius;
    }

    public void setClearingRadius(double clearingRadius) {
        this.clearingRadius = clearingRadius;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public int getMaximumNumberOfIndividualsPerNiche() {
        return maximumNumberOfIndividualsPerNiche;
    }

    public void setMaximumNumberOfIndividualsPerNiche(int maximumNumberOfIndividualsPerNiche) {
        this.maximumNumberOfIndividualsPerNiche = maximumNumberOfIndividualsPerNiche;
    }

    public double getIndividualRadius() {
        return individualRadius;
    }

    public void setIndividualRadius(double individualRadius) {
        this.individualRadius = individualRadius;
    }

    public INichingDistance<R> getNichingDistanceProcessor() {
        return nichingDistanceProcessor;
    }

    public void setNichingDistanceProcessor(INichingDistance<R> nichingDistanceProcessor) {
        this.nichingDistanceProcessor = nichingDistanceProcessor;
    }
}
