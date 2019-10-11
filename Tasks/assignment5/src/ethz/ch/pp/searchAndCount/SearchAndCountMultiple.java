package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

import java.util.concurrent.ForkJoinPool;

public class SearchAndCountMultiple {	
	
	//TODO: implement using ForkJoinPool and RecursiveTask
	public static Integer countNoAppearances(int[] input, int cutOff,
			Workload.Type wt, int noThreads) {


		long t0 = System.currentTimeMillis();
		SearchAndCountMultiple counter = new SearchAndCountMultiple(input, wt);
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCount Single takes "
				+ (t1 - t0) + " msec");

		ForkJoinPool forkJoinPool = new ForkJoinPool(noThreads);
		int num = forkJoinPool.invoke(counter);

		return num;
	}

	// count the number of elements for which doWork returns true
	private int count() {
		int count = 0;
		for (int i = 0; i < input.length; i++) {
			if (Workload.doWork(input[i], workloadType))
				count++;
		}
		return count;
	}

}