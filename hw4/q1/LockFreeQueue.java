//AtomicMarkedReference
import java.util.concurrent.atomic.*;

public class LockFreeQueue<T> implements MyQueue<T> {
  
  AtomicReference<QNode> head;
  AtomicReference<QNode> tail;

  public LockFreeQueue () {
  	  QNode temp = new QNode();
  	  head = new AtomicReference<QNode>(temp);
  	  tail = new AtomicReference<QNode>(temp);

  }

  public boolean enq(T value) {

    QNode temp = new QNode();
    temp.value = value;
    QNode tailNode;
    QNode nextNode=null;
    while(true) {
      tailNode = tail.get(); //get current tail
      nextNode = tailNode.next.get(); //get current tail next, should be null
      if(tailNode == tail.get()) { //if we still have the end node
          if (nextNode == null) { // and no one is modify          
              if(tailNode.next.compareAndSet(nextNode, temp)) {
                  break;
              }
          } else {
              tail.compareAndSet(tailNode, nextNode);
          }
      }
    }
    tail.compareAndSet(tailNode,temp);
    return true;
  }

  public T deq() {
    while(true) {
    	QNode headNode = head.get();
    	QNode tailNode = tail.get();
    	AtomicReference<QNode> nextNode = headNode.next;
    	if(headNode == head.get()) {
    	    if(head == tail) {
      		    //Is queue empty or tail falling behind
      		    if(nextNode == null) {
      		    	  return null;
      		    }
      		    tail.compareAndSet(tailNode,nextNode.get());
              break;
    	    } else {
      	    	T thisVal = nextNode.get().value;
              if(head.compareAndSet(headNode,nextNode.get())) {
                  //System.out.println("Dequeuing: " + thisVal); 
                  return thisVal;              
              }
    	    }
    	}
    }
    return null;
  }

  public String toString() {
      StringBuilder sb = new StringBuilder();
      QNode current = head.get().next.get();
      while((current != null)) {
          sb.append("Val: " + current.value.toString() + "\n");
          current = current.next.get();           
      }
      return sb.toString();
  }

  public class QNode {
  	T value;
  	AtomicReference<QNode> next = new AtomicReference<QNode>(null);
  }

}
