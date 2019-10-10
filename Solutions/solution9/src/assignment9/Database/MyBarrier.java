package assignment9.Database;

public class MyBarrier {
	int current, max;

	MyBarrier(int n){
        max = n;
        current = 0;
    }

	synchronized void await() {
		if (++current < max)
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		else{
			notifyAll();
			current = 0; 
		}		
	}
}