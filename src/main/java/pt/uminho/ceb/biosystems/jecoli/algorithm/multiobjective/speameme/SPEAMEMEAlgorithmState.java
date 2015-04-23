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
package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.speameme;

import java.util.GregorianCalendar;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmResult;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.AlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IAlgorithm;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.algorithm.IArchivedAlgorithmState;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.configuration.IConfiguration;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.representation.IRepresentation;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.solution.ISolutionSet;
import pt.uminho.ceb.biosystems.jecoli.algorithm.components.statistics.StatisticsConfiguration;


// TODO: Auto-generated Javadoc
/**
 * The Class SPEA2AlgorithmState.
 */
public class SPEAMEMEAlgorithmState<T extends IRepresentation> extends AlgorithmState<T> implements IArchivedAlgorithmState<T> {
	
	private static final long serialVersionUID = 3971279845831405838L;
	
	/** The archive. */
	protected ISolutionSet<T> archive;

	/**
	 * Instantiates a new SPEA2 algorithm state.
	 * 
	 * @param algorithm the algorithm
	 */
	public SPEAMEMEAlgorithmState(IAlgorithm<T> algorithm) {
		super(algorithm);
	}
	

	@Override
	public void initializeState() {
        currentIteration = 0;
        overallTime = 0;
        currentTime = GregorianCalendar.getInstance().getTimeInMillis();
        lastIterationTime = 0;
        solutionSet = null;
        archive = null;
        IConfiguration<T> configuration = algorithm.getConfiguration();
        StatisticsConfiguration statisticsConfiguration = configuration.getStatisticConfiguration();
        algorithmResult = new AlgorithmResult<T>(statisticsConfiguration.getNumberOfBestSolutionsToKeepPerRun(), statisticsConfiguration.isVerbose());
    }

	/**
	 * Update archive state.
	 * 
	 * @param newArchive the new archive
	 */
	public void updateArchiveState(ISolutionSet<T> newArchive){
		archive = newArchive; 
	}
	
	/**
	 * Gets the archive.
	 * 
	 * @return the archive
	 */
	public ISolutionSet<T> getArchive() {
		return archive;
	}

	/**
	 * Sets the archive.
	 * 
	 * @param archive the new archive
	 */
	public void setArchive(ISolutionSet<T> archive) {
		this.archive = archive;
	}

}
