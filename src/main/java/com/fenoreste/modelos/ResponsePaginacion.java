package com.fenoreste.modelos;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponsePaginacion implements Serializable{
    
	private Integer offset;
	private Integer limit;
	private Integer total;
	private static final long serialVersionUID = 1L;
}
