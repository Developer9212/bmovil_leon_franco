package com.fenoreste.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.MovimientoPaso;
import com.fenoreste.entity.MovimientoPasoPK;
import com.fenoreste.entity.Origen;
import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Producto;
import com.fenoreste.entity.ProductoBanca;
import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;
import com.fenoreste.modelos.RegistroTransaccion;
import com.fenoreste.modelos.RequestTransferencia;
import com.fenoreste.modelos.ResponseTransferencia;
import com.fenoreste.utilidades.HerramientasUtil;
import com.fenoreste.utilidades.Ogs;
import com.fenoreste.utilidades.Opa;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CapaTransferencias {
   
	@Autowired
	private HerramientasUtil herramientasUtil;	
	@Autowired
	private IPersonaService personaService;
	@Autowired
	private IAuxiliarService auxiliarService;
	@Autowired
	private IProductoService productoService;	
	@Autowired
	private ITablaService tablaService;
	@Autowired
	private IFuncionService funcionService;
	@Autowired
	private IOrigenService origenService;
	@Autowired
	private IMovimientoPasoService pasoService;
	@Autowired
	private IProductoBancaService productoBancaService;
	@Autowired
	private IAuxiliarDService auxiliarDService;
	String idtabla= "banca_movil";
	
	public ResponseTransferencia generarTransferencia(RequestTransferencia requestTx,Integer tipoTransferencia,Integer tipoMovimiento) {//tipoTransferencia=0->Entre cuentas propias,1->0 terceros  ----- TipoMov =0-> movimiento captacion,1->pago de prestamos 
		ResponseTransferencia responseTx = new ResponseTransferencia();
		try {
			//Buscamos la persona emisor
			Ogs ogs_emisor = null;
			Opa opa = null;
			PersonaPK pk_persona = null; 
			Persona socio_emisor = null; 
			Persona socio_receptor = null;
			AuxiliarPK auxiliar_pk = null;
			Auxiliar auxiliar_emisor = null;
			Auxiliar auxiliar_receptor = null;
			Producto producto_emisor = null;
			Producto producto_receptor = null;
			boolean bandera_aplicar = false;
			switch (tipoTransferencia) {
			case 1://Entre cuentas
				log.info("Dentro de case 1");
				//Buscamos el ogs emisor
				ogs_emisor = herramientasUtil.ogs(requestTx.getCuentaEmisora().getNumeroSocio());
				pk_persona = new PersonaPK(ogs_emisor.getIdorigen(),ogs_emisor.getIdgrupo(),ogs_emisor.getIdsocio());
				socio_emisor = personaService.buscarPorId(pk_persona);
				if(socio_emisor != null) {
					opa = herramientasUtil.opa(requestTx.getCuentaEmisora().getNumeroCuenta());
					//Nos aseguramos que el producto opere banca movil
					ProductoBanca productoBanca = productoBancaService.buscarPorId(opa.getIdproducto());
					if(productoBanca != null) {
						auxiliar_pk = new AuxiliarPK(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
						auxiliar_emisor = auxiliarService.buscarPorId(auxiliar_pk);
						if(auxiliar_emisor != null) {//Buscamos socio receptor
							producto_emisor = productoService.buscarPorId(auxiliar_emisor.getPk().getIdproducto());
							if(productoBanca.getTipoproducto() == 0 & productoBanca.isRetiro()) {
								if((auxiliar_emisor.getSaldo().doubleValue() - auxiliar_emisor.getGarantia().doubleValue()) >= requestTx.getMontoTransaccion().getImporte()) {
									MovimientoPasoPK pk_paso = null;
									opa = herramientasUtil.opa(requestTx.getCuentaAdquiriente().getNumeroCuenta());
									auxiliar_pk = new AuxiliarPK(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
									auxiliar_receptor = auxiliarService.buscarPorId(auxiliar_pk);									
									if(auxiliar_receptor != null) {
										if(auxiliar_receptor.getIdorigen().intValue() == ogs_emisor.getIdorigen() && auxiliar_receptor.getIdgrupo().intValue() == ogs_emisor.getIdgrupo() && auxiliar_receptor.getIdsocio().intValue() == ogs_emisor.getIdsocio()) {
											producto_receptor = productoService.buscarPorId(auxiliar_receptor.getPk().getIdproducto());
											log.info("Receptor :"+producto_receptor.getIdproducto());
											productoBanca = productoBancaService.buscarPorId(producto_receptor.getIdproducto());
											if(productoBanca != null) {
												if(productoBanca.isDeposito()) {
													TablaPK tb_pk = new TablaPK(idtabla,"usuario");
													Tabla tb_usuario_banca = tablaService.buscarPorId(tb_pk);
													Origen matriz=origenService.origenMatriz();
													DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
													
													log.info("PAsooooooo");
													log.info(matriz.getFechatrabajo().toString().substring(0,19));
													log.info("aspad");
													
													log.info(herramientasUtil.convertFechaDate(matriz.getFechatrabajo()).replace("T"," "));
													String fecha_parseda = herramientasUtil.convertFechaDate(matriz.getFechatrabajo()).replace("T"," ");
													LocalDateTime localDate = LocalDateTime.parse(fecha_parseda, dtf);
													log.info("PAsoooooood");
													Timestamp fecha_transferencia = Timestamp.valueOf(localDate);
													String sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
													MovimientoPaso movimientoPaso = new MovimientoPaso();
													switch (producto_receptor.getTipoproducto()) {
													case 0://Ahorro
														//Preparamos el movimiento(cargo)
														pk_paso = new MovimientoPasoPK(fecha_transferencia,
																										Integer.parseInt(tb_usuario_banca.getDato1()),
																										funcionService.sesion(),
																										String.valueOf(requestTx.getFolioSolicitante()),
																										auxiliar_emisor.getPk().getIdorigenp(),
																										auxiliar_emisor.getPk().getIdproducto(),
																										auxiliar_emisor.getPk().getIdauxiliar());												
														movimientoPaso.setPk(pk_paso);
														movimientoPaso.setIdorigen(ogs_emisor.getIdorigen());
														movimientoPaso.setIdgrupo(ogs_emisor.getIdgrupo());
														movimientoPaso.setIdsocio(ogs_emisor.getIdsocio());
														movimientoPaso.setCargoabono(0);
														movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
														movimientoPaso.setSai_aux(sai_auxiliar);
														MovimientoPaso movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
														
														//Registro movimiento abono
														pk_paso = new MovimientoPasoPK(fecha_transferencia,
																Integer.parseInt(tb_usuario_banca.getDato1()),
																funcionService.sesion(),
																String.valueOf(requestTx.getFolioSolicitante()),
																auxiliar_receptor.getPk().getIdorigenp(),
																auxiliar_receptor.getPk().getIdproducto(),
																auxiliar_receptor.getPk().getIdauxiliar());
														
														auxiliar_pk = new AuxiliarPK(auxiliar_receptor.getPk().getIdorigenp(),auxiliar_receptor.getPk().getIdproducto(),auxiliar_receptor.getPk().getIdauxiliar());
														sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
														movimientoPaso = new MovimientoPaso();
														movimientoPaso.setPk(pk_paso);
														movimientoPaso.setIdorigen(auxiliar_receptor.getIdorigen());
														movimientoPaso.setIdgrupo(auxiliar_receptor.getIdgrupo());
														movimientoPaso.setIdsocio(auxiliar_receptor.getIdsocio());
														movimientoPaso.setCargoabono(1);
														movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
														movimientoPaso.setSai_aux(sai_auxiliar);
														movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
														
														bandera_aplicar= true;
														break;

													case 2://Pago a prestamos
														//Preparamos el movimiento(Abono)
														log.info("Entro aquiiiiiiiiiiiiiiiiiiiiiiiiiii");
														pk_paso = new MovimientoPasoPK(fecha_transferencia,
																										Integer.parseInt(tb_usuario_banca.getDato1()),
																										funcionService.sesion(),
																										String.valueOf(requestTx.getFolioSolicitante()),
																										auxiliar_emisor.getPk().getIdorigenp(),
																										auxiliar_emisor.getPk().getIdproducto(),
																										auxiliar_emisor.getPk().getIdauxiliar());
														movimientoPaso = new MovimientoPaso();
														movimientoPaso.setPk(pk_paso);
														movimientoPaso.setIdorigen(ogs_emisor.getIdorigen());
														movimientoPaso.setIdgrupo(ogs_emisor.getIdgrupo());
														movimientoPaso.setIdsocio(ogs_emisor.getIdsocio());
														movimientoPaso.setCargoabono(0);
														movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
														movimientoPaso.setSai_aux(sai_auxiliar);
														movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
														
														//Registro movimiento abono
														pk_paso = new MovimientoPasoPK(fecha_transferencia,
																Integer.parseInt(tb_usuario_banca.getDato1()),
																funcionService.sesion(),
																String.valueOf(requestTx.getFolioSolicitante()),
																auxiliar_receptor.getPk().getIdorigenp(),
																auxiliar_receptor.getPk().getIdproducto(),
																auxiliar_receptor.getPk().getIdauxiliar());
														
														auxiliar_pk = new AuxiliarPK(auxiliar_receptor.getPk().getIdorigenp(),auxiliar_receptor.getPk().getIdproducto(),auxiliar_receptor.getPk().getIdauxiliar());
														sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
														movimientoPaso = new MovimientoPaso();
														movimientoPaso.setPk(pk_paso);
														movimientoPaso.setIdorigen(auxiliar_receptor.getIdorigen());
														movimientoPaso.setIdgrupo(auxiliar_receptor.getIdgrupo());
														movimientoPaso.setIdsocio(auxiliar_receptor.getIdsocio());
														movimientoPaso.setCargoabono(1);
														movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
														movimientoPaso.setSai_aux(sai_auxiliar);
														movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
														bandera_aplicar = true;
														break;
													}
												}else {
														log.info("......Producto receptor no acepta depositos......");													
												}
											}else {
												log.info(".......Producto receptor no opera banca movil.......");	
											}
											
										}else {
											log.info("......Cuentas no pertenecen al mismo socio......");
										}
																					
										//Vamos a procesar movimientos
										if(bandera_aplicar) {
											String procesados = funcionService.aplica_transaccion(pk_paso);
											log.info("Total procesados:"+procesados);
											if(Integer.parseInt(procesados) > 0 ) {
												funcionService.eliminarRegistrosProcesados(pk_paso);
											    //Vamos a obtener el folio de autorizacion 
											    AuxiliarD ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(auxiliar_pk);
											    responseTx.setFolioAutorizacion(String.format("%06d",ultimo_movimiento.getIdorigenc())+
											    		                        String.format("%06d",Integer.parseInt(ultimo_movimiento.getPeriodo()))+
											    		                        String.valueOf(ultimo_movimiento.getIdtipo())+
											    		                        String.format("%06d",ultimo_movimiento.getIdpoliza()));
											    RegistroTransaccion registro = new RegistroTransaccion();
											    registro.setFechaSolicitud(requestTx.getRegistro().getFechaSolicitud());
											    responseTx.setRegistro(registro);
											}	
										}										
									}
								}else {
									log.info("......Saldo insuficiente para completar la transaccion......");
								}
							}else {
								log.info("......Producto emisor no acepta retiros......");
							}							
						}else {
							log.info("......No existe cuenta emisora......");
						}
					}else {
						log.info(".......Producto no opera banca movil.......");
					}
					
					
				}else {
					log.info(".......Socio no existe.......");
				}
				break;

			case 2:
				
				break;
			}
			
		} catch (Exception e) {
			log.info("Error al generar transferencia..."+e.getMessage());
		}
		
		return responseTx;
	}
	
	
}
