package ethz.ch.pp.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) { 		
		taskA();
		taskB(generateRandomInput());
		taskC();		
		taskE(generateRandomInput(4), 5);
	}
	
	private final static Random rnd = new Random(42);

	public static int[] generateRandomInput() {
		return generateRandomInput(rnd.nextInt(10000) + 1);
	}
	
	public static int[] generateRandomInput(int length) {	
		int[] values = new int[length];		
		for (int i = 0; i < values.length; i++) {
			values[i] = rnd.nextInt(99) + 1;				
		}		
		return values;
	}
	
	public static int[] computePrimeFactors(int[] values) {		
		int[] factors = new int[values.length];	
		for (int i = 0; i < values.length; i++) {
			factors[i] = numPrimeFactors(values[i]);
		}		
		return factors;
	}
	
	public static int numPrimeFactors(int number) {
		int primeFactors = 0;
		int n = number;		
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				primeFactors++;
				n /= i;
			}
		}
		return primeFactors;
	}
	
	public static class ArraySplit {
		public final int startIndex;
		public final int length;
		
		ArraySplit(int startIndex, int length) {
			this.startIndex = startIndex;
			this.length = length;
		}
	}
	
	public static ArraySplit[] PartitionData(int length, int numPartitions) {
		ArraySplit[] partitions = new ArraySplit[numPartitions];
		
		int chunkSize = Math.max(1, length / numPartitions);
		int assignedInput = 0;
		for (int i = 0; i < numPartitions; i++) {
			int reimainingInput = length - assignedInput;
			int inputSize = Math.min(chunkSize, reimainingInput);
			if (i == numPartitions - 1) {
				inputSize = reimainingInput;
			}
			partitions[i] = new ArraySplit(assignedInput, inputSize);					
			
			assignedInput += inputSize;			
		}
		
		return partitions;
	}
	
	public static void taskA() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello Thread!");
			}
		});

		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] taskB(final int[] values) {
		final int[] result = new int[values.length];
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {				
				int[] primeFactors = computePrimeFactors(values);
				System.arraycopy(primeFactors, 0, result, 0, primeFactors.length );				
			}
		});

		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// Returns overhead of creating thread in nano-seconds
	public static long taskC() {		
		final int N = 1000;
		long startTime = System.nanoTime();
		for (int i = 0; i < N; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {							
				}
			});

			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.nanoTime();
		System.out.format("Thread over on current system is: %d nano-seconds\n", (endTime - startTime) / N);
		return (endTime - startTime) / N;
	}
	
	public static Thread createThreadForInputs(final int[] values, final int[] factors, final int l, final int size) {	

		return new Thread(new Runnable() {
			@Override
			public void run() {				
				for (int i = l; i < l + size; i++) {
					factors[i] = numPrimeFactors(values[i]);
				}
			}
		});
	}

	
	public static int[] taskE(final int[] values, final int numThreads) {
		final int[] result = new int[values.length];		
		List<Thread> threads = new ArrayList<Thread>();			
		
		ArraySplit[] parititions = PartitionData(values.length, numThreads);
		for (ArraySplit partition : parititions) {
			threads.add(createThreadForInputs(values, result, partition.startIndex, partition.length));
		}
		
		// start threads
		for (Thread thread: threads) {
			thread.start();
		}

		// wait until all threads finish
		for (Thread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}


}
