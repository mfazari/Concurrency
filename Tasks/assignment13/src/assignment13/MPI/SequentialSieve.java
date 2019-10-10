package assignment13.MPI;

public class SequentialSieve {
	/**
	 * A vector of numbers in which each number is marked
	 * either as "possibly prime" (value is '1') or "known
	 * not prime" (value is '0').
	 */
	int PossiblyPrime[];
	
	/**
	 * The number until which to look for prime numbers.
	 */
	int MaxNumber;
	
	/**
	 * Create a sequential prime factor sieve.
	 * 
	 * @param The maximal number up to which to compute prime
	 *        numbers.	
	 */
	SequentialSieve(int MaxNumber) {
		this.MaxNumber = MaxNumber;
		this.PossiblyPrime = new int[MaxNumber];
		
		for (int i = 0; i < MaxNumber; i++)
			PossiblyPrime[i] = 1;
		
		// 0 and 1 are not prime numbers.
		PossiblyPrime[0] = 0;
		PossiblyPrime[1] = 0;
	}
	
	/**
	 * Count the number of prime values from zero to MaxNumber.
	 * @return The number of prime values.
	 */
	public int getNumPrimes() {
		markAllNonPrimes();
		return countPrimes();
	}
	
	
	/**
	 * Check if a number is marked as possibly prime.
	 * 
	 * @param Index The number to check. 
	 * @return Returns true if the number may be prime.
	 */
	public boolean isPossiblyPrime(int Number) {
		return PossiblyPrime[Number] == 1;
	}
	
	/**
	 * Mark non-prime numbers that are a multiple of
	 * "Prime".
	 * 
	 * @param Prime The prime number that identifies the
	 *              numbers to mark.
	 */
	private void markPrimeMultiples(int Prime) {
		// Start from Prime * 2 instead of 'Prime', as 'Prime'
		// by itself is just a single factor and can consequently
		// be a prime.
		for (int i = 2 * Prime; i < MaxNumber; i += Prime)
			PossiblyPrime[i] = 0;
	}
	
	/**
	 * Mark all numbers that are non-primes.
	 */
	private void markAllNonPrimes() {
		int MaxFactor = ((int) Math.sqrt(MaxNumber)) + 1; 
		for (int i = 2; i < MaxFactor; i++) {
			if (isPossiblyPrime(i))
			  markPrimeMultiples(i);			
		}
	}
	
	/**
	 * Count all numbers marked as possibly Prime.
	 * 
	 * This function is expected to be called _after_ all actual
	 * primes have been identified.
	 * 
	 * @return The number of prime numbers in [0, MaxNumber[.
	 */
	private int countPrimes() {
		int Count = 0;
		for (int i = 2; i < MaxNumber; i++)
			if (isPossiblyPrime(i))
				Count++;
		return Count;
	}
}
