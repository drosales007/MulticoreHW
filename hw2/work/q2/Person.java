//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #2

public class Person extends Thread {

	boolean female;
	//SyncBathroomProtocol prot;
	BathroomProtocol prot;
	int timeInBath;

	public Person(boolean fem, BathroomProtocol lockProt, int myTimeInBath) {
		this.female = fem;
		this.prot = lockProt;
		this.timeInBath = myTimeInBath; 

	}

	public void run() {
		if(female) {
			prot.enterFemale();
			try {
				Thread.sleep(timeInBath);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			prot.leaveFemale();
		} else {
			prot.enterMale();
			try {
				Thread.sleep(timeInBath);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			prot.leaveMale();			
		}


	}
	
}