package org.github.bbaron.springcfg.example.webapp.config;

import org.springframework.context.annotation.PropertySource;

@ApplicationConfiguration
@PropertySource({ "classpath:META-INF/spring/database.properties", "classpath:META-INF/spring/hibernate.properties" })
public class MainCfg {

}
