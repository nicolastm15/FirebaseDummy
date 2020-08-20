package com.example.firebasedummyserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.firebasedummyserver.model.Message;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public Message sayHello() {
		return new Message("Hello World");
	}
	
}
