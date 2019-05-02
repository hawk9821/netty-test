package com.hawk.netty4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author zhangdonghao
 * @date 2019/4/30
 */
public class Server {
    private static final int port = 7777;
    private static EventLoopGroup group = new NioEventLoopGroup();
    private static ServerBootstrap server = new ServerBootstrap();

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public static void main(String[] args) {
        try {
            server.group(group);
            server.channel(NioServerSocketChannel.class);
            server.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    // 以("\n")为结尾分割的 解码器
                    pipeline.addLast("frame",new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
                    // 解码和编码，应和客户端一致
                    pipeline.addLast("decoder",new StringDecoder());
                    pipeline.addLast("encoder",new StringEncoder());
                    // 服务端业务逻辑
                    pipeline.addLast("handler",new ServerHandler());
                }
            });
            //服务器绑定端口监听
            ChannelFuture future = server.bind(port).sync();
            System.out.println("服务端启动成功...");
            // 监听服务器关闭监听
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
