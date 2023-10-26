package com.sistema.solicitudes.sgs;

import com.sistema.solicitudes.sgs.entities.Dependencia;
import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.Usuario;
import com.sistema.solicitudes.sgs.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SgsApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SgsApplication.class, args);
	}

}
