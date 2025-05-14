package com.fenoreste.service;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.entity.AbonoSpeiPK;
import com.fenoreste.entity.Auxiliar;
import com.fenoreste.entity.AuxiliarD;
import com.fenoreste.entity.AuxiliarPK;
import com.fenoreste.entity.ClabeInterbancaria;
import com.fenoreste.entity.MovimientoPaso;
import com.fenoreste.entity.MovimientoPasoPK;
import com.fenoreste.entity.Origen;
import com.fenoreste.entity.Persona;
import com.fenoreste.entity.PersonaPK;
import com.fenoreste.entity.Producto;
import com.fenoreste.entity.ProductoBanca;
import com.fenoreste.entity.SpeiAbono;
import com.fenoreste.entity.SpeiDispersion;
import com.fenoreste.entity.SpeiAbonoPaso;
import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;
import com.fenoreste.entity.Transferencia;
import com.fenoreste.modelos.RegistroTransaccion;
import com.fenoreste.modelos.RequestTransferencia;
import com.fenoreste.modelos.RequestTransferenciaSpei;
import com.fenoreste.modelos.ResponseActualizacionSpei;
import com.fenoreste.modelos.ResponseTransferencia;
import com.fenoreste.modelos.SpeiActualizacion;
import com.fenoreste.utilidades.HerramientasUtil;
import com.fenoreste.utilidades.Ogs;
import com.fenoreste.utilidades.Opa;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CapaTransferenciaService {

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
    String idtabla = "banca_movil";
    String idtablaspei = "spei";
    @Autowired
    private ITransferenciaService transferenciaService;
    @Autowired
    private IClabeInterbancariaService clabeInterbancariaService;
    @Autowired
    private ISpeiService speiService;

    public ResponseTransferencia generarTransferencia(RequestTransferencia requestTx, Integer tipoTransferencia,
                                                      Integer tipoMovimiento) {// tipoTransferencia=0->Entre cuentas propias,1->terceros ----- TipoMov =0->
        // movimiento captacion,1->pago de prestamos
        ResponseTransferencia responseTx = new ResponseTransferencia();
        responseTx.setCodigo(500);
        try {
            // Buscamos la persona emisor
            Ogs ogs_emisor = null;
            Opa opa = null;
            PersonaPK pk_persona = null;
            Persona socio_emisor = null;
            AuxiliarPK auxiliar_pk = null;
            Auxiliar auxiliar_emisor = null;
            Auxiliar auxiliar_receptor = null;
            Producto producto_emisor = null;
            Producto producto_receptor = null;
            boolean bandera_aplicar = false;
            log.info("Tipo de transferencia.......:" + tipoTransferencia);
            // Buscamos el ogs emisor
            ogs_emisor = herramientasUtil.ogs(requestTx.getCuentaEmisora().getNumeroSocio());
            pk_persona = new PersonaPK(ogs_emisor.getIdorigen(), ogs_emisor.getIdgrupo(), ogs_emisor.getIdsocio());
            socio_emisor = personaService.buscarPorId(pk_persona);

            if (socio_emisor != null) {
                opa = herramientasUtil.opa(requestTx.getCuentaEmisora().getNumeroCuenta());
                // Nos aseguramos que el producto opere banca movil
                ProductoBanca productoBanca = productoBancaService.buscarPorId(opa.getIdproducto());
                if (productoBanca != null) {
                    auxiliar_pk = new AuxiliarPK(opa.getIdorigenp(), opa.getIdproducto(), opa.getIdauxiliar());
                    auxiliar_emisor = auxiliarService.buscarPorId(auxiliar_pk);
                    if (auxiliar_emisor != null) {
                        producto_emisor = productoService.buscarPorId(auxiliar_emisor.getPk().getIdproducto());
                        if (productoBanca.getTipoproducto() == 0 & productoBanca.isRetiro()) {
                            if ((auxiliar_emisor.getSaldo().doubleValue()
                                    - auxiliar_emisor.getGarantia().doubleValue()) >= requestTx.getMontoTransaccion()
                                    .getImporte()) {

                                if ((auxiliar_emisor.getSaldo().doubleValue()
                                        - auxiliar_emisor.getGarantia().doubleValue()) - requestTx.getMontoTransaccion()
                                        .getImporte() > 1) {


                                    MovimientoPasoPK pk_paso = null;
                                    opa = herramientasUtil.opa(requestTx.getCuentaAdquiriente().getNumeroCuenta());
                                    auxiliar_pk = new AuxiliarPK(opa.getIdorigenp(), opa.getIdproducto(), opa.getIdauxiliar());
                                    auxiliar_receptor = auxiliarService.buscarPorId(auxiliar_pk);
                                    if (auxiliar_receptor != null) {
                                        producto_receptor = productoService.buscarPorId(auxiliar_receptor.getPk().getIdproducto());
                                        productoBanca = productoBancaService.buscarPorId(producto_receptor.getIdproducto());
                                        if (productoBanca != null) {
                                            if (productoBanca.isDeposito()) {
                                                TablaPK tb_pk = new TablaPK(idtabla, "usuario");
                                                Tabla tb_usuario_banca = tablaService.buscarPorId(tb_pk);
                                                String sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
                                                MovimientoPaso movimientoPaso = new MovimientoPaso();
                                                String horaServidorBase = funcionService.horaServidor();

                                                Date date = new Date();
                                                Timestamp timestamp = new Timestamp(date.getTime());
                                                switch (tipoTransferencia) {
                                                    case 1:// Entre cuentas
                                                        if (auxiliar_receptor.getIdorigen().intValue() == ogs_emisor.getIdorigen()
                                                                && auxiliar_receptor.getIdgrupo().intValue() == ogs_emisor.getIdgrupo()
                                                                && auxiliar_receptor.getIdsocio().intValue() == ogs_emisor.getIdsocio()) {
                                                            log.info("Accedio a cuentas propiaas");
                                                            switch (producto_receptor.getTipoproducto()) {
                                                                case 0:// Ahorro
                                                                    // Preparamos el movimiento(cargo)
                                                                    pk_paso = new MovimientoPasoPK(timestamp,
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
                                                                    movimientoPaso
                                                                            .setMonto(requestTx.getMontoTransaccion().getImporte());
                                                                    movimientoPaso.setSai_aux(sai_auxiliar);
                                                                    MovimientoPaso movimientoPasoRegistro = pasoService
                                                                            .guardar(movimientoPaso);

                                                                    // Registro movimiento abono
                                                                    pk_paso = new MovimientoPasoPK(timestamp,
                                                                            Integer.parseInt(tb_usuario_banca.getDato1()),
                                                                            funcionService.sesion(),
                                                                            String.valueOf(requestTx.getFolioSolicitante()),
                                                                            auxiliar_receptor.getPk().getIdorigenp(),
                                                                            auxiliar_receptor.getPk().getIdproducto(),
                                                                            auxiliar_receptor.getPk().getIdauxiliar());

                                                                    auxiliar_pk = new AuxiliarPK(
                                                                            auxiliar_receptor.getPk().getIdorigenp(),
                                                                            auxiliar_receptor.getPk().getIdproducto(),
                                                                            auxiliar_receptor.getPk().getIdauxiliar());
                                                                    sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
                                                                    movimientoPaso = new MovimientoPaso();
                                                                    movimientoPaso.setPk(pk_paso);
                                                                    movimientoPaso.setIdorigen(auxiliar_receptor.getIdorigen());
                                                                    movimientoPaso.setIdgrupo(auxiliar_receptor.getIdgrupo());
                                                                    movimientoPaso.setIdsocio(auxiliar_receptor.getIdsocio());
                                                                    movimientoPaso.setCargoabono(1);
                                                                    movimientoPaso
                                                                            .setMonto(requestTx.getMontoTransaccion().getImporte());
                                                                    movimientoPaso.setSai_aux(sai_auxiliar);
                                                                    movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                                                                    bandera_aplicar = true;
                                                                    break;

                                                                case 2:// Pago a prestamos
                                                                    // Preparamos el movimiento(Abono)
                                                                	String monto_liquidacion = funcionService.montoParaLiquidarPrestamo(auxiliar_receptor.getPk());
                                                                    log.info(":::::::Monto liquidacion:"+monto_liquidacion);
                                                                    
                                                                    if(requestTx.getMontoTransaccion().getImporte() <= (Double.parseDouble(monto_liquidacion))){
                                                                    	pk_paso = new MovimientoPasoPK(timestamp,
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
                                                                        movimientoPaso.setSai_aux("");
                                                                        movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                                                                        // Registro movimiento abono
                                                                        pk_paso = new MovimientoPasoPK(timestamp,
                                                                                Integer.parseInt(tb_usuario_banca.getDato1()),
                                                                                funcionService.sesion(),
                                                                                String.valueOf(requestTx.getFolioSolicitante()),
                                                                                auxiliar_receptor.getPk().getIdorigenp(),
                                                                                auxiliar_receptor.getPk().getIdproducto(),
                                                                                auxiliar_receptor.getPk().getIdauxiliar());

                                                                        auxiliar_pk = new AuxiliarPK(
                                                                                auxiliar_receptor.getPk().getIdorigenp(),
                                                                                auxiliar_receptor.getPk().getIdproducto(),
                                                                                auxiliar_receptor.getPk().getIdauxiliar());
                                                                        sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
                                                                        movimientoPaso = new MovimientoPaso();
                                                                        movimientoPaso.setPk(pk_paso);
                                                                        movimientoPaso.setIdorigen(auxiliar_receptor.getIdorigen());
                                                                        movimientoPaso.setIdgrupo(auxiliar_receptor.getIdgrupo());
                                                                        movimientoPaso.setIdsocio(auxiliar_receptor.getIdsocio());
                                                                        movimientoPaso.setCargoabono(1);
                                                                        movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
                                                                        movimientoPaso.setSai_aux("");
                                                                        movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
                                                                        bandera_aplicar = true;
                                                                        break;
                                                                    }else{
                                                                        responseTx.setCodigo(409);
                                                                        responseTx.setMensajeUsuario("El monto de transferencia traspasa al monto de liquidacion:"+monto_liquidacion);
                                                                        log.info("El monto de transferencia traspasa al monto de liquidacion:"+monto_liquidacion);
                                                                    }
                                                                    
                                                                    
                                                                    
                                                            }

                                                        } else {
                                                            log.info("......Cuentas no pertenecen al mismo socio......");
                                                            responseTx.setCodigo(409);
                                                            responseTx.setMensajeUsuario("Cuentas no pertenecen al mismo socio");
                                                        }

                                                        break;
                                                    case 2:// A terceros dentro de la entidad
                                                        log.info("................Transferencia a terceros dentro de la entidad......................");
                                                        log.info("ogs aux receptor:" + auxiliar_receptor.getIdorigen().intValue() + "\n" +
                                                                auxiliar_receptor.getIdgrupo().intValue() + "\n" +
                                                                auxiliar_receptor.getIdsocio() + "\n" +
                                                                "Ogs-Emisor:" + ogs_emisor.getIdorigen() + "," + ogs_emisor.getIdgrupo() + ogs_emisor.getIdsocio());
                                                        if (!(String.valueOf(auxiliar_receptor.getIdorigen().intValue()) + String.valueOf(auxiliar_receptor.getIdgrupo().intValue()) + String.valueOf(auxiliar_receptor.getIdsocio().intValue()))
                                                                .equals(
                                                                        String.valueOf(ogs_emisor.getIdorigen().intValue()) + String.valueOf(ogs_emisor.getIdgrupo().intValue()) + String.valueOf(ogs_emisor.getIdsocio().intValue()))) {
													/*
												}
												if (auxiliar_receptor.getIdorigen().intValue() != ogs_emisor.getIdorigen().intValue()
													&& auxiliar_receptor.getIdgrupo().intValue() != ogs_emisor.getIdgrupo().intValue()
													&& auxiliar_receptor.getIdsocio().intValue() != ogs_emisor.getIdsocio().intValue()) {
												*/
                                                            switch (producto_receptor.getTipoproducto()) {
                                                                case 0:// Ahorro
                                                                    // Preparamos el movimiento(cargo)
                                                                    pk_paso = new MovimientoPasoPK(timestamp,
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
                                                                    MovimientoPaso movimientoPasoRegistro = pasoService
                                                                            .guardar(movimientoPaso);

                                                                    // Registro movimiento abono
                                                                    pk_paso = new MovimientoPasoPK(timestamp,
                                                                            Integer.parseInt(tb_usuario_banca.getDato1()),
                                                                            funcionService.sesion(),
                                                                            String.valueOf(requestTx.getFolioSolicitante()),
                                                                            auxiliar_receptor.getPk().getIdorigenp(),
                                                                            auxiliar_receptor.getPk().getIdproducto(),
                                                                            auxiliar_receptor.getPk().getIdauxiliar());

                                                                    auxiliar_pk = new AuxiliarPK(
                                                                            auxiliar_receptor.getPk().getIdorigenp(),
                                                                            auxiliar_receptor.getPk().getIdproducto(),
                                                                            auxiliar_receptor.getPk().getIdauxiliar());
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

                                                                case 2:// Pago a prestamos
                                                                	String monto_liquidacion = funcionService.montoParaLiquidarPrestamo(auxiliar_receptor.getPk());
                                                                    log.info(":::::::Monto liquidacion:"+monto_liquidacion);
                                                                    if( requestTx.getMontoTransaccion().getImporte() <= (Double.parseDouble(monto_liquidacion))){
                                                                        pk_paso = new MovimientoPasoPK(timestamp,
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
                                                                        movimientoPaso.setSai_aux("");
                                                                        movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                                                                        // Registro movimiento abono
                                                                        pk_paso = new MovimientoPasoPK(timestamp,
                                                                                Integer.parseInt(tb_usuario_banca.getDato1()),
                                                                                funcionService.sesion(),
                                                                                String.valueOf(requestTx.getFolioSolicitante()),
                                                                                auxiliar_receptor.getPk().getIdorigenp(),
                                                                                auxiliar_receptor.getPk().getIdproducto(),
                                                                                auxiliar_receptor.getPk().getIdauxiliar());

                                                                        auxiliar_pk = new AuxiliarPK(
                                                                                auxiliar_receptor.getPk().getIdorigenp(),
                                                                                auxiliar_receptor.getPk().getIdproducto(),
                                                                                auxiliar_receptor.getPk().getIdauxiliar());
                                                                        sai_auxiliar = funcionService.sai_auxiliar(auxiliar_pk);
                                                                        movimientoPaso = new MovimientoPaso();
                                                                        movimientoPaso.setPk(pk_paso);
                                                                        movimientoPaso.setIdorigen(auxiliar_receptor.getIdorigen());
                                                                        movimientoPaso.setIdgrupo(auxiliar_receptor.getIdgrupo());
                                                                        movimientoPaso.setIdsocio(auxiliar_receptor.getIdsocio());
                                                                        movimientoPaso.setCargoabono(1);
                                                                        movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
                                                                        movimientoPaso.setSai_aux("");


                                                                        movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
                                                                        bandera_aplicar = true;
                                                                    }else{
                                                                        responseTx.setCodigo(409);
                                                                        responseTx.setMensajeUsuario("El monto de transferencia traspasa al monto de liquidacion:"+monto_liquidacion);
                                                                        log.info("El monto de transferencia traspasa al monto de liquidacion:"+monto_liquidacion);
                                                                    }
                                                                    break;
                                                            }
                                                            break;

                                                        } else {
                                                            responseTx.setCodigo(409);
                                                            responseTx.setMensajeUsuario("Numero de socio iguales deberia ser transferencia entre cuentas propias");
                                                            log.info(".....Socio es el mismo,deberia ser transferencia a cuenta propia......");
                                                        }

                                                }

                                                // Vamos a procesar movimientos
                                                if (bandera_aplicar) {
                                                    String procesados = funcionService.aplica_transaccion(pk_paso);
                                                    if (Integer.parseInt(procesados) > 0) {
                                                        log.info("vamos a eliminar registros");
                                                        funcionService.eliminarRegistrosProcesados(pk_paso);
                                                        log.info("Registros eliminados");
                                                        // Vamos a obtener el folio de autorizacion

                                                        AuxiliarD ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(auxiliar_pk, Integer.parseInt(tb_usuario_banca.getDato1()), 3);
                                                        log.info("auxiliar encontrado:" + ultimo_movimiento);
                                                        //String folioAutorizacion = String.format("%025s", movimientoPaso.getPk().getReferencia()).replace(' ', '0');
                                                        responseTx.setFolioAutorizacion(movimientoPaso.getPk().getReferencia());//String.format("%06d", ultimo_movimiento.getIdorigenc())+String.format("%06d",Integer.parseInt(ultimo_movimiento.getPeriodo()))+String.valueOf(ultimo_movimiento.getIdtipo())+ String.format("%06d",ultimo_movimiento.getIdpoliza()));
                                                        RegistroTransaccion registro = new RegistroTransaccion();
                                                        registro.setFechaRegistro(requestTx.getRegistro().getFechaSolicitud());
                                                        responseTx.setRegistro(registro);
                                                        //Vamos a guardar la transferencia
                                                        Transferencia tx = new Transferencia();
                                                        tx.setFoliosolicitante(requestTx.getFolioSolicitante().toString());
                                                        tx.setFecha(timestamp);
                                                        tx.setSocioorigen(requestTx.getCuentaEmisora().getNumeroSocio());
                                                        tx.setCuentaorigen(requestTx.getCuentaEmisora().getNumeroCuenta());
                                                        tx.setSociodestino(String.format("%06d", auxiliar_receptor.getIdorigen()) + String.format("%02d", auxiliar_receptor.getIdgrupo()) + String.format("%06d", auxiliar_receptor.getIdsocio()));
                                                        tx.setCuentadestino(requestTx.getCuentaAdquiriente().getNumeroCuenta());
                                                        tx.setMonto(requestTx.getMontoTransaccion().getImporte());
                                                        tx.setPolizacreada(ultimo_movimiento.getIdorigenc() + "-" + ultimo_movimiento.getPeriodo() + "-" + ultimo_movimiento.getIdtipo() + "-" + ultimo_movimiento.getIdpoliza());
                                                        tx.setFolioautorizacion(responseTx.getFolioAutorizacion());
                                                        tx = transferenciaService.guardar(tx);
                                                        log.info("......Poliza Generada:" + tx.getPolizacreada());
                                                        responseTx.setCodigo(200);
                                                    }
                                                }

                                            } else {
                                                responseTx.setCodigo(409);
                                                responseTx.setMensajeUsuario("Producto receptor no acepta depositos");
                                                log.info("......Producto receptor no acepta depositos......");
                                            }
                                        } else {
                                            responseTx.setCodigo(409);
                                            responseTx.setMensajeUsuario("Producto receptor no opera para banca movil");
                                            log.info(".......Producto receptor no opera banca movil.......");
                                        }
                                    } else {
                                        responseTx.setCodigo(409);
                                        responseTx.setMensajeUsuario("Cuenta receptora no existe");
                                        log.info("..........Cuenta receptora no existe..........");
                                    }
                                } else {
                                    responseTx.setCodigo(409);
                                    responseTx.setMensajeUsuario("Saldo final debe ser mayor a 1.00");
                                    log.info("......Saldo final debe ser mayor a 1.00......");
                                }
                            } else {
                                responseTx.setCodigo(409);
                                responseTx.setMensajeUsuario("Saldo insuficiente para completar la transaccion");
                                log.info("......Saldo insuficiente para completar la transaccion......");
                            }
                        } else {
                            responseTx.setCodigo(409);
                            responseTx.setMensajeUsuario("Producto emisor no acepta retiros");
                            log.info("......Producto emisor no acepta retiros......");
                        }
                    } else {
                        responseTx.setCodigo(409);
                        responseTx.setMensajeUsuario("No existe cuenta emisora");
                        log.info("......No existe cuenta emisora......");
                    }
                } else {
                    responseTx.setCodigo(409);
                    responseTx.setMensajeUsuario("Producto no opera banca movil");
                    log.info(".......Producto no opera banca movil.......");
                }
            } else {
                responseTx.setCodigo(409);
                responseTx.setMensajeUsuario("Socio no existe");
                log.info(".......Socio no existe.......");
            }

        } catch (Exception e) {
            if (responseTx.getMensajeUsuario().equals("")) {
                responseTx.setCodigo(500);
                responseTx.setMensajeUsuario("Error interno en el servidor");
            }
            log.info("Error al generar transferencia..." + e.getMessage());
            return responseTx;
        }

        return responseTx;
    }

    public ResponseTransferencia comenzarSpei(RequestTransferenciaSpei requestTx) {
        ResponseTransferencia response = new ResponseTransferencia();
        try {
            log.info("::::::::::::Peticion spei dispersion:::::::::::::::" + requestTx);
            Ogs ogs = herramientasUtil.ogs(requestTx.getOrdenanteSpei().getNumeroSocio());
            if (ogs != null) {
                Opa opa = herramientasUtil.opa(requestTx.getOrdenanteSpei().getNumeroCuenta());
                if (opa != null) {
                    AuxiliarPK aPk = new AuxiliarPK(opa.getIdorigenp(), opa.getIdproducto(), opa.getIdauxiliar());
                    Auxiliar a = auxiliarService.buscarPorId(aPk);
                    if (a != null) {
                        if (a.getEstatus() == 2) {
                            ClabeInterbancaria clabe = clabeInterbancariaService.buscarPorId(aPk);
                            if (clabe != null) {
                                if (requestTx.getOrdenanteSpei().getClabe().equals(clabe.getClabe())) {
                                    if (a.getSaldo().intValue() > 0) {
                                        if ((a.getSaldo().doubleValue() - a.getGarantia().doubleValue()) >= (requestTx.getMontoTransaccion().getImporte() + requestTx.getMontoTransaccion().getComision())) {
                                            if (((a.getSaldo().doubleValue() - a.getGarantia().doubleValue()) - (requestTx.getMontoTransaccion().getImporte() + requestTx.getMontoTransaccion().getComision())) > 1) {

                                                TablaPK tb_pk = new TablaPK(idtablaspei, "usuario_dispersion");
                                                Tabla tb_usuario_spei = tablaService.buscarPorId(tb_pk);
                                                Date date = new Date();
                                                Timestamp timestamp = new Timestamp(date.getTime());
                                                String sai_auxiliar = funcionService.sai_auxiliar(aPk);
                                                MovimientoPaso movimientoPaso = new MovimientoPaso();
                                                //Preparando movimiento cargo
                                                MovimientoPasoPK pk_paso = new MovimientoPasoPK(timestamp, Integer.parseInt(tb_usuario_spei.getDato1()), funcionService.sesion(),
                                                        String.valueOf(requestTx.getFolioSolicitante()),
                                                        a.getPk().getIdorigenp(),
                                                        a.getPk().getIdproducto(),
                                                        a.getPk().getIdauxiliar());
                                                movimientoPaso.setPk(pk_paso);
                                                movimientoPaso.setIdorigen(a.getIdorigen());
                                                movimientoPaso.setIdgrupo(a.getIdgrupo());
                                                movimientoPaso.setIdsocio(a.getIdsocio());
                                                movimientoPaso.setCargoabono(0);
                                                movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte() + requestTx.getMontoTransaccion().getComision());
                                                movimientoPaso.setSai_aux(sai_auxiliar);
                                                movimientoPaso.setIdordenspei(requestTx.getFolioSolicitante().intValue());
                                                MovimientoPaso movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                                                //Preparando movimiento Abono cuenta
                                                pk_paso = new MovimientoPasoPK(timestamp, Integer.parseInt(tb_usuario_spei.getDato1()), funcionService.sesion(),
                                                        String.valueOf(requestTx.getFolioSolicitante()),
                                                        0,
                                                        0,
                                                        0);
                                                tb_pk = new TablaPK(idtablaspei, "cuenta_dispersion");
                                                Tabla cuenta = tablaService.buscarPorId(tb_pk);
                                                movimientoPaso.setPk(pk_paso);
                                                movimientoPaso.setIdorigen(a.getIdorigen());
                                                movimientoPaso.setIdgrupo(a.getIdgrupo());
                                                movimientoPaso.setIdsocio(a.getIdsocio());
                                                movimientoPaso.setIdcuenta(cuenta.getDato1());
                                                movimientoPaso.setCargoabono(1);
                                                movimientoPaso.setMonto(requestTx.getMontoTransaccion().getImporte());
                                                movimientoPaso.setSai_aux(sai_auxiliar);
                                                movimientoPaso.setIdordenspei(requestTx.getFolioSolicitante().intValue());
                                                movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                                                //Vamos a generar poliza de comision por la dispersion
                                                //Preparando movimiento cargo
                                                tb_pk = new TablaPK(idtablaspei, "cuenta_comision_dispersion");
                                                cuenta = tablaService.buscarPorId(tb_pk);
                                                pk_paso = new MovimientoPasoPK(timestamp, Integer.parseInt(tb_usuario_spei.getDato1()), funcionService.sesion(),
                                                        String.valueOf(requestTx.getFolioSolicitante()),
                                                        0,
                                                        1,
                                                        0);
                                                movimientoPaso.setPk(pk_paso);
                                                movimientoPaso.setIdorigen(a.getIdorigen());
                                                movimientoPaso.setIdgrupo(a.getIdgrupo());
                                                movimientoPaso.setIdsocio(a.getIdsocio());
                                                movimientoPaso.setCargoabono(1);
                                                movimientoPaso.setMonto(requestTx.getMontoTransaccion().getComision());
                                                movimientoPaso.setIdordenspei(requestTx.getFolioSolicitante().intValue());
                                                movimientoPasoRegistro = pasoService.guardar(movimientoPaso);
                                                String procesados = funcionService.aplica_transaccion(movimientoPasoRegistro.getPk());
                                                if (Integer.parseInt(procesados) > 0) {
                                                    funcionService.eliminarRegistrosProcesados(pk_paso);
                                                    // Vamos a obtener el folio de autorizacion
                                                    AuxiliarD ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(aPk, Integer.parseInt(tb_usuario_spei.getDato1()), 3);
                                                    String folioAuto = String.format("%025d", new BigInteger(String.format("%06d", ultimo_movimiento.getIdorigenc()) + String.format("%06d", Integer.parseInt(ultimo_movimiento.getPeriodo())) + String.valueOf(ultimo_movimiento.getIdtipo()) + String.format("%06d", ultimo_movimiento.getIdpoliza())));
                                                    response.setFolioAutorizacion(folioAuto);

                                                    RegistroTransaccion registro = new RegistroTransaccion();
                                                    registro.setFechaRegistro(requestTx.getRegistro().getFechaSolicitud());
                                                    response.setRegistro(registro);

                                                    //Vamos a guardar la transferencia
                                                    Transferencia tx = new Transferencia();
                                                    tx.setFoliosolicitante(requestTx.getFolioSolicitante().toString());
                                                    tx.setFecha(timestamp);
                                                    tx.setSocioorigen(requestTx.getOrdenanteSpei().getNumeroSocio());
                                                    tx.setCuentaorigen(requestTx.getOrdenanteSpei().getNumeroCuenta());
                                                    tx.setMonto(requestTx.getMontoTransaccion().getImporte());
                                                    tx.setComision(requestTx.getMontoTransaccion().getComision());
                                                    tx.setCuentadestino(requestTx.getBeneficiarioSpei().getClabe());
                                                    tx.setPolizacreada(ultimo_movimiento.getIdorigenc() + "-" + ultimo_movimiento.getPeriodo() + "-" + ultimo_movimiento.getIdtipo() + "-" + ultimo_movimiento.getIdpoliza());
                                                    tx.setEsspei(true);
                                                    tx.setFolioautorizacion(response.getFolioAutorizacion());
                                                    tx = transferenciaService.guardar(tx);
                                                    log.info("......Poliza Generada:" + tx.getPolizacreada());

                                                    SpeiDispersion speiTx = new SpeiDispersion();
                                                    speiTx.setFoliosolicitante(requestTx.getFolioSolicitante().toString());
                                                    speiTx.setFolioautorizacion(response.getFolioAutorizacion());//String.format("%025d",new BigInteger(response.getFolioAutorizacion())).replace(' ', '0'));
                                                    speiTx.setEstadotransaccion("EN PROCESO");
                                                    speiTx = speiService.guardar(speiTx);
                                                    response.setCodigo(200);
                                                }
                                            } else {
                                                response.setCodigo(409);
                                                response.setMensajeUsuario("Saldo final debe ser mayor a 1.00");
                                                log.warn(":::::::::::::::::::::::Saldo final debe ser mayor a 1.00:::::::::::::::::::::::::");
                                            }

                                        } else {
                                            response.setCodigo(409);
                                            response.setMensajeUsuario("Saldo insuficiente para procesar la transaccion");
                                            log.warn(":::::::::::::::::::::::Saldo insuficiente para procesar la transaccion:::::::::::::::::::::::::");
                                        }
                                    } else {
                                        response.setCodigo(409);
                                        response.setMensajeUsuario("Saldo para producto auxiliar es 0");
                                        log.warn(":::::::::::::::::::::::Saldo para producto auxiliar es 0:::::::::::::::::::::::::");
                                    }

                                } else {
                                    response.setCodigo(409);
                                    response.setMensajeUsuario("Clabes STP no coinciden");
                                    log.warn("::::::::::::::::::::::Clabes STP no coinciden::::::::::::::::::::::");
                                }
                            } else {
                                response.setCodigo(409);
                                response.setMensajeUsuario("No existen registros para clabe interbancaria");
                                log.warn(":::::::::::::::::::::::No existen registros para clabe interbancaria::::::::::::::::::::::::::");
                            }
                        } else {
                            response.setCodigo(409);
                            response.setMensajeUsuario("Cuenta inactiva");
                            log.warn("::::::::::::::::::::::Cuenta inactiva::::::::::::::::::::");
                        }
                    } else {
                        response.setCodigo(409);
                        response.setMensajeUsuario("Cuenta ordenante no existe");
                        log.warn("::::::::::::::::::::::Cuenta ordenante no existe:::::::::::::::::::");
                    }
                } else {
                    response.setCodigo(409);
                    response.setMensajeUsuario("Formato de cuenta invalido");
                    log.warn("::::::::::::::::::::Formato de cuenta invalido:::::::::::::::::::::");
                }
            } else {
                response.setCodigo(404);
                response.setMensajeUsuario("Socio no existe");
                log.warn(":::::::::::::Usuario no existe::::::::::::::::::");
            }
        } catch (Exception e) {
            log.error(":::::::::::::::Error al procesar SPEI:::::::::" + e.getMessage());
        }
        return response;
    }

    public ResponseActualizacionSpei actualizarOrden(SpeiActualizacion orden) {
        ResponseActualizacionSpei response = new ResponseActualizacionSpei();
        log.info("::::::::::::::::::Actualizando orden spei::::::::::::::::::::::" + orden);
        try {
            SpeiDispersion spei = speiService.buscarPorId(String.valueOf(orden.getFolioAutorizacion()));
            if (spei != null) {
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                spei.setEstadotransaccion(orden.getEstadoTransaccion());
                spei.setFechaactualizacion(timestamp);
                spei.setFolioautorizacion(orden.getFolioAutorizacion());
                spei.setClaverastreo(orden.getClaveRastreoSpei());
                spei.setFoliosolicitante(String.valueOf(orden.getFolioSolicitante()));
                if (spei.getEstadotransaccion().toUpperCase().indexOf("APLICADA") != 0) {
                    log.info("::::::::::::Generando poliza ajuste::::::::::::::::::::::::");
                    Transferencia tx = transferenciaService.buscarPorId(spei.getFolioautorizacion());
                    Opa opa = herramientasUtil.opa(tx.getCuentaorigen());
                    log.info("" + opa);
                    AuxiliarPK aPk = new AuxiliarPK(opa.getIdorigenp(), opa.getIdproducto(), opa.getIdauxiliar());
                    Auxiliar a = auxiliarService.buscarPorId(aPk);
                    TablaPK tb_pk = new TablaPK(idtablaspei, "usuario_dispersion");
                    Tabla tb_usuario_spei = tablaService.buscarPorId(tb_pk);

                    String sai_auxiliar = funcionService.sai_auxiliar(aPk);
                    MovimientoPaso movimientoPaso = new MovimientoPaso();
                    tb_pk = new TablaPK(idtablaspei, "cuenta_comision_dispersion");
                    Tabla cuenta_comision = tablaService.buscarPorId(tb_pk);
                    //Preparando movimiento abono
                    MovimientoPasoPK pk_paso = new MovimientoPasoPK(timestamp, Integer.parseInt(tb_usuario_spei.getDato1()), funcionService.sesion(),
                            String.valueOf(orden.getFolioSolicitante()),
                            a.getPk().getIdorigenp(),
                            a.getPk().getIdproducto(),
                            a.getPk().getIdauxiliar());
                    movimientoPaso.setPk(pk_paso);
                    movimientoPaso.setIdorigen(a.getIdorigen());
                    movimientoPaso.setIdgrupo(a.getIdgrupo());
                    movimientoPaso.setIdsocio(a.getIdsocio());
                    movimientoPaso.setCargoabono(1);
                    movimientoPaso.setMonto(tx.getMonto() + tx.getComision());
                    movimientoPaso.setSai_aux(sai_auxiliar);
                    movimientoPaso.setIdordenspei(Integer.parseInt(tx.getFoliosolicitante().substring(0, 6)));
                    movimientoPaso.setSpei_cancelado(true);
                    MovimientoPaso movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                    //Preparando movimiento cargo
                    pk_paso = new MovimientoPasoPK(timestamp, Integer.parseInt(tb_usuario_spei.getDato1()), funcionService.sesion(),
                            String.valueOf(orden.getFolioSolicitante()),
                            0,
                            0,
                            0);
                    movimientoPaso.setPk(pk_paso);
                    movimientoPaso.setIdorigen(a.getIdorigen());
                    movimientoPaso.setIdgrupo(a.getIdgrupo());
                    movimientoPaso.setIdsocio(a.getIdsocio());
                    movimientoPaso.setIdcuenta(cuenta_comision.getDato1());
                    movimientoPaso.setCargoabono(0);
                    movimientoPaso.setMonto(tx.getMonto());
                    movimientoPaso.setSai_aux(sai_auxiliar);
                    movimientoPaso.setIdordenspei(Integer.parseInt(tx.getFoliosolicitante().substring(0, 6)));
                    movimientoPaso.setSpei_cancelado(true);
                    movimientoPasoRegistro = pasoService.guardar(movimientoPaso);


                    //Preparando movimiento cargo-- comision
                    pk_paso = new MovimientoPasoPK(timestamp, Integer.parseInt(tb_usuario_spei.getDato1()), funcionService.sesion(),
                            String.valueOf(orden.getFolioSolicitante()),
                            0,
                            1,
                            0);
                    movimientoPaso.setPk(pk_paso);
                    movimientoPaso.setIdorigen(a.getIdorigen());
                    movimientoPaso.setIdgrupo(a.getIdgrupo());
                    movimientoPaso.setIdsocio(a.getIdsocio());
                    movimientoPaso.setIdcuenta(cuenta_comision.getDato1());
                    movimientoPaso.setCargoabono(0);
                    movimientoPaso.setMonto(tx.getComision());
                    movimientoPaso.setSai_aux(sai_auxiliar);
                    movimientoPaso.setIdordenspei(Integer.parseInt(tx.getFoliosolicitante().substring(0, 6)));
                    movimientoPaso.setSpei_cancelado(true);
                    movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                    String procesados = funcionService.aplica_transaccion(movimientoPasoRegistro.getPk());
                    if (Integer.parseInt(procesados) > 0) {
                        funcionService.eliminarRegistrosProcesados(pk_paso);
                        AuxiliarD ad = auxiliarDService.buscarUltimoMovimiento(aPk, Integer.parseInt(tb_usuario_spei.getDato1()), 3);
                        spei.setPoliza_ajuste(String.valueOf(ad.getIdorigenc()) + "-" + ad.getPeriodo() + "-" + String.valueOf(ad.getIdtipo()) + "-" + ad.getIdpoliza());
                        response.setMensaje("Orden ejecutada con exito");
                        response.setCodigo(200);
                        speiService.guardar(spei);
                    }
                } else {
                    speiService.guardar(spei);
                    response.setMensaje("Orden ejecutada con exito");
                    log.info(":::::::::::::::::::::Orden actualizada con exito:::::::::::::::::::::");
                    response.setCodigo(200);
                }
            } else {
                response.setCodigo(409);
                response.setMensaje("Error no existe la orden que intenta actualizar");
                log.warn("::::::::::::::::No existe la orden que intenta actualiza::::::::::::::::::::::");
            }
        } catch (Exception e) {
            log.error("::::::::::::::.Error al actualizar orden spei:::::::::::::::::::::" + e.getMessage());
        }
        return response;
    }

    public ResponseTransferencia sendAbono(RequestTransferenciaSpei orden) {
        log.info(":::::::::::::::::::::::::::::Peticion sendAbono:::::::::::::::::::::::::::::" + orden);
        ResponseTransferencia response = new ResponseTransferencia();
        SpeiAbono abono = new SpeiAbono();
        try {
            ClabeInterbancaria clabe = clabeInterbancariaService.buscarPorClabe(orden.getBeneficiarioSpei().getClabe());
            if (clabe != null) {
                abono.setFechasolicitud(orden.getRegistro().getFechaSolicitud());
                abono.setClaverastreospei(orden.getClaveRastreoSpei());
                abono.setDescripcion(orden.getDescripcion());
                abono.setClabeordenante(orden.getOrdenanteSpei().getClabe());
                abono.setClabebeneficiario(orden.getBeneficiarioSpei().getClabe());
                abono.setNombrebeneficiario(orden.getBeneficiarioSpei().getNombre());
                abono.setMonto(orden.getMontoTransaccion().getImporte());
                abono.setFoliosolicitante(String.valueOf(orden.getFolioSolicitante()));
                abono.setReferencianumerica(orden.getOrdenanteSpei().getReferenciaNumerica());
                abono.setComision(orden.getMontoTransaccion().getComision());

                abono = speiService.guardar(abono);
                Auxiliar a = auxiliarService.buscarPorId(clabe.getPk());
                if (a != null) {
                    if (a.getEstatus() == 2) {
                        TablaPK tb_pk = new TablaPK(idtablaspei, "usuario_abono");
                        Tabla tb_usuario_spei = tablaService.buscarPorId(tb_pk);
                        String sai_auxiliar = funcionService.sai_auxiliar(a.getPk());
                        SpeiAbonoPaso movimientoPaso = new SpeiAbonoPaso();

                        //Abono a folio auxiliar
                        AbonoSpeiPK pk_paso = new AbonoSpeiPK(
                                Integer.parseInt(tb_usuario_spei.getDato1()),
                                funcionService.sesion(),
                                orden.getOrdenanteSpei().getReferenciaNumerica(), 1);
                        movimientoPaso.setPk(pk_paso);
                        movimientoPaso.setIdorigen(a.getIdorigen());
                        movimientoPaso.setIdgrupo(a.getIdgrupo());
                        movimientoPaso.setIdsocio(a.getIdsocio());
                        movimientoPaso.setEsentrada(true);
                        movimientoPaso.setIdorigenp(a.getPk().getIdorigenp());
                        movimientoPaso.setIdproducto(a.getPk().getIdproducto());
                        movimientoPaso.setIdauxiliar(a.getPk().getIdauxiliar());
                        movimientoPaso.setAcapital(orden.getMontoTransaccion().getImporte());
                        movimientoPaso.setTipopoliza(1);
                        movimientoPaso.setSai_aux(sai_auxiliar);
                        SpeiAbonoPaso movimientoPasoRegistro = pasoService.guardar(movimientoPaso);


                        //Cargo a cuenta contable par balanceo
                        pk_paso = new AbonoSpeiPK(
                                Integer.parseInt(tb_usuario_spei.getDato1()),
                                funcionService.sesion(),
                                orden.getOrdenanteSpei().getReferenciaNumerica(), 2);

                        tb_pk = new TablaPK(idtablaspei, "cuenta_abono");
                        Tabla cuenta_abono = tablaService.buscarPorId(tb_pk);
                        movimientoPaso = new SpeiAbonoPaso();
                        movimientoPaso.setPk(pk_paso);
                        movimientoPaso.setIdcuenta(cuenta_abono.getDato1());
                        movimientoPaso.setIdorigen(a.getIdorigen());
                        movimientoPaso.setIdgrupo(a.getIdgrupo());
                        movimientoPaso.setIdsocio(a.getIdsocio());
                        movimientoPaso.setEsentrada(false);
                        movimientoPaso.setAcapital(orden.getMontoTransaccion().getImporte());
                        movimientoPaso.setTipopoliza(1);

                        movimientoPasoRegistro = pasoService.guardar(movimientoPaso);

                        Integer movs_aplicados = funcionService.aplica_transaccion_spei(
                                movimientoPaso.getPk().getIdusuario(),
                                movimientoPaso.getPk().getSesion(),
                                1,
                                movimientoPaso.getPk().getReferencia());

                        AuxiliarD ultimo_movimiento = null;
                        if (movs_aplicados > 0) {
                            log.info("Total aplicados:" + movs_aplicados);
                            pasoService.eliminar(movimientoPasoRegistro);

                            Date date1 = new Date();
                            Timestamp timestamp1 = new Timestamp(date1.getTime());
                            abono.setFechaejecucion(timestamp1);

                            //Si los movimientos anteriores se aplicaron generamos comision de poliza
                            //Cargo a folio auxiliar
                            if (orden.getMontoTransaccion().getComision() > 0) {
                                pk_paso = new AbonoSpeiPK(
                                        Integer.parseInt(tb_usuario_spei.getDato1()),
                                        funcionService.sesion(),
                                        orden.getOrdenanteSpei().getReferenciaNumerica(), 3);

                                movimientoPaso.setPk(pk_paso);
                                movimientoPaso.setIdorigen(a.getIdorigen());
                                movimientoPaso.setIdgrupo(a.getIdgrupo());
                                movimientoPaso.setIdsocio(a.getIdsocio());
                                movimientoPaso.setEsentrada(false);
                                movimientoPaso.setIdorigenp(a.getPk().getIdorigenp());
                                movimientoPaso.setIdproducto(a.getPk().getIdproducto());
                                movimientoPaso.setIdauxiliar(a.getPk().getIdauxiliar());
                                movimientoPaso.setAcapital(orden.getMontoTransaccion().getComision());
                                movimientoPaso.setTipopoliza(3);
                                movimientoPaso.setSai_aux(sai_auxiliar);
                                movimientoPasoRegistro = pasoService.guardar(movimientoPaso);


                                movimientoPaso = new SpeiAbonoPaso();
                                pk_paso = new AbonoSpeiPK(
                                        Integer.parseInt(tb_usuario_spei.getDato1()),
                                        funcionService.sesion(),
                                        orden.getOrdenanteSpei().getReferenciaNumerica(), 4);

                                tb_pk = new TablaPK(idtablaspei, "cuenta_comision_abono");
                                cuenta_abono = tablaService.buscarPorId(tb_pk);
                                movimientoPaso.setPk(pk_paso);
                                movimientoPaso.setIdcuenta(cuenta_abono.getDato1());
                                movimientoPaso.setIdorigen(a.getIdorigen());
                                movimientoPaso.setIdgrupo(a.getIdgrupo());
                                movimientoPaso.setIdsocio(a.getIdsocio());
                                movimientoPaso.setEsentrada(true);
                                movimientoPaso.setAcapital(orden.getMontoTransaccion().getComision());
                                movimientoPaso.setTipopoliza(3);
                                movimientoPasoRegistro = pasoService.guardar(movimientoPaso);


                                movs_aplicados = funcionService.aplica_transaccion_spei(
                                        movimientoPaso.getPk().getIdusuario().intValue(),
                                        movimientoPaso.getPk().getSesion(),
                                        3,
                                        movimientoPaso.getPk().getReferencia());
                                pasoService.eliminar(movimientoPasoRegistro);
                                ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(a.getPk(), Integer.parseInt(tb_usuario_spei.getDato1()), 1);
                                abono.setPolizacomision(String.format("%06d", ultimo_movimiento.getIdorigenc()) + "-" + String.format("%06d", Integer.parseInt(ultimo_movimiento.getPeriodo())) + "-" + String.valueOf(ultimo_movimiento.getIdtipo()) + "-" + String.format("%06d", ultimo_movimiento.getIdpoliza()));
                            }

                            ultimo_movimiento = auxiliarDService.buscarUltimoMovimiento(a.getPk(), Integer.parseInt(tb_usuario_spei.getDato1()), 1);
                            abono.setAceptada(true);
                            abono.setPolizaabono(String.format("%06d", ultimo_movimiento.getIdorigenc()) + "-" + String.format("%06d", Integer.parseInt(ultimo_movimiento.getPeriodo())) + "-" + String.valueOf(ultimo_movimiento.getIdtipo()) + "-" + String.format("%06d", ultimo_movimiento.getIdpoliza()));
                            response.setFolioAutorizacion(String.format("%06d", ultimo_movimiento.getIdorigenc()) + String.format("%06d", Integer.parseInt(ultimo_movimiento.getPeriodo())) + String.valueOf(ultimo_movimiento.getIdtipo()) + String.format("%06d", ultimo_movimiento.getIdpoliza()));
                            RegistroTransaccion registro = new RegistroTransaccion();
                            registro.setFechaRegistro(orden.getRegistro().getFechaSolicitud());
                            response.setRegistro(registro);
                            response.setCodigo(200);
                            response.setMensajeUsuario("Exitoso");
                            speiService.guardar(abono);
                        }
                    } else {
                        response.setCodigo(409);
                        response.setMensajeUsuario("Folio auxiliar inactivo");
                        log.warn("::::::::::::::::::::Folio auxiliar inactivo::::::::::::::::");
                    }
                } else {
                    response.setCodigo(409);
                    response.setMensajeUsuario("No existe folio relacionado a clabe STP");
                    log.warn("::::::::::::::::::::::No existe folio relacionado a clabe STP::::::::::::::::::::::");
                }
            } else {
                response.setCodigo(404);
                response.setMensajeUsuario("No existen registros para clabe:" + orden.getBeneficiarioSpei().getClabe());
                log.warn("::::::::::::::::::::No existen registros para clabe:" + orden.getBeneficiarioSpei().getClabe() + "::::::::::::::::::::::::");
            }
        } catch (Exception e) {
            log.error("::::::::::::::.Error al actualizar orden spei:::::::::::::::::::::" + e.getMessage());
        }
        return response;
    }
}
