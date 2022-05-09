package com.example.springboot;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.concurrent.atomic.AtomicLong;

import static com.example.springboot.User.userData;

@RestController
@RequestMapping("/api")
public class HomeController {

	@GetMapping("/")
	public String greeting(){
		return "Hello World";
	}

	@GetMapping("/home")
	public Home home(){
		return new Home(true,"Welcome to the home page");
	}

	@GetMapping("/user/{userId}")
	public String getUserId(@PathVariable String userId){
		return "Your user id is " + userId;
	}

	@PostMapping("/register")
	public Home createUser(@RequestBody User data){
		userData.add(new User(data.name,data.email,data.password));

		for (User d:
			 userData) {
			System.out.println(d.email);
			System.out.println(d.password);
			System.out.println(d.name);

		}
		return new Home(true,"User added");
	}


}
