//Amy Reed - UTEID alr2434, email: amy_hindman@yahoo.com
//David Rosales - UTEID dar542, email: drosales007@gmail.com
//HW2 Question #2

public class MainTest {

	public static void main(String[] args) {

		BathroomProtocol protocol = null;
		if(args.length == 0) {
			System.out.println("Enter \"lock\" or \"sync\"");
			System.exit(0);
		}
		if(args[0].equals("lock")) {
			protocol = new LockBathroomProtocol();

		} else if(args[0].equals("sync")) {
			protocol = new SyncBathroomProtocol();
		} 
		
		Person firstmale = new Person(false,protocol, 5000);
		Person thmale = new Person(false,protocol, 7000);
		Person firstfemale = new Person(true,protocol, 10000);
		Person secfemale = new Person(true,protocol, 5000);
		Person thfemale = new Person(true, protocol,10000);
		Person secmale = new Person(false,protocol, 2000);
		Person fofemale = new Person(true, protocol,5000);
		Person fifemale = new Person(true, protocol,6000);
		Person sifemale = new Person(true, protocol,6000);
		Person sefemale = new Person(true, protocol,6000);
		Person fomale = new Person(false,protocol, 2000);
		Person fimale = new Person(false,protocol, 2000);
		Person eifemale = new Person(true,protocol, 2000);
		Person nifemale = new Person(true,protocol, 2000);
		Person simale = new Person(false,protocol, 2000);
		Person semale = new Person(false,protocol, 2000);
		Person tefemale = new Person(true,protocol, 2000);
		Person elfemale = new Person(true,protocol, 2000);
		Person eimale = new Person(false,protocol, 2000);
		Person nimale = new Person(false,protocol, 2000);
		Person twfemale = new Person(true,protocol, 2000);
		Person temale = new Person(false,protocol, 2000);
		Person tnfemale = new Person(true,protocol, 2000);
		Person elmale = new Person(false,protocol, 2000);

		long start = System.currentTimeMillis();
		firstmale.start();
		thmale.start();
		firstfemale.start();
		secfemale.start();
		thfemale.start();
		secmale.start();
		fofemale.start();
		fifemale.start();
		sifemale.start();
		sefemale.start();
		fomale.start();
		fimale.start();
		eifemale.start();
		nifemale.start();
		simale.start();
		semale.start();
		tefemale.start();
		elfemale.start();
		eimale.start();
		nimale.start();
		twfemale.start();
		temale.start();
		tnfemale.start();
		elmale.start();
		try {
			firstmale.join();
			thmale.join();
			firstfemale.join();
			secfemale.join();
			thfemale.join();
			secmale.join();
			fofemale.join();
			fifemale.join();
			sifemale.join();
			sefemale.join();
			fomale.join();
			fimale.join();	
			eifemale.join();
			nifemale.join();
			simale.join();
			semale.join();
			tefemale.join();
			elfemale.join();
			eimale.join();
			nimale.join();
			twfemale.join();
			temale.join();
			tnfemale.join();
			elmale.join();	
			long end = System.currentTimeMillis();
			start = end - start;
			System.out.println("The time for this protocol was: " + start);
			if(args[0].equals("lock")) {
				System.out.println("Total entered: " + ((LockBathroomProtocol)protocol).getTotalEntered() + " Total exited: " + ((LockBathroomProtocol)protocol).getTotalEntered());
			} else if(args[0].equals("sync")) {
				System.out.println("Total entered: " + ((SyncBathroomProtocol)protocol).getTotalEntered() + " Total exited: " + ((SyncBathroomProtocol)protocol).getTotalEntered());
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}