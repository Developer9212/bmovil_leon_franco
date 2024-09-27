DROP TABLE IF EXISTS users;
CREATE TABLE users(id integer,username varchar(45),password text,create_at date);


 /*PRODUCTOS QUE OPERAN BANCA MOVIL*/
DROP table IF EXISTS productos_banca_movil;
CREATE table productos_banca_movil(
   idproducto integer,
   tipoproducto integer,
   nombreproducto text,
   consulta boolean default true,
   deposito boolean default true,
   retiro boolean default true,
   primary key(idproducto)

);

INSERT INTO productos_banca_movil VALUES(131,0,'AHORRO CUENTA CONFIANZA',True,True,False);
INSERT INTO productos_banca_movil VALUES(130,0,'AHORRO CUENTA CORRIENTE',True,True,False);
INSERT INTO productos_banca_movil VALUES(170,0,'AHORRO JOVEN',True,True,False);
INSERT INTO productos_banca_movil VALUES(111,0,'AHORRO ADICIONAL',True,True,False);
INSERT INTO productos_banca_movil VALUES(202,1,'Deposito a plazo 90 dias',True,False,False);
INSERT INTO productos_banca_movil VALUES(203,1,'Deposito a plazo fijo 180 dias',True,False,False);
INSERT INTO productos_banca_movil VALUES(201,1,'Deposito a Plazo 60 dias',True,False,False);
INSERT INTO productos_banca_movil VALUES(200,1,'Deposito a plazo 30 dias',True,False,False);
INSERT INTO productos_banca_movil VALUES(31603,2,'PRESTAMO MAXIMO VIVIENDA',True,True,False);
INSERT INTO productos_banca_movil VALUES(31702,2,'PRESTAMO 31702 GAR',True,True,False);
INSERT INTO productos_banca_movil VALUES(31802,2,'PRESTAMO 31802 GAR',True,True,False);
INSERT INTO productos_banca_movil VALUES(31502,2,'PRESTAMO 31502 GAR',True,True,False);
INSERT INTO productos_banca_movil VALUES(31602,2,'PRESTAMO 31602 GAR',True,True,False);
INSERT INTO productos_banca_movil VALUES(32302,2,'CREDI-ECO',True,True,False);
INSERT INTO productos_banca_movil VALUES(30000,2,'PRESTAMO PARA PRUEBAS',True,True,False);
INSERT INTO productos_banca_movil VALUES(30312,2,'PRESTAMO AUTOMOVIL ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30412,2,'PRESTAMO CONFIANZA ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30512,2,'PRESTAMO ADICIONAL ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31523,2,'PRESTAMO ORDINARIO VIVIENDA ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30522,2,'PRESTAMO ADICIONAL ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31613,2,'PRESTAMO MAXIMO VIVIENDA ( Renovado ) ',True,True,False);
INSERT INTO productos_banca_movil VALUES(31902,2,'PRESTAMO MOTOCICLETA',True,True,False);
INSERT INTO productos_banca_movil VALUES(31112,2,'PRESTAMO ADICIONAL COMPUTADORA ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31212,2,'PRESTAMO PREAUTORIZADO ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31312,2,'PRESTAMO PREAUTORIZADO SIN GAR ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31503,2,'PRESTAMO ORDINARIO VIVIENDA',True,True,False);
INSERT INTO productos_banca_movil VALUES(30102,2,'ORDINARIO INSOLUTO ( Normal ) Consumo',True,True,False);
INSERT INTO productos_banca_movil VALUES(31322,2,'PRESTAMO PREAUTORIZADO SIN GAR ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31623,2,'PRESTAMO MAXIMO VIVIENDA ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30812,2,'PRESTAMO HIPOTECARIO SOBRE SAL ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30212,2,'PREAUTORIZADO CREDI CUMPLIDO ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31122,2,'PRESTAMO ADICIONAL COMPUTADORA ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31222,2,'PRESTAMO PREAUTORIZADO ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30612,2,'PRESTAMO MAXIMO ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30622,2,'PRESTAMO MAXIMO ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30722,2,'CREDITARJETA ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31002,2,'PRESTAMO ORDINARIO',True,True,False);
INSERT INTO productos_banca_movil VALUES(31713,2,'PRESTAMO HIPOTECARIO VIVIENDA ( Renovado ) ',True,True,False);
INSERT INTO productos_banca_movil VALUES(31723,2,'PRESTAMO HIPOTECARIO VIVIENDA ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30712,2,'CREDITARJETA ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30912,2,'PRESTAMO MAXIMO ESPECIAL ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31012,2,'PRESTAMO ORDINARIO ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30922,2,'PRESTAMO MAXIMO ESPECIAL ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31022,2,'PRESTAMO ORDINARIO ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30222,2,'PREAUTORIZADO CREDI CUMPLIDO ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30322,2,'PRESTAMO AUTOMOVIL ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30422,2,'PRESTAMO CONFIANZA ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(30122,2,'ORDINARIO INSOLUTO ( Reestructurado ) Consumo',True,True,False);
INSERT INTO productos_banca_movil VALUES(30822,2,'PRESTAMO HIPOTECARIO SOBRE SAL ( Reestructurado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31412,2,'PRESTAMO ADICIONAL ESCOLAR ( Renovado )',True,True,False);
INSERT INTO productos_banca_movil VALUES(31513,2,'PRESTAMO ORDINARIO VIVIENDA ( Renovado ) ',True,True,False);
INSERT INTO productos_banca_movil VALUES(30112,2,'ORDINARIO INSOLUTO ( Renovado ) Consumo',True,True,False);
INSERT INTO productos_banca_movil VALUES(31102,2,'PRESTAMO ADICIONAL COMPUTADORA',True,True,False);
INSERT INTO productos_banca_movil VALUES(31202,2,'PRESTAMO PREAUTORIZADO',True,True,False);
INSERT INTO productos_banca_movil VALUES(33102,2,'CREDITO REVOLVENTE',True,True,False);
INSERT INTO productos_banca_movil VALUES(31703,2,'PRESTAMO HIPOTECARIO SOBRE SAL VIVIENDA',True,True,False);
INSERT INTO productos_banca_movil VALUES(30802,2,'PRESTAMO HIPOTECARIO SOBRE SAL',True,True,False);
INSERT INTO productos_banca_movil VALUES(32102,2,'PRESTAMO CREDINOMINA',True,True,False);
INSERT INTO productos_banca_movil VALUES(30702,2,'CREDITARJETA',True,True,False);
INSERT INTO productos_banca_movil VALUES(30202,2,'PREAUTORIZADO CREDI CUMPLIDO',True,True,False);
INSERT INTO productos_banca_movil VALUES(31302,2,'PRESTAMO PREAUTORIZADO SIN GAR',True,True,False);
INSERT INTO productos_banca_movil VALUES(30302,2,'PRESTAMO AUTOMOVIL',True,True,False);
INSERT INTO productos_banca_movil VALUES(30402,2,'PRESTAMO CONFIANZA',True,True,False);
INSERT INTO productos_banca_movil VALUES(31402,2,'PRESTAMO ADICIONAL ESCOLAR',True,True,False);
INSERT INTO productos_banca_movil VALUES(30602,2,'PRESTAMO MAXIMO',True,True,False);
INSERT INTO productos_banca_movil VALUES(30902,2,'PRESTAMO MAXIMO ESPECIAL',True,True,False);
INSERT INTO productos_banca_movil VALUES(32202,2,'CREDIJOVEN',True,True,False);
INSERT INTO productos_banca_movil VALUES(31612,2,'PRESTAMO APOYO COVID-19',True,True,False);
INSERT INTO productos_banca_movil VALUES(30502,2,'PRESTAMO ADICIONAL',True,True,False);
INSERT INTO productos_banca_movil VALUES(110,0,'AHORRO ADULTO',True,True,True);




DROP TABLE IF EXISTS transferencias_banca_movil;
CREATE TABLE transferencias_banca_movil(
   
   foliosolicitante text,
   fecha timestamp with time zone,
   socioorigen text,
   cuentaorigen text,
   sociodestino text,
   cuentadestino text,
   monto numeric,
   comision numeric default 0.0,
   polizacreada text,
   esspei  boolean default false,
   folioautorizacion text,

   primary key(foliosolicitante,polizacreada)

);

DROP TABLE IF EXISTS transferencias_spei_dispersion;
CREATE TABLE transferencias_spei_dispersion(
     foliosolicitante text,
     folioautorizacion text,
     claverastreo      text,
     estadotransaccion text,
     fechaactualizacion timestamp with time zone,
     poliza_ajuste  text,

     primary key(foliosolicitante)
);


DROP TABLE IF EXISTS transferencias_spei_abono;
CREATE TABLE transferencias_spei_abono(
     foliosolicitante text,
     claverastreospei    text,
     ordenante text,
     descripcion text,
     clabeordenante text,
     referencianumerica text,
     referenciacobranza text,
     clabebeneficiario text,
     nombrebeneficiario text,
     monto numeric,
     comision numeric, 
     fechasolicitud text,
     fechaejecucion timestamp with time zone,
     aceptada boolean,
     polizaabono text,
     polizacomision text,

     primary key(claverastreospei)
);

DROP TABLE IF EXISTS ws_clabe_respuesta_alianza;
CREATE TABLE ws_clabe_respuesta_alianza(
     idoperacion       integer                     ,
     descripcion       character varying(45)       ,
     fecha             timestamp without time zone ,
     clabe             character varying(45)       ,
     idorigenp         integer                     ,
     idproducto        integer                     ,
     idauxiliar        integer                     ,
     codigohttp        integer                     ,
     mensajerespuesta  text                        

);


https://www.sat.gob.mx/consultas/93266/seguimiento-de-tramites-y-requerimientos#


Correo Francisco = refrazul@gmail.com

keytool -genkeypair -alias miAlias -keyalg RSA -keysize 2048 -keystore fenoreste.jks -dname "CN=Fenoreste, OU=Caja Buenos Aires, O=Caja Buenos Aires, L=Guadalupe, ST=Nuevo Leon, C=MX" -validity 730


<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header>
      <wsse:Security soap:mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
         <wsse:BinarySecurityToken EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" wsu:Id="9BBB3CCF54D2857CAA171640971428725">MIIDpTCCAo2gAwIBAgIEb4GthTANBgkqhkiG9w0BAQsFADCBgjELMAkGA1UEBhMCTVgxEzARBgNVBAgTCk51ZXZvIExlb24xEjAQBgNVBAcTCUd1YWRhbHVwZTEaMBgGA1UEChMRQ2FqYSBCdWVub3MgQWlyZXMxGjAYBgNVBAsTEUNhamEgQnVlbm9zIEFpcmVzMRIwEAYDVQQDEwlGZW5vcmVzdGUwHhcNMjQwNTIyMTgyMzAyWhcNMjYwNTIyMTgyMzAyWjCBgjELMAkGA1UEBhMCTVgxEzARBgNVBAgTCk51ZXZvIExlb24xEjAQBgNVBAcTCUd1YWRhbHVwZTEaMBgGA1UEChMRQ2FqYSBCdWVub3MgQWlyZXMxGjAYBgNVBAsTEUNhamEgQnVlbm9zIEFpcmVzMRIwEAYDVQQDEwlGZW5vcmVzdGUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCituHlEfCTD4BD4pNnPtNPP5haY4Jity04Iu7Ruh4bw5tO+J088g5C3brUIiJAAqOXMN1NP+43kqkzv/H3DYXlTVUT5m7dxGBAZJjAwbG7+mMRu1go0J20eahsIHUgprHMIkn0+119j0ad3v8HMjMgXeauGAPtdnfjcfa686baB7XXhlYGFBTRDbCjQD9764BNFlTGdHAorLbxZXOofQBWvFHvpNofIeST9Fgo00gZsZlelKTFB6gIKbwzy1qorjyYLw0VpcxFXnFd3qOmUpey9tT64ZFUhepRWzNJ5EaEueyvb2fzzOwuYKaIPqzoYyiNn6z0DHgTDDHa1HsJTSU7AgMBAAGjITAfMB0GA1UdDgQWBBSYKKWVHkDLhBrLZ7DbNWKtCNtqpjANBgkqhkiG9w0BAQsFAAOCAQEAl7wrDEolg45yqgp7HPu+AoX+9yb5SwIB9ymrueCCbhF1JKURI0sQ2bnRc42sYH/o8Rryiptqi4m+NheK0yee/frOckrVhfUlfICmTHo4LEVbjMghuCCe0xKJIG7+yMsfIm5VQ5J38Lp7lyqXopaK3QzvgxQBuCOjf2WJApoNRy1fBR9Mu5n0d5e08bny/rsOF9LOQfQPYfGPSDxUPk1se54XE1c88u2mU9ujIexvhfiAx6WtTYQwNbirlZxnPK2X/T1X2d4n+gf5l8dTHPamclECPUSUyTSCHWTfqqcb31mM+RWz31cKjBewUE8g1c4wS5f/TEcxsmmPN888HAgL+Q==</wsse:BinarySecurityToken>
         <xenc:EncryptedKey Id="EK-9BBB3CCF54D2857CAA171640971428724" xmlns:xenc="http://www.w3.org/2001/04/xmlenc#">
            <xenc:EncryptionMethod Algorithm="http://www.w3.org/2001/04/xmlenc#rsa-1_5"/>
            <ds:KeyInfo xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
               <wsse:SecurityTokenReference>
                  <wsse:Reference URI="#9BBB3CCF54D2857CAA171640971428725" ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3"/>
               </wsse:SecurityTokenReference>
            </ds:KeyInfo>
            <xenc:CipherData>
               <xenc:CipherValue>NqcXcs1CBquhUWHmX+BcZvcfYPXtG6vRKyDEfc+6Thw4PfmcToXJz3JpRxRCQPTfilGc8IFpJVFo94xoxctTYyegQJ9nVXWzHsRs4RC9J2R7avSyrWG17iljNixkhkXzMQzd36iqhqFAhfPcr/JpabWoK1jD7j6NTFewqeVtNglY/+fi4CtscWCDR1iWlb6MB/NNZXNXioxb9unP0RUmUcJTVaRSyfydQfaME4Dixtt35tuGwd1XjPdqGeSXtoueNGW7yP3qd22S3R7TKmp1RmNrJEoWinXU47kqVSkPSjtYkS6iUCAVaVGoqxO/O/J2hfDkxBSyvme7vIUKSiJ7iw==</xenc:CipherValue>
            </xenc:CipherData>
            <xenc:ReferenceList>
               <xenc:DataReference URI="#ED-15"/>
            </xenc:ReferenceList>
         </xenc:EncryptedKey>
         <wsse:BinarySecurityToken EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" wsu:Id="X509-9BBB3CCF54D2857CAA171640971428121">MIIDYjCCAkqgAwIBAgIEZacZsDANBgkqhkiG9w0BAQUFADBzMQswCQYDVQQGEwJNWDEPMA0GA1UECBMGTUVYSUNPMRcwFQYDVQQHEw5BbHZlcm8gT2JyZWdvbjEMMAoGA1UEChMDQXBwMRYwFAYDVQQLEw1UcmFuc2FjY2lvbmVzMRQwEgYDVQQDEwtXZWJzZXJ2aWNlczAeFw0yNDAxMTcwMDA1MDRaFw0yOTAxMTUwMDA1MDRaMHMxCzAJBgNVBAYTAk1YMQ8wDQYDVQQIEwZNRVhJQ08xFzAVBgNVBAcTDkFsdmVybyBPYnJlZ29uMQwwCgYDVQQKEwNBcHAxFjAUBgNVBAsTDVRyYW5zYWNjaW9uZXMxFDASBgNVBAMTC1dlYnNlcnZpY2VzMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqOYgO3ctLiMt8xEaT9QL+ynP7zLTvSK7VCwCI92d1yLbN3ZWNlIZq4nXsT5zp1SIhp9xgkXcFP1IXbBCpvygSZ5b0MXz7Kvw2Q2+IayOx0F6MTLRCGCV4FaqCLPpR0In8XmmRkm6tjBe22zk+cST3C5sSVWfGkU+qNWMRpYlJet+gnj0IILUhGOxz8PMdfGmbskq2raO/r2fux1OUFWYQ1oYWGbrXKc2hWibyT9edfgCq2H1ZdB+jZa1c5KQyuU5O0bqEZ4ri2OGQsp8b128AJ1a2IOhMchrt7u3pptzPZKn8gH0EyxmszLG+RlhTKTRWWCHg9QBv5OspdOflOa/QQIDAQABMA0GCSqGSIb3DQEBBQUAA4IBAQAa7Xca36fF+TWebWqCS6BIoXjAAVBHVkW6BILZKmjHMvvE8p29CsckQ6MVjf2KGxhn1h4y/H2o4Nj+uWnWRXH5HSIfrwkzE92mY7RJOhNxG3EZW9AVwVqD3/ACWcEvWQSfIFMMMrKBrm5sX62X3Jnd31R+s/QEeLkJpX9nXf2v7hfHqfuHq/BMh0qndDAvJbOR9ccO/XXlhNtbk6KiRe6ds69SvrGdD22njC/JyXphz0fsNKhrfhjJcHh+0RGRQBtZWWeZVRhzfkagtQ7n6Fe6LKIH00pmNA2wrD/ZNHhug0R/YWaEg6IK/50zjh9Y2yuYtTG2JCtYbuSkNCu2CpET</wsse:BinarySecurityToken>
         <ds:Signature Id="SIG-14" xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
            <ds:SignedInfo>
               <ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#">
                  <ec:InclusiveNamespaces PrefixList="soap" xmlns:ec="http://www.w3.org/2001/10/xml-exc-c14n#"/>
               </ds:CanonicalizationMethod>
               <ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/>
               <ds:Reference URI="#id-13">
                  <ds:Transforms>
                     <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#">
                        <ec:InclusiveNamespaces PrefixList="" xmlns:ec="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                     </ds:Transform>
                  </ds:Transforms>
                  <ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
                  <ds:DigestValue>YFFMXZkgSrH2aALMoamKE0CeRs8=</ds:DigestValue>
               </ds:Reference>
            </ds:SignedInfo>
            <ds:SignatureValue>CblQ/wzMNT37+5q8rhRAJUsyis59LbpHyJvOz/Wk75siRIxT4cJ/Kztn/M0IJJ5S3ZJS0M8CGRsp
FO3RG1oyVdL8Te5hrHrWWJ6BTOy5UT0zal+iOVSYUCYKdPDaqvO5O+GLyfqqOvSt1MS0QAX06rW0
cDASDM6AoIQXh9i3FsA4b+zhC7cPBfX18fddYferKkJ1QGNi1ij8LBtDuRT0/oMo91W1LtXAqQf8
wyxrY+MBKbDypmehuoH27WvYLxfdSVvq6UkXdAFNZb3XZW5PPV5MFqCMca49m6M6eupMmU6VJt5d
uVhV/IpX/hWknkEIojg6thp7qjdPuRJvbbt+ww==</ds:SignatureValue>
            <ds:KeyInfo Id="KI-9BBB3CCF54D2857CAA171640971428122">
               <wsse:SecurityTokenReference wsu:Id="STR-9BBB3CCF54D2857CAA171640971428123">
                  <wsse:Reference URI="#X509-9BBB3CCF54D2857CAA171640971428121" ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3"/>
               </wsse:SecurityTokenReference>
            </ds:KeyInfo>
         </ds:Signature>
      </wsse:Security>
   </soap:Header>
   <soap:Body wsu:Id="id-13" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
      <xenc:EncryptedData Id="ED-15" Type="http://www.w3.org/2001/04/xmlenc#Content" xmlns:xenc="http://www.w3.org/2001/04/xmlenc#">
         <xenc:EncryptionMethod Algorithm="http://www.w3.org/2001/04/xmlenc#tripledes-cbc"/>
         <ds:KeyInfo xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
            <wsse:SecurityTokenReference wsse11:TokenType="http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsse11="http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd">
               <wsse:Reference URI="#EK-9BBB3CCF54D2857CAA171640971428724"/>
            </wsse:SecurityTokenReference>
         </ds:KeyInfo>
         <xenc:CipherData>
            <xenc:CipherValue>3YXdV4X7kHpvNffYR5dW1XRMjFExSro181DvpNrVy30VfwMdxRyt4tcBssNyWgVdL+1L0VmTn3KL802DW04I7WmLq56GvhwGN18RS6zWUAhqOBKosINoyFhdalPBEdueoCcVUDlXFZ7tWGB3rvfOoVT/CylWYip7VNZMckXqYQEqQzQib1jbcGVwR8LZIhsffik6Jdd8lmuX7OhMMM0YoSfdWwyAvFk/yUaiv6bHgk56+POCtP3vv58dhITL9+yGb8j70w311gXouT20A61N4zr9BLl079kz39YMgdvPkIcoURp6VLDIp3tVWOvnd/gjg+lbXmxCNEPAT1rFSblTZUOansLQiwwGpRcY2rgw4BMBQwARSsFDZghT22yCU6c1UxVdCoHslqA9OgYQz7Z4jb7l9i4NDDvieB7Ev/4PFjD7BOXilNW2MtMyqRolN62gs4naPPTJOhn4lFJV0KbfaUOJWIV3C/EB42MuBHbCWH+60t3Gqok+5tysDCg/8gom</xenc:CipherValue>
         </xenc:CipherData>
      </xenc:EncryptedData>
   </soap:Body>
</soap:Envelope>