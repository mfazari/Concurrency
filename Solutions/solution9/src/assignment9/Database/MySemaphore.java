package assignment9.Database;

public class MySemaphore {

	private volatile int count;

	public MySemaphore(int maxCount) {
		this.count = maxCount;
	}

	public void acquire() throws InterruptedException {
		//here also the whole method could have been made synchronized, but this might be easier to read
		synchronized (this) {
			while (count == 0) {
				this.wait();
			}
			count--;
		}
	}

	public void release() {
		//here also the whole method could have been made synchronized, but this might be easier to read
		synchronized (this) {
			count++;
			this.notifyAll();
		}
	}

}
