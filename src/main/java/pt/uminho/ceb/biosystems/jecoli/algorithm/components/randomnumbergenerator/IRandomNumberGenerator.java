package pt.uminho.ceb.biosystems.jecoli.algorithm.components.randomnumbergenerator;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;


public interface IRandomNumberGenerator extends IDeepCopy {
	int nextInt();
	long nextLong();
	double nextDouble();
	float nextFloat();
	void setSeed(long seed);
	IRandomNumberGenerator deepCopy() throws Exception;
	double nextGaussian();
	public int nextInt(int n);
}
