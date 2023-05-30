package com.fenoreste.modelos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	private ResponseCredito datosCredito;
	@JsonInclude(value = Include.NON_EMPTY)
	private ResponseInversion datosInversion;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int codigo;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String mensaje;
	
	private static final long serialVersionUID = 1L;
}
