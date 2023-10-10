package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.Transferencia;

public interface TransferenciaDao extends JpaRepository<Transferencia, String> {
   
	
}
