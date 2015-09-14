//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #1

import java.util.*;

public class MainTest {
  public static void main(String[] args) {
      int processes = 10;
      ArrayList<Thread> myThreads = new ArrayList<Thread>();
      try {
          processes = Integer.parseInt(args[0]);
      } catch (Exception e) {
           e.printStackTrace();
      }
      if(processes > 0) {
          CyclicBarrier thisBarrier = new CyclicBarrier(processes);
          for(int i = 0; i< processes; i++) {
              Thread temp = new SomethingThread(i, 0, 25, thisBarrier);
              temp.start();
              myThreads.add(temp);
          }
          for(int i = 0; i< processes; i++) {
              try {
                  myThreads.get(i).join();

              } catch(InterruptedException e) {
                  e.printStackTrace();
              }
          }       
      }
  }
}