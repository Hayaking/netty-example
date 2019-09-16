package n2.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author haya
 */
public class CustomServerInitializer extends ChannelInitializer {
    private ChannelInboundHandlerAdapter handlerAdapter;

    public CustomServerInitializer(ChannelInboundHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast( "httpServerCodec", new HttpServerCodec() );
        channel.pipeline().addLast( handlerAdapter );
    }
}
