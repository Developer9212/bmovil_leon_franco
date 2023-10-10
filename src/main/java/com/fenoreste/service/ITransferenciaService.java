package com.fenoreste.service;

import com.fenoreste.entity.Transferencia;

public interface ITransferenciaService {
   
	public Transferencia buscarPorId(String folio);
	public Transferencia guardar(Transferencia movimiento);
}
