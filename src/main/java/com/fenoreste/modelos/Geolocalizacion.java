package com.fenoreste.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class Geolocalizacion implements Serializable {

    private String latitud;
    private String longitud;
    private String sistemaReferencia;

    private static final long serialVersionUID = 1L;

}
