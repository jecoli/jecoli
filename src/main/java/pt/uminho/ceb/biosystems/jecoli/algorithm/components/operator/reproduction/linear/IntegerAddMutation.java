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
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.integer.IntegerRepresentationFactory;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;

// TODO: Auto-generated Javadoc
/**
 * The Class IntegerAddMutation.
 */
public class IntegerAddMutation extends AbstractMutationOperator<ILinearRepresentation<Integer>,IntegerRepresentationFactory> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// maximum number of genes to apply mutation; 
	// actual number of affected genes will be random between 1 and this parameter
	/** The number genes to mutate. */
	int numberGenesToMutate = 1; 

	public IntegerAddMutation(int numberGenesToMutate){
		this.numberGenesToMutate = numberGenesToMutate;
	}
	
	
	@Override
	protected void mutateGenome(ILinearRepresentation<Integer> childGenome,IntegerRepresentationFactory solutionFactory,IRandomNumberGenerator randomNumberGenerator) 
	{

		int size = childGenome.getNumberOfElements();
				
		int maxs = 1;
		if(this.numberGenesToMutate>1) 
			maxs = (int) (randomNumberGenerator.nextDouble()*(this.numberGenesToMutate)+1); 

		int N = (int) (randomNumberGenerator.nextDouble()*(maxs)+1); // number of genes to change
		
		for(int j=0; j<N; j++)
		{
			int pos = (int) (randomNumberGenerator.nextDouble()*(size));
			
			if( randomNumberGenerator.nextDouble() > 0.5) 
			{
				int maxValue = ((IntegerRepresentationFactory)solutionFactory).getUpperBoundGeneLimitList().get(pos);
				childGenome.setElement(pos, (childGenome.getElementAt(pos)+1)%(maxValue));
			}
			else 
			{
				int minValue = ((IntegerRepresentationFactory)solutionFactory).getUpperBoundGeneLimitList().get(pos);
				if ( childGenome.getElementAt(pos) > minValue )
					childGenome.setElement(pos, childGenome.getElementAt(pos)-1 );
				else {
					int maxValue = ((IntegerRepresentationFactory)solutionFactory).getUpperBoundGeneLimitList().get(pos);
					childGenome.setElement(pos, maxValue-1);
				}
			}
		}
		
	}

	@Override
	public IntegerAddMutation deepCopy() {
		return new IntegerAddMutation(numberGenesToMutate);
	}
}
