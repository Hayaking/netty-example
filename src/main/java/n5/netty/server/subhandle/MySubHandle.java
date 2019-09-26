package n5.netty.server.subhandle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import n5.a1.annotation.MappingClazz;
import n5.a1.annotation.MappingMethod;
import n5.netty.common.Message;

/**
 * @author haya
 */
@MappingClazz(namespace = "mySubHandle")
public class MySubHandle {

    @MappingMethod(path = "handle")
    public void handle(ChannelGroup channelGroup, ChannelHandlerContext context, Message message) {
        System.out.println("!!!");
        Channel channel = context.channel();
        channelGroup.forEach( item -> {
            if (item != channel) {
                item.writeAndFlush( "other:" + channel.remoteAddress() + ", " + message.getBody() );
            } else {
                item.writeAndFlush( "self:" + message.getBody() );
            }
        } );
    }
}
