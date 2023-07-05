package com.fenoreste.service;

import com.fenoreste.entity.WsClabeRespuestaAlianzaPK;
import com.fenoreste.entity.Ws_clabe_respuesta_alianza;

public interface IWsClabeRespuestaAlianzaService {
   
	public Ws_clabe_respuesta_alianza buscarPorId(WsClabeRespuestaAlianzaPK pk);
	public Ws_clabe_respuesta_alianza guardar(Ws_clabe_respuesta_alianza objeto);
}
