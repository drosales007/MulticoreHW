import java.util.*;

public class MainTest {
		public static void main(String[] args) {
		BMonitor locker = new BMonitor();

		int numThreads = 20;
		if(args.length == 0 ) { 
			System.out.println("No threads specified, creating 10.");
		} else {
			try {
				numThreads = Integer.parseInt(args[0]);
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Thread> thrds = new ArrayList<Thread>();
		for(int i=0; i<numThreads; i++ ) {
			int direction = 0;
			if((i%3) == 0 ) { direction = 1;}
			Thread temp = new ThreadTest(direction,locker);
			temp.start();
			thrds.add(temp);
		}
		for(Thread th : thrds) {
			try {
			 	th.join();
			} catch(InterruptedException e) {
				e.printStackTrace();

			}
		}
	}
}