package com.pipeline.spring_docker_jenkins;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing")
public class TestingController {

    @GetMapping("/say-hello")
    public String sayHello(){
        return "Hello";
    }

}
