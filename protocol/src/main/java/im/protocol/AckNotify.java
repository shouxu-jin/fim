package im.protocol;

/**
 * IM服务器发送给发送端的通知
 */
public class AckNotify {
    private long messageId;
    private int receiverId;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
