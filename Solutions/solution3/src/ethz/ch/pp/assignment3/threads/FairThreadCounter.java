package ethz.ch.pp.assignment3.threads;


// executes threads after pattern

import ethz.ch.pp.assignment3.counters.Counter;

public class FairThreadCounter extends ThreadCounter {
		
	public FairThreadCounter(Counter counter, int id, int numThreads, int numIterations) {
		super(counter, id, numThreads, numIterations); //da obere klasse abstrakt ist, kann man nur auf Funktionen zugreifen, sie aber nicht instanzieren
	}

	@Override
	public void run() {
		for (int i = 0; i < numIterations; i++) {
			synchronized (counter) {
				while (counter.value() % numThreads != id) {
					try {
						counter.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				counter.increment();				
				counter.notifyAll();
			}
		}
	}

}
