package n5.a1.proxy;


import n5.a1.chain.Chain;
import n5.a1.dispatch.Dispatch;

import java.lang.reflect.Proxy;

/**
 * @author haya
 */
public class DispatchProxy {
    public Dispatch getDispatch(Dispatch dispatch, Chain chain) {
        return (Dispatch) Proxy.newProxyInstance(
                dispatch.getClass().getClassLoader() ,
                dispatch.getClass().getInterfaces(),
                new DispatchInvoc( dispatch,chain )
        );
    }
}
