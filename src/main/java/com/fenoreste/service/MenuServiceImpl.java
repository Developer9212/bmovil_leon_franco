package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.MenuDao;
import com.fenoreste.entity.Menu;
import com.fenoreste.entity.MenuPK;

@Service
public class MenuServiceImpl implements IMenuService{
    
	@Autowired
	MenuDao menuDao;
	
	@Override
	public Menu buscarPorId(MenuPK pk) {
		return menuDao.findById(pk).orElse(null);
	}

}
