package assignment13.MPI;
import mpi.*;

public class HelloWorld {
	
	public static void main(String[] args) {
		MPI.Init(args);
		int me = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		System.out.println("Hi from <"+me+">");
		System.out.println("Running with size <"+size+"> processes");
		MPI.Finalize(); 
	}

}
