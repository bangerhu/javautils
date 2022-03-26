package com.hujiya;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//// 同时注入多个模块的bean时，不同bean的包名必须相同
@SpringBootApplication()
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class IvayAppApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(IvayAppApplication.class, args);
            System.out.println("----------------------app start----------------------");
        } catch (Exception e) {
            log.error("e:", e);

        }
    }
}
