package n5.a1.annotation;

import java.lang.annotation.*;

/**
 * @author haya
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappingMethod {
    String path();
}
