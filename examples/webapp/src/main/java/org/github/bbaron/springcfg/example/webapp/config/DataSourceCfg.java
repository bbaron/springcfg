package org.github.bbaron.springcfg.example.webapp.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class DataSourceCfg {

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getProperty("database.driverClassName"));
		ds.setUrl(env.getProperty("database.url"));
		ds.setUsername(env.getProperty("database.username"));
		ds.setPassword(env.getProperty("database.password"));
		ds.setTestOnBorrow(true);
		ds.setTestOnReturn(true);
		ds.setTestWhileIdle(true);
		ds.setTimeBetweenEvictionRunsMillis(1800000);
		ds.setNumTestsPerEvictionRun(3);
		ds.setMinEvictableIdleTimeMillis(1800000);
		ds.setValidationQuery("SELECT 1");
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public Map<String, String> jpaProperties() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		map.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		map.put("hibernate.ejb.naming_strategy", env.getProperty("hibernate.ejb.naming_strategy"));
		map.put("hibernate.connection.charSet", env.getProperty("hibernate.connection.charSet"));
		return map;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName("persistenceUnit");
		emf.setDataSource(dataSource);
		emf.setJpaPropertyMap(jpaProperties());
		return emf;
	}
}
