import java.util.Random;

public class MultiLockTest {

	public static void main(String[] args){
		Random rand = new Random();
		MultiLock hotelFloor = new MultiLock(5);
		PersonThread p1 = new PersonThread(hotelFloor, rand.nextInt(2000), 1);
		PersonThread p2 = new PersonThread(hotelFloor, rand.nextInt(2000), 2);
		PersonThread p3 = new PersonThread(hotelFloor, rand.nextInt(2000), 3);
		PersonThread p4 = new PersonThread(hotelFloor, rand.nextInt(2000), 4);
		PersonThread p5 = new PersonThread(hotelFloor, rand.nextInt(2000), 5);
		PersonThread p6 = new PersonThread(hotelFloor, rand.nextInt(2000), 6);
		PersonThread p7 = new PersonThread(hotelFloor, rand.nextInt(2000), 7);
		PersonThread p8 = new PersonThread(hotelFloor, rand.nextInt(2000), 8);
		PersonThread p9 = new PersonThread(hotelFloor, rand.nextInt(2000), 9);
		PersonThread p10 = new PersonThread(hotelFloor, rand.nextInt(2000), 10);
		PersonThread p11 = new PersonThread(hotelFloor, rand.nextInt(2000), 11);
		PersonThread p12 = new PersonThread(hotelFloor, rand.nextInt(2000), 12);
		PersonThread p13 = new PersonThread(hotelFloor, rand.nextInt(2000), 13);
		PersonThread p14 = new PersonThread(hotelFloor, rand.nextInt(2000), 14);
		PersonThread p15 = new PersonThread(hotelFloor, rand.nextInt(2000), 15);
		PersonThread p16 = new PersonThread(hotelFloor, rand.nextInt(2000), 16);
		PersonThread p17 = new PersonThread(hotelFloor, rand.nextInt(2000), 17);
		PersonThread p18 = new PersonThread(hotelFloor, rand.nextInt(2000), 18);
		PersonThread p19 = new PersonThread(hotelFloor, rand.nextInt(2000), 19);
		PersonThread p20 = new PersonThread(hotelFloor, rand.nextInt(2000), 20);

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();
		p7.start();
		p8.start();
		p9.start();
		p10.start();
		p11.start();
		p12.start();
		p13.start();
		p14.start();
		p15.start();
		p16.start();
		p17.start();
		p18.start();
		p19.start();
		p20.start();
	}
}