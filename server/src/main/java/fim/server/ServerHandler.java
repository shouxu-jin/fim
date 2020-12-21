package fim.server;

import fim.protocol.Im;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        Im.Message message = (Im.Message) msg;
        System.out.println(message);
        Im.Message.DataType dataType = message.getDataType();
        if (dataType == Im.Message.DataType.LoginRequestType) {
            Im.LoginRequest loginRequest = message.getLoginRequest();
            int userId = loginRequest.getUserId();
            ChannelHolder.add(userId, ctx.channel());
        }
        if (dataType == Im.Message.DataType.MessageRequestType) {
            Im.MessageRequest request = message.getMessageRequest();
            int senderId = request.getSenderId();
            int receiverId = request.getReceiverId();
            Channel channel = ChannelHolder.get(receiverId);
            if (null != channel) {
                channel.writeAndFlush(Im.Message.newBuilder().setDataType(Im.Message.DataType.MessageRequestType).setMessageRequest(Im.MessageRequest.newBuilder().setSenderId(senderId).setReceiverId(receiverId).setMessageType(1).setContent(request.getContent())));
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
}
