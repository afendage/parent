package demo.helloword;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
  *  对于 ChannelOption.SO_BACKLOG的解释：
     服务器端 TCP 内核模块维护两个队列，我们可以称之为 A,B 吧
     客户端想服务端 connection 的时候，会发送带有 SYN 的标志包（第一次握手）
     服务端收到客户端发来的 SYN 时，想客户端发送 SYN ACK 确认（第二次握手）
     此时 TCP 内核模块把 客户端 链接加入到 A 队列中，然后服务器收到客户端发送来的 ACK 时（第三次握手）
     TCP 内核模块把 客户端 链接从 A 队列转移到 B 队列，链接完成，应用程序的 accept 会返回。
     也就是说 accept 从 B 队列中取出完成了三次握手的链接。
     A 队列和 B 队列的长度只和时 backlog . 当 A,B 队列的长度之和大于 backlog 时，新连接将会被 TCP 内核拒绝。
     所以，如果 backlog 过小， 可能会出现 accept 速度跟不上，A，B队列满了，导致新的客户端无法链接。
     要注意的是：backlog 对程序支持的链接数并无影响，backlog 影响的只是还没有被 accept 取出的链接。
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
