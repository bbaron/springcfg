package cfg.web;

import static org.junit.Assert.*;
import static org.springframework.web.context.ContextLoader.*;

import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import cfg.model.TestBean;

public class CustomXmlWebApplicationContextTest {

	@Test
	public void testInstantiation() throws Exception {
		ContextLoaderListener cll = new ContextLoaderListener();
		MockServletContext sc = new MockServletContext();
		sc.setContextPath("/ctx");
		sc.addInitParameter(CONTEXT_INITIALIZER_CLASSES_PARAM, CustomWebApplicationContextInitializer.class.getName());
		sc.addInitParameter(CONFIG_LOCATION_PARAM, "classpath:/CustomXmlWebApplicationContextTest-context.xml");
		sc.addInitParameter("spring.profiles.active", "deployed,web");
		WebApplicationContext ctx = cll.initWebApplicationContext(sc);
		TestBean actual = ctx.getBean(TestBean.class);
		TestBean optEnterraBean = new TestBean("/opt/enterra");
		assertEquals(optEnterraBean, actual);
	}
}
