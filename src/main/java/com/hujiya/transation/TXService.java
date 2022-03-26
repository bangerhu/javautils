package com.hujiya.transation;

import com.hujiya.test.entity.OrderEntity;
import com.hujiya.test.entity.UserEntity;
import com.hujiya.test.service.OrderService;
import com.hujiya.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TXService {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Transactional()
    public void serviceA() {
        UserEntity user = new UserEntity();
        user.setName("liuliu");
        userService.save(user);
    }

    @Transactional()
    public void serviceB() {
            OrderEntity order = new OrderEntity();
            order.setOrderNo("oooooooookd0000dkdkkdkdkdkdkddkkkdkdkdkdkdkdkdkdkkddkdkdkdkdkdkdkkdkdkdk");
            orderService.save(order);
    }

    @Transactional
    public void service() {
        serviceA();
//        serviceB();
    }
}
