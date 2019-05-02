package com.hawk.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Description: 服务端业务逻辑
 * @author zhangdonghao
 * @date 2019/4/30
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    /**
     *收到消息时，返回信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务端接受的消息 : " + msg);
        if("quit".equals(msg.trim())){//服务端断开的条件
            System.out.println("接受断开指定   ");
            ctx.close();
        }
        LocalDateTime now = LocalDateTime.now();
        // 返回客户端消息
        ctx.writeAndFlush(now+"\n");
    }

    /**
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
        ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭连接");
        super.channelInactive(ctx);
    }
}
