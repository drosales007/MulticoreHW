//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

public class CounterThread extends Thread {
	
	Counter counter;
	int increments;
	int threadId;
	boolean debug = true;

	public CounterThread(Counter counter,int increments,int threadId) {
		this.counter = counter;
		this.increments = increments;
		this.threadId = threadId;
	}

	public void run() {
		//debug("Thread " + threadId + " has " + increments + " counts");
		for (int i=0; i<increments; i++) {
			counter.increment();
		}
	}

	public int getThreadId() {
		return threadId;
	}

	private void debug(String msg) {
        if(debug) {
            System.out.println(this.getClass().getSimpleName() + ": " + msg);
        }
    }
}