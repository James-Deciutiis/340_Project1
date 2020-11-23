import java.util.concurrent.atomic.AtomicBoolean;

public class TeacherThread extends Thread{
	
	public static final int GIRL = 0;
	public static final int BOY = 1;
	public static long time = System.currentTimeMillis();
	private int classSize = 12;
	private int girlsBathroomCount = 0;
	private int boysBathroomCount = 0;
	private StudentThread [] students;
	private int [] bathroomQueue;
	private boolean isTeaching = false;
	AtomicBoolean atomicBoolean = new AtomicBoolean(true); 

	public TeacherThread(){
		students = new StudentThread[classSize];
		bathroomQueue = new int[classSize];

		for(int i = 0; i < classSize; i++){
			students[i] = new StudentThread(i);
		}
		
		for(int i = 0; i < classSize; i++){
			students[i].start();
		}
		
		//int doneWithBrCount is the amount of students that have finished going to the bathroom.
		//int brPosition is the spot in the bathroom line the student will be placed
		//in after being let into the school by the teacher after the busy wait.
		int doneWithBrCount = 0;
		int brPosition = 0;
		while(atomicBoolean.get()){
			for(int j = 0; j < classSize; j++){
				//checks if student is busy waiting in schoolyard, then teacher lets them in
				//students are put into the bathroom queue based on the order they were let into the school
				if(students[j].getBusy() == 1){
					students[j].setBusy(0);
					bathroomQueue[brPosition] = j;
					brPosition++;
				}
			}

			for(int j = 0; j < classSize; j++){
				if(students[bathroomQueue[j]].getWaiting() == 1 && students[bathroomQueue[j]].getGender() == BOY){
					//if theres room in boys bathroom let boy go to the bathroom
					if(boysBathroomCount < 2){
						students[bathroomQueue[j]].setWaiting(0);
						this.boysBathroomCount++;
					}
				}
				
				else if(students[bathroomQueue[j]].getWaiting() == 1 && students[bathroomQueue[j]].getGender() == GIRL){
					//if theres room in boys bathroom let boy go to the bathroom
					if(girlsBathroomCount < 2){
						students[bathroomQueue[j]].setWaiting(0);
						this.girlsBathroomCount++;
					}
				}

				//if student is done with the bathroom, free up a spot in the bathroom and move on
				if(students[bathroomQueue[j]].getDoneWithBathroom() == 1 && students[bathroomQueue[j]].getGender() == BOY){
					this.boysBathroomCount--;
					students[bathroomQueue[j]].setDoneWithBathroom(0);
					doneWithBrCount++;
				}
				else if(students[bathroomQueue[j]].getDoneWithBathroom() == 1 && students[bathroomQueue[j]].getGender() == GIRL){
					this.girlsBathroomCount--;
					students[bathroomQueue[j]].setDoneWithBathroom(0);
					doneWithBrCount++;
				}
			}
			
			if(doneWithBrCount == classSize){
				atomicBoolean.set(false);
			}
		}
		
		//just for the first class, make sure all students are ready
		this.sleepMessage("is getting ready to teach first class."); 
		this.printMessage("is teaching the first class, period 1");
		for(int i = 0; i < classSize;){
			if(students[i].getWaitingForClass() == 1){
				students[i].setWaitingForClass(0);	
				students[i].setClassInSession(true);
				i++;
			}
		}
		try{
			this.sleep(100);
		}
		catch(InterruptedException e){
		}
		this.wakeUpStudents();
		
		atomicBoolean.set(true);
		int period = 2;
		//teach the rest of the classes
		while(atomicBoolean.get()){
			if(period == 3){
				this.sleepMessage("is getting ready for office hours.");
				this.printMessage("is teaching office hours, period: " + period);

			}
			else{
				this.sleepMessage("is getting ready to teach next class.");
				this.printMessage("is teaching the next class, period: " + period);
			}

			for(int i = 0; i < classSize; i++){
				students[i].setClassInSession(true);
				if(students[i].getWaiting() == 1){
					students[i].setWaiting(0);
					students[i].setBusy(0);
				}
			}
			
			try{
				this.sleep(5000);
			}
			catch(InterruptedException e){
			}
			this.wakeUpStudents();	

			period++;
			
			for(int i = 0; i < classSize; i++){
				students[i].setClassInSession(false);
			}

			if(period == 6){
				atomicBoolean.set(false);
			}
		}

		for(int i = 0; i < classSize;){
			if(i == classSize-1){
				break;
			}

			if(students[i].isAlive() && students[i].getWaiting() == 1){
				students[i].setWaiting(0);
				students[i].joinMessage(" has joined student " + (i+1) + " and is now going home");
				i++;
			}
		}
			
		if(students[classSize-1].isAlive() && students[classSize-1].getWaiting() == 1){
			students[classSize-1].setWaiting(0);
			students[classSize-1].joinMessage(" has joined the teacher and is now going home");
		}
		
		for(int i = 0; i < classSize; i++){
			students[i].printReport();
		}

		this.printMessage("is done teaching class, now going home and locking up the school");
	}
	
	public void run(){
		System.out.println("Teacher thread created");
	}
	
	public void sleepMessage(String msg){
		try{
			System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Teacher " + msg);
			this.sleep(3000);
		}
		catch(InterruptedException e){

		}
	}
	
	public void printMessage(String msg){
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Teacher " + msg);
	}
	
	public void wakeUpStudents(){
		for(int i = 0; i < this.classSize; i++){
			if(!this.students[i].isInterrupted() && this.students[i].getBusy() == 0){
				this.students[i].interruptMessage(" is woken up by the teacher! (INTERRUPTED)");
			}
		}
	}

	public boolean getIsTeaching(){
		return this.isTeaching;
	}
}
