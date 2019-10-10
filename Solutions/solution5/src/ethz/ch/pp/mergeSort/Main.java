package ethz.ch.pp.mergeSort;

import ethz.ch.pp.util.DatasetGenerator;

public class Main {

	public static void main(String[] args) {

	    DatasetGenerator dg = new DatasetGenerator(1024*1024*5);
	    int[] input = dg.generate();
	    
	    taskA(input);
	    taskB(input, 2);
	    
	    taskA(input);
	    taskB(input, 1);
	    taskB(input, 2);
	    taskB(input, 3);
	    taskB(input, 4);
	    taskB(input, 5);
	    taskB(input, 6);
	    taskB(input, 7);
	    taskB(input, 8);
	    
	    taskB(input, Runtime.getRuntime().availableProcessors());
	}
	
	public static void taskA(int[] input){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			MergeSortSingle.sort(input);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("!!For " + input.length + " MergeSort Single takes " + ((t1 - t0)/5) + " msec");
	}
	
	public static void taskB(int[] input, int numThreads){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			MergeSortMulti.sort(input, numThreads);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("!!For " + input.length + " MergeSort Multi with " + numThreads + " threads takes " + ((t1 - t0)/5) + " msec");
	}

}

