package assignment13.MPI;

import mpi.MPI;


public class PrimeSieve {
	public static void main(String[] args) {
		
		long Start, NumPrimes, Stop;
		int MaxNumber = 1000 * 1000 * 100;
		boolean RunSequential = true;
		boolean RunMPI = false;
		if (RunSequential) {
			Start = System.nanoTime();
			SequentialSieve S = new SequentialSieve(MaxNumber);
			NumPrimes = S.getNumPrimes();
			Stop = System.nanoTime();		
			System.out.println("SEQ: There are " + NumPrimes + " in the range [0," + MaxNumber + "[");
			System.out.println("SEQ: Timing: " + ((Stop - Start) / 1000000));
		}
		
		
		
		if (RunMPI) {
			Start = System.nanoTime();
			MPI.Init(args);
			int ID = MPI.COMM_WORLD.Rank();
			int Partitions = MPI.COMM_WORLD.Size();
			MPISieve MS = new MPISieve(MaxNumber, ID, Partitions);
			NumPrimes = MS.getNumPrimes();
			Stop = System.nanoTime();
			MPI.Finalize();
		
			if (ID == 0) {
				System.out.println("MPI: Ranks: " + Partitions); 
				System.out.println("MPI: Timing: " + ((Stop - Start) / 1000000)); 
				System.out.println("MPI: There are " + NumPrimes + " in the range [0," + MaxNumber + "[");
			}
		}
	}
}



