/*Q.4 (20 points) An old bridge has only one lane and can only hold at most
4 cars at a time without risking collapse. Create a monitor with methods
arriveBridge(int direction) and exitBridge() that controls traffic so
that at any given time, there are at most 4 cars on the bridge, and all of them
are going the same direction. A car calls arriveBridge when it arrives at
the bridge and wants to go in the specified direction (0 or 1); arriveBridge
should not return until the car is allowed to get on the bridge. A car calls
exitBridge when it gets off the bridge, potentially allowing other cars to
get on. Don’t worry about starving cars trying to go in one direction; just
make sure cars are always on the bridge when they can be. You must use
Java ReentrantLock and Condition variables. Ensure that you signal only
the appropriate cars when you exit the bridge.*/
import java.util.concurrent.locks.*;

public class BMonitor {
	
	final ReentrantLock bridgekey = new ReentrantLock();
	Condition overLimit = bridgekey.newCondition();
	Condition directionChange = bridgekey.newCondition();
	int currentCars;
	int currentDirection;


	public BMonitor() {
		currentCars = 0;
		currentDirection = -1;

	}

	public void arriveBridge(int direction) {
		try {
			bridgekey.lock();
			if(currentCars >= 4) { System.out.println("Direction: " + direction + " Too many cars: " + currentCars);}
			while(currentCars >= 4) { overLimit.await();}
			if (currentDirection != -1 && currentDirection != direction) { System.out.println("Dir: " + direction + " waiting for direction to change. CurrentDirection is: " + currentDirection);}
			while(currentDirection != -1 && currentDirection != direction) { directionChange.await();}
			System.out.println("Direction " + direction + " entering the bridge. Cars is: " + currentCars + " current direction: " + currentDirection);
			currentDirection = direction;
			currentCars++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bridgekey.unlock();
		}

	}

	public void exitBridge() {
		bridgekey.lock();
		currentCars--;
		System.out.println("Car exited, count is: " + currentCars);
		if(currentCars == 0) { 
			currentDirection = -1;
			System.out.println("Sigaling direction change: " + currentDirection);
			directionChange.signal();
		}
		overLimit.signal();
		bridgekey.unlock();

	}

}