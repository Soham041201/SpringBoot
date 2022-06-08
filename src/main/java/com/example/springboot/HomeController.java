package com.example.springboot;

import com.example.springboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;


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
	public ResponseEntity<Home> home(@RequestHeader String Authorization,@RequestHeader String token){
		String id = Authorization.split("Bearer")[1].trim();
		boolean isValid = JsonToken.verifyToken(token);

		User user = repo.findByid(id);
		if(user.isLogin){
			if(isValid){
				return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"Welcome to our project " + user.name.toUpperCase()+ '!'));
			}
			user.isLogin = false;
			repo.save(user);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Your token has expired! Please login again to view this"));

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
		String token = JsonToken.generateToken(userModel.email,userModel.password);
	if(userModel != null){

		return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"New user created" ,token, userModel.id));
	}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Error occurred"));
	}
	@PostMapping("/login")
	public ResponseEntity<Home> getUsers(@RequestBody Login data){
		User user = repo.findByEmail(data.email);
	if(user.isLogin){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"User already logged in ", user.id));
	}
		if(user.password.equals(data.password)){
			user.isLogin = true;
			repo.save(user);
			String token = JsonToken.generateToken(user.email,user.password);
			return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"Logged in successfully",token,user.id));

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Incorrect password"));
	}
	@PostMapping("/otp")
	public ResponseEntity<Home> getOTP(@RequestBody Login data){
		User user = repo.findByEmail(data.email);
		if(user.isLogin){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"User already logged in ", user.id));
		}
		if(user.password.equals(data.password)){
			user.isLogin = true;
			repo.save(user);
			String token = JsonToken.generateTokenForOTP(user.email,user.password);
			Random random = new Random();
			long otp = random.nextInt(1000);
			return ResponseEntity.status(HttpStatus.OK).body(new Home(true,"Your otp is " + otp,token,user.id));

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Home(false,"Incorrect password"));
	}

}
