package com.fenoreste.service;

import com.fenoreste.entity.SpeiAbono;
import com.fenoreste.entity.SpeiDispersion;

public interface ISpeiService {
  
	public SpeiDispersion buscarPorId(String folio);
	public SpeiDispersion guardar(SpeiDispersion mov);
	
	public SpeiAbono buscarPorIdAbono(String claveRastreo);
	public SpeiAbono guardar(SpeiAbono mov);
	
	
}
