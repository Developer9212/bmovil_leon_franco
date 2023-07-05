package com.fenoreste.alianza_stp;

import lombok.Data;

@Data
public class tokenModel {    
	private String access_token;
	private String token_type;
	private String expires_in;
	private String scope;
	
}
