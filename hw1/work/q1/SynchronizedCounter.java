//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW1 Question #2

// TODO 
// Use synchronized to protect count
public class SynchronizedCounter extends Counter {
    @Override
    public void increment() {
    	synchIncrement();
    }

    private synchronized void synchIncrement() {
    	count++;
    }
}
