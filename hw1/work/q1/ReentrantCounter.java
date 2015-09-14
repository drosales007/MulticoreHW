//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID, email: drosales007@gmail.com
//HW1 Question #2

import java.util.concurrent.locks.ReentrantLock;
// TODO
// Use ReentrantLock to protect the count
public class ReentrantCounter extends Counter {

	ReentrantLock relock;

	public ReentrantCounter() {
		relock = new ReentrantLock();
	}
    @Override
    public void increment() { 		
		relock.lock();
		count++;
		relock.unlock();  
    }
}
