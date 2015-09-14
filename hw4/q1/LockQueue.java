import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.*;

public class LockQueue<T> implements MyQueue<T> {
/*Implement lock-based and lock-free unbounded queues. For the lock based implementation, use different
locks for enq and deq operations; use AtomicInteger for the variable count. For the Lock-based
implementation, the deq operation should block if the queue is empty. 
*/

  ReentrantLock enqLock; 
  ReentrantLock deqLock; 
  AtomicInteger count;
  QNode head;
  QNode tail;

  public LockQueue() {
      head = new QNode() ;
      tail = head;
      enqLock = new ReentrantLock();
      deqLock = new ReentrantLock();
      count = new AtomicInteger();
  }

  public boolean enq(T value) {
      if( value == null) throw new NullPointerException();
      enqLock.lock();
      try {
          QNode newNode = new QNode();
          newNode.value = value;
          newNode.next = null;
          tail.next = newNode;
          tail = newNode;
          count.getAndIncrement();
      } finally {
          enqLock.unlock();
      }
      return true;
  }

  public T deq() {
      T result;
      deqLock.lock();
      while(count.get() == 0) {
          try {
            Thread.sleep(5);
          } catch(InterruptedException e) {
            e.printStackTrace();
          }
      }
      result = head.next.value;
      head = head.next;
      count.getAndDecrement();
      deqLock.unlock();
      return result;
  }


  public String toString() {
      StringBuilder sb = new StringBuilder();
      QNode current = head.next;
      while((current != null)) {
          sb.append("Val: " + current.value.toString() + "\n");
          current = current.next;           
      }
      return sb.toString();
  }

  public class QNode {
    	T value;
    	QNode next;
  }
}
