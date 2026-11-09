package co.edu.usco.pw.parcial_consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
				.authorizeHttpRequests(auth -> auth
						.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
						.requestMatchers("/login", "/403", "/error").permitAll()
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
						.requestMatchers("/consultas/nueva", "/consultas/guardar", "/consultas/editar/**",
								"/consultas/actualizar/**", "/consultas/eliminar/**")
						.hasAuthority("ADMINISTRADOR")
						.requestMatchers("/medicos", "/medicos/**").hasAuthority("ADMINISTRADOR")
						.requestMatchers("/consultas/horario/**").hasAuthority("PACIENTE")
						.requestMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ADMINISTRADOR", "MEDICO", "PACIENTE")
						.requestMatchers(HttpMethod.PUT, "/api/consultas/*/horario").hasAuthority("PACIENTE")
						.requestMatchers("/api/**").hasAuthority("ADMINISTRADOR")
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/consultas", true).permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
				.exceptionHandling(ex -> ex.accessDeniedHandler((request, response, e) -> {
					if (request.getRequestURI().startsWith(request.getContextPath() + "/api")) {
						response.setStatus(403);
						response.setContentType("text/plain;charset=UTF-8");
						response.getWriter().write("No tiene permisos para realizar esta accion con su rol");
					} else {
						response.sendRedirect(request.getContextPath() + "/403");
					}
				}))
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
