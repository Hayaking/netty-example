package n5.a1.chain;

import java.lang.reflect.Method;

/**
 * @author haya
 */
public class ChainImpl extends Chain {

    @Override
    public Object around(Object proxy, Method method, Object[] args, Object target) throws Exception {
        if (this.next == null) {
            return method.invoke( target, args );
        }
        return this.next.around( proxy, method, args, target );
    }

    @Override
    public void after(Object proxy, Method method, Object[] args, Object target) throws Exception {
        System.out.println("after");
    }
}
