import java.util.*;

public class MainTest {
		public static void main(String[] args) {
		myLock locker = new myLock();

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
			boolean threadtype = false;
			if((i%3) == 0 ) { threadtype = true;}
			Thread temp = new ThreadTest(threadtype,locker);
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