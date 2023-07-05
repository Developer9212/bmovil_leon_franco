package com.fenoreste.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.PaisDao;
import com.fenoreste.entity.Pais;

@Service
public class PaisServiceImpl implements IPaisService {
    
	@Autowired
    private PaisDao paisDao;
	
	@Override
	public Pais buscarPorId(Integer id) {
		return paisDao.findById(id).orElse(null);
	}

}
