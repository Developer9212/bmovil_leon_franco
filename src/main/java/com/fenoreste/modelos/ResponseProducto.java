package com.fenoreste.modelos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ResponseProducto implements Serializable {

	private String id;
	private String nombre;
	@JsonInclude(value = Include.NON_EMPTY)
	private Boolean tienePlanAhorro;

	private static final long serialVersionUID = 1L;
}
