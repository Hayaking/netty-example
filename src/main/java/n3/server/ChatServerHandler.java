package n3.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author haya
 */
@Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE );

    @Override
    protected void channelRead0(ChannelHandlerContext context, String msg) {
        Channel channel = context.channel();
        channelGroup.forEach( item->{
            if (item != channel) {
                item.writeAndFlush( "other:" + channel.remoteAddress() + ", " + msg );
            } else {
                item.writeAndFlush( "self:"  + msg );
            }
        } );
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
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println( "active: " + channel.remoteAddress() );
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println( "inactive: " + channel.remoteAddress() );
    }


}
