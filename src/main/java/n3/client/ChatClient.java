package n3.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import n3.server.ChatServerInitializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

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
                String str;
                do {
                    str = buf.readLine();
                    channel.writeAndFlush( str +"\r\n");
                } while (!"exit".equals( str ));
            }
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
