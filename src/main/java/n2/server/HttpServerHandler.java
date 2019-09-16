package n2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author haya
 */
@Sharable
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        ByteBuf text = Unpooled.copiedBuffer( "hahahahahhahahahah", CharsetUtil.UTF_8 );
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                text
        );
        response.headers().set( HttpHeaderNames.CONTENT_TYPE, "text/plain" );
        response.headers().set( HttpHeaderNames.CONTENT_LENGTH, text.readableBytes() );
        context.writeAndFlush(response);
        System.out.println("!");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.writeAndFlush( Unpooled.EMPTY_BUFFER )
                .addListener( ChannelFutureListener.CLOSE );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
