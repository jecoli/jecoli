package pt.uminho.ceb.biosystems.jecoli.algorithm.components.niching;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolution;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.SolutionSet;

/**
 * Created by ptiago on 05-01-2015.
 */
public class NichingProcessor {

    public static <T extends IRepresentation> ISolutionSet<T> generateNichingSolutionSet(ISolutionSet<T> currentSolutionSet,
                                                                                         NichingConfiguration<T> nichingConfiguration) {
        ISolutionSet<T> newSolutionSet = new SolutionSet<>();
        int numberOfSolutions = currentSolutionSet.getNumberOfSolutions();
        double[][] distanceMatrix = new double[numberOfSolutions][numberOfSolutions];
        processDistanceMatrix(currentSolutionSet, distanceMatrix, nichingConfiguration.getNichingDistanceProcessor());
        processNewSolutionSet(currentSolutionSet,nichingConfiguration,newSolutionSet, distanceMatrix);
        processNicheClearing(nichingConfiguration,newSolutionSet,distanceMatrix);
        return newSolutionSet;
    }

    private static <T extends IRepresentation> void processNicheClearing(NichingConfiguration<T> nichingConfiguration,
                                                                         ISolutionSet<T> newSolutionSet, double[][] distanceMatrix) {
        double clearingRadius = nichingConfiguration.getClearingRadius();
        double defaultFitnessValue = nichingConfiguration.getDefaultFitnessValue();
        int maximumNumberOfElementsInTheNiche = nichingConfiguration.getMaximumNumberOfIndividualsPerNiche();
        int currentNumberOfElementsInNiche = 0;


        for(int i = 0;i < distanceMatrix.length;i++)
            for(int j = 0;j < distanceMatrix.length;j++){
                if(currentNumberOfElementsInNiche < maximumNumberOfElementsInTheNiche){
                    double distance = 0;
                    if(i < j) distance = distanceMatrix[i][j];
                    if(distance < clearingRadius)
                        currentNumberOfElementsInNiche += 1;
                }else {
                    for(int w = j; w < distanceMatrix.length;w++)
                        newSolutionSet.getSolution(w).setScalarFitnessValue(defaultFitnessValue);
                    return;
                }
            }
    }

    private static <T extends IRepresentation> void processNewSolutionSet(ISolutionSet<T> currentSolutionSet,NichingConfiguration<T> nichingConfiguration,
                                                                          ISolutionSet<T> newSolutionSet, double[][] distanceMatrix) {
        for(int i = 0; i < currentSolutionSet.getNumberOfSolutions();i++){
            ISolution<T> solution = currentSolutionSet.getSolution(i);
            double newFitnessValueNumerator = Math.pow(solution.getScalarFitnessValue(), nichingConfiguration.getBeta());
            double individualSharedFitnessValueDenominator = computeIndividualSharedFitnessValue(nichingConfiguration,distanceMatrix,i);
            double newFitnessValue = newFitnessValueNumerator/individualSharedFitnessValueDenominator;
            ISolution<T> newSolution = solution.deepCopy();
            newSolution.setScalarFitnessValue(newFitnessValue);
            newSolutionSet.add(newSolution);
        }

    }

    private static <T extends IRepresentation> double computeIndividualSharedFitnessValue(NichingConfiguration<T> nichingConfiguration,
                                                                                          double[][] distanceMatrix, int currentIndividualIndex) {
        double individualSharedFitnessValueDenominator = 0;
        double radius = nichingConfiguration.getIndividualRadius();
        double alpha = nichingConfiguration.getAlpha();

        for(int i = 0; i < distanceMatrix.length;i++){
            double distance = distanceMatrix[currentIndividualIndex][i];
            if(distance < radius){
                individualSharedFitnessValueDenominator += 1-Math.pow(distance/radius,alpha);
            }
        }
        return individualSharedFitnessValueDenominator;
    }

    private static <T extends IRepresentation> void processDistanceMatrix(ISolutionSet<T> currentSolutionSet,
                                                                          double[][] distanceMatrix, INichingDistance<T> nichingDistanceProcessor) {
        for (int i = 0; i < currentSolutionSet.getNumberOfSolutions(); i++) {
            ISolution<T> currentSolutionI = currentSolutionSet.getSolution(i);
            T solutionIRepresentation = currentSolutionI.getRepresentation();
            for (int j = 0; j < currentSolutionSet.getNumberOfSolutions(); j++) {
                if (i < j) {
                    ISolution<T> currentSolutionJ = currentSolutionSet.getSolution(j);
                    T solutionJRepresentation = currentSolutionJ.getRepresentation();
                    double distance = nichingDistanceProcessor.computeDistance(solutionIRepresentation, solutionJRepresentation);
                    distanceMatrix[i][j] = distance;
                    distanceMatrix[j][i] = distance;
                }
            }
        }
    }

}
