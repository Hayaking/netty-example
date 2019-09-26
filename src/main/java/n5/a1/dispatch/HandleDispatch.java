package n5.a1.dispatch;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import n5.a1.annotation.MappingDriver;
import n5.netty.common.Message;

import java.lang.reflect.Method;

/**
 * @author haya
 */
public class HandleDispatch implements Dispatch {
    @Override
    public Object dispatch(ChannelGroup channelGroup, ChannelHandlerContext context, Message message) {
        try {
            if (MappingDriver.CLASS_POLL.containsKey( message.getNamespace() )) {
                Class clazz = MappingDriver.CLASS_POLL.get( message.getNamespace() );
                Method method = MappingDriver.METHOD_POLL
                        .get( message.getNamespace() )
                        .get( message.getPath() );
                return method.invoke( clazz.newInstance(), channelGroup, context, message );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println( MappingDriver.CLASS_POLL.containsKey( message.getNamespace() ) );
        return false;
    }
}
