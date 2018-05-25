package demo.frameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * TCP粘包/分包的原因：
 * 应用程序写入的字节大小大于套接字发送缓冲区的大小，会发生拆包现象。
 * 而应用程序写入数据小于套接字缓冲区大小，网卡将应用多次写入的数据发送到网络上，这将会发生粘包现象。
 * 进行MSS大小的TCP分段，当TCP报文长度-TCP头部长度>MSS的时候将发生拆包。
 * 以太网帧的payload（净荷）大于MTU（1500字节）进行ip分片。
 * 三种解决方法：
 * 消息定长：FixedLengthFrameDecoder类,不足指定长度的会出现丢包（可以讲不够的长度加 空格）
 * 包尾增加特殊字符分割：行分隔符类：LineBasedFrameDecoder或自定义分隔符类 ：DelimiterBasedFrameDecoder
 * 将消息分为消息头和消息体：LengthFieldBasedFrameDecoder类。分为有头部的拆包与粘包、长度字段在前且有头部的拆包与粘包、多扩展头部的拆包与粘包。
 */
public class Server {

    public static void main(String[] args) {

        EventLoopGroup pGroup = new NioEventLoopGroup(); //用于处理服务端接受客户端的链接
        EventLoopGroup cGroup = new NioEventLoopGroup(); //进行网路通信（网络读写的）
        ServerBootstrap bootstrap = new ServerBootstrap(); //创建辅助工具类，用于服务器通道的一系列配置
        bootstrap.group(pGroup,cGroup)                      //绑定两个线程组
                .channel(NioServerSocketChannel.class)      //指定NIO的模型
                .option(ChannelOption.SO_BACKLOG.SO_BACKLOG,1024)//制定TCP 的缓冲区
                .option(ChannelOption.SO_BACKLOG.SO_SNDBUF,32*1024)//发送的缓冲大小
                .option(ChannelOption.SO_RCVBUF,32*1024)    //接受的缓冲大小
                .option(ChannelOption.SO_KEEPALIVE,true)    //保持链接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //ch.pipeline().addLast(new FixedLengthFrameDecoder(5));//定长解码类
                        ByteBuf buf = Unpooled.copiedBuffer("$".getBytes());
                        // 自定义分隔符类
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ServerHandler()); //配置具体的数据接受方法处理
                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(8084).sync(); //进行绑定
            future.channel().closeFuture().sync();                      //等待关闭
            pGroup.shutdownGracefully();
            cGroup.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
