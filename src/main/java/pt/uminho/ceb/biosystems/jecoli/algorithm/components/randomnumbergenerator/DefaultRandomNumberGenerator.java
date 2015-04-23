package pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator;

import java.util.Random;

public class DefaultRandomNumberGenerator implements IRandomNumberGenerator{
	
	private static final long serialVersionUID = -5206544032557061559L;
	
	protected boolean userSeed;
	protected long seed;
	protected Random randomNumberGenerator;
	
	public DefaultRandomNumberGenerator(long seed) {
		userSeed = true;
		this.seed = seed;
		randomNumberGenerator = new Random(seed);
	}
	
	public DefaultRandomNumberGenerator() {
		randomNumberGenerator = new Random();
		userSeed = false;
	}

	@Override
	public IRandomNumberGenerator deepCopy(){
		if(userSeed)
			return new DefaultRandomNumberGenerator(seed);
		return new DefaultRandomNumberGenerator();
	}

	@Override
	public int nextInt() {
		return randomNumberGenerator.nextInt();
	}

	@Override
	public long nextLong() {
		return randomNumberGenerator.nextLong();
	}

	@Override
	public double nextDouble() {
		return randomNumberGenerator.nextDouble();
	}

	@Override
	public float nextFloat() {
		return randomNumberGenerator.nextFloat();
	}

	@Override
	public void setSeed(long seed) {
		this.seed = seed;
		userSeed = true;
		randomNumberGenerator = new Random(seed);
	}

	@Override
	public double nextGaussian() {
		return randomNumberGenerator.nextGaussian();
	}

	@Override
	public int nextInt(int n){
		return randomNumberGenerator.nextInt(n);
	}
	
}
