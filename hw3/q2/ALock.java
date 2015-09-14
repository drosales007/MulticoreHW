import java.util.concurrent.atomic.AtomicInteger;
// TODO 
// Implement Anderson’s array-based lock

public class ALock implements MyLock {
    ThreadLocal<Integer> mySlot;
    AtomicInteger tailSlot;
    volatile boolean[] available;
    volatile int size;

    public ALock(int numThread) {
      // TODO: initialize your algorithm
      size = numThread;
      tailSlot = new AtomicInteger(0);
      available = new boolean[size];
      available[0] = true;
      mySlot = new ThreadLocal<Integer>(){ protected Integer initialValue() { return 0;} };
    }

    @Override
    public void lock(int myId) {
          int slot = tailSlot.getAndIncrement() % size;
          //System.out.println("The slot is: " + slot);
          mySlot.set(slot);
          while (!available[slot]) {
              /*try {
                Thread.sleep(5);
              } catch(Exception e) {
                e.printStackTrace();
              }*/            
          };
          //System.out.println("Thread " + myId + " got the lock");
    }

    @Override
    public void unlock(int myId) {
          int slot = mySlot.get();
          available[slot] = false;
          available[(slot + 1) % size] = true; 
          //System.out.println("Thread: " + myId + " released the lock.");     
    }
}

