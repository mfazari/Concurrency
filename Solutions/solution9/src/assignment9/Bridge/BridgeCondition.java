package assignment9.Bridge;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeCondition extends Bridge {

	final Lock bridgeLock = new ReentrantLock();
	Condition truckCanEnter = bridgeLock.newCondition();
	Condition carCanEnter = bridgeLock.newCondition();

	volatile private int carCount = 0;
	volatile private int truckCount = 0;

	public void enterCar() throws InterruptedException {
		bridgeLock.lock();
		while (carCount >= 3 || truckCount >= 1) {
			carCanEnter.await();
		}
		carCount++;
		bridgeLock.unlock();
	}

	public void leaveCar() {
		bridgeLock.lock();
		carCount--;
		if (carCount == 0)
			truckCanEnter.signalAll();
		if (carCount < 3)
			carCanEnter.signalAll();
		bridgeLock.unlock();
	}

	public void enterTruck() throws InterruptedException {
		bridgeLock.lock();
		while (carCount >= 1 || truckCount >= 1) {
			truckCanEnter.await();
		}
		truckCount++;
		bridgeLock.unlock();
	}

	public void leaveTruck() {
		bridgeLock.lock();
		truckCount--;
		if (truckCount == 0) {
			truckCanEnter.signalAll();
			carCanEnter.signalAll();
		}
		bridgeLock.unlock();
	}

	public static void main(String[] args) {
		Random r = new Random();
		BridgeCondition b = new BridgeCondition();
		for (int i = 0; i < 100; ++i) {
			if (r.nextBoolean()) {
				(new Car()).driveTo(b);
			} else {
				(new Truck()).driveTo(b);
			}
		}
	}

}
