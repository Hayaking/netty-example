package n5.a1.chain;

import java.lang.reflect.Method;

/**
 * @author haya
 */
public abstract class Chain {
    protected Chain next;

    public Object before(Object proxy, Method method, Object[] args, Object target) throws Exception {
        if (this.next == null) {
            return this.around( proxy, method, args, target );
        }
        return nextChain( proxy, method, args, target );
    }

    abstract Object around(Object proxy, Method method, Object[] args, Object target) throws Exception;

    abstract void after(Object proxy, Method method, Object[] args, Object target) throws Exception;

    Object nextChain(Object proxy, Method method, Object[] args, Object target) throws Exception {
        return this.next.before( proxy, method, args, target );
    }

    public Chain getNext() {
        return next;
    }

    public void setNext(Chain next) {
        this.next = next;
    }
}
