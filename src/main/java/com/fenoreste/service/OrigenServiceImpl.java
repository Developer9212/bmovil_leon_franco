package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.OrigenDao;
import com.fenoreste.entity.Origen;

@Service
public class OrigenServiceImpl implements IOrigenService{
    
	@Autowired
	private OrigenDao origenDao;
	
	@Override
	public Origen buscarPorId(Integer idorigen) {
		return origenDao.findById(idorigen).orElse(null);
	}

	@Override
	public Origen origenMatriz() {
		return origenDao.buscarMatriz();
	}

	
}
