public class TesterLockFree extends Thread {
	
	int id;
	LockFreeQueue<Integer> queueing;
	int range;

	public TesterLockFree(int id, int range, LockFreeQueue<Integer> queueing) {
        this.id = id;
        this.queueing = queueing;
        this.range = range;
	}

	public void run() {
		if((this.id%3) == 1) {
		//	System.out.println("TryingDequeued: ");
		//	System.out.println("Finishing dequeue: " + queueing.deq().toString());			
		}

		for(int i=id; i<range; i++) {
		//	System.out.println("Enqueing: " + i);
			queueing.enq(new Integer(i));
		}
		//System.out.println("Dequeued: " + queueing.deq().toString());

	}

	public static void main(String[] args) {
		LockFreeQueue<Integer> testqueue = new LockFreeQueue<Integer>();
		Long start = System.currentTimeMillis();
		for(int i=0; i<2000; i=i+5) {
			TesterLockFree thisThread = new TesterLockFree(i, i+5, testqueue);
			thisThread.start();
		}
		Long end = System.currentTimeMillis();
		System.out.println("~2000 ops took: " + (end-start));
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("Final queue: \n"  + testqueue.toString());

	}
}