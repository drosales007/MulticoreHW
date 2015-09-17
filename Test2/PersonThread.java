import java.util.Random;

public class PersonThread extends Thread{

	MultiLock hotelFloor;
	int timeInRoom;
	int threadId;
	Random rando;
	
	public PersonThread(MultiLock hotelFloor, int timeInRoom, int threadId){
		this.timeInRoom = timeInRoom;
		this.hotelFloor = hotelFloor;
		this.threadId = threadId;
		this.rando = new Random();
	}

	public void run(){
		int roomNum = rando.nextInt(5) + 1;
		System.out.println("Thread ID: " + threadId + " is requesting access to room " + roomNum);
		hotelFloor.request(roomNum);
		System.out.println("Thread ID: " + threadId + " has entered room " + roomNum);
		try{
			Thread.sleep(timeInRoom);
		} catch (InterruptedException exc){
			exc.printStackTrace();
		}
		System.out.println("Thread ID: " + threadId + " has left room " + roomNum);
		hotelFloor.release(roomNum);
	}
}