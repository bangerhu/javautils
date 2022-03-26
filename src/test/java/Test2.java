import java.util.List;
import java.util.concurrent.Callable;

class InitClass2{
    public static Object f1 = new Callable(){

        {
            System.out.println("父类静态类变量1");
        }
        @Override
        public Object call() throws Exception {
            return null;
        }
    };
    public Object f2 = new Callable(){

        {
            System.out.println("父类静态类变量2");
        }
        @Override
        public Object call() throws Exception {
            return null;
        }
    };
    static{  
        System.out.println("运行父类静态代码");  
    }  
}  
  
class SubInitClass2 extends InitClass2{
    public Object f2 = new Callable(){

        {
            System.out.println("子类类静态类变量2");
        }
        @Override
        public Object call() throws Exception {
            return null;
        }
    };
    static{  
        System.out.println("运行子类静态代码");  
    }  
}  
  
public class Test2 {  
    public static void main(String[] args) throws ClassNotFoundException{  
        new SubInitClass2();  
    }  
}  