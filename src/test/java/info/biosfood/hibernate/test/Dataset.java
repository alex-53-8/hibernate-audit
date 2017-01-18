package info.biosfood.hibernate.test;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Dataset {
    String file();
    String database() default "DBNAME";
    boolean useImMemoryServer() default true;
    String sessionFactoryBean() default "sessionFactory";
}
