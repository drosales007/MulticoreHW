//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #2

import java.util.concurrent.locks.*;

// TODO
// Use locks and condition variables to implement this bathroom protocol
public class LockBathroomProtocol implements BathroomProtocol {
  // declare the lock and conditions here

   	final ReentrantLock bathroomLock = new ReentrantLock(); 
   	Condition doorOpen = bathroomLock.newCondition();
   	Condition noWait = bathroomLock.newCondition();
   	int femaleCount;
  	int maleCount;
  	int maleWait;
  	int femaleWait;
  	int overallEntered;
  	int overallExited;

  	public LockBathroomProtocol() {
  	    femaleCount = 0;
        femaleWait = 0;
        maleCount = 0;
        maleWait = 0;
        overallExited = 0;
        overallEntered = 0;
  	}

  	public int getTotalEntered() {
    	return overallEntered;
  	}

  	public int getTotalExited() {
    	return overallExited;
  	}

	public void enterMale() {
		
		bathroomLock.lock();
		System.out.println("Male try.         Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
		boolean setWait = false;
	   	try {
	   		while (femaleWait > 0 ) { noWait.await();}
	   		while (femaleCount > 0) { 
	   			if(!setWait) { 
	   				maleWait++;
	   				setWait=true;
	   				System.out.println("Male waiting.     Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
	   			}
	   			doorOpen.await();
	       	}
	       	maleCount++;
	       	System.out.println("Male entered.     Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
	       	overallEntered++;
	       	doorOpen.signal();
	       	if(setWait) { 
	       		maleWait--;
	       		if(maleWait == 0) {
	       			noWait.signal();
	       			
	       		}
	       	}
	       		   
		       
		} catch(InterruptedException e) {
			e.printStackTrace();

		} finally {
			bathroomLock.unlock();
		}

	  }

	  public void leaveMale() {
	  	  bathroomLock.lock();
	  	  maleCount--;
	  	  overallExited++;
	  	  System.out.println("Male exited.      Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
	  	  doorOpen.signal();
	  	  noWait.signal();
	  	  bathroomLock.unlock();
	  	  

	  }

	  public void enterFemale() {
	  	System.out.println("Female try.       Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
		bathroomLock.lock();
		boolean setWait = false;
	   	try {
	   		while (maleWait > 0 ) { noWait.await();}
	   		while (maleCount > 0) { 
	   			if(!setWait) { 
	   				femaleWait++;
	   				setWait=true;
	   				System.out.println("Female waiting.   Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
	   			}
	   			doorOpen.await();
	       	}
	       	femaleCount++;
	       	System.out.println("Female entered.   Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
	       	overallEntered++;
	       	doorOpen.signal();
	       	if(setWait) { 
	       		femaleWait--;
	       		if(femaleWait == 0) {
	       			noWait.signal();
	       		}
	       	}
	       		   
		} catch(InterruptedException e) {
			e.printStackTrace();
		       
		} finally {
			bathroomLock.unlock();
		}
	  }

	  public void leaveFemale() {
	  	  bathroomLock.lock();
	  	  femaleCount--;
	  	  System.out.println("Female exited.    Female count: " + femaleCount + " Male count: " + maleCount + " Female wait: " + femaleWait + " Male wait: " + maleWait);
	  	  overallExited++;
	  	  doorOpen.signal();
	  	  noWait.signal();
	  	  bathroomLock.unlock();
	  	  
	  }




}
