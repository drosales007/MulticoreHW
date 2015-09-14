import java.util.concurrent.Semaphore;

public class CyclicBarrierD {
    // TODO: Declare variables and the constructor for CyclicBarrier
    // Note that you can use only semaphores but not synchronized blocks and
    // locks
    int parties;
    boolean waiting[];
    Semaphore s;

    public CyclicBarrierD(int parties) {
        // TODO: The constructor for this CyclicBarrier
        this.parties = parties;
        this.waiting = new boolean[parties];
        this.s = new Semaphore(parties);
        for (int i=0; i<parties; i++){
            waiting[i] = false;
        }
    }

    public int await() throws InterruptedException {
        // Waits until all parties have invoked await on this barrier.
        // If the current thread is not the last to arrive then it is
        // disabled for thread scheduling purposes and lies dormant until
        // the last thread arrives.
        // Returns: the arrival index of the current thread, where index
        // (parties - 1) indicates the first to arrive and zero indicates
        // the last to arrive.
        s.acquire();
        int me = s.availablePermits();
        waiting[me] = true;
        while(s.availablePermits() != 1){
            Thread.sleep(5);
        }
        waiting[me] = false;
        boolean x = true;
        while (x){
            for (int i=0; i<parties; i++){
                if (waiting[i]){
                    break;
                }
                if (i==(parties-1) && waiting[i] == false){
                    x = false;
                }
            }
        s.release();
        }
        return me;
    }
}