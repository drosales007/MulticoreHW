/*Give Java class for Latch. A Latch is initialized with
a given count. It supports two methods, await() and countDown(). The
await methods block until the current count reaches zero due to invocations
of the countDown() method, after which all waiting threads are released and
any subsequent invocations of await return immediately. This is a one-shot
phenomenon – the count cannot be reset.*/
import java.util.concurrent.locks.*;

public class Latch {

	final ReentrantLock locker = new ReentrantLock();
	Condition countReached = locker.newCondition();
	int count;
	int currentCount;

	public Latch(int count) {
		this.count=count;
		currentCount = 0;
	}

	public void await() {
		locker.lock();
		System.out.println("Adding to waiters, the count is: " + currentCount);
		try {
			while(currentCount < count) {countReached.await();}
			System.out.println("Count reached moving on: " + currentCount);
		} catch(InterruptedException e) {}
		finally {
			locker.unlock();
		}
		//locker.unlock();
	}

	public void countDown() {
		locker.lock();
		if(currentCount < count) { 
			currentCount++;
			
		}
		System.out.println("Cound down called: " + currentCount);
		countReached.signal();
		locker.unlock();
		
		
	}
}
