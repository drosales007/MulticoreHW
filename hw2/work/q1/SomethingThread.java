//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #1

import java.util.concurrent.Semaphore;

public class SomethingThread extends Thread {

	private int myId;
	private int myCountWait;
	private int myCount;
	private int barrierNum;
	private CyclicBarrier wait;

	public SomethingThread(int id, int countWaiter, int barrierNum, CyclicBarrier barrier) {
		this.myId = id;
		this.myCountWait = countWaiter;
		this.myCount = 0;
		this.wait = barrier;
		this.barrierNum = barrierNum;
	}

	public void run() {
		while(myCount < 50) {
			try {
				Thread.sleep(myCountWait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myCount++;
			System.out.println("Thread " + myId + " count is now " + myCount);
			if(myCount == barrierNum) {
				//System.out.println("Thread ID: " + myId + " is waiting for the rest of the threads at count: " + myCount);
				try {
					int myPlace = wait.await();
					//System.out.println("Thread " + myId + " was number " + myPlace + " for count to 25");
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(myCount == (barrierNum+10)) {
				//System.out.println("Thread ID: " + myId + " is now waiting for threads at count (shows reuse of barrier): " + myCount);
				try {
					int myPlace = wait.await();
					//System.out.println("Thread " + myId + " was number " + myPlace + " for count to 35.");
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(myCount == (barrierNum+20)) {
				//System.out.println("Thread ID: " + myId + " is now waiting for threads at count (shows reuse of barrier): " + myCount);
				try {
					int myPlace = wait.await();
					//System.out.println("Thread " + myId + " was number " + myPlace + " for count to 35.");
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Thread " + myId + " reached count 50.");		
	}
}