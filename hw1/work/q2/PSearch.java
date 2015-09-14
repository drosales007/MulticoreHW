//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

import java.util.concurrent.Callable;
import java.util.concurrent.*;
import java.util.*;

public class PSearch implements Callable<Integer> {
  // TODO: Declare variables 
  int x;
  int[] A;
  int begin;
  int end;

  public static ExecutorService threadPool = Executors.newCachedThreadPool();

  public PSearch(int x, int[] A, int begin, int end) {   
    // TODO: The constructor for PSearch 
    // x: the target that you want to search
    this.x = x;
    // A: the array that you want to search for the target
    this.A = A;
    // begin: the beginning index (inclusive)
    this.begin = begin;
    // end: the ending index (exclusive)
    this.end = end;
  }

  public Integer call() throws Exception {
    // TODO: your algorithm needs to use this method to get results
    // You should search for x in A within begin and end
    // Return -1 if no such target
    for (int i=begin; i<=end; i++){
      if (A[i]==x){
        return i;
      }
    }
    return Integer.valueOf(-1);
  }

  public static int parallelSearch(int x, int[] A, int n) {
    // TODO: your search algorithm goes here
    // You should create a thread pool with n threads 
    // Then you create PSearch objects and submit those objects to the thread
    // pool
    ArrayList<Future<Integer>> threadVals = new ArrayList<Future<Integer>>();
    int searchLen = A.length/n;
    int remain = A.length % n;
    int start = 0;
    int fin = searchLen - 1;
    for (int i=1; i<=n; i++){
      if (i==n){
        start += remain;
        fin += remain;
      }
      PSearch s = new PSearch(x, A, start, fin);
      start += searchLen;
      fin += searchLen;
      Future<Integer> f1 = threadPool.submit(s);
      threadVals.add(f1);
    }
    for (int j=0; j<threadVals.size(); j++){
      try {
        int returnVal = (threadVals.get(j)).get();
        if (returnVal != -1){
          threadPool.shutdown();
          return returnVal;
        }
      }
      catch(Exception exc) {
        System.err.println(exc);
      }
    }
    threadPool.shutdown();
    return -1; // return -1 if the target is not found
  }

  public static void main (String[] args){
    int x = 2;
    int numThreads = 5;
    int[] arr = {0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9};
    int myResult = parallelSearch(x, arr, numThreads);
    if (arr.length <= 0) {
      System.out.println("The array given is empty");
      System.exit(0);
    }
    if (numThreads > arr.length) {
      System.out.println("The number of threads is greater than the number of items in the array");
      System.exit(0);
    }
    if (myResult == -1) {
      System.out.println("The number " + x + " was not found in the given array");
    }
    else {
      System.out.println("The number " + x + " was found at index " + myResult);
    }
  }
}