package org.github.bbaron.springcfg.example.webapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(DataSourceCfg.class)
@ImportResource("classpath:META-INF/spring/applicationContext-jpa.xml")
@ComponentScan(basePackages = "org.github.bbaron.springcfg.example.webapp.model")
public class MainCfg {

}
