package ethz.ch.pp.assignment3.counters;

//TODO: implement
    public class SequentialCounter implements Counter {
        private int c = 0;

        public void increment() {
            c++;
        }

        public int value() {
            return c;
        }
    }