package assignment10;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import assignment10.CoarseGrainedLockList;
import assignment10.FineGrainedLockList;
import assignment10.LazyLockList;
import assignment10.OptimisticLockList;
import assignment10.SequentialList;
import assignment10.SortedListInterface;

public class SortedLinkedListTest {

	/**
	 * Number of threads during the test. 
	 */
	private static final int INSERTER_COUNT = 128;
	/**
	 * Number of elements added to the list during the test.  
	 */
	private static final int N_ELEMENTS = 1000;

	@Test(timeout=5000)
	public void testSequential() {
		test(new SequentialList<Integer>());
	}
	
	@Test(timeout=5000)
	public void testCoarseGrained() {
		test(new CoarseGrainedLockList<Integer>());
	}

	@Test(timeout=10000)
	public void testfineGrained() {
		test(new FineGrainedLockList<Integer>());
	}

	@Test(timeout=5000)
	public void testLazyLock() {
		test(new LazyLockList<Integer>());
	}

	@Test(timeout=5000)
	public void testOptimisticLock() {
		test(new OptimisticLockList<Integer>());
	}

	public void test(SortedListInterface<Integer> list) {
		/* The idea of this test is to have INSERTER_COUNT many threads that
		 * simultaneously add and remove elements to and from the list. 
		 * At the end the list should contain all numbers between 0 and N_ELEMENTS.
		 * During the test another thread is running list.contains(N_ELEMENTS) several times. If 
		 * it fails to find the number, we know, that the contains method 
		 * is not thread-safe.
		 */
		
		// the reference array contains all numbers between 0 and N_ELEMENTS
		// but is shuffled (not sorted) 
		int[] reference = new int[N_ELEMENTS];
		for (int i = 0; i< N_ELEMENTS; i++){
			reference[i] = i;
		}
		Collections.shuffle(Arrays.asList(reference));
		
		// check whether we actually run the test on a multi-core machine
		int noOfCores = Runtime.getRuntime().availableProcessors();
		assertTrue("Running on a multicore machine.", noOfCores > 1);

		// insert an element, that will never be removed 
		list.add(N_ELEMENTS);

		// create and start all threads that will be adding and removing numbers
		List<Thread> inserters = new LinkedList<Thread>();
		for (int i=0; i<INSERTER_COUNT; i++) {
			inserters.add(new InserterThread(i, list, reference));
		}
		for (Thread t : inserters) {
			t.start();
		}

		// checks whether we can simultaneously find the last, fixed element in the list
		// if this fails, then the contains method is not implemented thread-safe
		for(int i=0; i<N_ELEMENTS; i++){
			assertTrue("The last element was not found in the list, despite being added",list.contains(N_ELEMENTS));
		} 		

		for (Thread t : inserters) {
			try {
				t.join();
			} catch (InterruptedException e) {}
		}
		
		// checks whether the list still contains all elements 
		// between 0 and N_ELEMENTS in the right order (sorted)
		int expected = 0;
		for(Integer i : list.toLinkedList()){
			assertTrue("Wrong number found at position " + expected, i.equals(expected));
			expected ++;
		}
		// compares the size of the list with the expected size
		assertTrue("Some (" + (N_ELEMENTS + 1 - expected) + ") elements are missing ", expected == N_ELEMENTS + 1);
	}

	private class InserterThread extends Thread {
		private SortedListInterface<Integer> list;
		private int id;
		private int[] reference;

		public InserterThread(int id, SortedListInterface<Integer> list, int[] toInsert) {
			this.id = id;
			this.list = list;
			this.reference = toInsert;
		}

		@Override
		public void run() {
			/* Every inserter thread adds, removes and adds again
			 *  a specified set of numbers to the the list.
			 *  The add and remove stages are not synchronised with a barrier, 
			 *  so the operations could interleave between different threads. 
			 *  There is a high probability for race conditions if
			 *  the methods add and remove are not thread-safe. 
			 */

			for(int i = id; i<N_ELEMENTS; i+=INSERTER_COUNT){
					list.add(reference[i]);
			}
			for(int i = id; i<N_ELEMENTS; i+=INSERTER_COUNT){
                	list.remove(reference[i]);
			}
			for(int i = id; i<N_ELEMENTS; i+=INSERTER_COUNT){
					list.add(reference[i]);
			}			
		}
	}
}
