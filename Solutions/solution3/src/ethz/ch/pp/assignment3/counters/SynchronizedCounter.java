package ethz.ch.pp.assignment3.counters;

public class SynchronizedCounter implements Counter {
    private int c = 0;

    public synchronized void increment() {		
        c++;
    }

    public synchronized int value() {
        return c;
    }
}


//compared to normal counter

/*

package ethz.ch.pp.assignment3.counters;

public class SequentialCounter implements Counter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public int value() {
        return c;
    }
}
*/