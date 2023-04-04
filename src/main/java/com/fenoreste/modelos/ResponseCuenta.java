package com.fenoreste.modelos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ResponseCuenta implements Serializable{
   
	private String numeroCuenta;
	private String tipoCuenta;
	private String subtipoCuenta;
	private String nivelOperacion;
	private String tipoRelacion;
	private String estatus;
	private String fechaApertura;
	private String fechaUltimoMovimiento;
	private String fechaUltimaNotificacion;
	private String numeroSocio;
	private String nombreSocio;
	private String alias;
	private String clabe;
	private ResponseProducto producto;
	private List<ResponseSaldo>saldos;
	@JsonInclude(value = Include.NON_EMPTY)
	private ResponseCredito datoCredito;
	@JsonInclude(value = Include.NON_EMPTY)
	private ResponseInversion datoInversion;
	
	private static final long serialVersionUID = 1L;
}
