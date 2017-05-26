package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective;

import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.impl.ArrayPoint;

public class JMetalUtils {
	
	public static Front convertMatrixToFront(double[][] matrix) {
		
		ArrayFront front = new ArrayFront(matrix.length, matrix[0].length);
	
		for(int i=0; i<front.getNumberOfPoints(); i++){
			ArrayPoint arrayPoint = new ArrayPoint(matrix[i]);
			front.setPoint(i, arrayPoint);
		}
		
		return front;
	}
	
	public static double hypervolume(Front front, Front referenceFront) {

	    Front invertedFront;
	    invertedFront = FrontUtils.getInvertedFront(front);

	    int numberOfObjectives = referenceFront.getPoint(0).getNumberOfDimensions() ;

	    // STEP4. The hypervolume (control is passed to the Java version of Zitzler code)
	    Hypervolume<?> hyper = new Hypervolume<>();	   
	    return hyper.calculateHypervolume(FrontUtils.convertFrontToArray(invertedFront),
	        invertedFront.getNumberOfPoints(), numberOfObjectives);
	  }
}
