package pt.uminho.ceb.biosystems.jecoli.jUnitsTests.randomnumbergenerator;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator.IRandomNumberGenerator;

public class DummyRandomNumberGenerator implements IRandomNumberGenerator {
	protected double currentNumber;
	
	public DummyRandomNumberGenerator(){
		currentNumber = 0.0;
	}
	
	public DummyRandomNumberGenerator(DummyRandomNumberGenerator generator){
		currentNumber = generator.currentNumber;
	}
	
	
	@Override
	public IRandomNumberGenerator deepCopy()  {
		return new DummyRandomNumberGenerator(this);
	}
	
	protected boolean isEven(double number){
		return ((int)number) % 2 == 0;
	}

	@Override
	public int nextInt() {
		double value = currentNumber;
		currentNumber++;
		if(isEven(value))
			return 1;
		return 0;
	}

	@Override
	public long nextLong() {
		double value = currentNumber;
		currentNumber++;
		if(isEven(value))
			return 1;
		return 0;
	}

	@Override
	public double nextDouble() {
		double value = currentNumber;
		currentNumber++;
		if(isEven(value))
			return 1;
		return 0;
	}

	@Override
	public float nextFloat() {
		double value = currentNumber;
		currentNumber++;
		if(isEven(value))
			return 1;
		return 0;
	}

	@Override
	public void setSeed(long seed) {}

	@Override
	public double nextGaussian() {
		return 0;
	}

	@Override
	public int nextInt(int n) {
		return 0;
	}

}
