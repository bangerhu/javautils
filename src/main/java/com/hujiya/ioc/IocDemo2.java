package com.hujiya.ioc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.StaticApplicationContext;

public class IocDemo2 {

    public static void main(String[] args) {

        StaticApplicationContext parent = new StaticApplicationContext();
        StaticApplicationContext child = new StaticApplicationContext(parent);

        AbstractBeanDefinition rbd = BeanDefinitionBuilder.rootBeanDefinition(B.class).setScope(BeanDefinition.SCOPE_SINGLETON)
                .addPropertyValue("name", "hujiya").addPropertyValue("age", 35).getBeanDefinition();
        parent.registerBeanDefinition("b", rbd);

        AbstractBeanDefinition cbd = BeanDefinitionBuilder.childBeanDefinition("b").setScope(BeanDefinition.SCOPE_PROTOTYPE)
                .addPropertyValue("age", 20).getBeanDefinition();
        child.registerBeanDefinition("b", cbd);

        System.out.println(parent.getBean(B.class).name);
        B a1 = (B) parent.getBean("b");
        System.out.println(a1.age);

        System.out.println(child.getBean(B.class));
        B a2 = (B) child.getBean("b");
        System.out.println(a2);

        int a = 1+1;
    }
}

@Setter
@Getter
class B {

    String name;
    int age;
}