package com.learn.rest.webservices.restfulwebservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.learn.rest.webservices.restfulwebservices.hello.HelloWorldBean;

@RestController
public class HelloWorldController {
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello!";
	}
	
	@GetMapping("hello-world-bean")
	public HelloWorldBean showHelloWorldBean() {
		return new HelloWorldBean("Hello Web!");
	}
	
	@GetMapping("hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello %s!", name));
	}
}
