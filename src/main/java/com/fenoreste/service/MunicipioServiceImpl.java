package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.MunicipioDao;
import com.fenoreste.entity.Municipio;

@Service
public class MunicipioServiceImpl implements IMunicipioService{
    
	@Autowired
	private MunicipioDao municipioDao;
	
	@Override
	public Municipio buscarPorId(Integer id) {
		return municipioDao.findById(id).orElse(null);
	}

}
