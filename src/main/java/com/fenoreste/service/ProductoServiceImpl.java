package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.ProductoDao;
import com.fenoreste.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService{
  
	@Autowired
	private ProductoDao productoDao;
	
	@Override
	public Producto buscarPorId(Integer id) {
		return productoDao.findById(id).orElse(null);
	}
    
	
}
