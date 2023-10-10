package com.fenoreste.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.alianza_stp.SpeiClient;
import com.fenoreste.alianza_stp.personaFisicaModel;
import com.fenoreste.entity.ActividadEconomica;
import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.Colonia;
import com.fenoreste.entity.Estado;
import com.fenoreste.entity.Menu;
import com.fenoreste.entity.MenuPK;
import com.fenoreste.entity.Municipio;
import com.fenoreste.entity.Pais;
import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;
import com.fenoreste.entity.Trabajo;
import com.fenoreste.entity.WsClabeRespuestaAlianzaPK;
import com.fenoreste.entity.Ws_clabe_respuesta_alianza;
import com.fenoreste.entity.ClabeInterbancaria;
import com.fenoreste.entity.Clabe;
import com.fenoreste.utilidades.HerramientasUtil;
import com.fenoreste.utilidades.Ogs;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CapaSTPService {

	@Autowired
	private SpeiClient speiClient;
	@Autowired
	private HerramientasUtil herramientasUtil;
	@Autowired
	private IPersonaService personaService;
	@Autowired
	private ITablaService tablaService;
	@Autowired
	private IAuxiliarService auxiliarService;
	@Autowired
	private IClabeInterbancariaService clabeInterbancariaService;
	@Autowired
	private IClabeService clabeService;
	@Autowired
	private IColoniaService coloniaService;
	@Autowired
	private IMunicipioService municipioService;
	@Autowired
	private IEstadoService estadoService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private ITrabajoService trabajoService;
	@Autowired
	private IActividadEconomicaService actividadEconomicaService;
	@Autowired
	private IPaisService paisService;
	@Autowired
	private IWsClabeRespuestaAlianzaService wsClabeRespuestaAlianzaService;

	public Ws_clabe_respuesta_alianza altaClabe(String socio) {
		personaFisicaModel modelPF = new personaFisicaModel();
		Ws_clabe_respuesta_alianza ws_clabe_respuesta_alianza = new Ws_clabe_respuesta_alianza();
		try {
			Ogs ogs = herramientasUtil.ogs(socio);
			PersonaPK personaPk = new PersonaPK(ogs.getIdorigen(), ogs.getIdgrupo(), ogs.getIdsocio());
			Persona persona = personaService.buscarPorId(personaPk);
			if (persona != null) {
				TablaPK tablaPk = new TablaPK("param", "productos_para_cuenta_clabe");
				Tabla tbSpei = tablaService.buscarPorId(tablaPk);
				Auxiliar auxiliar = auxiliarService.buscarPorOgsIdproducto(persona.getPk().getIdorigen(),persona.getPk().getIdgrupo(), persona.getPk().getIdsocio(),Integer.parseInt(tbSpei.getDato2()));
				if (auxiliar != null) {
					//Buscamos registros de clabe Interbancaria
					ClabeInterbancaria clabeInterbancaria = clabeInterbancariaService.buscarPorId(auxiliar.getPk());
					if(clabeInterbancaria != null) {
						//Buscamos detalles de clabe
						Clabe detalleClabe = clabeService.buscarPorId(clabeInterbancaria.getClabe());
						if(detalleClabe != null) {
							modelPF.setNumeroSocio(socio);
							modelPF.setNombre(persona.getNombre());
							modelPF.setPrimerApellido(persona.getAppaterno());
							modelPF.setSegundoApellido(persona.getApmaterno());
							modelPF.setClabe(clabeInterbancaria.getClabe());
							modelPF.setRfc(persona.getRfc());
							modelPF.setCurp(persona.getCurp());
							modelPF.setFechaNacimiento(String.valueOf(persona.getFechanacimiento()));
							//Buscamos idMunicipio para llegar a idEstado
							Colonia colonia = coloniaService.buscarPorId(persona.getIdcolonia());
						    Municipio municipio = municipioService.buscarPorId(colonia.getIdmunicipio());
						    Estado estado = estadoService.buscarPorId(municipio.getIdestado());
							modelPF.setEntidadFederativa(String.valueOf(estado.getIdestado()));
							MenuPK menuPK = new MenuPK("sexo",persona.getSexo().intValue());
							Menu menu = menuService.buscarPorId(menuPK);
							modelPF.setGenero(menu.getDescripcion().substring(0,1));
							Trabajo trabajo = trabajoService.buscarPorId(persona.getPk());
							if(trabajo != null) {
								if(trabajo.getActividad_economica() != null) {
									ActividadEconomica actividadEconomica = actividadEconomicaService.buscarPorId(trabajo.getActividad_economica());
									modelPF.setActividadEconomica(actividadEconomica.getDescripcion());	
								}else {
								    log.info("...............Actividad economica invalida............");	
								}								
							}else {
						       log.info("...........Persona no tiene un trabajo registrado..........");		
							}														
							modelPF.setDomicilio(persona.getCalle() + persona.getNumeroext() + persona.getNumeroint());
							modelPF.setClaveElector(persona.getClaveOficial());
							Pais pais = paisService.buscarPorId(estado.getIdpais());
							
							modelPF.setPaisNacimiento(pais.getNombre().toUpperCase().substring(0,3));
							log.info("json formado:"+modelPF);
							String[] resultadoAlta= speiClient.altaClabe(modelPF);
							
							
							WsClabeRespuestaAlianzaPK pkClabeAlianza = new WsClabeRespuestaAlianzaPK(1,clabeInterbancaria.getClabe(), auxiliar.getPk().getIdorigenp(), auxiliar.getPk().getIdproducto(),auxiliar.getPk().getIdauxiliar());
                            ws_clabe_respuesta_alianza.setPk(pkClabeAlianza);
                            ws_clabe_respuesta_alianza.setCodigohttp(Integer.parseInt(resultadoAlta[0]));
                            ws_clabe_respuesta_alianza.setDescripcion("alta clabe");
                            ws_clabe_respuesta_alianza.setFecha(new Date());
                            ws_clabe_respuesta_alianza.setMensajerespuesta(resultadoAlta[1]);
                            
                            wsClabeRespuestaAlianzaService.guardar(ws_clabe_respuesta_alianza);
						}else {
							log.info("..............No existen detalles de clabe...............");
						}
					}else {
						log.info("...........No existen registros para clabe interbancaria............");
					}
 				} else {
					log.info("............Producto asociado a spei no existe..............");
				}
			} else {
				log.info(".........Persona no existe.............");
			}
		} catch (Exception e) {
			log.info(":::::::::::::::::Error al dar de alta clabe::::::::::::::::::..."+e.getMessage());
		}
       return ws_clabe_respuesta_alianza;    		   
	}
}
