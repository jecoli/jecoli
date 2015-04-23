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
package pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm;

import java.io.Serializable;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionContainer;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.AlgorithmRunStatistics;


/**
 * The Class AlgorithmResult.
 * Keeps the results of the optimization algorithms.
 */
public class AlgorithmResult<T extends IRepresentation> implements IAlgorithmResult<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** The algorithm statistics. */
    protected IAlgorithmStatistics<T> algorithmStatistics;
    
    /**
     * Instantiates a new algorithm result.
     * 
     * @param numberOfSolutionsToKeepPerRun the number of solutions to keep per run
     */
    public AlgorithmResult(int numberOfSolutionsToKeepPerRun, boolean isVerbose) {
        algorithmStatistics = new AlgorithmRunStatistics<T>(numberOfSolutionsToKeepPerRun,isVerbose);
    }
  
 
    public void processAlgorithmState(AlgorithmState<T> algorithmState) {
    	algorithmStatistics.calculateStatistics(algorithmState);        
    }


    @Override
    public IAlgorithmStatistics<T> getAlgorithmStatistics() {
        return algorithmStatistics;
    }

    @Override
    public ISolutionContainer<T> getSolutionContainer() {
        return algorithmStatistics.getSolutionContainer();
    }
    
    public void setSolutionContainer(ISolutionContainer<T> container){
    	this.algorithmStatistics.setSolutionContainer(container);
    }


}