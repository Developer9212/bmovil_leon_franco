package com.fenoreste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.service.CapaSTPService;


@RestController
@RequestMapping("spei/alianza")
@CrossOrigin(origins = "*")

public class ClabesSTPController {
   
	@Autowired
	private CapaSTPService capaSTPService;
	
	@GetMapping(value="altaclabe/{socio}")
	public ResponseEntity<?> altaClabe(@PathVariable("socio") String socio) {
		capaSTPService.altaClabe(socio);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
