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

import com.fenoreste.modelos.ResponseCuenta;
import com.fenoreste.modelos.ResponseError;
import com.fenoreste.modelos.ResponseMovimientos;
import com.fenoreste.service.CapaCuentaService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/cma")
@CrossOrigin(origins = "*")
@Slf4j
public class CuentasController {
	
	@Autowired
	private CapaCuentaService capaCuentaService;

	@GetMapping(value="cuentas-captacion/{numeroCuenta}",params = {"subtipoCuenta"})
	public ResponseEntity<?> buscarCuentaCaptacion(@PathVariable("numeroCuenta") String numeroCuenta,
												   @RequestParam("subtipoCuenta") String tipoCuenta) {
		log.info("Accediendo a detalles cuenta captacion....................");
		ResponseCuenta response = capaCuentaService.detalleCuentaCaptacion(numeroCuenta,tipoCuenta);		
		if(response.getCodigo() == 200) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			ResponseError error = new ResponseError();
			error.setCodigo("App-"+response.getCodigo()+".AppN-M");
			error.setMensajeUsuario(response.getMensaje());
			return new ResponseEntity<>(error,HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value="cuentas-credito/{numeroCuenta}",params = {"subtipoCuenta"})
	public ResponseEntity<?> buscarCuentaCredito(@PathVariable("numeroCuenta") String numeroCuenta,
												 @RequestParam("subtipoCuenta") String tipoCuenta) {
		ResponseCuenta response = capaCuentaService.detalleCuentaColocacion(numeroCuenta,tipoCuenta);
		log.info("Accediendo a detalles cuenta colocacion....................");
		if(response.getNumeroCuenta() != null) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value="cuentas/{numeroCuenta}/movimientos",params = {"subtipo"})
	public ResponseEntity<?> buscarCuentaMovimientos(@PathVariable("numeroCuenta") String numeroCuenta,
													 @RequestParam("subtipo") String tipo,
													 @RequestParam("fechaInicio") String fechaInicio,
													 @RequestParam("fechaFin") String fechaFin,
													 @RequestParam("offset") int inicio,
													 @RequestParam("limit") int limit) {
		ResponseMovimientos response = capaCuentaService.movimientosCuenta(numeroCuenta,tipo,fechaInicio,fechaFin,inicio,limit);
		log.info("Accediendo a movimientos cuenta....................");
		if(response.getNumeroCuenta() != null) {
			return new ResponseEntity<>(response,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.CONFLICT);
		}
	}
}
