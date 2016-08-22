package ba.pehli.facebook.config;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class PostProcessor implements BeanPostProcessor{
	Logger logger = Logger.getLogger(PostProcessor.class);

	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		logger.debug("##### > bean " + name + " created!");
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		return bean;
	}

}
