package n5.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import n5.netty.common.Message;

/**
 * @author haya
 */
@Sharable
public class ChatClientHandler extends SimpleChannelInboundHandler<Message> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE );

    @Override
    protected void channelRead0(ChannelHandlerContext context, Message message) {
        System.out.println(message);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext context) {
        Channel channel = context.channel();
        channelGroup.writeAndFlush( "server: " + channel.remoteAddress() + ", join!" );
        channelGroup.add( channel );
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext context) {
        Channel channel = context.channel();
        channelGroup.writeAndFlush( "server: " + channel.remoteAddress() + ",removed!" );
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println( "active: " + channel.remoteAddress() );
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println( "inactive: " + channel.remoteAddress() );
    }

}
