package co.edu.usco.pw.parcial_consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("API de Consultas Medicas").version("1.0.0")
						.description("API para gestionar las consultas medicas de una clinica. "
								+ "ADMINISTRADOR puede crear, editar y eliminar medicos y consultas. "
								+ "MEDICO solo ve las consultas asignadas a el. "
								+ "PACIENTE ve sus citas y solo puede cambiar el horario de ellas. "
								+ "Para probar, presione Authorize e ingrese usuario y contrasena.")
						.contact(new Contact().name("Santiago Valencia")))
				.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
				.components(new Components().addSecuritySchemes("basicAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
	}

}
