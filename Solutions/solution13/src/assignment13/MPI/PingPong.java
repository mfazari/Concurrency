package assignment13.MPI;
import mpi.*;

public class PingPong {
	
	static private int BufferSize = 1;
	static private int Buffer[] = new int[BufferSize];
	static private int Iterations = 512;
	
	public static void main(String[] args) {
		MPI.Init(args);
		int Rank = MPI.COMM_WORLD.Rank();
		int NumRanks  = MPI.COMM_WORLD.Size();
		
		if (NumRanks != 2) {
			System.out.println("Benchmark expected to be run by 2 process only.");
			System.exit(0);
		}
		
		if (Rank == 0) {
			Buffer[0] = 0;
			for (int i = 0; i < Iterations; i++) {
				long Start = System.nanoTime();
				
				MPI.COMM_WORLD.Send(Buffer, 0, BufferSize, MPI.INT, 1, 0);
				MPI.COMM_WORLD.Recv(Buffer, 0, BufferSize, MPI.INT, 1, 0);
				Buffer[0]+=1;
				
				long End = System.nanoTime();
				double Passed = (End - Start) / 1000.0;
				System.out.println("Time: " + Passed + "us, Counter: " + Buffer[0]);
			}
		} else {
			for (int i = 0; i < Iterations; i++) {
				MPI.COMM_WORLD.Recv(Buffer, 0, BufferSize, MPI.INT, 0, 0);
				Buffer[0]+=1;
				MPI.COMM_WORLD.Send(Buffer, 0, BufferSize, MPI.INT, 0, 0);
			}	
		}
		MPI.Finalize();
		System.out.println("Rank <" + Rank + "> stopped");
	}
}
