public class ThreadTest extends Thread {
	

	boolean type1;
	myLock prot;

	public ThreadTest(boolean type1, myLock prot) {
		this.type1 = type1;
		this.prot = prot;
	}

	public void run() {
		if(type1) {
			System.out.println("Requesting type 1");
			prot.request1();
			System.out.println("Type 1 got in.");
			prot.release();
		} else {
			System.out.println("Requesting type 2");
			prot.request2();
			System.out.println("Type 2 got in.");
			prot.release();			
		}

	}
}