import java.util.concurrent.atomic.*;

public class LockFreeListSet<T> implements ListSet<T> {
  AtomicMarkableReference<LLNode> list;
  AtomicMarkableReference<LLNode> end;

  public LockFreeListSet () {
  	LLNode first = new LLNode();
  	first.next = new AtomicMarkableReference<LLNode>(null,false);
  	list = new AtomicMarkableReference<LLNode>(first,false);
  	end = new AtomicMarkableReference<LLNode>(first,false);
  }

  public boolean add(T value) {
  	LLNode newNode = new LLNode();
  	newNode.value = value;
  	LLNode currentEnd;
  	LLNode currentNext;
  	boolean[] mark;
  	boolean[] mark2;
    boolean success = false;
    if(!contains(value)) {
    	while(true) {
  	  	mark = new boolean[1];
  	  	mark2 = new boolean[1];
  	  	currentEnd = end.get(mark);
          currentNext = end.getReference().next.get(mark2);
  	  	if(currentEnd == end.getReference()) {
  	  		if(currentNext == null) {
  	  			end.getReference().next.compareAndSet(null,newNode,mark2[0],!mark2[0]);
            success = true;
  	  			break;
  	  		} else {
  	  			end.compareAndSet(currentEnd,currentNext,mark[0],!mark[0]);
  	  		}
  	  	}
  	  }
  	  end.compareAndSet(currentEnd,newNode,mark[0],!mark[0]);
    } 
    return success;
  }


  public boolean remove(T value) {
  	boolean[] mark = new boolean[1];
  	AtomicMarkableReference<LLNode> previous;
  	AtomicMarkableReference<LLNode> delete;
  	LLNode deleteNode;
  	while(true) {
  		previous = findNode(value);
  		if(previous != null) {
	  		if(previous.getReference() != null) {
	  			delete = previous.getReference().next;
	  			deleteNode = previous.getReference().next.get(mark);
	  			//Set delete mark on previous.next to true
	  			//System.out.println("Trying to set delete mark to true on value: " + ((Integer)value).toString());
	  			if(delete.attemptMark(deleteNode,!mark[0])) {
	  				//System.out.println("Attempt mark was true for node: " + ((Integer)value).toString());
	  				break;
	  			} else {
	  				//System.out.println("Attempt mark was false for node: " +((Integer)value).toString());
	  			}
	  		}
	  	}
  	}
  	boolean[] mark2 = new boolean[1];
  	if(previous.getReference().next.compareAndSet(deleteNode,delete.getReference().next.getReference(),mark2[0],!mark2[0])) {
	    //System.out.println("Successfully moved previous to point to next node for deleted node: " + value.toString());
	} else {
		//System.out.println("Failed to move previous to skip this node: " + value.toString());
	}
  	return true;
  }
  /*To remove a node curr, two actions need to be taken. First, the mark of AtomicMarkableReference
curr.next needs to be set true. This corresponds to setting isDeleted flag true in the lock based Linked
List. We can use a CAS to perform this action. If the CAS does not succeed, then the remove operation
can be retried. The second action requires a check that pred is not deleted, and pred.next points to curr. If
the condition is true, then pred.next should be set to curr. The check and update can be done atomically
in a CAS operation.*/
  public boolean contains(T value) {
  	//System.out.println("Calling contains");
  	boolean[] mark = new boolean[1];
  	if(findNode(value) != null) {
  		return true;
  	}
    return false;
  }

  public AtomicMarkableReference<LLNode> findNode(T value) {
  	  //System.out.println("Calling find node on: " + value.toString());
      LLNode current = list.getReference().next.getReference();
      AtomicMarkableReference<LLNode> previous = list;
      while((current != null)) {
          if(current.value.equals(value)) {
          	return previous;
          }
          previous = previous.getReference().next;
          current = current.next.getReference();           
      }
      return null;
  }

  public String toString() {
      StringBuilder sb = new StringBuilder();
      LLNode current = list.getReference().next.getReference();
      AtomicMarkableReference<LLNode> previous = list;
      while((current != null)) {
          sb.append("Val: " + current.value.toString() + "\n");          
      	  current = current.next.getReference();
      }
      return sb.toString();
  }

  public class LLNode{
  	 T value;
  	 AtomicMarkableReference<LLNode> next = new AtomicMarkableReference<LLNode>(null,false);
  }

}



