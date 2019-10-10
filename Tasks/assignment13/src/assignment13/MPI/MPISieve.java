package assignment13.MPI;

import mpi.MPI;

public class MPISieve {
/**
	 * Create an MPI parallel prime factor sieve.
	 * 
	 * @param The maximal number up to which to compute prime
	 *        numbers.	
	 * @param NumRanks The number of different Ranks that are run.
	 * @param Rank The rank of this node.
	 */
	MPISieve(int MaxNumber, int Rank, int NumRanks) {
	}
	
	/**
	 * Count the number of primes.
	 * 
	 * @return
	 */
	public int getNumPrimes() {
		markAllNonPrimes();
		return countPrimes();
	}
	
	/**
	 * Mark all numbers that are non-primes.
	 */
	private void markAllNonPrimes() {
	}

	/**
	 * Count the number of primes within the local partition
	 * and obtain the overall number of primes by reducing
	 * across all ranks.
	 * 
	 * @return The total number of primes.
	 */
	private int countPrimes() {
		return -1;
	}
}