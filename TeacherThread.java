import java.util.concurrent.atomic.AtomicBoolean;

public class TeacherThread extends Thread{
	
	public static final int GIRL = 0;
	public static final int BOY = 1;
	public static long time = System.currentTimeMillis();
	private int classSize = 1;
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
			students[i] = new StudentThread(Integer.toString(i));
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
		
		this.sleepMessage("is getting ready to teach first class."); 
		for(int i = 0; i < classSize; i++){
			System.out.println("hellow");	
			students[i].setWaiting(0);	
		}
		this.printMessage("is teaching the first class, period 1");
		
		try{
			this.sleep(10000);
		}
		catch(InterruptedException e){
		}

		//teaching first class now
			

		System.out.println("TEST TEST TEST TEST");
	}
	
	

	public void run(){
		System.out.println("Teacher thread created");
	}
	
	public void sleepMessage(String msg){
		try{
			System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Teacher " + msg);
			this.sleep(10000);
		}
		catch(InterruptedException e){

		}
	}
	
	public void printMessage(String msg){
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Teacher " + msg);
	}
	
	public boolean getIsTeaching(){
		return this.isTeaching;
	}
}
