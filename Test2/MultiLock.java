import java.util.concurrent.locks.*;
import java.util.Random;

public class MultiLock{

	int rooms;
	int[] occupantsIn;
	boolean[] occupied;
	final ReentrantLock roomLock = new ReentrantLock();
	Condition waiting = roomLock.newCondition();
	Random rando;
	
	public MultiLock(int rooms){
		this.rooms = rooms;
		this.occupantsIn = new int[rooms];
		this.occupied = new boolean[rooms];
		for (int i=0; i<rooms; i++){
			occupantsIn[i] = 0;
			occupied[i] = false;
		}
		this.rando = new Random();
	}

	public void request(int roomNumber){
		roomLock.lock();
		try{
			boolean x = true;
			while (x){
				for (int i=0; i<rooms; i++){
					if (occupied[i] && (roomNumber-1)==0){
						String msg = "Waiting - Room occupancy is as follows ";
						for (int j=0; j<rooms; j++){
							msg += "Rm" + (j+1) + ":" + " " + occupantsIn[j] + "   ";
						}
						System.out.println(msg);
						waiting.await();
						i = -1;
					}
					else if(occupied[i] && (roomNumber-1)!=i){
						String msg = "Waiting - Room occupancy is as follows ";
						for (int k=0; k<rooms; k++){
							msg += "Rm" + (k+1) + ":" + " " + occupantsIn[k] + "   ";
						}
						System.out.println(msg);
						waiting.await();
						i = -1;
					}
				}
				x = false;
			}
			occupied[roomNumber-1] = true;
			occupantsIn[roomNumber-1]++;
			System.out.println("Room " + roomNumber + " now has " + occupantsIn[roomNumber-1] + " occupant(s)");
		} catch (InterruptedException exc){
			exc.printStackTrace();
		} finally {
			roomLock.unlock();
		}
	}

	public void release(int roomNumber){
		roomLock.lock();
		if (occupantsIn[roomNumber-1]==0){
			System.out.println("There was noboy in room " + roomNumber);
		} else {
			occupantsIn[roomNumber-1]--;
			if (occupantsIn[roomNumber-1]==0){
				occupied[roomNumber-1] = false;
				System.out.println("Room " + roomNumber + " is now empty.");
				waiting.signalAll();
			}
		}
		roomLock.unlock();
	}
}














