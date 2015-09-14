//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

// TODO
// Implement the bakery algorithm
import java.util.concurrent.atomic.AtomicIntegerArray;

public class BakeryLock implements MyLock {

	int processes;
    AtomicIntegerArray number;
	//int[] number;
	boolean[] choosing;
    boolean debug = true;

    public BakeryLock(int numThread) {
    	processes = numThread;
        //debug("Number of processes is: " + numThread);
    	choosing = new boolean[processes];
    	//number = new int[processes];
        number = new AtomicIntegerArray(processes);
    	for(int i=0; i<processes; i++) {
    		choosing[i] = false;
    		number.set(i,0);
    	}
    }

    @Override
    public void lock(int myId) {
        if(myId >= choosing.length) {
            //debug("Trying to access choosing w/ID " + myId);
        }
    	choosing[myId] = true;
    	for(int j=0; j<processes; j++) {
    		if(number.get(j) > number.get(myId)) {
    			number.set(myId,number.get(j));
    		}
    	}
    	number.set(myId, (number.get(myId)+1));
    	choosing[myId] = false;
     	for(int j=0; j<processes; j++) {
    		while(choosing[j]) {
    			try {
    				Thread.sleep(5);
    			} catch (Exception e) { 
                    System.out.println("There was an exception in sleep: ");
                    e.printStackTrace();
                }
    		}

    		while((number.get(j) != 0) && 
    			((number.get(j) < number.get(myId)) || 
    			((number.get(j) == number.get(myId)) && j < myId))) {
    			try {
    				Thread.sleep(5);
    			}catch(Exception e) { 
                    System.out.println("There was an exception in sleep: ");
                    e.printStackTrace();
                }
    		}
    	}
    }

    @Override
    public void unlock(int myId) {
    	number.set(myId, 0);
    }

    private void debug(String msg) {
        if(debug) {
            System.out.println(this.getClass().getSimpleName() + ": " + msg);
        }
    }
}
