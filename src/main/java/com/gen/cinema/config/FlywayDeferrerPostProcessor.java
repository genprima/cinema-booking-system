package com.gen.cinema.config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import java.util.stream.Stream;

@Component
public class FlywayDeferrerPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory != null) {
            Stream.of(beanFactory.getBeanNamesForType(javax.sql.DataSource.class))
                .forEach(name -> excludeDependsOnFlywayFromBean(beanFactory, name));
            excludeDependsOnFlywayFromBean(beanFactory, "entityManagerFactory");
        }
    }

    private void excludeDependsOnFlywayFromBean(ConfigurableListableBeanFactory beanFactory, String beanName) {
        if (beanFactory != null && beanName != null) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String[] dependsOn = beanDefinition.getDependsOn();
            if (dependsOn != null && dependsOn.length > 0) {
                beanDefinition.setDependsOn(Stream.of(dependsOn)
                    .filter(name -> !name.equals("flyway"))
                    .toArray(String[]::new));
            }
        }
    }
} 