package com.fenoreste.alianza_stp;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.fenoreste.entity.Tabla;
import com.fenoreste.entity.TablaPK;
import com.fenoreste.service.ITablaService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@Slf4j
public class SpeiClient {

	@Autowired
	private ITablaService tablaService;
	
	private OkHttpClient client = new OkHttpClient().newBuilder().build();
    private MediaType mediaType = null;	
    private RequestBody body = null;
    private Request request = null;
    private Response response = null;
    
    private String host = "http://40.117.44.204";
    private String basePath = "/api/v1/cmas";
    private String tokenPath = "/oauth2/token";
    private String altaClabePath = "/clabe-personas-fisicas";
    
    
    private Gson gson = new Gson();
    private String json = null;
    private JSONObject jsonObject = null;
    
	private tokenModel tokenAuth() {
		tokenModel modeloToken = new tokenModel();
			 try {
					String auth = codificarCrendenciales();
		            mediaType= MediaType.parse("application/x-www-form-urlencoded");
					body = RequestBody.create(mediaType, "grant_type=client_credentials&scope=cmas");
					request = new Request.Builder()
					  .url(host + basePath + tokenPath)
					  .method("POST", body)
					  .addHeader("Authorization", "Basic "+ auth)
					  .addHeader("Content-Type", "application/x-www-form-urlencoded")
					  .build();
					response = client.newCall(request).execute();
					String resultado = response.body().string();
					modeloToken = gson.fromJson(resultado,tokenModel.class);
				} catch (IOException e) {
				  log.info("::::::::::::::::::::Error al conseguir token:::::::::::::::::::::::"+e.getMessage());
				}
			 
			
				
				return modeloToken;
	}
	
	
	public String[] altaClabe(personaFisicaModel modelo) {
		String[] respuesta = new String[2];
		try {
			     	mediaType = MediaType.parse("application/json");
					json = gson.toJson(modelo);
					body = RequestBody.create(mediaType, json);
					request = new Request.Builder()
					  .url(host + basePath + altaClabePath)
					  .method("POST", body)
					  .addHeader("Authorization", "Bearer "+ tokenAuth().getAccess_token())
					  .addHeader("Content-Type", "application/json")
					  .build();
					response = client.newCall(request).execute();
					respuesta[0] = String.valueOf(response.code());
					String resultado = response.body().string();
					if(response.code() != 201) {						
						log.info("Codigo http:"+response.code()+",respuesta:"+resultado);
						respuesta[1] = resultado;
					}else {
						log.info("Respuesta Alianza:"+resultado);
					}
		} catch (Exception e) {
			log.info("::::::::::::::::::Error al dar de alta clabe::::::::::::::::::::::"+e.getMessage());
		}
		return respuesta;
	}
	
	
	private String codificarCrendenciales() {
		  String cadenaCod = "";
		  try {
			  TablaPK tb_pk  = new TablaPK("banca_movil","credenciales_token");	
		      Tabla tb_token = tablaService.buscarPorId(tb_pk);
		      String cadena = tb_token.getDato1() + ":" + tb_token.getDato2();
			  byte[] credentialsBytes = cadena.getBytes();
		      byte[] encodedCredentialsBytes = Base64.encode(credentialsBytes);
		      cadenaCod = new String(encodedCredentialsBytes);  
	   	  } catch (Exception e) {
			log.info("::::::::::::::::::Error al codificar cadena::::::::::::::::::::::"+e.getMessage());
		  }
	  return cadenaCod;	    
	}
	
}
