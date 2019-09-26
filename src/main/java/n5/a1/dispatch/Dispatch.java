package n5.a1.dispatch;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import n5.netty.common.Message;

/**
 * @author haya
 */
public interface Dispatch {
    Object dispatch(ChannelGroup channelGroup, ChannelHandlerContext context, Message message);

}
