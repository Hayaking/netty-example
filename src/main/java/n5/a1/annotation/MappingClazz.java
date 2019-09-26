package n5.a1.annotation;

import java.lang.annotation.*;

/**
 * @author haya
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappingClazz {
    String namespace() default "/";
}
