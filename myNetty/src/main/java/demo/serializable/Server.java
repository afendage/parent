package demo.serializable;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.marshalling.MarshallingDecoder;

/**
 * 编码/解码传输对象+文件
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
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
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
