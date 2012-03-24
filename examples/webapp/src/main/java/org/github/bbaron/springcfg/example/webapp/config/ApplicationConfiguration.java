package org.github.bbaron.springcfg.example.webapp.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.github.bbaron.springcfg.example.webapp.model.Models;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackageClasses = Models.class)
@Import(DataSourceCfg.class)
@ImportResource("classpath:META-INF/spring/applicationContext-jpa.xml")
public @interface ApplicationConfiguration {
	/**
	 * Explicitly specify the name of the Spring bean definition associated
	 * with this Configuration class.  If left unspecified (the common case),
	 * a bean name will be automatically generated.
	 *
	 * <p>The custom name applies only if the Configuration class is picked up via
	 * component scanning or supplied directly to a {@link AnnotationConfigApplicationContext}.
	 * If the Configuration class is registered as a traditional XML bean definition,
	 * the name/id of the bean element will take precedence.
	 *
	 * @return the specified bean name, if any
	 * @see org.springframework.beans.factory.support.DefaultBeanNameGenerator
	 */
	String value() default "";

}
