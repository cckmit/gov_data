package com.cloud.gds.gmsanalyse.service.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


/**
 * 修改当ApplicationContext关机时，一次性bean的销毁顺序，防止不必要的异常
 *
 * @Author : yaonuan
 * @Email : 1042703214@qq.com
 * @Date : 2019-04-29
 */
@Component
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition bd = beanFactory.getBeanDefinition("feignContext");
		bd.setDependsOn("eurekaServiceRegistry", "inetUtils");
	}

}
