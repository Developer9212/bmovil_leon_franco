package com.fenoreste.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "transferencias_spei_abono")
@Data
public class SpeiAbono {
    
	@Id
	private String claverastreospei;
	private String foliosolicitante;
	private String descripcion;
	private String ordenante;
	private String clabeordenante;
	private String referencianumerica;
	private String referenciacobranza;
	private String clabebeneficiario;
	private String nombrebeneficiario;
	private Double monto;
	private Double comision; 
	private String fechasolicitud;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaejecucion;
	private boolean aceptada;
	private String polizaabono;
	private String polizacomision;
}
