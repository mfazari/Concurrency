package ethz.ch.pp.fib;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciMulti extends RecursiveTask<BigInteger> implements IFibonacci {

	private static final long serialVersionUID = -8352639308306099303L;

	private final int n;
	public FibonacciMulti(int n) {
		this.n = n;
	}

	@Override
	public BigInteger fib(int n) {

		ForkJoinPool fjp = new ForkJoinPool();
		long t0 = System.currentTimeMillis();
		BigInteger num = fjp.invoke(new FibonacciMulti(n));
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + n + ") FibMulti takes " + (t1 - t0) + " msec. Result: " + num.toString());

		return num;
	}

	@Override
	protected BigInteger compute() {
		if (n <= 10) {
			FibonacciSeq seq = new FibonacciSeq();
			return seq.fib(n);
		}

		FibonacciMulti sc1 = new FibonacciMulti(n-1);
		sc1.fork();

		FibonacciMulti sc2 = new FibonacciMulti(n-2);
		return sc2.compute().add(sc1.join());	
	}

}
