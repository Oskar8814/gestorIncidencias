package ies.ruizgijon.gestorincidencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestorIncidenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorIncidenciasApplication.class, args);
		// Uncomment the following lines to test password encoding
		// PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// System.out.println(passwordEncoder.encode("admin123"));

	}

}
