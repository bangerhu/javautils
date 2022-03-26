import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Test3 {

    @Autowired
    RedisTemplate redisTemplate;

    public Test3() {
    }

    static<A,B,C> void test(List<A> list, B b, List<?> c) {

        list.forEach(obj -> System.out.println(obj));
    }

    public static <A> void main(String[] args) throws Exception {
        Test3 test3 = new <A>Test3();

        System.out.println("ggooooooooo");
    }
}
