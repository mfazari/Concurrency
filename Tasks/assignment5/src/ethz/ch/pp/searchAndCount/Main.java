package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.DatasetGenerator;
import ethz.ch.pp.util.Workload;

public class Main {

	public static void main(String[] args) {

		DatasetGenerator dg = new DatasetGenerator(100000);
		int[] input = dg.generate();

		taskA(input, Workload.Type.HEAVY);
		taskB(input, 1000, Workload.Type.HEAVY, 4);
	}

	public static void taskA(int[] input, Workload.Type wt){		
		SearchAndCountSingle.countNoAppearances(input, wt);
	}

	public static void taskB(int[] input, int cutoff, Workload.Type wt, int numThreads){		
		SearchAndCountMultiple.countNoAppearances(input, cutoff, wt, numThreads);
	}

}
