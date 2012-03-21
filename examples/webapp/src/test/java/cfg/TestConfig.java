package cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cfg.config.local.StandardConfig;
import cfg.model.TestBean;

@Configuration
@Import(StandardConfig.class)
public class TestConfig {

	@Value("${prop1}")
	private String prop1;
	@Value("${prop2}")
	private String prop2;

	@Bean
	public TestBean testBean() {
		return new TestBean(prop1, prop2);
	}
}
