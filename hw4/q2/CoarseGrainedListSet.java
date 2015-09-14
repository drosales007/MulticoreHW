
import java.util.concurrent.locks.*;

public class CoarseGrainedListSet<T> implements ListSet<T> {

  final ReentrantLock listLock = new ReentrantLock(); 
  LLNode list;
  LLNode end;

  public CoarseGrainedListSet() {
    LLNode start = new LLNode();
    start.next = null;
    start.value = null;
    list = start;
    end = start;
  }

  public boolean add(T value) {
    if(!contains(value)) {
      LLNode newNode = new LLNode();
      newNode.next = null;
      newNode.value = value;
      listLock.lock();
      end.next = newNode;
      end = end.next;
      listLock.unlock();
      return true;      
    }
    return false;
  }
  public boolean remove(T value) {
  	listLock.lock();
  	LLNode previous = findNode(value);
  	//System.out.println("Before list:\n" + toString() + "\n" + "Searching for: " + value.toString()  + "The previous node is: " + previous.value.toString());
  	if(previous != null) {
  		previous.next = previous.next.next;
  		//System.out.println("Previous now pointing to: " + previous.next.value.toString());
  		listLock.unlock();
  		return true;
  	}
  	listLock.unlock();
    return false;
  }
  public boolean contains(T value) {
  	listLock.lock();
    if(findNode(value) != null) {
    	listLock.unlock();
    	return true;
    }
    listLock.unlock();
    return false;
  }

  public LLNode findNode(T value) {
  	LLNode current = list.next;
  	LLNode previous = list;
  	while(current != null) {
  		if (current.value.equals(value)) {
  			return previous;
  		} else {
  			previous = previous.next;
  			current = current.next;
  		}
  	}
  	return null;
  }

  public String toString() {
      StringBuilder sb = new StringBuilder();
      LLNode current = list.next;
      while((current != null)) {
          sb.append("Val: " + current.value.toString() + "\n");          
      	  current = current.next;
      }
      return sb.toString();
  }


  public class LLNode {
  	T value;
  	LLNode next;
  }
}
