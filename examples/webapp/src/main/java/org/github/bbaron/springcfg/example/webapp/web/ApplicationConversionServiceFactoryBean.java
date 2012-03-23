package org.github.bbaron.springcfg.example.webapp.web;

import org.github.bbaron.springcfg.example.webapp.model.AppConfig;
import org.github.bbaron.springcfg.example.webapp.model.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;


@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		// Register application converters and formatters
	}

	@Autowired
    AppConfigRepository appConfigRepository;

	public Converter<AppConfig, String> getAppConfigToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.github.bbaron.springcfg.example.webapp.model.AppConfig, java.lang.String>() {
            public String convert(AppConfig appConfig) {
                return new StringBuilder().append(appConfig.getPropertyName()).append(" ").append(appConfig.getPropertyValue()).toString();
            }
        };
    }

	public Converter<Long, AppConfig> getIdToAppConfigConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.github.bbaron.springcfg.example.webapp.model.AppConfig>() {
            public org.github.bbaron.springcfg.example.webapp.model.AppConfig convert(java.lang.Long id) {
                return appConfigRepository.findOne(id);
            }
        };
    }

	public Converter<String, AppConfig> getStringToAppConfigConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.github.bbaron.springcfg.example.webapp.model.AppConfig>() {
            public org.github.bbaron.springcfg.example.webapp.model.AppConfig convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), AppConfig.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAppConfigToStringConverter());
        registry.addConverter(getIdToAppConfigConverter());
        registry.addConverter(getStringToAppConfigConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
