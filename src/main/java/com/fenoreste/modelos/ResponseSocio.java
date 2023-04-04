package com.fenoreste.modelos;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSocio{
    
	@JsonPropertyDescription("numeroSocio")
	private String numeroSocio;
	private String estatus;
	private ResponseSocioPersonales personales;
	private ResponseSocioContacto contacto;
    @JsonProperty(access = Access.WRITE_ONLY)
	private Integer codigo;
    @JsonInclude(value = Include.NON_NULL)
	private String mensaje;
}
