/*Q.5 (20 points) Write a Java class FifoLock that provides the following
methods getTicket(), requestCS(int ticketnumber) and releaseCS().
When any thread invokes getTicket(), it gets a ticket number. You should
ensure that no two ticket numbers are identical. After getting a ticket number,
the thread can invoke requestCS anytime. It must provide the ticket
number that it received using getTicket. Assume that threads do not cheat
and provide the genuine ticket number. You should guarantee the following
property. Any process that invokes requestCS enters the critical section if
the critical section is empty and there is no process with lower ticket number
waiting in the queue.*/
import java.util.concurrent.locks.*;
import java.util.*;

public class FifoLock {

	final ReentrantLock locker = new ReentrantLock();
	Condition isLocked = locker.newCondition();
	final ReentrantLock ticketer = new ReentrantLock();
	int currentGetTick;
	boolean locked;
	ArrayList<Integer> ticketQueue = new ArrayList<Integer>();
	
	public FifoLock() {
		this.currentGetTick = 0;
		this.locked=false;

	}

	public int getTicket() {
		int thisTick = 0;
		ticketer.lock();
		currentGetTick++;
		thisTick = currentGetTick;
		ticketer.unlock();
		return thisTick;

	}

	public void requestCS(int ticketnumber) {
		locker.lock();
		try {
			boolean lowestTicket=false;
			System.out.println("Checking for lower ticket.");
			ticketQueue.add(ticketnumber);
			while(!lowestTicket) {
				lowestTicket = true;
			 	for(Integer i : ticketQueue) {
			 		if(i < ticketnumber) {
			 			lowestTicket=false;
			 			System.out.println("Ticket: " + ticketnumber + "Found a ticket." + i + " lower than my ticket: " + ticketnumber);
			 			isLocked.signal();
			 			break;
			 		} 
			 	}
			 	if(!lowestTicket) {
			 		System.out.println("Ticket: " + ticketnumber + "Found a lower ticket. Waiting for the queue to change.");
			 		isLocked.await();
			 	}		 	
			}
			System.out.println("No more lower tickets. Checking if locked");
			if(locked) { System.out.println("Lowest ticket is ticket: " + ticketnumber + " is waiting for lock.");}
			while(locked) { isLocked.await();}
			System.out.println("Ticket: " + ticketnumber + " got the lock, removing from queue.");
			ticketQueue.remove(ticketQueue.indexOf(ticketnumber));
			locked=true;
			//isLocked.signal();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			locker.unlock();
		}
	}

	public void releaseCS() {
		locker.lock();
		locked=false;
		System.out.println("Releasing the lock.");
		isLocked.signal();
		locker.unlock();



	}
}