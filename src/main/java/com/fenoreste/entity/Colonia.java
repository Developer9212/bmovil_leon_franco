package com.fenoreste.entity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author root
 */

@Entity
@Table(name = "colonias")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Colonia implements Serializable {

    @Id
    private Integer idcolonia;
    private String nombre;
    private Integer idmunicipio;
    private String codigopostal;

    private static final long serialVersionUID = 1L;
    
}


