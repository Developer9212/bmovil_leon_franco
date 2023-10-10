package com.fenoreste.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "ws_siscoop_clabe_interbancaria")
@Data
public class ClabeInterbancaria {
     
	 @Id
	 private AuxiliarPK pk; 
	 private String clabe; 
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date fecha_hora; 
	 private boolean asignada;
	 private boolean activa;
	 private boolean bloqueada;

}
