package com.fenoreste.modelos;

import java.math.BigInteger;

import lombok.Data;

@Data
public class RequestTransferencia {

	private BigInteger folioSolicitante;
	private String descripcion;
	private CuentaDto cuentaEmisora;
	private CuentaDto cuentaAdquiriente;
	private DetallesTransaccion montoTransaccion;
	private RegistroTransaccion registro;
	
	
}
