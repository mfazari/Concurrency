package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountSingle {

	private int[] input;	
	Workload.Type workloadType;

	private SearchAndCountSingle(int[] input, Workload.Type wt) {
		this.input = input;		
		this.workloadType = wt;
	}

	public static int countNoAppearances(int[] input, Workload.Type wt) {
		long t0 = System.currentTimeMillis();
		SearchAndCountSingle counter = new SearchAndCountSingle(input, wt);
		int num = counter.count();
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCount Single takes "
				+ (t1 - t0) + " msec");
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