// TODO
// Implement the MCS Lock 

import java.util.concurrent.atomic.*;

public class MCSLock implements MyLock {

	private volatile AtomicReference<Node> tailNode;	  
	private volatile ThreadLocal<Node> myNode;


	public class Node {
		volatile int id = -1;
		volatile boolean locked = false;
		volatile Node next = null;
	}

    public MCSLock(int numThread) {
    	tailNode = new AtomicReference<Node>();
    	myNode = new ThreadLocal<Node>(){ protected Node initialValue() { return new Node();}};
    }

    @Override
    public void lock(int myId) {
    	//System.out.println("Thread: " + myId + " requesting the lock.");
    	Node thisNode = myNode.get();
		Node predecessor = tailNode.getAndSet(thisNode);
		thisNode.id = myId;
		if(predecessor != null) {
			thisNode.locked = true;
			predecessor.next = thisNode;
			while(thisNode.locked) { 
				/*try {
					Thread.sleep(5);
				} catch(Exception e) {
					e.printStackTrace();
				}*/
			}
			//System.out.println("Thread: " + myId + "has the lock."); 
		} 
    }

    @Override
    public void unlock(int myId) {
    	//System.out.println("Releasing the lock for: " + myId);
    	Node thisNode = myNode.get();
		if(thisNode.next == null) {
			if(tailNode.compareAndSet(thisNode,null))
				return;
			while(thisNode.next == null) { 
				/*try {
					Thread.sleep(5);
				} catch(Exception e) {
					e.printStackTrace();
				}*/
			}  
		}
		thisNode.next.locked = false;
		thisNode.next = null;
		thisNode.id = -1;
		
    }
}
