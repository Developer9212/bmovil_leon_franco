package com.fenoreste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.entity.Ws_clabe_respuesta_alianza;
import com.fenoreste.modelos.ResponseError;
import com.fenoreste.service.CapaSTPService;
import com.fenoreste.service.IFuncionService;


@RestController
@RequestMapping("spei/alianza")
@CrossOrigin(origins = "*")

public class ClabesSTPController {
   
	@Autowired
	private CapaSTPService capaSTPService;
	
	@Autowired
	private IFuncionService funcionService;
	
	@GetMapping(value="altaclabe/{socio}")
	public ResponseEntity<?> altaClabe(@PathVariable("socio") String socio) {
		ResponseError error = new ResponseError();
		if(funcionService.horaActividad()) {
			Ws_clabe_respuesta_alianza res = capaSTPService.altaClabe(socio);
			return new ResponseEntity<>(res,HttpStatus.CREATED);	
		}else {
			error.setCodigo("App-"+409+".AppN-M");
			error.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
			return new ResponseEntity<>(error,HttpStatus.CONFLICT);	
		}
		
		
	}
}
