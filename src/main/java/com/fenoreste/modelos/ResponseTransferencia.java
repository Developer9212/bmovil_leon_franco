package com.fenoreste.modelos;

import lombok.Data;

@Data
public class ResponseTransferencia {
   
	private String folioAutorizacion;
	private RegistroTransaccion registro;
}
