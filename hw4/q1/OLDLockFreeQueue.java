//AtomicMarkedReference
import java.util.concurrent.atomic.*;

public class LockFreeQueue<T> implements MyQueue<T> {
  
  AtomicMarkableReference<QNode> head;
  AtomicMarkableReference<QNode> tail;

  public LockFreeQueue () {
  	  QNode temp = new QNode();
  	  temp.next = null;
  	  head = new AtomicMarkableReference<QNode>(temp,false);
  	  tail = head;
  }

  public boolean enq(T value) {

    QNode temp = new QNode();
    temp.value = value;
    temp.next = null;
    QNode tailNode;
    AtomicReference<QNode> nextNode;
    boolean[] mark;
    while(true) {
    	mark = new boolean[1];
    	tailNode = tail.get(mark); //get current tail
    	nextNode = tail.getReference().next; //get current tail next, should be null
    	if(tailNode == tail.getReference()) { //if we still have the end node
    		if (nextNode == null) { // and no one is modify
    			//compareAndSet(V expectedReference, V newReference, boolean expectedMark, boolean newMark)           
            if(tailNode.next.compareAndSet(null, temp)) {
                break;
            } else {
                tail.compareAndSet(tailNode, nextNode.get(), mark[0],!mark[0]);
              break;
            }
    		}
    	}
    }
    tail.compareAndSet(tailNode,temp,mark[0],!mark[0]);
    return false;
  }

  public T deq() {
  	/*D1: loop // Keep trying until Dequeue is done
D2: head = Q->Head // Read Head
D3: tail = Q->Tail // Read Tail
D4: next = head.ptr->next // Read Head.ptr->next
D5: if head == Q->Head // Are head, tail, and next consistent?
D6: if head.ptr == tail.ptr // Is queue empty or Tail falling behind?
D7: if next.ptr == NULL // Is queue empty?
D8: return FALSE // Queue is empty, couldn’t dequeue
D9: endif
// Tail is falling behind. Try to advance it
D10: CAS(&Q->Tail, tail, <next.ptr, tail.count+1>)
D11: else // No need to deal with Tail
// Read value before CAS
// Otherwise, another dequeue might free the next node
D12: *pvalue = next.ptr->value
// Try to swing Head to the next node
D13: if CAS(&Q->Head, head, <next.ptr, head.count+1>)
D14: break // Dequeue is done. Exit loop
D15: endif
D16: endif
D17: endif
D18: endloop
D19: free(head.ptr) // It is safe now to free the old node
D20: return TRUE // Queue was not empty, dequeue succeeded*/
    while(true) {
    	boolean[] mark = new boolean[1];
    	boolean[] mark2 = new boolean[1];
    	QNode headNode = head.get(mark);
    	QNode tailNode = tail.get(mark2);
    	AtomicReference<QNode> nextNode = headNode.next;
    	if(headNode == head.getReference()) {
    	    if(head == tail) {
      		    //Is queue empty or tail falling behind
      		    if(nextNode == null) {
      		    	  return null;
      		    }
      		    tail.compareAndSet(tailNode,nextNode.get(),mark[0],!mark[0]);
              break;
    	    } else {
      	    	T thisVal = nextNode.get().value;
              if(head.compareAndSet(headNode,nextNode.get(),mark[0],!mark[0])) { 
                  return thisVal;              
              }
    	    }
    	}
    }
    return null;
  }

  public String toString() {
      StringBuilder sb = new StringBuilder();
      QNode current = head.getReference();
      while(current != null) {
        sb.append("Val: " + current.value + "\n");
        current = current.next.get();
      }

      return sb.toString();

  }

  public class QNode {
  	T value;
  	AtomicReference<QNode> next = new AtomicReference<QNode>();
  }

}
