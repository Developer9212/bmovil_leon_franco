package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.Ws_siscoop_Clabe_interbancaria;

public interface ClabeInterbancariaDao extends JpaRepository<Ws_siscoop_Clabe_interbancaria,AuxiliarPK>{
  
}
