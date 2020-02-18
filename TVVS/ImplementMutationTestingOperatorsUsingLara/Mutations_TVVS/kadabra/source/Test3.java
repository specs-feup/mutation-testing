public class Test3 {
	
    public static final int x = 222;

    private final int a = 222;

    private final int b = 1;

    private int c = 4;


    // Constructor
    public Test3(int b) {
        this.b = b << 2;
    }

    // Global variable declaration
    public int foo() {
        final int d = 3;
        return c;
    }
	
	public int operation() {
		int sum = 0;
		if(a>b)
			sum = a + b + c;
		return sum;
	}
	
}