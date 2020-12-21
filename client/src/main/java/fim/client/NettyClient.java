package fim.client;

import fim.protocol.Im;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.Scanner;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class);
        // 设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 设置禁用nagle算法
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 设置连接超时时长
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);
        // 设置初始化Channel
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                pipeline.addLast(new ProtobufDecoder(Im.Message.getDefaultInstance()));
                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast(new ProtobufEncoder());
                pipeline.addLast(new ClientHandler());
                pipeline.addLast(new HeartbeatHandler());
            }
        });
        Channel channel = null;
        try {
            channel = bootstrap.connect("127.0.0.1", 1313).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        int senderId = Integer.parseInt(args[0]);
        int receiverId = Integer.parseInt(args[1]);
        Im.Message.Builder loginRequest = Im.Message.newBuilder().setDataType(Im.Message.DataType.LoginRequestType).setLoginRequest(Im.LoginRequest.newBuilder().setUserId(senderId));
        channel.writeAndFlush(loginRequest);
        while (true) {
            String line = scanner.nextLine();
            Im.Message.Builder request = Im.Message.newBuilder().setDataType(Im.Message.DataType.MessageRequestType).setMessageRequest(Im.MessageRequest.newBuilder().setSenderId(senderId).setReceiverId(receiverId).setMessageType(1).setContent(line));
            channel.writeAndFlush(request);
        }
    }
}
