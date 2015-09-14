import java.util.concurrent.locks.*;

// TODO
// Use locks and condition variables to implement this bathroom protocol
public class PLock {
  // declare the lock and conditions here

   	final ReentrantLock protLock = new ReentrantLock(); 
   	Condition numHigh = protLock.newCondition();
   	Condition locked = protLock.newCondition();
   	boolean locklock;
  	int numhighpri;
  	int numlowpri;
  	int numinCS;

  	public PLock() {
  	    locklock = false;
        numhighpri = 0;
        numlowpri = 0;
        numinCS = 0;
  	}


	public void requestCS(int priority) {
		
		protLock.lock();
		System.out.println("");
	   	try {
	   		if(priority == 1 ) {
	   			numhighpri++;
	   			System.out.println("In high priority request, numHigh is: " + numhighpri + " numlow: " + numlowpri + " in CS is: " + numinCS);
		   		while (locklock) { locked.await();}
		   		locklock = true;
		   		numinCS++;
		   		numhighpri--;
		   		numHigh.signal();
		   		locked.signal();
		   		System.out.println("High Priority got CS, numHigh is: " + numhighpri + " numlow: " + numlowpri + " in CS is: " + numinCS);
		   	} else {
		   		System.out.println("In low priority, checking for high: " + numhighpri + " numlow: " + numlowpri + " in CS is: " + numinCS);
		   		numlowpri++;
		   		while (numhighpri > 0 ) { numHigh.await();}
		   		System.out.println("Low priority out of line, waiting for CS.");
		   		while (locklock ) { locked.await();}
		   		System.out.println("In low priority, got the CS.");
		   		locklock = true;
		   		numinCS++;
		   		numlowpri--;
		   		locked.signal();
		   		System.out.println("Low priority got CS, numHigh is: " + numhighpri + " numlow: " + numlowpri + " in CS is: " + numinCS);
		   	}       
		} catch(InterruptedException e) {
			e.printStackTrace();

		} finally {
			protLock.unlock();
		}
	  }

	  public void releaseCS() {
	  	  protLock.lock();
	  	  locklock = false;
	  	  numinCS--;
	  	  locked.signal();
	  	  System.out.println("UNLOCKING*****" + "numhigh: " + numhighpri + " numlow: " + numlowpri + " in CS is: " + numinCS);
	  	  numHigh.signal();
	  	  protLock.unlock();
	  	  

	  }

}
