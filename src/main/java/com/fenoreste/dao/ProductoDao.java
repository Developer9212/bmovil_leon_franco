package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.Producto;

public interface ProductoDao extends JpaRepository<Producto,Integer> {
	
	

}
