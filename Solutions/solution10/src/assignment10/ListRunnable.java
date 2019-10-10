package assignment10;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ListRunnable implements Runnable {
	long numOps;
	double insertPercentage;
	SortedListInterface<Long> list;
	private CyclicBarrier barrier;
	long seed;

	ListRunnable(long numOps, double inPer, SortedListInterface<Long> list, CyclicBarrier barrier, long seed) {
		this.numOps = numOps;
		this.insertPercentage = inPer;
		this.list = list;
		this.barrier = barrier;
		this.seed = seed;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + " has started.");
			Random randomNum = new Random(this.seed);
			long cntInserts = (long) (numOps * insertPercentage);

			while (numOps > 0) {
				long nextVal = randomNum.nextLong();
				if (randomNum.nextInt(2) == 0 && cntInserts > 0) {
					list.add(nextVal);
					cntInserts--;
				} else {
					if (randomNum.nextInt(2) == 0)
						list.remove(nextVal);
					else
						list.contains(nextVal);
				}
				numOps--;
			}
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
        	System.out.println(Thread.currentThread().getName() + " has not been able to finish.");
        }

	}

}
