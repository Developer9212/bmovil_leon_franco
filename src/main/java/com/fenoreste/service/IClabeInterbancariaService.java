package com.fenoreste.service;

import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.ClabeInterbancaria;

public interface IClabeInterbancariaService {

	public ClabeInterbancaria buscarPorId(AuxiliarPK pk);
	public ClabeInterbancaria buscarPorClabe(String clabe);
}
