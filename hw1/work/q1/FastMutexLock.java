//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

// TODO 
// Implement Fast Mutex Algorithm
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class FastMutexLock implements MyLock {

    AtomicInteger x;
    AtomicInteger y;
    //boolean[] flagup;
    AtomicIntegerArray flagup;
    int processes;
    boolean debug = true;
    public FastMutexLock(int numThread) {
      // TODO: initialize your algorithm
      x = new AtomicInteger(-1);
      y = new AtomicInteger(-1);
      flagup = new AtomicIntegerArray(numThread);
      for (int i=0;i<flagup.length();i++) {
        flagup.set(i,0);
      }
      processes = numThread;
    }

    @Override
    public void lock(int myId) {
      // TODO: the locking algorithm
        while(true) {
            flagup.set(myId,1);
            x.set(myId);
            
            if(y.get() != -1) {
                flagup.set(myId,0);
                while(y.get() != -1) { wait(myId);}
                continue;
            } else {
                y.set(myId);
                if(x.get() == myId) {
                    return;
                } else {
                    flagup.set(myId,0);
                    for(int j = 0; j<processes; j++) {
                        while(flagup.get(j) == 1) { wait(myId); }
                    }
                    if(y.get() == myId) return;
                    else {
                        while(y.get() != -1) { wait(myId); }
                        continue;
                    }
                }
            }
        }
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
        y.set(-1);
        x.set(-1);
        flagup.set(myId,0);
    }

    public void wait(int myId) {
      try {
        Thread.sleep(5);
      }catch(Exception e) {
        debug("Exception during sleep for thread: " + myId);
        e.printStackTrace();
      }
    }

    private void debug(String msg) {
        if(debug) {
            System.out.println(this.getClass().getSimpleName() + ": " + msg);
        }
    }
}

