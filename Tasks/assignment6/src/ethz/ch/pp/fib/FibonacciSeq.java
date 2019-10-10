package ethz.ch.pp.fib;

import java.math.BigInteger;

public class FibonacciSeq implements IFibonacci {

	@Override
	public BigInteger fib(int n) {
		if (n <= 1) return BigInteger.valueOf(n);
		return fib(n - 1).add(fib(n - 2));
	}

}
