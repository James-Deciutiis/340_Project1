public class TeacherThread extends Thread{
	
	private int classSize = 1;
	private StudentThread [] students;

	public TeacherThread(){
		students = new StudentThread[classSize];

		for(int i = 0; i < classSize; i++){
			students[i] = new StudentThread(Integer.toString(i));
		}
		
		for(int i = 0; i < classSize; i++){
			students[i].start();
		}
		
		students[0].setBusy(0);
		while(students[0].getBusy() == 1){
		}
			
		System.out.println("TEST TEST TEST TEST");
	}
	
	

	public void run(){
		System.out.println("Teacher thread created");
	}
}
