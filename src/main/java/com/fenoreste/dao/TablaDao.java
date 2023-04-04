package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;

public interface TablaDao extends JpaRepository<Tabla,TablaPK> {

}
