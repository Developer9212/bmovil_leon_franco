package com.fenoreste.entity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "transferencias_spei_dispersion")
@Data
public class SpeiDispersion {     
	@Id
	private String folioautorizacion;
	private String foliosolicitante;    
    private String claverastreo;
    private String estadotransaccion;
    @Temporal(TemporalType.TIMESTAMP)
	private Date fechaactualizacion;
    private String poliza_ajuste;
}
