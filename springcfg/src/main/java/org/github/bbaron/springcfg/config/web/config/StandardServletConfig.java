package org.github.bbaron.springcfg.config.web.config;

import javax.servlet.ServletContext;

import org.github.bbaron.springcfg.config.BootstrapConfig;
import org.github.bbaron.springcfg.config.Config;
import org.github.bbaron.springcfg.config.DeployedProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.ServletContextAware;


@Configuration
@Import(BootstrapConfig.class)
@DeployedProfile("web")
public class StandardServletConfig implements Config, ServletContextAware {

	private ServletContext ctx;

	@Override
	@Bean
	public String getApplicationName() {
		return ctx.getContextPath();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.ctx = servletContext;
	}

}
