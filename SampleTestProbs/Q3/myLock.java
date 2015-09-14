/*Q.3 (15 points) Implement the class myLock to coordinate entry into a
room by various threads. There are two types of threads in the system - type1
and type2. The only requirement is that there should not be two threads
of different types in the room at any time. Implement the class myLock
with three methods void request1(), void request2() and release().
Threads of type 1 use request1() and threads of type 2 use request2() to
enter the room. All threads use release() to exit the room. You do not
have to worry about starvation.*/
import java.util.concurrent.locks.*;
public class myLock {

	boolean type1In;
	boolean type2In;
	public final ReentrantLock locker = new ReentrantLock();
	Condition typeOut = locker.newCondition();



	public myLock() {
		type1In = false;
		type2In = false;

	}


	public void request1() {
		//Check to see if room has type2 in it, wait
		//go and increase count of type 1
		try {
			locker.lock();
			if(type2In) System.out.println("Lock: Waiting for type2 to leave.");
			while(type2In) { typeOut.await();}
			type1In = true;
			System.out.println("Lock: Type 1 is in.");
			typeOut.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			locker.unlock();
		}

	}

	public void request2() {
		try {
			locker.lock();
			if(type1In) System.out.println("Lock: Waiting for type1 to leave.");
			while(type1In) { typeOut.await();}
			type2In = true;
			System.out.println("Lock: Type 2 is in.");
			typeOut.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			locker.unlock();
		}

	}

	public void release() {
		locker.lock();
		if(type1In) {
			System.out.println("Setting type1In to false.");
			type1In = false;
		}
		if(type2In) {
			System.out.println("Setting type2In to false.");
			type2In = false;
		}
		typeOut.signal();
		locker.unlock();
	}


}
