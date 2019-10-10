package ethz.ch.pp.assignment3.counters;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter {    
    private AtomicInteger c = new AtomicInteger(0);	//Makes sure, that threads provide same result

    public void increment() {
        c.incrementAndGet();
    }

    public int value() {
        return c.get();
    }
}