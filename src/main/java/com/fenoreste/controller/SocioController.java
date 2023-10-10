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
import com.fenoreste.modelos.ResponseError;
import com.fenoreste.modelos.ResponseSocio;
import com.fenoreste.service.CapaSocioService;
import com.fenoreste.service.IFuncionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/cma/socios")
@CrossOrigin(origins = "*")
@Slf4j
public class SocioController {

	@Autowired
	private CapaSocioService capaSocioService;
	@Autowired
	private IFuncionService funcionService;
	
	@GetMapping("/{numeroSocio}")
	public ResponseEntity<?> buscarSocio(@PathVariable("numeroSocio") String numeroSocio) {
		ResponseError error = new ResponseError();
		if(funcionService.horaActividad()) {
			ResponseSocio response = capaSocioService.buscarSocioPorOgs(numeroSocio);			
			if(response.getCodigo() == 200) {
				return new ResponseEntity<>(response,HttpStatus.OK);
			}else {
				error = new ResponseError();
				error.setCodigo("App-"+response.getCodigo()+".AppN-M");
				error.setMensajeUsuario(response.getMensaje());
				if(response.getCodigo() == 404) {
					return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
				}else if(response.getCodigo() == 409) {
					return new ResponseEntity<>(error,HttpStatus.CONFLICT);
				}else if(response.getCodigo() == 500) {
					return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);	
				}else {
					log.info("Codigo de errror invalido:"+response.getCodigo());
					return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}	
		}else {
			error = new ResponseError();
			error.setCodigo("App-"+409+".AppN-M");
			error.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
			return new ResponseEntity<>(error,HttpStatus.CONFLICT);
		}
		
	}
	
	@GetMapping(value="/{numeroSocio}/cuentas",params = {"subtipo"})
	public ResponseEntity<?> buscarSocioCuentas(@PathVariable(name="numeroSocio") String numeroSocio,
			  									@RequestParam(name="subtipo") String tipo,
			  									@RequestParam(name="offset") int offset,
			  									@RequestParam(name="limit") int limit) {
		log.info("Iniciando..................");
		ResponseError error = new ResponseError();
		if(funcionService.horaActividad()) {
			ResponseCuentaPrincipal response = capaSocioService.buscarSocioCuentas(numeroSocio, tipo, offset, limit);
			if(response.getCodigo() == 200) {
				return new ResponseEntity<>(response,HttpStatus.OK);
			}else {			
				error.setCodigo("App-"+response.getCodigo()+".AppN-M");
				error.setMensajeUsuario(response.getMensaje());
				if(response.getCodigo() == 404) {
					return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
				}else if(response.getCodigo() == 409) {
					return new ResponseEntity<>(error,HttpStatus.CONFLICT);
				}else if(response.getCodigo() == 500) {
					return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);	
				}else {
					log.info("Codigo de errror invalido:"+response.getCodigo());
					return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}else {
			error = new ResponseError();
			error.setCodigo("App-"+409+".AppN-M");
			error.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
			return new ResponseEntity<>(error,HttpStatus.CONFLICT);
		}
		
	}
	
}
