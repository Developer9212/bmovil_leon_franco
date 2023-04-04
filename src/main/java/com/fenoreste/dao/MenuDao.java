package com.fenoreste.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.entity.Menu;
import com.fenoreste.entity.MenuPK;

public interface MenuDao extends JpaRepository<Menu,MenuPK> {

}
