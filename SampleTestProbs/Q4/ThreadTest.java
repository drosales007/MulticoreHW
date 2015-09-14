public class ThreadTest extends Thread {
	

	int type;
	BMonitor prot;

	public ThreadTest(int type, BMonitor prot) {
		this.type = type;
		this.prot = prot;
	}

	public void run() {
		if(type==1) {
			System.out.println("Requesting direction 1");
			prot.arriveBridge(1);
			System.out.println("Direction 1 got in.");
			prot.exitBridge();
		} else if (type == 0) {
			System.out.println("Requesting direction 0");
			prot.arriveBridge(0);
			System.out.println("Direction 0 got in.");
			prot.exitBridge();			
		}

	}
}