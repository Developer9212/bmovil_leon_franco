package com.fenoreste.entity;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ws_clabe_respuesta_alianza")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ws_clabe_respuesta_alianza {    
	@EmbeddedId
	private WsClabeRespuestaAlianzaPK pk;
    private String descripcion;
    private Date fecha;    
    private Integer codigohttp;
    private String mensajerespuesta;
}
