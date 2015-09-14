public class ThreadTest extends Thread {
	

	int type;
	FifoLock prot;
	int ticket;

	public ThreadTest(FifoLock prot) {
		this.prot = prot;
	}

	public void run() {
		ticket = prot.getTicket();
		prot.requestCS(ticket);
		prot.releaseCS();
	}
}