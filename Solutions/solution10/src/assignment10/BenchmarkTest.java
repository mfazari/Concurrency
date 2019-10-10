package assignment10;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BenchmarkTest {

	static long numOps = 30000;
	static long initialElems = 10000;
	static double insertPercentage = 0.25;
	static int numThreads = Runtime.getRuntime().availableProcessors();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		executeAndTime(executor, new CoarseGrainedLockList<Long>());
		executeAndTime(executor, new FineGrainedLockList<Long>());
		executeAndTime(executor, new OptimisticLockList<Long>());
		executeAndTime(executor, new LazyLockList<Long>());
		executor.shutdown();
	}

	public static void executeAndTime(ExecutorService executor,
			SortedListInterface<Long> list) {
		final CyclicBarrier cb = new CyclicBarrier(numThreads + 1);
		initialLoad(list);
		// set up
		final long start = System.currentTimeMillis();
		for (int i = 0; i < numThreads; i++) {
			ListRunnable lr = new ListRunnable(numOps, insertPercentage, list,
					cb, i);
			executor.execute(lr);
		}

		try {
			cb.await();
			long end = System.currentTimeMillis();
			// This task will be executed once all thread reaches barrier
			System.out.println(String.format(
					"All threads finished using %s. Time elapsed [%.2f secs]",
					list.getClass().getName(), (end - start) / 1000.0));
		} catch (InterruptedException | BrokenBarrierException e) {
			System.out.println("Main Thread interrupted!");
			e.printStackTrace();
		}
	}
	
	public static void initialLoad(SortedListInterface<Long> list) {
		Random randomNum = new Random(1);
		for (int i = 0; i < initialElems; i++) {
			long nextVal = randomNum.nextLong();
			list.add(nextVal);
		}
	}
}
