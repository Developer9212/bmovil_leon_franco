package com.fenoreste;

import java.time.ZoneId;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BancaLeonFrancoApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.systemDefault()));
		SpringApplication.run(BancaLeonFrancoApplication.class, args);
	}

}
