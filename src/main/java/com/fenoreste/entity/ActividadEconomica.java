package com.fenoreste.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "actividades_economicas")
@Data
public class ActividadEconomica {	
	@Id
    private Integer id_actividad;
	private String descripcion;
}
