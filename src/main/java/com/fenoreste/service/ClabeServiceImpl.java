package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.ClabeDao;
import com.fenoreste.entity.Clabe;

@Service
public class ClabeServiceImpl implements IClabeService{
    
	@Autowired
	private ClabeDao clabeDao;
	
	@Override
	public Clabe buscarPorId(String clabe) {
		return clabeDao.findById(clabe).orElse(null);
	}

}
