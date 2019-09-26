package n5.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import n5.netty.common.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author haya
 */
public class ChatClient {
    private final int port;

    public ChatClient(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            Channel channel = bootstrap.group( eventLoopGroup )
                    .channel( NioSocketChannel.class )
                    .handler( new ChatClientInitializer() )
                    .connect( "localhost", port )
                    .sync()
                    .channel();
            try (BufferedReader buf = new BufferedReader( new InputStreamReader( System.in ) )) {
                Message message = null;
                do {
                    message = new Message();
                    message.setNamespace(buf.readLine());
                    message.setPath(buf.readLine());
                    message.setBody(buf.readLine());
                    System.out.println(message);
                    channel.writeAndFlush( message);
                } while (!"exit".equals( message.getBody() ));
            }
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
