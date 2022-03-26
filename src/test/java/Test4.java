import java.util.HashMap;
import java.util.Map;

class InitClass {
    static {
        System.out.println("初始化InitClass");
    }

    public static String a = null;
    public final static String b = "b";

    public static void method() {
    }
}

class SubInitClass extends InitClass {
    static {
        System.out.println("初始化SubInitClass");
    }
}

public class Test4 {

    private static int x = 100;
    public static void main(String args[ ]){
        Test4 hs1 = new Test4(); hs1.x++;
        System.out.println( "x=" +x);
        Test4 hs2 = new Test4();
        hs2.x++;
        System.out.println( "x=" +x);
        hs1=new Test4();
        hs1.x++;
        System.out.println( "x=" +x);
        Test4.x--;
        System.out.println( "x=" +x);
    }
}