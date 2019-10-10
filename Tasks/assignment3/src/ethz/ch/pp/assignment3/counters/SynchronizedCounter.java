package ethz.ch.pp.assignment3.counters;

//TODO: implement
public class SynchronizedCounter implements Counter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized int value() {
        return c;
    }

}