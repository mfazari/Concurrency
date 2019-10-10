package ethz.ch.pp.mergeSort;

import ethz.ch.pp.util.DatasetGenerator;

public class Main {

	public static void main(String[] args) {

	    DatasetGenerator dg = new DatasetGenerator(100000);
	    int[] input = dg.generate();
	    
	    taskA(input);
	    taskB(input, Runtime.getRuntime().availableProcessors());
	}
	
	public static void taskA(int[] input){		
		MergeSortSingle.sort(input);
	}
	
	public static void taskB(int[] input, int numThreads){		
		MergeSortMulti.sort(input, 4);
	}

}
