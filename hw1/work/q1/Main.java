//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

import java.util.*;

public class Main {

        
    public static void main (String[] args) {
        Counter counter = null;
        ArrayList<Thread> threads = new ArrayList<Thread>();
        MyLock lock;
        long executeTimeMS = 0;
        int numThread = 6;
        int numTotalInc = 1200000;


        if (args.length < 3) {
            System.err.println("Provide 3 arguments");
            System.err.println("\t(1) <algorithm>: fast/bakery/synchronized/"
                    + "reentrant");
            System.err.println("\t(2) <numThread>: the number of test thread");
            System.err.println("\t(3) <numTotalInc>: the total number of "
                    + "increment operations performed");
            System.exit(-1);
        }

        numThread = Integer.parseInt(args[1]);
        numTotalInc = Integer.parseInt(args[2]);

        if (args[0].equals("fast")) {
            lock = new FastMutexLock(numThread);
            counter = new LockCounter(lock);
        } else if (args[0].equals("bakery")) {
            lock = new BakeryLock(numThread);
            counter = new LockCounter(lock);
        } else if (args[0].equals("synchronized")) {
            counter = new SynchronizedCounter();
        } else if (args[0].equals("reentrant")) {
            counter = new ReentrantCounter();
        } else {
            System.err.println("ERROR: no such algorithm implemented");
            System.exit(-1);
        }



        // TODO
        // Please create numThread threads to increment the counter
        // Each thread executes numTotalInc/numThread increments
        // Please calculate the total execute time in millisecond and store the
        // result in executeTimeMS
        long startTime = System.currentTimeMillis();
        for(int i=0; i<numThread; i++) {
            int portion = numTotalInc/numThread;
            CounterThread temp = new CounterThread((Counter)counter, portion, i);
            temp.start();
            threads.add(temp);
        }
        for(int i=0; i<numThread; i++) {
            try {
                threads.get(i).join();
            } catch (Exception e) { 
                System.out.println("There was an exception in join: ");
                e.printStackTrace();
            }            
        }
        long endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        // all threads finish incrementing
        // Checking if the result is correct
        if (counter == null ||
            counter.getCount() != (numTotalInc/numThread) * numThread) {
          System.out.println("Count: " + counter.getCount());
          System.out.println("Count should be: " + ((numTotalInc/numThread)*numThread));
          System.err.println("Error: The counter is not equal to the number "
              + "of total increment");
        } else {
          // print total execute time if the result is correct
          //System.out.println("Time to execute: ");
          //System.out.println("Count: " + counter.getCount());
          //System.out.println("Count should be: " + ((numTotalInc/numThread)*numThread));
          System.out.println(executeTimeMS);
        }
    }

}
