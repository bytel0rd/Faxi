package com.avenuer.faxi;

import com.avenuer.faxi.authentication.doa.AuthCredentialDoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FaxiApplication {

	@Autowired
	AuthCredentialDoa authDoa;

	public static void main(String[] args) {
		SpringApplication.run(FaxiApplication.class, args);
	}

}
