import java.util.*;

public class MainTest {

		
		
		public static void main(String[] args) {
		Latch locker = new Latch(2);

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
			Thread temp = new ThreadTest2(i,locker);
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