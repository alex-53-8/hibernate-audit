package info.biosfood.hibernate.audit.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T get(Class<T> clazz) {
        return (T) context
                .getAutowireCapableBeanFactory()
                .autowire(clazz, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }


}
