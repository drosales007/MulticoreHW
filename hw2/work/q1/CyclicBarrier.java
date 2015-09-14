//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #1

import java.util.concurrent.Semaphore;

public class CyclicBarrier {
  // TODO: Declare variables and the constructor for CyclicBarrier
  // Note that you can use only semaphores but not synchronized blocks and
  // locks 

  Semaphore barrier;
  int processes;
  boolean everyoneMadeIt;




  public CyclicBarrier(int parties) {
    // TODO: The constructor for this CyclicBarrier
    this.processes = parties;
    this.barrier = new Semaphore(processes);
  }

  public int await() throws InterruptedException {
    // Waits until all parties have invoked await on this barrier.
    // If the current thread is not the last to arrive then it is
    // disabled for thread scheduling purposes and lies dormant until
    // the last thread arrives.
    // Returns: the arrival index of the current thread, where index
    // (parties - 1) indicates the first to arrive and zero indicates
    // the last to arrive.
    while(everyoneMadeIt) { 
        //System.out.println("Checking all permits are released from last round before acquiring.");
        Thread.sleep(5);
    }
    barrier.acquire();
    int arrivalIndex = barrier.availablePermits();
    //System.out.println("Permit " + arrivalIndex + "taken.");
    if(arrivalIndex == 0) { 
        System.out.println("Everone made it, no count should be lower than current barrier.");
        everyoneMadeIt = true;
    }
    while(everyoneMadeIt == false) {
        Thread.sleep(5);
    }
    barrier.release();
    int temppermits = barrier.availablePermits();
    //System.out.println("Thread " + arrivalIndex + " release permit. " + temppermits + " are left to release."); 
    if(temppermits == processes) {
        //System.out.println("All permits released.");
        everyoneMadeIt = false;
    }
    return arrivalIndex;
  }

}
