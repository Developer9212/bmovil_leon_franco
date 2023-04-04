package com.fenoreste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.modelos.RequestTransferencia;
import com.fenoreste.modelos.ResponseTransferencia;
import com.fenoreste.service.CapaTransferencias;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/cma/transferencia")
@CrossOrigin(origins = "*")
@Slf4j
public class TransferenciaController {
	
	@Autowired
	private CapaTransferencias capaTransferencias;
	
	@GetMapping(value="entre-cuentas")
	public ResponseEntity<?> entreCuentas(@RequestBody RequestTransferencia peticion) {
		
		Integer tipoMovimiento = 0;
			if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("AHORR")) {
				tipoMovimiento = 0;
			}else if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("CRED")) {
				tipoMovimiento = 1;
			}
		
		log.info("Generando Transferencia....................");
		ResponseTransferencia responseTx = capaTransferencias.generarTransferencia(peticion,1,tipoMovimiento);
		
		if(responseTx.getFolioAutorizacion() != null) {
			return new ResponseEntity<>(responseTx,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
   
	
	
}
