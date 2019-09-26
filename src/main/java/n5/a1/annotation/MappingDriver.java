package n5.a1.annotation;

import n5.a1.util.Utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author haya
 */
public class MappingDriver {

    public static final Map<String, Class> CLASS_POLL = new HashMap<>();
    public static final Map<String, Map<String,Method>> METHOD_POLL = new HashMap<>();


    public void start() throws IOException {
        Set<Class<?>> set = Utils.getClasses( "n5.netty.server.subhandle" );
        for (Class item : set) {
            // 类是否包含注解
            if (!item.isAnnotationPresent( MappingClazz.class )) {
                continue;
            }
            // 获取注解
            MappingClazz clazzAnnotation = (MappingClazz) item.getAnnotation( MappingClazz.class );
            // namespace : clazz
            CLASS_POLL.put( clazzAnnotation.namespace(), item );
            if (! METHOD_POLL.containsKey( clazzAnnotation.namespace() )){
                METHOD_POLL.put( clazzAnnotation.namespace(), new HashMap<>(5) );
            }
            // 获取类的方法
            Method[] methods = item.getMethods();
            // 遍历方法
            for (Method methodItem : methods) {
                // 方法是否包含注解
                if (methodItem.isAnnotationPresent( MappingMethod.class )) {
                    MappingMethod methodAnnotation = methodItem.getAnnotation( MappingMethod.class );
                    // path : method
                    METHOD_POLL.get( clazzAnnotation.namespace() )
                            .put( methodAnnotation.path(), methodItem );
                }
            }
        }
    }
}
