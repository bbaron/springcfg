package cfg;

import static com.google.common.collect.Maps.*;
import static org.junit.Assert.*;
import static org.springframework.core.env.StandardEnvironment.*;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import cfg.model.TestBean;

import com.google.common.collect.ImmutableMap;

public class DeployedProfileTest {

	@Rule
	public TemporaryConfigFolder sysEnvConfig = new TemporaryConfigFolder("sysenv");
	@Rule
	public TemporaryConfigFolder sysPropConfig = new TemporaryConfigFolder("sysprop");

	@Test
	public void sourceFromSystemEnv() throws Exception {
		sysEnvConfig.saveProperties("envvar1", "envvar2");
		Map<String, ?> extraProperties = ImmutableMap.of(
				"app.cfg", sysEnvConfig.getAppCfg(), 
				"app.base",	sysEnvConfig.getAppBase(), 
				"app.name", sysEnvConfig.getAppName());
		AnnotationConfigApplicationContext ctx = newAnnotationConfigContextLoader(TestConfig.class);
		replacePropertySource(ctx, SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, extraProperties);
		ctx.getEnvironment().setActiveProfiles("deployed");
		ctx.refresh();
		TestBean testBean = ctx.getBean(TestBean.class);
		assertNotNull("Test bean not in app context", testBean);
		TestBean expected = new TestBean("envvar1", "envvar2");

		assertEquals(expected, testBean);
	}

	@Test
	public void sourceFromSystemProperties() throws Exception {
		sysEnvConfig.saveProperties("envvar1", "envvar2");
		Map<String, ?> envProperties = ImmutableMap.of(
				"app.cfg", sysEnvConfig.getAppCfg(), 
				"app.base",	sysEnvConfig.getAppBase(), 
				"app.name", sysEnvConfig.getAppName());
		sysPropConfig.saveProperties("propvar1", "propvar2");
		Map<String, ?> syspropProperties = ImmutableMap.of(
				"app.cfg", sysPropConfig.getAppCfg(), 
				"app.base",	sysPropConfig.getAppBase(), 
				"app.name", sysPropConfig.getAppName());
		AnnotationConfigApplicationContext ctx = newAnnotationConfigContextLoader(TestConfig.class);
		replacePropertySource(ctx, SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, syspropProperties);
		replacePropertySource(ctx, SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, envProperties);
		ctx.getEnvironment().setActiveProfiles("deployed");
		ctx.refresh();
		TestBean testBean = ctx.getBean(TestBean.class);
		assertNotNull("Test bean not in app context", testBean);
		TestBean expected = new TestBean("propvar1", "propvar2");

		assertEquals(expected, testBean);
	}

	private AnnotationConfigApplicationContext newAnnotationConfigContextLoader(Class<?>... classes) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(classes);
		return ctx;
	}

	private void replacePropertySource(AnnotationConfigApplicationContext ctx, String name,
			Map<String, ?> extraProperties) {
		MapPropertySource mps = new MapPropertySource(name, newHashMap(extraProperties));
		ctx.getEnvironment().getPropertySources().replace(name, mps);
	}
}
