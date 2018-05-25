package demo.helloword;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.printf("服务端接受到消息："+buf.toString(CharsetUtil.UTF_8)+"\r\n");

        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String response = "服务端响应,并返回客户端发送的消息-"+new String(req,"utf-8");
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes("utf-8")));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("\r\n服务端处理完成".getBytes("utf-8")));
    }
}
