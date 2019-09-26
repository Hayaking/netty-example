package n5.a1.handler;

import n5.a1.annotation.MappingClazz;
import n5.a1.annotation.MappingMethod;

/**
 * @author haya
 */
@MappingClazz(namespace = "haya")
public class HayaHandler {
    @MappingMethod(path = "all")
    public Object getAll() {
        return "getAll";
    }

    @MappingMethod(path = "one")
    public Object getOne() {
        return "getOne";
    }
}
