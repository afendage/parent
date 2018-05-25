package demo.serializable;

import com.common.GzipUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Client {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(); // 配置一个网络通信的 group
        Bootstrap bootstrap = new Bootstrap();          // 创建辅助工具类，用于服务器通道的一系列配置
        bootstrap.group(group)                          // 绑定指定 group
                .channel(NioSocketChannel.class)                // 指定NIO的模型
                .handler(new ChannelInitializer<SocketChannel>() {  // 客户端 handler, 服务端写 childHandler
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        ch.pipeline().addLast(new ClientHandler()); //配置具体的数据方法处理
                    }
                });

        try {
            // 异步请求到指定服务端
            ChannelFuture future = bootstrap.connect("127.0.0.1",8084).sync();

            //发送消息
            Req req = new Req();
            req.setId(1+"");
            req.setName("finger");
            req.setRequestMessage("client message");

            // 文件
            try {
                URL url = GzipUtils.class.getClassLoader().getResource("006.jpg");
                File file = new File(url.getFile());
                FileInputStream in = new FileInputStream(file);
                byte[] data = new byte[in.available()];
                in.read(data);
                in.close();
                req.setAttachement(GzipUtils.gzip(data));
            } catch (Exception e) {
                e.printStackTrace();
            }


            future.channel().writeAndFlush(req);

            future.channel().closeFuture().sync();  //等待关闭（服务端处理完成，客户单得到回调信息后）
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
