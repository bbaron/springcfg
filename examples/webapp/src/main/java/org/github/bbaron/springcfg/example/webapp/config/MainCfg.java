package org.github.bbaron.springcfg.example.webapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(DataSourceCfg.class)
@ImportResource("classpath:META-INF/spring/applicationContext-jpa.xml")
@ComponentScan(basePackages = "org.github.bbaron.springcfg.example.webapp.model")
@PropertySource({"classpath:META-INF/spring/database.properties", "classpath:META-INF/spring/hibernate.properties"})
public class MainCfg {

}
