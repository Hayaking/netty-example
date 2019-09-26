package n5.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import n5.a1.annotation.MappingDriver;
import n5.a1.chain.ChainImpl;
import n5.a1.dispatch.Dispatch;
import n5.a1.dispatch.HandleDispatch;
import n5.a1.proxy.DispatchProxy;
import n5.netty.common.Message;

import java.io.IOException;

/**
 * @author haya
 */
@Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<Message> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE );
    static {
        try {
            new MappingDriver().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void channelRead0(ChannelHandlerContext context, Message msg) {
        System.out.println(msg);
        Dispatch dispatch = new DispatchProxy().getDispatch( new HandleDispatch(), new ChainImpl() );
        dispatch.dispatch( channelGroup, context, msg );
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
