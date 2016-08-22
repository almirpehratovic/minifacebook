package ba.pehli.facebook.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="ba.pehli.facebook.repository", entityManagerFactoryRef="emf",transactionManagerRef="transactionManager")
@ComponentScan(basePackages="ba.pehli.facebook.service")
@Import(SecurityConfig.class)
public class ApplicationConfig {
	@Bean
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
				.setName("dataSource")
				.addScript("db/schema.sql")
				.addScript("db/data.sql")
				.build();
		return db;
	}
	
	@Bean(initMethod="start",destroyMethod="stop")
	public Server h2Server() throws SQLException{
		Server server = Server.createWebServer("-web","-webAllowOthers","-webPort","8082");
		return server;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource){
		LocalContainerEntityManagerFactoryBean b = new LocalContainerEntityManagerFactoryBean();
		b.setDataSource(dataSource);
		b.setPackagesToScan("ba.pehli.facebook.domain");
		b.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		props.put("hibernate.max_fetch_depth", "3");
		props.put("hibernate.jdbc.fetch_size", "50");
		props.put("hibernate.jdbc.batch_size", "10");
		props.put("hibernate.show_sql", "false");
		
		b.setJpaProperties(props);
		
		return b;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager man = new JpaTransactionManager(emf);
		return man;
	}
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.addBasenames("/WEB-INF/lang/messages");
		return ms;
	}
}
