package fim.server;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelHolder {
    private static final Map<Integer, Channel> userChannelMap = new ConcurrentHashMap<>();
    private static final Map<Channel, Integer> channelUserMap = new ConcurrentHashMap<>();

    public static void add(Integer userId, Channel channel) {
        userChannelMap.put(userId, channel);
        channelUserMap.put(channel, userId);
    }

    public static void remove(Channel channel) {
        Integer userId = channelUserMap.remove(channel);
        if (null != userId) {
            userChannelMap.remove(userId);
        }
    }

    public static Channel get(Integer userId) {
        return userChannelMap.get(userId);
    }
}
