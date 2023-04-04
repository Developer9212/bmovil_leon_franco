package com.fenoreste.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.modelos.ResponseCuentaPrincipal;
import com.fenoreste.modelos.ResponseSocio;
import com.fenoreste.service.CapaSocio;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/cma/socios")
@CrossOrigin(origins = "*")
@Slf4j
public class SocioController {

	@Autowired
	private CapaSocio capaSocioService;
	
	@GetMapping("/{numeroSocio}")
	public ResponseEntity<?> buscarSocio(@PathVariable("numeroSocio") String numeroSocio) {
		ResponseSocio response = capaSocioService.buscarSocioPorOgs(numeroSocio);
		if(response.getCodigo() == 200) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value="/{numeroSocio}/cuentas",params = {"subtipo"})
	public ResponseEntity<?> buscarSocioCuentas(@PathVariable(name="numeroSocio") String numeroSocio,
			  									@RequestParam(name="subtipo") String tipo,
			  									@RequestParam(name="offset") int offset,
			  									@RequestParam(name="limit") int limit) {
		log.info("Iniciando..................");
		ResponseCuentaPrincipal response = capaSocioService.buscarSocioCuentas(numeroSocio, tipo, offset, limit);
		if(response.getCuentas().size() > 0) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
}
