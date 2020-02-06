public class Test4 {
	
	private String b;
	private Test4 c; 
	
	//Constructor
	public Test4(String b) {
		this.b = new String("");
		this.c = null;
	}
	
	//Global variable declaration
	public int test1() {
		this.c.test5();
		return 0;
	}

	public int test2(String object) {
		object = new String();
		if (object.length() == 2) {
			b = "hey";
		}
		return -1;
	}		

	public double test4(long a, boolean b) {
		return 0;
	}	

	public void test5() {
	}
	
}

