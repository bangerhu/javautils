package com.hujiya.ioc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

public class IocDemo {

    public static void main(String[] args) {

        StaticApplicationContext parent = new StaticApplicationContext();
        StaticApplicationContext child = new StaticApplicationContext(parent);

        RootBeanDefinition rbd = new RootBeanDefinition();
        rbd.setBeanClass(A.class);
        rbd.setScope(BeanDefinition.SCOPE_SINGLETON);
        rbd.getPropertyValues().add("name", "hujiya");
        rbd.getPropertyValues().add("age", 35);
        parent.registerBeanDefinition("a", rbd);


        ChildBeanDefinition cbd = new ChildBeanDefinition("a");
        cbd.setBeanClass(A.class);
        cbd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        cbd.getPropertyValues().add("age", 30);
        child.registerBeanDefinition("a", cbd);

//        System.out.println(parent.getBean(A.class).name);
//        A a1 = (A) parent.getBean("a");
//        System.out.println(a1.age);


        System.out.println(child.getBean(A.class));
        A a2 = (A) child.getBean("a");
        System.out.println(a2);
    }
}

@Setter
@Getter
class A {

    String name;
    int age;
}