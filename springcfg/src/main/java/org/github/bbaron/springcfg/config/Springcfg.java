package org.github.bbaron.springcfg.config;


public interface Springcfg {
	public static final String APP_CFG_PROPERTY_NAME = "springcfg.app.cfg";
	public static final String APP_BASE_PROPERTY_NAME = "springcfg.app.base";
	public static final String APP_NAME_PROPERTY_NAME = "springcfg.app.name";
	public static final String APP_NAME_DEFAULT = "local";
	String getApplicationName();
}
