package com.hujiya.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class TestController {


    @RequestMapping("/test")
    public String test() {
        return "test";
    }



    public static void main(String[] args) {
        SpringApplication.run(TestController.class, args);


        System.out.println();
    }
}
