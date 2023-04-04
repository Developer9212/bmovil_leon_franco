
package com.fenoreste.service;

import com.fenoreste.entity.MovimientoPaso;

public interface IMovimientoPasoService {
   
	public MovimientoPaso guardar(MovimientoPaso movimiento);
	public void eliminar(MovimientoPaso movimiento);
}
