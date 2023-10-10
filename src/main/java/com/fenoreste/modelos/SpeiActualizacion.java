package com.fenoreste.modelos;

import java.math.BigInteger;

import lombok.Data;

@Data
public class SpeiActualizacion { 
	private BigInteger folioSolicitante;
	private String folioAutorizacion;
	private String claveRastreoSpei;
	private String estadoTransaccion;
	private RegistroTransaccion registro;
	private String mensajeProveedor;
	
}
