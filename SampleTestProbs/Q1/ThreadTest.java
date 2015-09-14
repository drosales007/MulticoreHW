public class ThreadTest extends Thread {

	

	PLock prot;
	int id;
	int priority;

	public ThreadTest(int id, PLock lockProt, int priority) {
		this.id = id;
		this.prot = lockProt;
		this.priority = priority; 

	}

	public void run() {
		System.out.println("Thread: " + id + " with pri: " + priority + " trying to get the lock.");
		prot.requestCS(priority);
		System.out.println("Thread: " + id + " with pri: " + priority + " got the CS.");
		System.out.println("Thread: " + id + " releasing the cs.");
		prot.releaseCS();
	}
	
}