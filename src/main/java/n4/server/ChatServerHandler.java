package n4.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author haya
 */
@Sharable
public class ChatServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            IdleStateEvent idleState = (IdleStateEvent) event;
            switch (idleState.state()) {
                case ALL_IDLE:
                    System.out.println("读写空闲");
                    break;
                case READER_IDLE:
                    System.out.println("写空闲");
                    break;
                case WRITER_IDLE:
                    System.out.println("读空闲");
                    break;
                default:
                    break;
            }
        }
        context.channel().close();
    }
}
