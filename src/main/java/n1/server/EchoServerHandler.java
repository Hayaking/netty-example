package n1.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author haya
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println( "Server received: " + in.toString( CharsetUtil.UTF_8 ) );
        context.write( in );
        context.flush();
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext context) {
//        context.writeAndFlush( Unpooled.EMPTY_BUFFER )
//                .addListener( ChannelFutureListener.CLOSE );
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
