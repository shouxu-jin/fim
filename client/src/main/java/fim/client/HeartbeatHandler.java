package fim.client;

import fim.protocol.Im;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channel = ctx.channel();
        sendHeartbeat(channel);
    }

    private void sendHeartbeat(Channel channel) {
        ScheduledFuture<?> future = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                channel.writeAndFlush(Im.Message.newBuilder().setDataType(Im.Message.DataType.HeartbeatType).setHeartbeat(Im.Heartbeat.getDefaultInstance()));
            } else {
                channel.closeFuture();
                throw new RuntimeException();
            }
        }, 10, TimeUnit.SECONDS);

        future.addListener((GenericFutureListener) future1 -> {
            if (future1.isSuccess()) {
                sendHeartbeat(channel);
            }
        });
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
