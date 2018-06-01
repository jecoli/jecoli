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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.linear;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentationFactory;

/**
 * The Class LinearGenomeShrinkMutation.
 */
public class LinearGenomeShrinkMutation<G> extends AbstractMutationOperator<ILinearRepresentation<G>, ILinearRepresentationFactory<G>> {

    /**
     *
     */
    private static final long serialVersionUID = 7223358769996538104L;
    /**
     * The number genes to remove.
     */
    int numberGenesToRemove = 1;


    /**
     * Instantiates a new linear genome shrink mutation.
     *
     */
    public LinearGenomeShrinkMutation() {
        numberGenesToRemove = 1;
    }

    /**
     * Instantiates a new linear genome shrink mutation.
     *
     * @param numberGenesToRemove the number genes to remove
     */
    public LinearGenomeShrinkMutation(int numberGenesToRemove) {
        this.numberGenesToRemove = numberGenesToRemove;
    }

    @Override
    protected void mutateGenome(ILinearRepresentation<G> childGenome, ILinearRepresentationFactory<G> solutionFactory, IRandomNumberGenerator randomNumberGenerator) {
        int numberOfGenes = childGenome.getNumberOfElements();
        int N = randomNumberGenerator.nextInt(numberGenesToRemove) + 1;

        if (numberOfGenes - N >= 1) {
            for (int k = 0; k < N; k++) {
                int pos = randomNumberGenerator.nextInt(numberOfGenes);//(int) (randomNumberGenerator.nextDouble()*(numberOfGenes-k));
                childGenome.removeElementAt(pos);
            }
        }
    }

    /**
     * Gets the number genes to remove.
     *
     * @return the number genes to remove
     */
    public int getNumberGenesToRemove() {
        return numberGenesToRemove;
    }

    /**
     * Sets the number genes to remove.
     *
     * @param numberGenesToRemove the new number genes to remove
     */
    public void setNumberGenesToRemove(int numberGenesToRemove) {
        this.numberGenesToRemove = numberGenesToRemove;
    }

    @Override
    public LinearGenomeShrinkMutation<G> deepCopy() {
        return new LinearGenomeShrinkMutation<G>(numberGenesToRemove);
    }
}
