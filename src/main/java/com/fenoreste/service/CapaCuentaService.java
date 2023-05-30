package com.fenoreste.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fenoreste.entity.Amortizacion;
import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Producto;
import com.fenoreste.modelos.Movimiento;
import com.fenoreste.modelos.ResponseCredito;
import com.fenoreste.modelos.ResponseCuenta;
import com.fenoreste.modelos.ResponseInversion;
import com.fenoreste.modelos.ResponseMovimientos;
import com.fenoreste.modelos.ResponsePaginacion;
import com.fenoreste.modelos.ResponsePago;
import com.fenoreste.modelos.ResponseProducto;
import com.fenoreste.modelos.ResponseSaldo;
import com.fenoreste.utilidades.HerramientasUtil;
import com.fenoreste.utilidades.Opa;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CapaCuentaService {

	@Autowired
	private IAuxiliarService auxiliarService;

	@Autowired
	private IAuxiliarDService auxiliarDService;
	
	@Autowired
	private HerramientasUtil util;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private IOrigenService origenService;
	
	@Autowired
	private IFuncionService funcionService;
	
	@Autowired
	private IAmortizacionService amortizacionService;
	

	public ResponseCuenta detalleCuentaCaptacion(String opaBase,String tipoCuenta) {
		log.info("Llegando a detalles captacion.....");
		ResponseCuenta response = new ResponseCuenta();
		try {
			Opa opa = util.opa(opaBase);
			if(opa.getIdorigenp() > 0 && opa.getIdproducto() > 0 && opa.getIdauxiliar() > 0) {
			AuxiliarPK cuenta_ejePk = new AuxiliarPK(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			Auxiliar cuenta_eje = auxiliarService.buscarPorId(cuenta_ejePk);
			   if(cuenta_eje != null) {
				   if(cuenta_eje.getEstatus() == 2) {
						log.info("Accediendo con estatus 2");
						response.setNumeroCuenta(opaBase);
						//Buscamos el producto para saber que tipo de cuenta es
						Producto producto = productoService.buscarPorId(cuenta_eje.getPk().getIdproducto());
						ResponseProducto responseProducto = new ResponseProducto();
						ResponseSaldo res_saldo = null;
						List<ResponseSaldo>saldos = new ArrayList<>();
						switch (producto.getTipoproducto()) {
						case 0:
							if(tipoCuenta.equals("AHORRO")) {
								response.setTipoCuenta("PERSONAL");
								response.setSubtipoCuenta("AHORRO");
								response.setNivelOperacion("DEPOSITOS_Y_RETIROS");
								responseProducto.setTienePlanAhorro(true);
								
								res_saldo = new ResponseSaldo();						  
								res_saldo.setTipo("DISPONIBLE");
								res_saldo.setMonto(cuenta_eje.getSaldo().doubleValue() - cuenta_eje.getGarantia().doubleValue());
								res_saldo.setMoneda("MXN");						  
								saldos.add(res_saldo);
								  
								res_saldo = new ResponseSaldo();
								res_saldo.setTipo("BLOQUEADO");
								res_saldo.setMonto(cuenta_eje.getGarantia().doubleValue());
								res_saldo.setMoneda("MXN");
								saldos.add(res_saldo);
							}					
							break;
						case 1:
							if(tipoCuenta.equals("INVERSION")) {
								response.setTipoCuenta("PERSONAL");
								response.setSubtipoCuenta("CREDITO");
								
								ResponseInversion inversion_res = new ResponseInversion();
								res_saldo = new ResponseSaldo();
								response.setSubtipoCuenta("INVERSION");
								response.setNivelOperacion("SOLO_CONSULTA");
								
								  
								res_saldo.setTipo("ACTUAL");
								res_saldo.setMonto(cuenta_eje.getSaldo().doubleValue());
								res_saldo.setMoneda("MXN");						  
								saldos.add(res_saldo);
								res_saldo = new ResponseSaldo();
								if(auxiliarService.fechaVencimientoAmortizacion(cuenta_eje.getPk()).after(origenService.buscarPorId(cuenta_eje.getPk().getIdorigenp()).getFechatrabajo())) {							  						  
									  res_saldo.setTipo("BLOQUEADO");
									  res_saldo.setMonto(cuenta_eje.getSaldo().doubleValue() );
									  res_saldo.setMoneda("MXN");
								  }else {
									  res_saldo.setTipo("DISPONIBLE");
									  res_saldo.setMonto(cuenta_eje.getSaldo().doubleValue());
									  res_saldo.setMoneda("MXN");							  
								  }
								saldos.add(res_saldo);
								
								 inversion_res.setFechaVencimiento(util.convertFechaDate(auxiliarService.fechaVencimientoAmortizacion(cuenta_eje.getPk())).replace("T00:00:00",""));
								 response.setDatosInversion(inversion_res);
							}	
						   break;
						}
						response.setTipoRelacion("UNICO_PROPIETARIO");
						response.setEstatus("APERTURADA");
						response.setFechaApertura(util.convertFechaDate(cuenta_eje.getFechaactivacion()).substring(0,10));
						//Vamos a buscar fecha de ultimo movimiento
						AuxiliarD ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(cuenta_ejePk);
						response.setFechaUltimoMovimiento(util.convertFechaDate(ultimo_movimiento.getFecha()).substring(0,10));
						response.setFechaUltimaNotificacion(util.convertFechaDate(ultimo_movimiento.getFecha()).substring(0,10));
						
						//Buscamos datos personales del socio
						PersonaPK personaPk = new PersonaPK(cuenta_eje.getIdorigen(),cuenta_eje.getIdgrupo(),cuenta_eje.getIdsocio());
						Persona persona = personaService.buscarPorId(personaPk);
						response.setNumeroSocio(String.format("%06d",persona.getPk().getIdorigen())+persona.getPk().getIdgrupo()+String.format("%06d",personaPk.getIdsocio()));
						response.setNombreSocio(persona.getNombre()+" "+persona.getAppaterno()+" "+persona.getApmaterno());
						response.setAlias(producto.getNombre());
						response.setClabe("");
						
						
						responseProducto.setId(producto.getIdproducto().toString());
						responseProducto.setNombre(producto.getNombre());
						
						response.setProducto(responseProducto);
						response.setSaldos(saldos);
						
					}else {
						response.setCodigo(409);
						response.setMensaje("Cuenta inactiva");
					}
			   }else {
				   response.setCodigo(404);
				   response.setMensaje("Cuenta no existe");
			   }
			}else {
				   response.setCodigo(409);
				   response.setMensaje("Numero de cuenta invalido,requerido 19 digitos");
			}
		} catch (Exception e) {
			  log.info("Error al obtener detalles de cuenta captacion:" + e.getMessage());
			  return response;
			}
		return response;
	}
	
	
	public ResponseCuenta detalleCuentaColocacion(String opaBase,String tipoCuenta) {
		ResponseCuenta response = new ResponseCuenta();
		try {
			Opa opa = util.opa(opaBase);
			AuxiliarPK cuenta_ejePk = new AuxiliarPK(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			Auxiliar cuenta_eje = auxiliarService.buscarPorId(cuenta_ejePk);
			
			if(cuenta_eje.getEstatus() == 2) {
			   //Buscamos el producto para saber que tipo de cuenta es
				Producto producto = productoService.buscarPorId(cuenta_eje.getPk().getIdproducto());
				if(producto.getTipoproducto() == 2) {
					ResponseProducto responseProducto = new ResponseProducto();
					ResponseSaldo res_saldo = null;
					List<ResponseSaldo>saldos = new ArrayList<>();
					switch (producto.getTipoproducto()) {
					case 2:
						if(tipoCuenta.equals("CREDITO")) {
							response.setNumeroCuenta(opaBase);
						    response.setTipoCuenta("PERSONAL");
							response.setSubtipoCuenta("CREDITO");
							response.setNivelOperacion("SOLO_DEPOSITOS");
							
							log.info("Si entro como prestamo");
							ResponseCredito res_credito = new ResponseCredito();
							  
							res_saldo = new ResponseSaldo();
							
							//Buscamos el saldo origen(Primer pago)
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("ORIGEN");
							res_saldo.setMonto(cuenta_eje.getMontoautorizado().doubleValue());
							res_saldo.setMoneda("MXN");
							  
							saldos.add(res_saldo);
							  
							//Buscamos el monto para liquidar el prestamo
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("LIQUIDACION");
							res_saldo.setMonto(new Double(funcionService.montoParaLiquidarPrestamo(cuenta_eje.getPk())));
							res_saldo.setMoneda("MXN");
							  
							saldos.add(res_saldo);
							  
							//Seteamos saldo actual
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("PRINCIPAL");
							res_saldo.setMonto(cuenta_eje.getSaldo().doubleValue());
							res_saldo.setMoneda("MXN");
							saldos.add(res_saldo);
							  
							//El resto de tipo de saldo lo calculamos con el SAI_AUXILIAR
							String sai_auxiliar = funcionService.sai_auxiliar(cuenta_eje.getPk());
							String [] sai_parameters = sai_auxiliar.split("\\|");
							List<String> valores_sai = Arrays.asList(sai_parameters);
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("INTERESES_VENCIDOS");
							res_saldo.setMonto(new Double(valores_sai.get(6)));
							res_saldo.setMoneda("MXN");
							saldos.add(res_saldo);
							  
							//Moratorios
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("INTERESES_MORATORIOS_VENCIDOS");
							res_saldo.setMonto(new Double(valores_sai.get(15)));
							res_saldo.setMoneda("MXN");
							saldos.add(res_saldo);
							  
							//Iva Intereses Ordinarios
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("IVA_INTERESES_VENCIDOS");
							res_saldo.setMonto(new Double(valores_sai.get(17)));
							res_saldo.setMoneda("MXN");
							saldos.add(res_saldo);
							  
							//Iva Intereses Moratorios
							res_saldo = new ResponseSaldo();
							res_saldo.setTipo("IVA_INTERESES_MORATORIOS_VENCIDOS");
							res_saldo.setMonto(new Double(valores_sai.get(18)));
							res_saldo.setMoneda("MXN");
							saldos.add(res_saldo);
							  
							//Datos extra Credito
							res_credito.setTasaAnual(cuenta_eje.getTasaio().doubleValue());
							List<Amortizacion>pagadas = amortizacionService.pagadas(cuenta_eje.getPk());
							Amortizacion ultima_amortizacion = amortizacionService.buscarUltimoPago(cuenta_eje.getPk());
							res_credito.setPlazosRestantes(cuenta_eje.getPlazo()-pagadas.size());
							res_credito.setPlazosTotales((int) cuenta_eje.getPlazo());
							res_credito.setUnidadPlazo("MESES");
							res_credito.setFechaOrigen(util.convertFechaDate(cuenta_eje.getFechaactivacion())+"T00:00:00Z");
							res_credito.setFechaTermino(util.convertFechaDate(ultima_amortizacion.getVence())+"T00:00:00Z");
							  
							//Proximo Pago
							String cadena_prestamo_cuento = funcionService.prestamo_cuanto(cuenta_eje.getPk(),cuenta_eje.getTipoamortizacion().intValue());
							String[]parametros_prestamo_cuanto = cadena_prestamo_cuento.split("\\|");
							List<String>lista_detalles_prestamo = Arrays.asList(parametros_prestamo_cuanto);
							ResponsePago pago = new ResponsePago();
							pago.setFecha(valores_sai.get(8));
							pago.setMonto(new Double(lista_detalles_prestamo.get(0)));
							pago.setMoneda("MXN");
							res_credito.setProximoPago(pago);
							  
							//Ultimo Pago
							pago = new ResponsePago();
							pago.setFecha(util.convertFechaDate(ultima_amortizacion.getVence())+"T00:00:00");
							pago.setMonto(ultima_amortizacion.getAbono().doubleValue());
							pago.setMoneda("MXN");
							  
							res_credito.setUltimoPago(pago);
							  
							response.setDatosCredito(res_credito);
							response.setSaldos(saldos);
							
							response.setTipoRelacion("UNICO_PROPIETARIO");
							response.setEstatus("APERTURADA");
							response.setFechaApertura(util.convertFechaDate(cuenta_eje.getFechaactivacion()).substring(0,10));
							//Vamos a buscar fecha de ultimo movimiento
							AuxiliarD ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(cuenta_ejePk);
							response.setFechaUltimoMovimiento(util.convertFechaDate(ultimo_movimiento.getFecha()).substring(0,10));
							response.setFechaUltimaNotificacion(util.convertFechaDate(ultimo_movimiento.getFecha()).substring(0,10));
							
							//Buscamos datos personales del socio
							PersonaPK personaPk = new PersonaPK(cuenta_eje.getIdorigen(),cuenta_eje.getIdgrupo(),cuenta_eje.getIdsocio());
							Persona persona = personaService.buscarPorId(personaPk);
							response.setNumeroSocio(String.format("%06d",persona.getPk().getIdorigen())+persona.getPk().getIdgrupo()+String.format("%06d",personaPk.getIdsocio()));
							response.setNombreSocio(persona.getNombre()+" "+persona.getAppaterno()+" "+persona.getApmaterno());
							
							response.setAlias(producto.getNombre());
							response.setClabe("");
							
							
							responseProducto.setId(producto.getIdproducto().toString());
							responseProducto.setNombre(producto.getNombre());
							
							response.setProducto(responseProducto);
							response.setSaldos(saldos);
						}						
						break;	
				 }				
			   }				
			}	
		} catch (Exception e) {
			log.info("Error al obtener detalles de cuenta colocacion:" + e.getMessage());
		}
		return response;
	}

    public ResponseMovimientos movimientosCuenta(String opaBase,String tipo,String fechaInicio,String fechaFin,int inicioPage,int finPage) {
         ResponseMovimientos response = new ResponseMovimientos();
         try {
        	 Opa opa = util.opa(opaBase);
        	 AuxiliarPK auxiliarPK = new AuxiliarPK(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
        	 Auxiliar auxiliar = auxiliarService.buscarPorId(auxiliarPK);
        	 
        	 if(auxiliar.getEstatus() == 2) {
        		 ResponsePaginacion paginacion = new ResponsePaginacion();
        		 response.setNumeroCuenta(opaBase);
        		 response.setTipoCuenta("PERSONAL");
        		 response.setSubtipoCuenta(tipo);
        		 List<Movimiento>listadoMovimientos = new ArrayList<>();
        		 List<AuxiliarD>listaAuxiliaresD = auxiliarDService.buscarTodosMovs(auxiliarPK,util.convertFechaString(fechaInicio),util.convertFechaString(fechaFin),PageRequest.of(inicioPage,finPage));
        		 for(int i=0;i<listaAuxiliaresD.size();i++) {
        			 AuxiliarD mov_ad = listaAuxiliaresD.get(i);
        			 Movimiento mov = new Movimiento();
        			 log.info("Numero de transaccion:"+mov_ad.getTransaccion());
        			 mov.setId(String.valueOf(mov_ad.getTransaccion()));
        			 if(mov_ad.getCargoabono() == 0) {
                          mov.setDescripcion("RETIRO"); 
                          mov.setTipo("CREDITO");
        			 }else {
                          mov.setDescripcion("DEPOSITO"); 
                          mov.setTipo("DEBITO");
        			 }
        			 log.info("fechaaaaaaaaaaaaaaaaaaa.................."+util.convertFechaDateHora(mov_ad.getFecha()));
        			 mov.setFechaTransaccion(util.convertFechaDate(mov_ad.getFecha())+"T"+util.convertFechaDateHora(mov_ad.getFecha()));
        			 mov.setFechaPublicacion(util.convertFechaDate(mov_ad.getFecha())+"T"+util.convertFechaDateHora(mov_ad.getFecha()));
        			 mov.setMonto(mov_ad.getMonto().doubleValue());
        			 listadoMovimientos.add(mov);
        		 }
        		 
        		 paginacion.setTotal(auxiliarDService.contadorPorFecha(auxiliarPK, util.convertFechaString(fechaInicio),util.convertFechaString(fechaFin)));
        		 paginacion.setOffset(inicioPage);
        		 paginacion.setLimit(finPage);
        		 response.setMovimientos(listadoMovimientos);
        		 response.setPaginacion(paginacion);
        		 
        	 }else {
        	   log.info("Cuenta no esta activa");
        	 }
			
		} catch (Exception e) {
		    log.info("Error al obtener movimientos:"+e.getMessage());
		}
        
         return response;
    }
}
