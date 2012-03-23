package org.github.bbaron.springcfg.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class CustomWebApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void initialize(ConfigurableWebApplicationContext ctx) {
		logger.info("initialize " + ctx);
		for (PropertySource<?> ps : ctx.getEnvironment().getPropertySources()) {
			logger.info("property source: " + ps.getName() + " " + ps.getClass());
		}
			
	}

}
