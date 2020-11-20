import java.util.Random;
public class StudentThread extends Thread{
	public static final int GIRL = 0;
	public static final int BOY = 1;
	public static final int MAX_SLEEP_TIME = 10000;
	public static long time = System.currentTimeMillis();
	private String Student_Id;
	private int gender;
	private int busy;
	private int waiting;
	private int waitingForClass;
	private int doneWithBathroom = 0;
	private boolean schoolInSession = true;

	public StudentThread(String id){
		this.Student_Id = id;
		this.gender = getRandomGender();
		this.printMessage(" Gender is: " + this.gender);
	}

	public void run(){
		//Student thread created
		this.printMessage(" woken up!");

		//Health questionaire
		this.sleepMessage(" is filling out health questionaire");
		
		//Student commutes to school
		this.sleepMessage(" is commuting to school");
		
		//wait to be let into the school
		this.printMessage(" waiting in the schoolyard to be called by teacher");
		this.busy = 1;
		while(this.busy == 1){
			this.idle();
		}
		this.printMessage(" has been let in by the teacher");

		//waiting for bathroom
		this.printMessage(" waiting for bathroom");
		this.waiting = 1;
		this.yield();
		this.yield();
		this.yield();
		while(this.waiting == 1){
			this.idle();
		}
		this.sleepMessage(" is using the bathroom");
		this.printMessage(" is done using the bathroom");
		this.doneWithBathroom++;
		
		//this decides if the student is excited or not
		if(this.isStudentExcited()){
			this.printMessage(" is excited to learn!");
			this.setPriority(MAX_PRIORITY);
			this.sleepMessage(" priority increased");
			this.setPriority(NORM_PRIORITY);
			this.printMessage(" priority set back to normal");
		}
		
		//wait for first class to start
		this.printMessage(" is waiting for the first class to start");
		this.waitingForClass = 1;
		while(this.waitingForClass == 1){
			this.idle();
		}
		this.sleepMessage(" is attending the first class and is now sleeping.");
		
		//student tries to have fun between class
		while(this.schoolInSession){	
			this.busy = 1;
			this.sleepMessage(" is hurrying to have fun before next class");
			this.busy = 0;	
			this.waiting = 1;
		}

		//Student finishes day
		this.printMessage(" is done");
	}
	
	public void sleepMessage(String msg){
		try{
			System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Student " + this.Student_Id + msg);
			this.sleep(this.getRandomSleepTime());
		}
		catch(InterruptedException e){
			return;
		}
	}

	public void idle(){
		try{
			this.sleep(1);
		}
		catch(InterruptedException e){
			return;
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
		int retval = rand.nextInt(2);

		return retval;
	}
	
	public boolean isStudentExcited(){
		Random rand = new Random();
		int i = rand.nextInt(3);

		return i == 1;
	}
	
	public void setSchoolInSession(boolean session){
		this.schoolInSession = session;
	}

	public int getBusy(){
		return this.busy;
	}

	public void setBusy(int busy){
		this.busy = busy;
	}
	
	public int getWaiting(){
		return this.waiting;
	}

	public void setWaiting(int new_waiting){
		this.waiting = new_waiting;
	}
	
	public int getWaitingForClass(){
		return this.waitingForClass;
	}

	public void setWaitingForClass(int new_waiting){
		this.waitingForClass = new_waiting;
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
