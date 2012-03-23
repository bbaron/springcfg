package org.github.bbaron.springcfg.config.local;

import org.github.bbaron.springcfg.config.BootstrapConfig;
import org.github.bbaron.springcfg.config.Config;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


@Configuration
@Import(BootstrapConfig.class)
@Profile("deployed")
public class StandardConfig implements Config, ApplicationContextAware  {

	private ApplicationContext ctx;
	
	@Override
	@Bean
	public String getApplicationName() {
		return ctx.getEnvironment().getProperty("app.name", "local");
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
