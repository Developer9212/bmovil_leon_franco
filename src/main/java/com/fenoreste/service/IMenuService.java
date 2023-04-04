package com.fenoreste.service;

import com.fenoreste.entity.Menu;
import com.fenoreste.entity.MenuPK;

public interface IMenuService {
   
	public Menu buscarPorId(MenuPK pk);
}
