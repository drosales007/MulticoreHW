public class ThreadTest2 extends Thread {

	

	Latch prot;
	int id;
	int priority;

	public ThreadTest2(int id, Latch lockProt) {
		this.id = id;
		this.prot = lockProt;
	}

	public void run() {
		if((id%4) == 0 ) {
			prot.countDown();
		} else {
			System.out.println("Thread: " + id + " Is waiting.");
			prot.await();
			System.out.println("Thread: " + id + " went.");
		}
	}
	
}