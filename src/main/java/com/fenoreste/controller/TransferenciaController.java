package com.fenoreste.controller;

import javax.xml.ws.Response;

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
import com.fenoreste.modelos.RequestTransferenciaSpei;
import com.fenoreste.modelos.ResponseActualizacionSpei;
import com.fenoreste.modelos.ResponseError;
import com.fenoreste.modelos.ResponseTransferencia;
import com.fenoreste.modelos.SpeiActualizacion;
import com.fenoreste.service.CapaTransferenciaService;
import com.fenoreste.service.IFuncionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/cma/transferencia")
@CrossOrigin(origins = "*")
@Slf4j
public class TransferenciaController {
	
	@Autowired
	private CapaTransferenciaService capaTransferencias;
	@Autowired
	private IFuncionService funcionService;
	
	@PostMapping(value="entre-cuentas")
	public ResponseEntity<?> entreCuentas(@RequestBody RequestTransferencia peticion) {
		log.info("................Accediendo a transferencia entre cuentas propias...............");
		Integer tipoMovimiento = 0;
		ResponseError error = new ResponseError();
		if(funcionService.horaActividad()) {
			if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("AHORR")) {
				tipoMovimiento = 0;
			}else if(peticion.getCuentaAdquiriente().getSubtipoCuenta().toUpperCase().contains("CRED")) {
				tipoMovimiento = 1;
			}
		  ResponseTransferencia responseTx = capaTransferencias.generarTransferencia(peticion,1,tipoMovimiento);
		
		  if(responseTx.getCodigo() == 200 && responseTx.getFolioAutorizacion() != null) {
			  return new ResponseEntity<>(responseTx,HttpStatus.CREATED);
		  }else {			  
			  error.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
			  error.setMensajeUsuario(responseTx.getMensajeUsuario());
			  if(responseTx.getCodigo() == 409) {
				 error.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
				 return new ResponseEntity<>(error,HttpStatus.CONFLICT);	
			  }else {
				 error.setCodigo("App-"+responseTx.getCodigo()+".AppN-M");
				 error.setMensajeUsuario("Error interno en el servidor");
				 return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			  }		
		   }
		}else {
			error.setCodigo("App-"+409+".AppN-M");
			error.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
			return new ResponseEntity<>(error,HttpStatus.CONFLICT);
		}
		
	}
	
	@PostMapping(value="a-terceros")
	public ResponseEntity<?> aTerceros(@RequestBody RequestTransferencia peticion) {
		log.info("................Accediendo a transferencia terceros dentro de la entidad...............");
		ResponseError responseError = new ResponseError();
		if(funcionService.horaActividad()) {
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
		}else {
			responseError.setCodigo("App-"+409+".AppN-M");
			responseError.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
			return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);
		}
		
	}
   
	
	@PostMapping(value="comenzar-spei")
	public ResponseEntity<?> comenzarSpei(@RequestBody RequestTransferenciaSpei peticion) {
		log.info("................Accediendo a comenzar spei...............");
		
		ResponseError responseError = new ResponseError();
		if(funcionService.horaActividad()) {
			ResponseTransferencia responseTx = capaTransferencias.comenzarSpei(peticion);
		    if(responseTx.getCodigo() ==200 || responseTx.getFolioAutorizacion() != null) {
			     return new ResponseEntity<>(responseTx,HttpStatus.CREATED);
		    }else {
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
	  } else{
		responseError.setCodigo("App-"+409+".AppN-M");
		responseError.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
		return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);
	 }
	}
	
	@PostMapping(value="finalizar-spei")
	public ResponseEntity<?> finalizarSpei(@RequestBody SpeiActualizacion actualizacion) {
		log.info("................Accediendo a actualizar spei...............");
		ResponseError responseError = new ResponseError();
		if(funcionService.horaActividad()) {
			ResponseActualizacionSpei responseA = capaTransferencias.actualizarOrden(actualizacion);
			 if(responseA.getCodigo() ==200) {
				 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			  }else {				 
				 responseError.setCodigo(String.valueOf(responseA.getCodigo()));
				 responseError.setCodigo("App-"+responseError.getCodigo()+".AppN-M");
				 responseError.setMensajeUsuario(responseA.getMensaje());
			     return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);
			}	
		}else {
			responseError.setCodigo("App-"+409+".AppN-M");
			responseError.setMensajeUsuario("VERIFIQUE SU HORARIO DE ACTIVIDAD DIA,HORA O CONTACTE A SU PROVEEDOR...");
			return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);
		}
		
	}
	
	@PostMapping(value="recibir-deposito-spei")
	public ResponseEntity<?> sendAbono(@RequestBody RequestTransferenciaSpei abono) {
		log.info("................Accediendo a send Abono...............");
		System.out.println(abono);
		ResponseTransferencia tx = capaTransferencias.sendAbono(abono);
		if(tx.getCodigo() == 200) {
			return new ResponseEntity<>(tx,HttpStatus.OK);
		}else {
			ResponseError responseError = new ResponseError();
			responseError.setCodigo(String.valueOf(tx.getCodigo()));
			responseError.setCodigo("App-"+responseError.getCodigo()+".AppN-M");
			responseError.setMensajeUsuario(tx.getMensajeUsuario());
		    return new ResponseEntity<>(responseError,HttpStatus.CONFLICT);
		}
	
	}
	
	
	
	
	
	
}
