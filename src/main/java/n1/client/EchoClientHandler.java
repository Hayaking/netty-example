package n1.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import static io.netty.channel.ChannelHandler.Sharable;

/**
 * @author haya
 */
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext context) {
        context.writeAndFlush( Unpooled.copiedBuffer( "Netty rocks!", CharsetUtil.UTF_8 ) );
    }

    @Override
    public void channelRead0(ChannelHandlerContext context, ByteBuf in) {
        System.out.println( "Client received: " + in.toString( CharsetUtil.UTF_8 ) );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
