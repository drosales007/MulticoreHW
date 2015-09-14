//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

// TODO
// Use MyLock to protect the count
// This counter can use either BakeryLock or FastMutexLock to protect the count

public class LockCounter extends Counter {

	MyLock counterlock;
	boolean debug = true;

    public LockCounter(MyLock lock) {
    	this.counterlock = lock;
        count = 0;
    }

    @Override
    public void increment() {
        int id = ((CounterThread)Thread.currentThread()).getThreadId();
        counterlock.lock(id);
    	count++;
        //System.out.println("Thread: " + id + " is incrementing. Count is now: " + count);
        counterlock.unlock(id);
        //debug("Count is now: " + count);
    }

    private void debug(String msg) {
        if(debug) {
            System.out.println(this.getClass().getSimpleName() + ": " + msg);
        }
    }
}
