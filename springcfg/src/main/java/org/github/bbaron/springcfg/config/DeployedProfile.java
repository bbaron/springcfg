package org.github.bbaron.springcfg.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Profile("deployed")
public @interface DeployedProfile {
	/**
	 * The set of profiles for which this component should be registered.
	 */
	String[] value() default "";
}
