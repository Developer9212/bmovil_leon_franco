package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.MovimientoPaso;
import com.fenoreste.entity.MovimientoPasoPK;

public interface MovimientoPasoDao extends JpaRepository<MovimientoPaso,MovimientoPasoPK> {

}
