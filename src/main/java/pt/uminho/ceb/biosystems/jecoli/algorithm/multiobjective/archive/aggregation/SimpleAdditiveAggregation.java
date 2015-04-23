package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.aggregation;


public class SimpleAdditiveAggregation implements IAggregationFunction{

	@Override
	public Double aggregate(Double[] values) {
			
		double toret = 0;
		
		for(int i=0; i< values.length; i++){
			toret += values[i];
		}
		
//		System.out.println("Aggregation: "+toret);
		
		return toret;
	}

}
