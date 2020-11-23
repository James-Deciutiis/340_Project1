import java.util.Random;
public class StudentThread extends Thread{

	public static final int GIRL = 0;
	public static final int BOY = 1;
	public static final int MAX_SLEEP_TIME = 5000;
	public static long time = System.currentTimeMillis();
	private int Student_Id;
	private int gender;
	private int busy;
	private int waiting;
	private int waitingForClass = 0;
	private int doneWithBathroom = 0;
	private int classesAttended = 0;
	private boolean classInSession = false;
	private boolean schoolInSession = true;
	private String [] classReport;

	public StudentThread(int id){
		this.Student_Id = id;
		this.gender = getRandomGender();
		String gender_string;

		if(this.gender == BOY){
			gender_string = "BOY";
		}
		else{
			gender_string = "GIRL";
		}

		this.printMessage(" Gender is: " + gender_string);
		this.classReport = new String[5]; 
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
		this.yieldMessage(" has yielded");
		this.yieldMessage(" has yielded");
		this.yieldMessage(" has yielded");
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
			this.sleepMessage(" priority increased, now sleeping");
			this.setPriority(NORM_PRIORITY);
			this.printMessage(" priority set back to normal");
		}
		
		//wait for first class to start
		this.printMessage(" is waiting for the first class to start");
		this.waitingForClass = 1;
		while(this.waitingForClass == 1){
			this.idle();
		}

		//student tries to have fun between class
		int period = 1;
		int classNumber = 1;
		while(this.schoolInSession){	
			this.waiting = 0;
			this.busy = 1;

			//if class is in session before they get there then they've missed class and have to wander campus
			if(this.classInSession){
				if(period == 3){
					this.sleepMessage(" has missed period: " + period + " class: office hours, and is now wandering around campus");	
					period++;
				}
				else{
					this.sleepMessage(" has missed period: " + period + " class: " + classNumber + " and is now wandering around campus");	
					period++;
					classNumber++;
				}

				while(this.classInSession){
					this.idle();
				}
				if(period == 6){
					this.schoolInSession = false;
					continue;
				}
			}
			
			this.waiting = 1;
			this.printMessage(" busy waiting for the next class to start");
			while(this.waiting == 1){
				this.idle();
			}

			if(period == 3){
				this.sleepMessage(" is in office hours, period: " + period + " and has fallen asleep.");		
				this.classReport[classesAttended] = "Period: 3, office hours";
				this.classesAttended++;
			}
			else{
				this.sleepMessage(" is in period: " + period + " class: " + classNumber + " and has fallen asleep.");		
				this.classReport[classesAttended] = "Period: " + period + ", class: " + classNumber;
				this.classesAttended++;
				classNumber++;
			}

			while(this.classInSession){
				this.idle();
			}
			period++;
			if(period == 6){
				this.schoolInSession = false;
				continue;
			}
			this.sleepMessage(" is hurrying to have fun before next class");
		}
		
		this.printMessage(" is waiting to join their friend before they go home");
		this.waiting = 1;
		while(this.waiting == 1){
			this.idle();
		}

		//Student finishes day
		this.printMessage(" has finished school");
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

	public void yieldMessage(String msg){
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
	
	public void printReport(){
		System.out.println("<---------- End of school report for student: " + this.Student_Id + " ---------->");
		System.out.println("Number of classes attended: " + this.classesAttended + " out of 5 total");
		System.out.println("Classes attended: "); 
		
		for(int i = 0; i < this.classesAttended; i++){
			System.out.println(this.classReport[i]);
		}
	}
		
	public void joinMessage(String msg){
		try{
			System.out.println("[" + (System.currentTimeMillis() - time) + "] " + "Student " + this.Student_Id + msg);
			this.join();	
		}
		catch(InterruptedException e){
			return;
		}
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
	
	public void setClassInSession(boolean session){
		this.classInSession = session;
	}

	public boolean getClassInSession(){
		return this.classInSession;
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
