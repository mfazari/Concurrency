package ethz.ch.pp.fib;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FibonacciSeqCache implements IFibonacci {

	private List<BigInteger> mCache = new ArrayList<BigInteger>();
	
	@Override
	public BigInteger fib(int n) {
		mCache.clear();		
		while (mCache.size() <= n) {
			mCache.add(BigInteger.ZERO);			
		}
		
		return compute(n);			
	}	
	
	private BigInteger compute(int n) {
		if (n <= 1) return BigInteger.valueOf(n);
		
		if (mCache.get(n) != BigInteger.ZERO) {
			return mCache.get(n);
		}
		
		BigInteger result = compute(n - 1).add(compute(n - 2));
		mCache.set(n, result);
		return result;
	}
	
	
	
}
