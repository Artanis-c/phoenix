package src.annotation;

/*
@author: tom.cui
@date 2020/4/16
@description: 
*/

import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import src.orm.config.OrmConfig;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(OrmConfig.class)
@EnableSwagger2
public @interface EnableFramework {
}
