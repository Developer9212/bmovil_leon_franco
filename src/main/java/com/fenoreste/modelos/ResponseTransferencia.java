package com.fenoreste.modelos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class ResponseTransferencia implements Serializable{
   
	private String folioAutorizacion;
	private RegistroTransaccion registro;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String codigo;
    @JsonInclude(value = Include.NON_NULL)
	private String mensajeUsuario;
	
	private static final long serialVersionUID = 1L;
}
