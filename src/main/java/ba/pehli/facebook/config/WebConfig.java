package ba.pehli.facebook.config;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import net.sf.jasperreports.engine.JasperReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import ba.pehli.facebook.interceptor.GlobalAttributesInterceptor;
import ba.pehli.facebook.service.UserService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="ba.pehli.facebook.controller")
public class WebConfig extends WebMvcConfigurerAdapter{
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/res/**").addResourceLocations("/static/").setCacheControl(CacheControl.maxAge(10, TimeUnit.DAYS));
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		registry.addInterceptor(lci).addPathPatterns("/**");
		
		registry.addInterceptor(globalAttributesInterceptor()).addPathPatterns("/**")
			.excludePathPatterns("/login","/register");
	}
	
	@Bean 
	public GlobalAttributesInterceptor globalAttributesInterceptor(){
		return new GlobalAttributesInterceptor();
	}
	
	@Bean
	public ViewResolver beanNameViewResolver(){
		BeanNameViewResolver res = new BeanNameViewResolver();
		res.setOrder(0);
		return res;
	}
	
	@Bean
	public ViewResolver urlBasedViewResolver(){
		UrlBasedViewResolver res = new UrlBasedViewResolver();
		res.setViewClass(TilesView.class);
		res.setOrder(1);
		return res;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer conf = new TilesConfigurer();
		conf.setDefinitions("/WEB-INF/layouts/layouts.xml");
		return conf;
	}
	
	@Bean 
	public LocalValidatorFactoryBean validator(){
		return new LocalValidatorFactoryBean();
	}
	
	@Bean 
	public LocaleResolver localeResolver(){
		CookieLocaleResolver res = new CookieLocaleResolver();
		res.setCookieName("userLanguage");
		res.setCookieMaxAge(-1);
		return res;
	}
	
	@Bean 
	public JasperReportsPdfView postReport(){
		JasperReportsPdfView rep = new JasperReportsPdfView();
		rep.setReportDataKey("dataSource");
		//rep.setSubReportDataKeys("subReportDataSource");
		
		rep.setUrl("/WEB-INF/reports/post-report.jrxml");
		Properties props = new Properties();
		props.setProperty("subReport", "/WEB-INF/reports/post-subreport.jrxml");
		rep.setSubReportUrls(props);
		
		return rep;
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}
	
}

