package demo.serializable;

import com.common.GzipUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileOutputStream;

public class ServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Req req = (Req) msg;
        byte[] attachement = GzipUtils.ungzip(req.getAttachement());
        String writePath = System.getProperty("user.dir") +File.separatorChar+"myNetty"+ File.separatorChar + "receive" + File.separatorChar + "006_1.jpg";
        FileOutputStream fos = new FileOutputStream(writePath);
        fos.write(attachement);
        fos.close();

        System.out.printf("Server:"+req.getId()+", name:"+req.getName()+", msg:"+req.getRequestMessage());
        req.setRequestMessage("server msg");
        ctx.writeAndFlush(req);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("\r\n服务端处理完成".getBytes("utf-8")));
    }
}
