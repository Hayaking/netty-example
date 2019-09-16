package n3.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author haya
 */
public class ChatClientInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) {
        channel
                .pipeline()
                .addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ) )
                .addLast( new LengthFieldPrepender( 4 ) )
                .addLast( new StringDecoder( CharsetUtil.UTF_8 ) )
                .addLast( new StringEncoder( CharsetUtil.UTF_8 ) )
                .addLast( new ChatClientHandler() );
    }
}
