//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #2

// TODO
// Use synchronized, wait(), notify(), and notifyAll() to implement this
// bathroom protocol

public class SyncBathroomProtocol implements BathroomProtocol {

  int femaleCount;
  int maleCount;
  int maleWait;
  int femaleWait;
  int overallEntered;
  int overallExited;


  public SyncBathroomProtocol() {
      femaleCount = 0;
      femaleWait = 0;
      maleCount = 0;
      maleWait = 0;
      overallExited = 0;
      overallEntered = 0;
  }

  public int getTotalEntered() {
    return overallEntered;
  }

  public int getTotalExited() {
    return overallExited;
  }
  
  public void enterMale() {
      closeDoor(false);

  }

  public void leaveMale() {
      openDoor(false);

  }

  public void enterFemale() {
      closeDoor(true);
  }

  public void leaveFemale() {
      openDoor(true);
  }

  public synchronized void closeDoor(boolean female) {
      boolean setWait = false;
      try {
        if(female) {

            while(maleCount > 0 || maleWait > 0) {
                while(maleWait > 0) { wait();}
                if(!setWait) { 
                    femaleWait++; 
                    setWait=true;
                    System.out.println("Female waiting.   Female count: " + femaleCount + " Female wait: " + femaleWait + " Male count: " + maleCount + " Male wait: " + maleWait);
                }
                wait();
            }
            femaleCount++;
            overallEntered++;
            if(setWait) {femaleWait--;}
            System.out.println("Female entered.   Female count: " + femaleCount + " Female wait: " + femaleWait + " Male count: " + maleCount + " Male wait: " + maleWait);

        } else {
            while(femaleCount > 0 || femaleWait > 0) {
                while(femaleWait > 0) { wait();}
                if(!setWait) {
                    setWait=true;
                    System.out.println("Male waiting.     Female count: " + femaleCount + " Female wait: " + femaleWait + " Male count: " + maleCount + " Male wait: " + maleWait); 
                    maleWait++; 
                }
                wait();
            }
            maleCount++;
            overallEntered++;
            if(setWait) {maleWait--;}
            System.out.println("Male entered.     Female count: " + femaleCount + " Female wait: " + femaleWait + " Male count: " + maleCount + " Male wait: " + maleWait);
        }
      } catch(InterruptedException e) {
          e.printStackTrace();

      }

  }

  public synchronized void openDoor(boolean female) {
      if(female) {
          femaleCount--;
          overallExited++;
          System.out.println("Female exited.    Female count: " + femaleCount + " Female wait: " + femaleWait + " Male count: " + maleCount + " Male wait: " + maleWait);
          notifyAll();
      } else {
          maleCount--;
          overallExited++;
          System.out.println("Male exited.      Female count: " + femaleCount + " Female wait: " + femaleWait + " Male count: " + maleCount + " Male wait: " + maleWait);
          notifyAll();
      }
  }


}
