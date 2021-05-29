package com.application.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({ @PropertySource("classpath:db.properties"),
	@PropertySource("classpath:email.properties") })

public class PropertiesFileRegistration {

}
