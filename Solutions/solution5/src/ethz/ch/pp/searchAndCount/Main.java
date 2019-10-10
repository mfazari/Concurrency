package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.DatasetGenerator;
import ethz.ch.pp.util.Workload;

public class Main {

	public static void main(String[] args) {

		DatasetGenerator dg = new DatasetGenerator(1024*1024*5);
		int[] input = dg.generate();

		taskA(input, Workload.Type.HEAVY);
		taskA(input, Workload.Type.HEAVY);
		
		taskA(input, Workload.Type.HEAVY);
		taskB(input, 100, Workload.Type.HEAVY, 4);
		
		taskB(input, 1, Workload.Type.HEAVY, 4);
		taskB(input, 10, Workload.Type.HEAVY, 4);
		
		taskB(input, 100, Workload.Type.HEAVY, 1);
		taskB(input, 100, Workload.Type.HEAVY, 2);
		taskB(input, 100, Workload.Type.HEAVY, 3);
		taskB(input, 100, Workload.Type.HEAVY, 4);
		taskB(input, 100, Workload.Type.HEAVY, 6);
		taskB(input, 100, Workload.Type.HEAVY, 5);
		
		taskB(input, 1000, Workload.Type.HEAVY, 4);
		taskB(input, 10000, Workload.Type.HEAVY, 4);
	}

	public static void taskA(int[] input, Workload.Type wt){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			SearchAndCountSingle.countNoAppearances(input, wt);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("!!For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCount Single takes "
				+ ((t1 - t0)/5) + " msec");
	}

	public static void taskB(int[] input, int cutoff, Workload.Type wt, int numThreads){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			SearchAndCountMultiple.countNoAppearances(input, cutoff, wt, numThreads);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("!!For (inputsize=" + input.length + ",cutoff="
				+ cutoff + ",workload=" + wt + ",nothreads=" + numThreads
				+ ") SearchAndCount Multiple takes " + ((t1 - t0)/5) + " msec");
	}

}
