//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID, email: drosales007@gmail.com
//HW1 Question #2

// TODO 
// Implement Fast Mutex Algorithm
import java.util.concurrent.atomic.AtomicInteger;

public class FastMutexLock implements MyLock {

    AtomicInteger x;
    AtomicInteger y;
    boolean[] flagup;
    int processes;
    boolean debug = true;
    public FastMutexLock(int numThread) {
      // TODO: initialize your algorithm
      x = new AtomicInteger(-1);
      y = new AtomicInteger(-1);
      flagup = new boolean[numThread];
      for (int i=0;i<flagup.length;i++) {
        flagup[i] = false;
      }
      processes = numThread;
    }

    @Override
    public void lock(int myId) {
      // TODO: the locking algorithm
        while(true) {
            flagup[myId] = true;
            x.set(myId);
            
            if(y.get() != -1) {
                flagup[myId] = false;
                while(y.get() != -1) { wait(myId);}
                continue;
            } else {
                y.set(myId);
                if(x.get() == myId) {
                    return;
                } else {
                    flagup[myId] = false;
                    boolean x = true;
                    while (x){
                        for(int j = 0; j<processes; j++) {
                            if(flagup[j] == true) {
                                wait(myId);
                            }
                        }
                        x = false;
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
        flagup[myId] = false;
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

    public boolean free(){
        for (int i=0; i<processes; i++){
            if (flagup[i] == true){
                return false;
            }
        }
        return true;
    }
}