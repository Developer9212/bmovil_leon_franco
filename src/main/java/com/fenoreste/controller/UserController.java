package com.fenoreste.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.alianza_stp.SpeiClient;
import com.fenoreste.alianza_stp.tokenModel;
import com.fenoreste.entity.User;
import com.fenoreste.service.IUserService;


@RestController
@RequestMapping({"/users" })
@CrossOrigin(origins = "*")
public class UserController {
    
	@Autowired
	private IUserService userSevice;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private SpeiClient speiClient;
	
	@GetMapping
	public ResponseEntity<?>obtenerUsuarios(){
		List<User>users = userSevice.findAll();
		if(users != null) {
			return new ResponseEntity<>(users,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create_user")
	public ResponseEntity<?>crearUsuario(@RequestBody User user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setId(new Random().nextInt());
		
		this.userSevice.save(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@GetMapping("/tokenTest")
	public tokenModel token() {		
		return speiClient.tokenAuth();
	}
	
}
