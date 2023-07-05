package com.fenoreste.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.dao.WsClabeRespuestaAlianzaDao;
import com.fenoreste.entity.WsClabeRespuestaAlianzaPK;
import com.fenoreste.entity.Ws_clabe_respuesta_alianza;

@Service
public class WSClabeRespuestaAlianzaServiceImpl implements IWsClabeRespuestaAlianzaService{
    
	@Autowired
	private WsClabeRespuestaAlianzaDao wsClabeRespuestaAlianzaDao;
	
	@Override
	public Ws_clabe_respuesta_alianza buscarPorId(WsClabeRespuestaAlianzaPK pk) {
		return wsClabeRespuestaAlianzaDao.findById(pk).orElse(null);
	}

	@Override
	public Ws_clabe_respuesta_alianza guardar(Ws_clabe_respuesta_alianza objeto) {
		return wsClabeRespuestaAlianzaDao.save(objeto);
	}

}
