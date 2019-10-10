package ethz.ch.pp.fib;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class FibonacciMultiCache extends RecursiveTask<BigInteger> implements IFibonacci {

	private static final long serialVersionUID = -5889309543771277318L;

	private static ForkJoinPool pool = new ForkJoinPool();
	private BigInteger[] cache;
	private Object[] locks;


	private int n = 0;
	public FibonacciMultiCache() {
	}

	//called by fib(int)
	private FibonacciMultiCache(int n) {
		this.n=n;
		cache = new BigInteger[n + 1];
		locks = new Object[n + 1];
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new Object();
		}
	}

	//called by compute()
	private FibonacciMultiCache(int n, BigInteger[] cache, Object[] locks){
		this.n=n;
		this.cache=cache;
		this.locks=locks;
	}


	@Override
	public BigInteger fib(int n) {
		return pool.invoke(new FibonacciMultiCache(n));
	}

	@Override
	protected BigInteger compute() {
		if(n<0)  throw new IllegalArgumentException("Negative");
		if(n==0) return BigInteger.ZERO;
		if(n<=2) return BigInteger.ONE;
		
		synchronized (locks[n]) {
			if(cache[n]!=null) return cache[n];
		}
		
		FibonacciMultiCache task1 = new FibonacciMultiCache(n-1, cache,locks);
		FibonacciMultiCache task2 = new FibonacciMultiCache(n-2, cache,locks);
		task1.fork();
		BigInteger res2 = task2.compute();
		BigInteger res = task1.join().add(res2);

		synchronized (locks[n]) {
			cache[n] = res;
		}

		return res;
	}
}