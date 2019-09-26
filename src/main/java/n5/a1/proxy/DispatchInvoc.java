package n5.a1.proxy;

import n5.a1.chain.Chain;
import n5.a1.dispatch.Dispatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author haya
 */
public class DispatchInvoc implements InvocationHandler {
    private Dispatch dispatch;
    private Chain chain;

    public DispatchInvoc(Dispatch dispatch, Chain chain) {
        this.dispatch = dispatch;
        this.chain = chain;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return chain.before( proxy, method, args, dispatch );
    }
}
