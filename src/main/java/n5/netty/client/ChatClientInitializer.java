package n5.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author haya
 */
public class ChatClientInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) {
        channel
                .pipeline()
                .addLast( new ObjectDecoder( 1024 * 1024, ClassResolvers.weakCachingConcurrentResolver( this.getClass().getClassLoader() ) ) )
                .addLast( new ObjectEncoder() )
                .addLast( new ChatClientHandler() );
    }
}
