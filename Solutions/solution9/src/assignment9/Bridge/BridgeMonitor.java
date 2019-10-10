package assignment9.Bridge;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeMonitor extends Bridge {

	private int carCount = 0;
	private int truckCount = 0;
	private final Object monitor = new Object();

	public void enterCar() throws InterruptedException {
		synchronized(monitor)
		{
			while (carCount >= 3 || truckCount >= 1) {
					monitor.wait();
			}
			carCount++;
		}
	}

	public void leaveCar() {
		synchronized (monitor) {
			carCount--;
			monitor.notifyAll();
		}
	}

	public void enterTruck() throws InterruptedException {
		synchronized (monitor) {
			while (carCount >= 1 || truckCount >= 1) {
					monitor.wait();
			}
			truckCount++;
		}
	}

	public void leaveTruck() {
		synchronized (monitor) {
			truckCount--;
			if (truckCount == 0) {
					monitor.notifyAll();
			}
		}
	}

	public static void main(String[] args) {
		Random r = new Random();
		BridgeMonitor b = new BridgeMonitor();
		for (int i = 0; i < 20; ++i) {
			if (r.nextBoolean()) {
				(new Car()).driveTo(b);
			} else {
				(new Truck()).driveTo(b);
			}
		}
	}

}
