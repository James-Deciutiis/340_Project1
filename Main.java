public class Main{
	public static void main(String args []){
		int classSizeArg;
		if(args.length > 0){
			try{
				classSizeArg = Integer.parseInt(args[0]);
				TeacherThread teacher = new TeacherThread(classSizeArg); 	
				teacher.start();
			}
			catch (NumberFormatException e){
				System.err.println("Argument" + args[0] + " must be an integer.");
				System.exit(1);
			}
		}
		else{
			TeacherThread teacher = new TeacherThread(); 	
			teacher.start();
		}
	}
}
