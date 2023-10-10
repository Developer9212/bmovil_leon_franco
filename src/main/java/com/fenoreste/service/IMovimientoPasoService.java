
package com.fenoreste.service;

import com.fenoreste.entity.MovimientoPaso;
import com.fenoreste.entity.SpeiAbonoPaso;

public interface IMovimientoPasoService {
   
	public MovimientoPaso guardar(MovimientoPaso movimiento);
	public void eliminar(MovimientoPaso movimiento);
	
	public SpeiAbonoPaso guardar(SpeiAbonoPaso abono);
	public void eliminar(SpeiAbonoPaso abono);
}
