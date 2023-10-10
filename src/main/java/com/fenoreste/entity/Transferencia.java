package com.fenoreste.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "transferencias_banca_movil")
@Data
public class Transferencia {
	   
	   @Id
	   private String folioautorizacion;
	   private String foliosolicitante;
	   @Temporal(TemporalType.TIMESTAMP)
	   private Date fecha;
	   private String socioorigen;
	   private String cuentaorigen;
	   private String sociodestino;
	   private String cuentadestino;
	   private Double monto;
	   private Double comision;
	   private String polizacreada;
	   
	   private boolean esspei;
	
}
