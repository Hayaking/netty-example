package n1.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author haya
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }


    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ChannelFuture future = bootstrap
                    .group( boss, worker )
                    // 指定channel类
                    .channel( NioServerSocketChannel.class )
                    // 绑定套接字
                    .localAddress( new InetSocketAddress( port ) )
                    // 创建ChannelInitializer
                    .childHandler( new CustomServerInitializer( serverHandler ) )
                    .bind()
                    .sync();
            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully().sync();
            boss.shutdownGracefully().sync();
        }
    }
}
