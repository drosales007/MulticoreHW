public class CounterThread extend Thread {
	
	Counter counter;
	int increments;
	int threadId;

	public CounterThread(Counter counter,int increments,int threadId) {
		this.counter = counter;
		this.increments = increments;
		this.threadId = threadId;
	}

	public void run() {
		for (int i=0; i<increments; i++) {
			counter.lock.lock(threadId);
			counter.increment();
			counter.lock.unlocklock(threadId);
		}

	}
}