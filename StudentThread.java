import java.util.Random;
public class StudentThread extends Thread{
	
	public static final int sleepTime = 10000;
	private String Student_Id;
	private int busy = 0;

	public StudentThread(String id){
		this.Student_Id = id;
		System.out.println("busy " + this.busy);
	}

	public void run(){
		//Student thread created
		System.out.println("Student " + this.Student_Id + " woken up!");

		//Health questionaire
		this.sleepMessage("Student " + this.Student_Id + " is filling out health questionaire");
		
		//Student commutes to school
		this.sleepMessage("Student " + this.Student_Id + " is commuting to school");
		
		System.out.println("Student " + this.Student_Id + " waiting in the schoolyard to be called by teacher");
		this.busy++;
		while(busy == 1){
			//wait in schoolyard

		}


		//Student finishes day
		System.out.println("Student " + this.Student_Id + " done");
	}
	
	public void sleepMessage(String msg){
		try{
			System.out.println(msg);
			this.sleep(sleepTime);
		}
		catch(InterruptedException e){

		}
	}

	public void interruptMessage(String msg){
		System.out.println(msg);
		this.interrupt();
	}
	
	public int getBusy(){
		return this.busy;
	}

	public void setBusy(int busy){
		this.busy = busy;
	}
}
