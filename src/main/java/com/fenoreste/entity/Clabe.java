package com.fenoreste.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ws_siscoop_clabe")
@Data
public class Clabe {
     
	 @Id
	 private String clabe;
	 private Date fecha; 
	 private boolean seleccionada;
	 private boolean asignada;
	 private boolean eliminada;
	 private Date fecha_vencimiento; 

}
