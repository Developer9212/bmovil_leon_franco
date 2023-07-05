package com.fenoreste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.modelos.RequestTransferencia;
import com.fenoreste.modelos.ResponseError;
import com.fenoreste.modelos.ResponseTransferencia;
import com.fenoreste.service.CapaTransferenciaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/cma/transferencia")
@CrossOrigin(origins = "*")
@Slf4j
public class TransferenciaController {
	
	@Autowired
	private CapaTransferenciaService capaTransferencias;
	
	@PostMapping(value="entre-cuentas")
	public ResponseEntity<?> entreCuentas(@RequestBody RequestTransferencia peticion) {
		log.info("................Accediendo a transferencia entre cuentas propias...............");
		Integer tipoMovimiento = 0;
			if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("AHORR")) {
				tipoMovimiento = 0;
			}else if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("CRED")) {
				tipoMovimiento = 1;
			}
		ResponseTransferencia responseTx = capaTransferencias.generarTransferencia(peticion,1,tipoMovimiento);
		
		if(responseTx.getCodigo() == 200 && responseTx.getFolioAutorizacion() != null) {
			return new ResponseEntity<>(responseTx,HttpStatus.CREATED);
		}else {
			ResponseError responseError = new ResponseError();
			responseError.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
			responseError.setMensajeUsuario(responseTx.getMensajeUsuario());
			if(responseTx.getCodigo() == 409) {
				responseError.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
				return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);	
			}else {
				responseError.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
				responseError.setMensajeUsuario("Error interno en el servidor");
				return new ResponseEntity<>(responseError,HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}
	}
	
	@PostMapping(value="a-terceros")
	public ResponseEntity<?> aTerceros(@RequestBody RequestTransferencia peticion) {
		log.info("................Accediendo a transferencia terceros dentro de la entidad...............");
		Integer tipoMovimiento = 0;
			if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("AHORR")) {
				tipoMovimiento = 0;
			}else if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("CRED")) {
				tipoMovimiento = 1;
			}
		ResponseTransferencia responseTx = capaTransferencias.generarTransferencia(peticion,2,tipoMovimiento);
		
		if(responseTx.getCodigo() ==200 || responseTx.getFolioAutorizacion() != null) {
			return new ResponseEntity<>(responseTx,HttpStatus.CREATED);
		}else {
			ResponseError responseError = new ResponseError();
			responseError.setCodigo("App-"+responseError.getCodigo()+".AppN-M");
			responseError.setMensajeUsuario(responseTx.getMensajeUsuario());
			if(responseTx.getCodigo() ==409) {
				responseError.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
				responseError.setMensajeUsuario(responseTx.getMensajeUsuario());
				return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);	
			}else {
				responseError.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
				responseError.setMensajeUsuario("Error interno en el servidor");
				return new ResponseEntity<>(responseError,HttpStatus.INTERNAL_SERVER_ERROR);
			}	
		}
	}
   
	
	
}
