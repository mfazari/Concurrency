package ethz.ch.pp.searchAndCount;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import ethz.ch.pp.util.Workload;


public class SearchAndCountMultiple extends RecursiveTask<Integer> {

	private int[] input;
	private int start;
	private int length;
	private int cutOff;
	private Workload.Type workloadType;

	private SearchAndCountMultiple(int[] input, int start, int length,
			int cutOff, Workload.Type wt) {
		this.input = input;
		this.start = start;
		this.length = length;
		this.cutOff = cutOff;
		this.workloadType = wt;
	}

	private static final long serialVersionUID = 3396462358908632200L;

	public static Integer countNoAppearances(int[] input, int cutOff,
			Workload.Type wt, int noThreads) {
		SearchAndCountMultiple countapp = new SearchAndCountMultiple(input, 0,
				input.length, cutOff, wt);
		ForkJoinPool fjp = new ForkJoinPool(noThreads);
		long t0 = System.currentTimeMillis();
		int num = fjp.invoke(countapp);
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",cutoff="
				+ cutOff + ",workload=" + wt + ",nothreads=" + noThreads
				+ ") SearchAndCount Multiple takes " + (t1 - t0) + " msec");

		return num;
	}

	// This method has to count all the appearances of elem from first to last-1 inclusively
	@Override
	protected Integer compute() {

		// if the input is small than cutOff compute directly
		if (length <= cutOff) {
			int count = 0;
			for (int i = start; i < start + length; i++) {
				if (Workload.doWork(input[i], workloadType))
					count++;
			}

			return count;
		}

		// otherwise compute function by splitting the work
		int halfSize = (length) / 2;
		SearchAndCountMultiple sc1 = new SearchAndCountMultiple(input, start,
				halfSize, cutOff, workloadType);
		sc1.fork();

		SearchAndCountMultiple sc2 = new SearchAndCountMultiple(input, start + halfSize, length - halfSize, cutOff, workloadType);
		return sc2.compute() + sc1.join();
	}

}