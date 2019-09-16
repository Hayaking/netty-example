package n1.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;

/**
 * @author haya
 */
public class CustomClientInitializer extends ChannelInitializer {
    private ChannelInboundHandlerAdapter handlerAdapter;

    public CustomClientInitializer(ChannelInboundHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast( handlerAdapter );
    }
}
