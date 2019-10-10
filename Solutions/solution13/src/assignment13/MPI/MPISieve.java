package assignment13.MPI;

import mpi.MPI;

public class MPISieve {
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
	 * The MPI rank of the current node.
	 */
	int Rank;
	
	/**
	 * The number of MPI ranks among which to partition the
	 * problem.
	 */
	int NumRanks;
	
	/**
	 * The lower value starting from which this rank is in
	 * charge of computing prime numbers.
	 */
	int LowerVal;
	
	/**
	 * The upper value (exclusive) until which this rank is
	 * in charge of computing prime numbers.
	 */
	int UpperVal;
	
	/**
	 * The size of the partitions into which the overall problem
	 * is split.
	 */
	int PartitionSize;
	
	/**
	 * Create an MPI parallel prime factor sieve.
	 * 
	 * @param The maximal number up to which to compute prime
	 *        numbers.	
	 * @param NumRanks The number of different Ranks that are run.
	 * @param Rank The rank of this node.
	 */
	MPISieve(int MaxNumber, int Rank, int NumRanks) {
		this.MaxNumber = MaxNumber;
		this.Rank = Rank;
		this.NumRanks = NumRanks;
		
		PartitionSize = (int) Math.ceil(((double)MaxNumber) / NumRanks);
		LowerVal = PartitionSize * Rank;
		UpperVal = PartitionSize * (Rank + 1);
		if (UpperVal > MaxNumber)
			UpperVal = MaxNumber;
		
		PossiblyPrime = new int[PartitionSize];
		
		for (int i = 0; i < PartitionSize; i++)
			PossiblyPrime[i] = 1;
		
		if (Rank == 0) {
			// 0 and 1 are not prime numbers.
			PossiblyPrime[0] = 0;
			PossiblyPrime[1] = 0;
		}
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
	 * Mark all numbers that are multiples of "Prime".
	 *  
	 * Scan the range of numbers managed by this process and
	 * mark all numbers as nun-primes that are non-trivial
	 * multiples of "Prime".
	 * 
	 * @param Prime The prime factor we use.
	 */
	private void markPrimeMultiples(int Prime) {
		int Start = LowerVal;
		if (Start % Prime != 0)
			Start = LowerVal - (LowerVal %  Prime) + Prime;
		
		for (int i = Start; i < UpperVal; i += Prime) {
			if (i != Prime)
				PossiblyPrime[getLocalIndex(i)] = 0;
		}
	}
	
	/**
	 * Get the local index for "Number".
	 * 
	 * @param Number The number to look up.
	 * @return The local index of "Number".
	 */
	public int getLocalIndex(int Number) {
		return Number - LowerVal;
	}
	
	/**
	 * Check if a number is marked as possibly prime.
	 * 
	 * @param Index The number to check. 
	 * @return Returns true if the number may be prime.
	 */
	public boolean isPossiblyPrime(int Number) {
		return PossiblyPrime[getLocalIndex(Number)] == 1;
	}
	
	/**
	 * Mark all numbers that are non-primes.
	 */
	private void markAllNonPrimes() {
		int CurrentPrime = 2;
		
		double Upper = Math.sqrt(MaxNumber);
		while (CurrentPrime <= Upper) {
			markPrimeMultiples(CurrentPrime);
			
			if (Rank == 0) {
				for (int i = CurrentPrime + 1; i <= UpperVal; i++) {
					if (isPossiblyPrime(i)) {
						CurrentPrime = i;
						break;
					}
				}	
			}
				
			int Buffer[] = { CurrentPrime };
			MPI.COMM_WORLD.Bcast(Buffer, 0, 1, MPI.INT, 0);
			CurrentPrime = Buffer[0];
		}
	}

	/**
	 * Count the number of primes within the local partition
	 * and obtain the overall number of primes by reducing
	 * across all ranks.
	 * 
	 * @return The total number of primes.
	 */
	private int countPrimes() {
		int Count = 0;
		for (int i = LowerVal; i < UpperVal; i++)
			if (isPossiblyPrime(i))
				Count++;
		
		int SendBuffer[] = { Count };
		int ReceiveBuffer[] = new int[1];
		MPI.COMM_WORLD.Reduce(SendBuffer, 0, ReceiveBuffer, 0, 1, MPI.INT, MPI.SUM, 0);
		Count = ReceiveBuffer[0];
		return Count;
	}
}