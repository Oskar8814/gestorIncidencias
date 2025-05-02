package ies.ruizgijon.gestorincidencias.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// Configuración de autorizaciones
		http.authorizeHttpRequests(auth -> auth
				// 1) Recursos estáticos
				.requestMatchers("/css/**", "/js/**", "/img/**","/font/**").permitAll()

				// 2) Endpoints públicos
				.requestMatchers("/", "/login").permitAll()

				.requestMatchers("/usuario/**").hasRole("ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/incidencias/crearIncidencia/**").hasAnyRole("TECNICO", "ADMIN","AUXILIAR") //Al ser mas especifica que la posterior Auxiliar solo se le permite crear incidecias
				.requestMatchers("/incidencias/**").hasAnyRole("TECNICO", "ADMIN")
				.requestMatchers("/modificarContrasena/**").hasAnyRole("ADMIN","TECNICO","AUXILIAR")
				.requestMatchers("/error").permitAll()
            	.anyRequest().authenticated()
				// Revisar todas las rutas y dar los persmisos pertinentes de forma adecuada.
				// .requestMatchers("/index/**").hasAnyRole("TECNICO", "ADMIN")
				// .requestMatchers("/incidenciasProgreso/**").hasAnyRole("TECNICO", "ADMIN")
				// .requestMatchers("/incidenciasPendientes/**").hasAnyRole("TECNICO", "ADMIN")
				// .requestMatchers("/incidenciasResueltas/**").hasAnyRole("TECNICO", "ADMIN")
				// .requestMatchers("/crearIncidencia/**").hasAnyRole("TECNICO", "ADMIN", "AUXILIAR")
				// .requestMatchers("/admin/**").hasAnyRole("TECNICO", "ADMIN")

                // 6) Cualquier otra ruta que no hayas mapeado => denegada o autenticada
                //    - .denyAll() para bloquear lo que no esté contemplado
                //    - .authenticated() para requerir cualquier usuario
                // .anyRequest().permitAll()
            )

				// Configuración de login
				.formLogin(form -> form.loginPage("/") // Indica que la ruta GET "/" muestra tu plantilla login
						.usernameParameter("mail") // name del input en tu formulario
						.passwordParameter("password") // name del input password
						.defaultSuccessUrl("/incidencias/index", true) // Redirección al hacer login exitoso
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/") // A dónde se redirige tras hacer logout
						.permitAll());

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// El AuthenticationManager se necesita si vas a inyectarlo en servicios para hacer login manual
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

	    return authBuilder.build();
	}


}