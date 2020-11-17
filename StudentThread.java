import java.util.Random;
public class StudentThread extends Thread{
	
	public static final int GIRL = 0;
	public static final int BOY = 1;
	public static final int MAX_SLEEP_TIME = 10000;
	public static long time = System.currentTimeMillis();
	private String Student_Id;
	private int gender;
	private int busy = 0;
	private int waitingForBathroom = 0;
	private int doneWithBathroom = 0;

	public StudentThread(String id){
		this.Student_Id = id;
		this.gender = getRandomGender();
	}

	public void run(){
		//Student thread created
		this.printMessage(" woken up!");

		//Health questionaire
		this.sleepMessage(" is filling out health questionaire");
		
		//Student commutes to school
		this.sleepMessage(" is commuting to school");
		
		this.printMessage(" waiting in the schoolyard to be called by teacher");
		this.busy++;
		while(this.busy == 1){/*wait in schoolyard*/}
		this.printMessage(" has been let in by the teacher");

		//waiting for bathroom
		this.printMessage(" waiting for bathroom");
		this.waitingForBathroom++;
		while(this.waitingForBathroom == 1){
			this.yield();
			this.yield();
			this.yield();
		}
		this.sleepMessage(" is using the bathroom");
		this.doneWithBathroom++;

		//Student finishes day
		this.printMessage(" is done");
	}
	
	public void sleepMessage(String msg){
		try{
			System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Student " + this.Student_Id + msg);
			this.sleep(this.getRandomSleepTime());
		}
		catch(InterruptedException e){

		}
	}

	public void interruptMessage(String msg){
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Student " + this.Student_Id + msg);
		this.interrupt();
	}
	
	public void printMessage(String msg){
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Student " + this.Student_Id + msg);
	}

	public int getRandomSleepTime(){
		Random rand = new Random();
		int retval = rand.nextInt(MAX_SLEEP_TIME);

		return retval;
	}
	
	public int getRandomGender(){
		Random rand = new Random();
		int retval = rand.nextInt(BOY);

		return retval;
	}
	
	public int getBusy(){
		return this.busy;
	}

	public void setBusy(int busy){
		this.busy = busy;
	}
	
	public int getWaitingForBathroom(){
		return this.waitingForBathroom;
	}

	public void setWaitingForBathroom(int waiting){
		this.waitingForBathroom = waiting;
	}
	
	public int getGender(){
		return this.gender;
	}

	public int getDoneWithBathroom(){
		return this.doneWithBathroom;
	}

	public void setDoneWithBathroom(int done){
		this.doneWithBathroom = done;
	}
}
