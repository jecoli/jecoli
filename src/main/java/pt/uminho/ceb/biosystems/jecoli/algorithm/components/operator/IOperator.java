package pt.uminho.ceb.biosystems.jecoli.algorithm.components.operator;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.IDeepCopy;


public interface IOperator extends IDeepCopy{
	
	IOperator deepCopy() throws Exception;
}
