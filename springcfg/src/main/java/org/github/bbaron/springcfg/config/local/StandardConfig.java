package org.github.bbaron.springcfg.config.local;

import org.github.bbaron.springcfg.config.BootstrapConfig;
import org.github.bbaron.springcfg.config.Springcfg;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;


@Configuration
@Import(BootstrapConfig.class)
@Profile("deployed")
public class StandardConfig implements Springcfg, EnvironmentAware  {

	private Environment env;
	
	public StandardConfig() {
	}

	public StandardConfig(Environment env) {
		this.env = env;
	}

	@Override
	@Bean
	public String getApplicationName() {
		return env.getProperty(APP_NAME_PROPERTY_NAME, APP_NAME_DEFAULT);
	}

	@Override
	public void setEnvironment(Environment env) {
		this.env = env;
	}

}
