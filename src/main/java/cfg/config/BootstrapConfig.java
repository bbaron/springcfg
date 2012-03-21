package cfg.config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

@Configuration
@Profile("deployed")
public class BootstrapConfig {
	public static final String APP_CFG_PARAM_NAME = "app.cfg";
	public static final String APP_BASE_PARAM_NAME = "app.base";
	private static final String CONFIG_ROOT = "/opt/enterra";
	private static final String LOCATION_PATTERN_FMT = "file:%s/*.properties";
	private static final Logger logger = LoggerFactory.getLogger(BootstrapConfig.class);

	@Bean
	public static PropertySourcesPlaceholderConfigurer applicationPlaceholderConfigurer(ApplicationContext ctx, Config cfg) {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		String appCfgLocation = cfg.getApplicationName();
		logger.info("App Name = " + appCfgLocation);
		String appBase = ctx.getEnvironment().getProperty(APP_BASE_PARAM_NAME, CONFIG_ROOT);
		
		File appCfgPath = new File(appBase, appCfgLocation);
		logger.info("appCfgPath = " + appCfgPath.getAbsolutePath());
		if ((appCfgPath.canRead() && appCfgPath.isDirectory()) == false) {
			logger.warn(appCfgPath + " is not a readable directory. Application will not be properly configured.");
			return pspc;
		}
		try {
			String locationPattern = String.format(LOCATION_PATTERN_FMT, appCfgPath.getAbsolutePath());
			Resource[] resources = ctx.getResources(locationPattern);
			logger.info("property resources found " + Arrays.toString(resources));
			pspc.setLocations(resources);
			pspc.setOrder(0);
		} catch (IOException e) {
			logger.warn("IOException processing config in " + appCfgPath + ": " + e, e);
		}
		return pspc;
	}


}
