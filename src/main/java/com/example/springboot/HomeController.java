package com.example.springboot;

import com.example.springboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HomeController {

	@Autowired
	UserRepository repo;

	@GetMapping("/")
	public String greeting(){
		return "Hello World";
	}

	@GetMapping("/home")
	public ResponseEntity<Home> home(@RequestHeader String Authorization){
		String id = Authorization.split("Bearer")[1].trim();
		User user = repo.findByid(id);
		if(user.isLogin){
			return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"Welcome to our project " + user.name.toUpperCase()+ '!'));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Please login to view this"));
	}

	@GetMapping("/logout/{userId}")
	public ResponseEntity<Home> logoutUser(@PathVariable String userId){
		User user = repo.findByid(userId);
		if(user != null && user.isLogin){
			user.isLogin = false;
			repo.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"User logged out"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"User already logged out"));
	}

	@PostMapping("/register")
	public ResponseEntity<Home> createUser(@RequestBody User data){

		if(repo.findByEmail(data.email) != null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"User already exists"));
		};
		User userModel = new User(data.name, data.email, data.password,true);
		repo.save(userModel);
		System.out.println(userModel);
	if(userModel != null){
		return ResponseEntity.status(HttpStatus.OK).body(new Home(true,userModel.id));
	}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Error occurred"));
	}
	@PostMapping("/login")
	public ResponseEntity<Home> getUsers(@RequestBody Login data){
		User user = repo.findByEmail(data.email);
	if(user.isLogin){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"User already logged in"));
	}
		if(user.password.equals(data.password)){
			user.isLogin = true;
			repo.save(user);
			return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"Logged in successfully" + user.id));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Incorrect password"));
	}
}
