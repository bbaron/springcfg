package cfg.config.local;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import cfg.config.BootstrapConfig;
import cfg.config.Config;

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
