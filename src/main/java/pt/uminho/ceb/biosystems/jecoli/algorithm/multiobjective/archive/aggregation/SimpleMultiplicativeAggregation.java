package pt.uminho.ceb.biosystems.jecoli.algorithm.multiobjective.archive.aggregation;

import java.util.Arrays;

public class SimpleMultiplicativeAggregation implements IAggregationFunction {

	@Override
	public Double aggregate(Double[] values) {
		
		int size = values.length;
		
		if(size==0)
			return 1.0;
		else if (size == 1)
			return values[0];
		else
			return values[0] * aggregate(Arrays.copyOfRange(values, 1, size));
		
	}

}
