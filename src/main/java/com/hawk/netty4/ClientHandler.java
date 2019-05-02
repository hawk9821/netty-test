package com.hawk.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.Delimiters;

/**
 * @author zhangdonghao
 * @date 2019/4/30
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("客户端接受的消息: " + msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
//        for (int i = 0 ; i < 100;i++){
//            ctx.writeAndFlush("hello world!" +Delimiters.lineDelimiter());
//        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("正在连接... ");
        super.channelActive(ctx);
        String msg = "hello world!";
        for (int i = 0; i < 20; i++) {
            ctx.writeAndFlush(i + "      " +msg + "\n");
        }
//        ctx.writeAndFlush("quit\n");
    }
        @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接关闭! ");
        super.channelInactive(ctx);
    }
}
