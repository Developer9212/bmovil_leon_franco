package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.ClabeInterbancariaDao;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.Ws_siscoop_Clabe_interbancaria;

@Service
public class ClabeInterbancariaServiceImpl implements IClabeInterbancariaService{
    
	@Autowired
	private ClabeInterbancariaDao clabeInterbancariaDao;
	
	@Override
	public Ws_siscoop_Clabe_interbancaria buscarPorId(AuxiliarPK pk) {
		return clabeInterbancariaDao.findById(pk).orElse(null);
	}

}
