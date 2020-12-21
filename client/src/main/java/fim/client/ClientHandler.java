package fim.client;

import fim.protocol.Im;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        Im.Message message = (Im.Message) msg;
        Im.Message.DataType dataType = message.getDataType();
        if (Im.Message.DataType.MessageRequestType.equals(dataType)) {
            Im.MessageRequest request = message.getMessageRequest();
            System.out.println(request.getSenderId() + ": " + request.getContent());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端异常");
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
        }
        ctx.close();
    }
}
