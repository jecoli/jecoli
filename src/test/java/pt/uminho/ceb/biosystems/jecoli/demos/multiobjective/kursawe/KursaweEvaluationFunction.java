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
package pt.uminho.ceb.biosystems.jecoli.demos.multiobjective.kursawe;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.AbstractMultiobjectiveEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.IEvaluationFunction;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.evaluationfunction.InvalidEvaluationFunctionInputDataException;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.linear.ILinearRepresentation;



// TODO: Auto-generated Javadoc
/**
 * The Class KursaweEvaluationFunction.
 */
public class KursaweEvaluationFunction<T extends IRepresentation> extends AbstractMultiobjectiveEvaluationFunction<ILinearRepresentation<Double>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9047419840577490750L;

	/**
	 * Instantiates a new kursawe evaluation function.
	 * 
	 * @param isMaximization the is maximization
	 */
	public KursaweEvaluationFunction(boolean isMaximization) {
		super();
	}

	/* (non-Javadoc)
	 * @see core.EvaluationFunction#evaluate(core.representation.IRepresentation)
	 */
	@Override
	public Double[] evaluateMO(ILinearRepresentation<Double> solutionRepresentation) throws Exception {
		Double[] fitnesses = new Double[2]; // two objectives
				        
	    double aux, xi, xj;
	    
	    //objective 1
	    double f0 = 0.0;	    
	    for (int var = 0; var < solutionRepresentation.getNumberOfElements() -1 ; var++) {
	    	xi = solutionRepresentation.getElementAt(var) * solutionRepresentation.getElementAt(var); 
	    	xj = solutionRepresentation.getElementAt(var+1) * solutionRepresentation.getElementAt(var+1);  

	    	aux = (-0.2) * Math.sqrt(xi + xj);
	    	f0 += (-10.0) * Math.exp(aux);
	    }
	        
	    //objective 2
	    double f1 = 0.0;	        
	    for (int var = 0; var < solutionRepresentation.getNumberOfElements() ; var++) {
	      f1 += (Math.pow(Math.abs(solutionRepresentation.getElementAt(var)),0.8) + 5.0 * Math.sin(Math.pow(solutionRepresentation.getElementAt(var),3.0)));
	    } 
	        
	    fitnesses[0]=f0 * 1;
	    fitnesses[1]=f1 * 1;
	    
		return fitnesses;		
	}

	@Override
	public void verifyInputData()
			throws InvalidEvaluationFunctionInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IEvaluationFunction<ILinearRepresentation<Double>> deepCopy()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

}
