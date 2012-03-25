package org.github.bbaron.springcfg.config.local;

import static org.github.bbaron.springcfg.config.Springcfg.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

@RunWith(MockitoJUnitRunner.class)
public class StandardConfigTest {

	@Mock
	private Environment env;
	private StandardConfig sc = new StandardConfig();

	@Before
	public void setUp() {
		sc.setEnvironment(env);
	}

	@Test
	public void appNameIsDeterminedFromTheAppNameEnvProperty() throws Exception {
		when(env.getProperty(eq(APP_NAME_PROPERTY_NAME), anyString())).thenReturn("myapp");
		when(env.getProperty(eq(APP_NAME_PROPERTY_NAME))).thenReturn("myapp");
		assertEquals("myapp", sc.getApplicationName());
	}

}
