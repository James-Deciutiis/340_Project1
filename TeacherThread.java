import java.util.concurrent.atomic.AtomicBoolean;

public class TeacherThread extends Thread{
	
	public static final int GIRL = 0;
	public static final int BOY = 1;
	private int classSize = 6;
	private int girlsBathroomCount = 0;
	private int boysBathroomCount = 0;
	private StudentThread [] students;
	private int [] bathroomQueue;
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
		
		int i = 0;
		while(atomicBoolean.get()){
			for(int j = 0; j < classSize; j++){
				//checks if student is busy waiting in schoolyard, then teacher lets them in
				//students are put into the bathroom queue based on the order they were let into the school
				if(students[j].getBusy() == 1){
					students[j].setBusy(0);
					bathroomQueue[i] = j;
				}
			}

			for(int j = 0; j < classSize; j++){
				if(students[bathroomQueue[j]].getWaitingForBathroom() == 1 && students[bathroomQueue[j]].getGender() == BOY){
					//if theres room in boys bathroom let boy go to the bathroom
					if(boysBathroomCount < 2){
						students[bathroomQueue[j]].setWaitingForBathroom(0);
						boysBathroomCount++;
					}
				}
				
				else if(students[bathroomQueue[j]].getWaitingForBathroom() == 1 && students[bathroomQueue[j]].getGender() == GIRL){
					//if theres room in boys bathroom let boy go to the bathroom
					if(boysBathroomCount < 2){
						students[bathroomQueue[j]].setWaitingForBathroom(0);
						girlsBathroomCount++;
					}
				}

				//if student is done with the bathroom, free up a spot in the bathroom and move on
				if(students[bathroomQueue[j]].getDoneWithBathroom() == 1 && students[bathroomQueue[j]].getGender() == BOY){
					boysBathroomCount--;
					students[bathroomQueue[j]].setDoneWithBathroom(0);
					i++;
				}
				else if(students[bathroomQueue[j]].getDoneWithBathroom() == 1 && students[bathroomQueue[j]].getGender() == GIRL){
					girlsBathroomCount--;
					students[bathroomQueue[j]].setDoneWithBathroom(0);
					i++;
				}
			}
			
			if(i >= classSize){
				atomicBoolean.set(false);
			}
		}
		
		System.out.println("TEST TEST TEST TEST");
	}
	
	

	public void run(){
		System.out.println("Teacher thread created");
	}
}
