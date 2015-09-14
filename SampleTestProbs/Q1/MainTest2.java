import java.util.*;

public class MainTest2 {

	public static void main(String[] args) {

		int numThreads = 5;

		PLock protocol = new PLock();
		if(args.length == 0) {
			System.out.println("Setting numthreads to 5.");

		}  else {
			try {
				numThreads = Integer.parseInt(args[0]);
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}

		ArrayList<Thread> thrds = new ArrayList<Thread>();
		for(int i=0; i<numThreads; i++ ) {
			int pri = i%2;
			Thread temp = new ThreadTest(i,protocol,pri);
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