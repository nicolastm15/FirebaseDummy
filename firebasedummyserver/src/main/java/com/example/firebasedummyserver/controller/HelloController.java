package com.example.firebasedummyserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.firebasedummyserver.model.Message;

@RestController
public class HelloController {

	@GetMapping("/admin/hello")
	public Message sayHelloToAdmin() {
		return new Message("Hello Admin");
	}

	@GetMapping("/basic/hello")
	public Message sayHelloToUser() {
		return new Message("Hello User");
	}
	
}
