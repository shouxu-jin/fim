package im.protocol;

/**
 * 接收端发给IM服务器的ack
 */
public class AckRequest {
    private int senderId;
    private long messageId;

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
