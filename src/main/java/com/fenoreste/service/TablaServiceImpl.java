package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.TablaDao;
import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;

@Service
public class TablaServiceImpl implements ITablaService{
    
	@Autowired
	private TablaDao tablaDao;
	
	@Override
	public Tabla buscarPorId(TablaPK pk) {
		return tablaDao.findById(pk).orElse(null);
	}

}
