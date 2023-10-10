package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.ClabeInterbancaria;

public interface ClabeInterbancariaDao extends JpaRepository<ClabeInterbancaria,AuxiliarPK>{
  
	public ClabeInterbancaria findByClabe(String clabe);
}
