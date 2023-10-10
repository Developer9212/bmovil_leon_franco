package com.fenoreste.modelos;

import java.math.BigInteger;

import lombok.Data;

@Data
public class RequestTransferenciaSpei {

	private BigInteger folioSolicitante;
	private String claveRastreoSpei;
	private String descripcion;
	private OrdenanteSpei ordenanteSpei;
	private BeneficiarioSpei beneficiarioSpei;
	private DetallesTransaccion montoTransaccion;
	private RegistroTransaccion registro;
	
}
