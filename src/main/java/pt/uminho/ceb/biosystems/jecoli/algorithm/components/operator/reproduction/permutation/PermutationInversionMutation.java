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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator.reproduction.permutation;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.permutations.PermutationRepresentationFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PermutationInversionMutation.
 */
public class PermutationInversionMutation extends AbstractPermutationMutationOperator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 476286767033788591L;
	/** The radius. */
	int radius; // length of the inverted segment; if = 0, it is randomly generated  
	
	/**
	 * Instantiates a new permutation inversion mutation.
	 * 
	 * @param solutionFactory the solution factory
	 */
	public PermutationInversionMutation (){
		radius = 0;
	}

	/**
	 * Instantiates a new permutation inversion mutation.
	 * 
	 * @param solutionFactory the solution factory
	 * @param radius the radius
	 */
	public PermutationInversionMutation(int radius){
		this.radius = radius;
	}

	
	/* (non-Javadoc)
	 * @see operators.reproduction.permutations.AbstractPermutationMutationOperator#mutateGenome(core.representation.permutations.PermutationRepresentation)
	 */
	@Override
	protected void mutateGenome(PermutationRepresentation childGenome,PermutationRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{
		int numberOfGenes = childGenome.getNumberOfElements();
		
		int pos = (int) (randomNumberGenerator.nextDouble()*(numberOfGenes));

		int k, i, j, l;
		if(radius==0) k = (int) (randomNumberGenerator.nextDouble()*(numberOfGenes-1));
		else k = radius;
		int n = (int)(k/2);

		for(i=0, j=pos, l=(pos+k-1)%numberOfGenes; i<n; i++,j=(j+1)%numberOfGenes) 
		{
			Integer aux = childGenome.getElement(j);
			childGenome.setElement(j, childGenome.getElement(l) );
			childGenome.setElement(l, aux );
	    	if(l > 0) l--;
	    	else l=numberOfGenes-1;
	  	}
	}

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the radius.
	 * 
	 * @param radius the new radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public PermutationInversionMutation deepCopy() throws Exception {
		return new PermutationInversionMutation(radius);
	}
	
}
