package com.pl;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.pl.controller.GroupMemberController;
import com.pl.controller.ResearchGroupController;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

@Component
@Configuration
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ResearchGroupController.class);
        register(GroupMemberController.class);
        // Registro de la clase de los CORS
        register(CORSFilter.class);
        // Propiedad para que si se produce un 404 en Jersey,
        // la peticion pase al siguiente filtro (Spring Web),
        // de esta forma conseguimos servir estáticos.
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
    
    @PostConstruct
    public void init() {
    	// Inicialización de Swagger
        this.configSwagger();
    }
    
    // Configuración de Swagger
    private void configSwagger(){
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setTitle("Gradle [Spring Boot + Spring Data + Jersey + Swagger]");
        config.setVersion("v1.0");
        config.setContact("Pablo Borrego y Manuel Lara");
        config.setSchemes(new String[] { "http", "https" });
        config.setBasePath("/");
        config.setResourcePackage("com.pl.controller");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}