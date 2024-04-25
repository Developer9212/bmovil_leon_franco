package com.fenoreste.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fenoreste.entity.Amortizacion;
import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.MenuPK;
import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Producto;
import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;
import com.fenoreste.entity.ClabeInterbancaria;
import com.fenoreste.modelos.ResponseCredito;
import com.fenoreste.modelos.ResponseCuenta;
import com.fenoreste.modelos.ResponseCuentaPrincipal;
import com.fenoreste.modelos.ResponseInversion;
import com.fenoreste.modelos.ResponsePaginacion;
import com.fenoreste.modelos.ResponsePago;
import com.fenoreste.modelos.ResponseProducto;
import com.fenoreste.modelos.ResponseSaldo;
import com.fenoreste.modelos.ResponseSocio;
import com.fenoreste.modelos.ResponseSocioContacto;
import com.fenoreste.modelos.ResponseSocioPersonales;
import com.fenoreste.utilidades.HerramientasUtil;
import com.fenoreste.utilidades.Ogs;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CapaSocioService {
	
	@Autowired
	private HerramientasUtil utileria;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IAuxiliarService auxiliarService;
	
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IAuxiliarDService auxiliarDService;
	
	
	@Autowired
    private IFuncionService funcionService;	
	
	@Autowired
	private IAmortizacionService amortizacionService;
	
	@Autowired
	private ITablaService tablaService;
	
	@Autowired
	private IClabeInterbancariaService clabeInterbancariaService;
	
	@Autowired
	private HerramientasUtil util;

	public ResponseSocio buscarSocioPorOgs(String ogs) {
		ResponseSocio response = new ResponseSocio();
		response.setCodigo(409);
		try {
			//Deserealizamos el ogs
			Ogs ogs_d = utileria.ogs(ogs);
			//Buscamos la persona por ogs
			PersonaPK persona_pk = new PersonaPK(ogs_d.getIdorigen(),ogs_d.getIdgrupo(),ogs_d.getIdsocio());
			Persona persona = personaService.buscarPorId(persona_pk);
			if(persona != null) {
					//Buscamos que cumpla con la parte social
					Auxiliar aux_parte_social = auxiliarService.buscarPorOgsIdproductoSocial(persona.getPk().getIdorigen(),persona.getPk().getIdgrupo(),persona.getPk().getIdsocio());
					if(aux_parte_social != null) {
						 if(aux_parte_social.getEstatus() == 2) {
							 response.setNumeroSocio(ogs);
							   response.setEstatus("ACTIVO");
							   ResponseSocioPersonales personales = new ResponseSocioPersonales();
							   personales.setNombre(persona.getNombre());
							   personales.setPrimerApellido(persona.getAppaterno());
							   personales.setSegundoApellido(persona.getApmaterno());
							   personales.setCurp(persona.getCurp());
							   personales.setDomicilio(persona.getCalle()+","+persona.getNumeroint());
							   personales.setFechaNacimiento(persona.getFechanacimiento().toString());
							   MenuPK menu_pk = new MenuPK("sexo",persona.getSexo().intValue());
							   personales.setGenero(menuService.buscarPorId(menu_pk).getDescripcion().toUpperCase().substring(0,1));
							   ResponseSocioContacto contactos = new ResponseSocioContacto();
							   contactos.setCorreoElectronico(persona.getEmail());
							   contactos.setMovil(persona.getCelular());
							   response.setContacto(contactos);						   
							   response.setPersonales(personales);						   
							   response.setCodigo(200);
						 }else {
							 response.setMensaje("Parte social inactiva");
							 log.info("............Parte social inactiva......");
						 }						   
					}else {
					   response.setMensaje("Socio no cumple con la parte social");	
					}
			}else {
				response.setCodigo(404);
				response.setMensaje("Socio no existe en el core");
				log.info("..........Socio no existe en el core..............");
			}			
		} catch (Exception e) {
		   System.out.println("Error al buscar socio por ogs:"+e.getMessage());
		   return response;
		}
		
		return response;
	}
	
	
	public ResponseCuentaPrincipal buscarSocioCuentas(String socio,String tipoCuenta,int pageStartIndex,int pageSize) {
		ResponseCuentaPrincipal response = new ResponseCuentaPrincipal();
		log.info("....................Buscando cuenta socio....................");
		try {
			//Buscamos al socio con el ogs
			Ogs ogs = utileria.ogs(socio);
			response.setCodigo(409);
			if(ogs.getIdorigen() > 0 && ogs.getIdgrupo() > 0 && ogs.getIdsocio() > 0 ) {
				PersonaPK persona_id = new PersonaPK(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio());				
				Persona persona = personaService.buscarPorId(persona_id);				
				if(persona.getPk() != null) {			
					//Buscamos clabe STP
					TablaPK pkClabeStp = new TablaPK("banca_movil","producto_clabe");
					Tabla tbStp = tablaService.buscarPorId(pkClabeStp);
					//Buscamos todos los folios auxilaires activos
					List<ResponseCuenta>listaCuentas = new ArrayList<>();
					List<Auxiliar>_Folios = new ArrayList<>();
					ResponsePaginacion paginacion = new ResponsePaginacion();
					paginacion.setOffset(pageStartIndex);
					paginacion.setLimit(pageSize);
					switch(tipoCuenta.toUpperCase()) {				     
					case "AHORRO":
						 paginacion.setTotal(auxiliarService.contadorProductos(persona_id, 1));
					     _Folios = auxiliarService.listaAhorros(persona_id,PageRequest.of(pageStartIndex, pageSize));				     
					     break;
					case "INVERSION":
						paginacion.setTotal(auxiliarService.contadorProductos(persona_id, 2));
						_Folios = auxiliarService.listaInversiones(persona_id,PageRequest.of(pageStartIndex, pageSize));
						 break;
					case "CREDITO":
						paginacion.setTotal(auxiliarService.contadorProductos(persona_id, 3));
						_Folios = auxiliarService.listaPrestamos(persona_id,PageRequest.of(pageStartIndex, pageSize));
						 break;
					}					
					response.setPaginacion(paginacion);
					if(_Folios.size() > 0) {
						for(int i=0;i<_Folios.size();i++) {					 
							  Auxiliar a = _Folios.get(i);
							  if(a.getEstatus() == 2) {
								  Producto p = productoService.buscarPorId(a.getPk().getIdproducto());
								  AuxiliarD ad = auxiliarDService.buscarUltimoMovimiento(a.getPk());
								  if(p.getIdproducto() != 100) {
									  ResponseCuenta res = new ResponseCuenta();
									  ResponseProducto res_prod = new ResponseProducto();
									  List<ResponseSaldo>saldos = new ArrayList<>();
									  ResponseSaldo res_saldo = null;
									  log.info("Nombre PRPductos:"+p.getNombre().toUpperCase());
									 
									  
									  if(p.getTipoproducto() == 0){
										  log.info("El idproducto es:"+a.getPk().getIdproducto());
										  if(Integer.parseInt(tbStp.getDato1()) == a.getPk().getIdproducto()) {
											  log.info("Si accedio a buscar clabe");
											  //Aqui buscamos la clabe en ClabeInterbancaria
											  ClabeInterbancaria clabe_interbancaria = clabeInterbancariaService.buscarPorId(a.getPk());
											  log.info("La clabe interbancaria es:"+clabe_interbancaria.getClabe());
											  if(clabe_interbancaria != null) {
												  log.info("Se seteo la clabe");
												  res.setClabe(clabe_interbancaria.getClabe());   
											  }
										  }
										  log.info("Entro como Ahorro");
										  res.setNumeroSocio(socio);
										  res.setNombreSocio(persona.getNombre() + " "+ persona.getAppaterno() + " " + persona.getApmaterno());
										  res.setTipoCuenta("PERSONAL");
										  log.info("1");
										  res_saldo = new ResponseSaldo();
										  res.setSubtipoCuenta("AHORRO");
										  res.setNivelOperacion("DEPOSITOS_Y_RETIROS");						  
										  res_saldo.setTipo("ACTUAL");
										  res_saldo.setMonto(a.getSaldo().doubleValue());
										  res_saldo.setMoneda("MXN");						  
										  saldos.add(res_saldo);
										  log.info("2");
										  
										  res_saldo = new ResponseSaldo();						  
										  res_saldo.setTipo("DISPONIBLE");
										  res_saldo.setMonto(a.getSaldo().doubleValue() - a.getGarantia().doubleValue());
										  res_saldo.setMoneda("MXN");						  
										  saldos.add(res_saldo);
										  
										  log.info("3");
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("BLOQUEADO");
										  res_saldo.setMonto(a.getGarantia().doubleValue());
										  res_saldo.setMoneda("MXN");
										  saldos.add(res_saldo);
										  
										  log.info("4");
										  res.setNumeroCuenta(String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar()));
										  res.setAlias(p.getNombre());						 
										  res_prod.setId(String.valueOf(p.getIdproducto()));
										  res_prod.setNombre(p.getNombre());
										  res.setProducto(res_prod);
										  res.setTipoRelacion("UNICO_PROPIETARIO");
										  res.setFechaApertura(util.convertFechaDate(a.getFechaactivacion()));
										  log.info("ad:"+ad);
										  if(ad!= null) {
											  log.info("Fecha Ultimo mov:"+ad.getFecha());
											  res.setFechaUltimoMovimiento(ad.getFecha().replace(" ","T").substring(0,19));
											  log.info(ad.getFecha().replace(" ","T").substring(0,19));
										  }else {
											  log.info("no hay movimientos para producto:"+p.getIdproducto());
										  }
										  
										  log.info("5");
										  //res.setClabe("");//String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar()));  
										  res.setSaldos(saldos);
										  listaCuentas.add(res);
									  }else if(p.getTipoproducto() ==1 ) {
										  log.info("Entro como Inversion");
										  ResponseInversion inversion_res = new ResponseInversion();
										  res.setNumeroSocio(socio);
										  res.setNombreSocio(persona.getNombre() + " "+ persona.getAppaterno() + " " + persona.getApmaterno());
										  res.setTipoCuenta("PERSONAL");
										  res.setClabe("");//String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar()));
										  res_saldo = new ResponseSaldo();
										  res.setSubtipoCuenta("INVERSION");
										  res.setNivelOperacion("SOLO_CONSULTA");
										  
										  res_saldo.setTipo("ACTUAL");
										  res_saldo.setMonto(a.getSaldo().doubleValue());
										  res_saldo.setMoneda("MXN");						  
										  saldos.add(res_saldo);
										  /*
										  res_saldo = new ResponseSaldo();
										  log.info(auxiliarService.fechaVencimientoAmortizacion(a.getPk()).toString());
										  if(auxiliarService.fechaVencimientoAmortizacion(a.getPk()).after(origenService.buscarPorId(a.getPk().getIdorigenp()).getFechatrabajo())) {							  						  
											  res_saldo.setTipo("BLOQUEADO");
											  res_saldo.setMonto(a.getSaldo().doubleValue() );
											  res_saldo.setMoneda("MXN");
										  }else {
											  res_saldo.setTipo("DISPONIBLE");
											  res_saldo.setMonto(a.getSaldo().doubleValue());
											  res_saldo.setMoneda("MXN");							  
										  }
										  
										  saldos.add(res_saldo);
										  */
										  
										  
										  res.setNumeroCuenta(String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar()));
										  res.setAlias(p.getNombre());
										  res_prod.setId(String.valueOf(p.getIdproducto()));
										  res_prod.setNombre(p.getNombre());
										  res.setProducto(res_prod);
										  res.setTipoRelacion("UNICO_PROPIETARIO");
										  res.setFechaApertura(util.convertFechaDate(a.getFechaactivacion()));
										  res.setFechaUltimoMovimiento(ad.getFecha().replace(" ","T").substring(0,19));
										  res.setFechaUltimaNotificacion(null);
										  
										  inversion_res.setFechaVencimiento(util.convertFechaDate(auxiliarService.fechaVencimientoAmortizacion(a.getPk())).replace("T00:00:00",""));
										  res.setSaldos(saldos);
										  res.setDatosInversion(inversion_res);
										  listaCuentas.add(res);
										  
									   }else {
										  log.info("Si entro como prestamo");
										  ResponseCredito res_credito = new ResponseCredito();
										  res.setClabe("");//String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar()));
										  res.setNumeroCuenta(String.format("%06d",a.getPk().getIdorigenp())+String.format("%05d",a.getPk().getIdproducto())+String.format("%08d",a.getPk().getIdauxiliar()));
										  res.setAlias(p.getNombre());
										  res.setNumeroSocio(socio);
										  res.setNombreSocio(persona.getNombre() + " "+ persona.getAppaterno() + " " + persona.getApmaterno());
										  res.setTipoCuenta("PERSONAL");						  
										  res_saldo = new ResponseSaldo();
										  res.setSubtipoCuenta("CREDITO");
										  res.setNivelOperacion("SOLO_DEPOSITOS");				  
										
										  
										  //Buscamos el saldo origen(Primer pago)
										  /*
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("ORIGEN");
										  res_saldo.setMonto(a.getMontoautorizado().doubleValue());
										  res_saldo.setMoneda("MXN");
										  
										  saldos.add(res_saldo);
										  */ 
										  //Buscamos el monto para liquidar el prestamo
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("LIQUIDACION");
										  res_saldo.setMonto(Double.parseDouble(funcionService.montoParaLiquidarPrestamo(a.getPk())));
										  res_saldo.setMoneda("MXN");
										  
										  saldos.add(res_saldo);
										  
										  //Seteamos saldo actual
										  /*
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("PRINCIPAL");
										  res_saldo.setMonto(a.getSaldo().doubleValue());
										  res_saldo.setMoneda("MXN");
										  saldos.add(res_saldo);*/
										  
										  //El resto de tipo de saldo lo calculamos con el SAI_AUXILIAR
										  String sai_auxiliar = funcionService.sai_auxiliar(a.getPk());
										  String [] sai_parameters = sai_auxiliar.split("\\|");
										  List<String> valores_sai = Arrays.asList(sai_parameters);
										  /*System.out.println("Interese ordinarios:"+sai_auxiliar);						  
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("INTERESES_ORDINARIOS");
										  res_saldo.setMonto(new Double(valores_sai.get(6)));
										  res_saldo.setMoneda("MXN");
										  saldos.add(res_saldo);
										  
										  //Moratorios
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("INTERESES_MORATORIOS");
										  res_saldo.setMonto(new Double(valores_sai.get(15)));
										  res_saldo.setMoneda("MXN");
										  saldos.add(res_saldo);
										  
										  //Iva Intereses Ordinarios
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("IVA_INTERESES_ORDINARIOS");
										  res_saldo.setMonto(new Double(valores_sai.get(17)));
										  res_saldo.setMoneda("MXN");
										  saldos.add(res_saldo);
										  
										  //Iva Intereses Moratorios
										  res_saldo = new ResponseSaldo();
										  res_saldo.setTipo("IVA_INTERESES_MORATORIOS");
										  res_saldo.setMonto(new Double(valores_sai.get(18)));
										  res_saldo.setMoneda("MXN");
										  saldos.add(res_saldo);*/
										  
										  //Datos extra Credito
										  res_credito.setTasaAnual(a.getTasaio().doubleValue());
										  List<Amortizacion>pagadas = amortizacionService.pagadas(a.getPk());
										  Amortizacion ultima_amortizacion = amortizacionService.buscarUltimoPago(a.getPk());
										  res_credito.setPlazosRestantes(a.getPlazo()-pagadas.size());
										  res_credito.setPlazosTotales((int) a.getPlazo());
										  res_credito.setUnidadPlazo("MESES");
										  res_credito.setFechaOrigen(util.convertFechaDate(a.getFechaactivacion())+"T00:00:00Z");
										  res_credito.setFechaTermino(util.convertFechaDate(ultima_amortizacion.getVence())+"T00:00:00Z");
										  
										  //Proximo Pago
										  String cadena_prestamo_cuento = funcionService.prestamo_cuanto(a.getPk(),a.getTipoamortizacion().intValue());
										  String[]parametros_prestamo_cuanto = cadena_prestamo_cuento.split("\\|");
										  List<String>lista_detalles_prestamo = Arrays.asList(parametros_prestamo_cuanto);
										  ResponsePago pago = new ResponsePago();
										  pago.setFecha(valores_sai.get(8));
										  pago.setMonto(Double.parseDouble(lista_detalles_prestamo.get(0)));
										  pago.setMoneda("MXN");
										  res_credito.setProximoPago(pago);
										  
										  //Ultimo Pago
										  pago = new ResponsePago();
										  pago.setFecha(util.convertFechaDate(ultima_amortizacion.getVence()));
										  pago.setMonto(ultima_amortizacion.getAbono().doubleValue());
										  pago.setMoneda("MXN");
										  
										  res_credito.setUltimoPago(pago);
										  
										  res_prod.setId(String.valueOf(p.getIdproducto()));
										  res_prod.setNombre(p.getNombre());
										  res.setProducto(res_prod);
										  
										  res.setTipoRelacion("UNICO_PROPIETARIO");
										  res.setFechaApertura(util.convertFechaDate(a.getFechaactivacion()));
										  res.setFechaUltimoMovimiento(ad.getFecha().replace(" ","T").substring(0,19));
										  res.setFechaUltimaNotificacion(null);
										  
										  
										  res.setDatosCredito(res_credito);
										  res.setSaldos(saldos);
										  listaCuentas.add(res);
										  
									   }
									  res.setEstatus("APERTURADA");									 
							      }/*else {
							    	 response.setMensaje("No se puede operar sobre producto 100,"+p.getNombre());
								    log.info("....No se puede operar sobre producto 100....");
							     }	*/			  
							   }else {
								   response.setMensaje("Cuenta inactiva");
								   log.info("...........Cuenta inactiva");
							   }
							}//fin bucle for
						response.setCuentas(listaCuentas);
						response.setCodigo(200);
					   }else {						
					     response.setCodigo(404);
					     response.setMensaje(tipoCuenta+" no aperturadas para socio:"+socio);
					     log.info("......."+tipoCuenta+" no aperturadas para socio:"+socio+"........");
					   }					
				}else {
					response.setCodigo(404);
					response.setMensaje("Socio no existe");
				}
			}else {
		      response.setMensaje("Numero de socio invalido,valor estimado 14 digitos");		
			}
		} catch (Exception e) {
			log.info("...........Error al obtener cuentas.....");
			return response;
		}
		return response;
	}
	
	
	
	
}
