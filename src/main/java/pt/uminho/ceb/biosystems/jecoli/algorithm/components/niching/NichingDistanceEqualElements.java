package pt.uminho.ceb.biosystems.jecoli.algorithm.components.niching;

import java.util.TreeSet;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.set.ISetRepresentation;

/**
 * Created by ptiago on 06-01-2015.
 */
public class NichingDistanceEqualElements implements INichingDistance<ISetRepresentation<Integer>> {
    @Override
    public double computeDistance(ISetRepresentation<Integer> individualRepresentationI, ISetRepresentation<Integer> individualRepresentationJ) {
        TreeSet<Integer> iRepresentation = individualRepresentationI.getGenome();
        TreeSet<Integer> jRepresentation = individualRepresentationJ.getGenome();
        int counter = 0;
        for(Integer iValue:iRepresentation)
            if(jRepresentation.contains(iValue))
                counter += 1;
        return counter;
    }
}
