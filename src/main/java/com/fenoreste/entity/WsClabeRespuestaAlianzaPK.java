package com.fenoreste.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WsClabeRespuestaAlianzaPK implements Serializable {   
	private Integer idoperacion;
	private String clabe;
    private Integer idorigenp;
    private Integer idproducto;
    private Integer idauxiliar;
    
    private static final long serialVersionUID = 1L;
}
