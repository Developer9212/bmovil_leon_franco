package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.ProductoBancaDao;
import com.fenoreste.entity.ProductoBanca;

@Service
public class ProductoBancaServiceImpl implements IProductoBancaService{
    
	@Autowired
	private ProductoBancaDao productoBancaDao;
	
	@Override
	public ProductoBanca buscarPorId(Integer id) {
		return productoBancaDao.findById(id).orElse(null);
	}

}
