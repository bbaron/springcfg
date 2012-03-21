package cfg.web.config;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.ServletContextAware;

import cfg.config.BootstrapConfig;
import cfg.config.Config;
import cfg.config.DeployedProfile;

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
